/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cn.com.dhcc.system.modules.security.rest;

import cn.com.dhcc.common.annotation.rest.AnonymousDeleteMapping;
import cn.com.dhcc.common.annotation.rest.AnonymousGetMapping;
import cn.com.dhcc.common.annotation.rest.AnonymousPostMapping;
import cn.com.dhcc.common.config.RsaProperties;
import cn.com.dhcc.common.exception.BadRequestException;
import cn.com.dhcc.common.utils.RedisUtils;
import cn.com.dhcc.common.utils.RsaUtils;
import cn.com.dhcc.common.utils.SecurityUtils;
import cn.com.dhcc.common.utils.StringUtils;
import cn.com.dhcc.system.modules.security.config.bean.LoginCodeEnum;
import cn.com.dhcc.system.modules.security.config.bean.LoginProperties;
import cn.com.dhcc.system.modules.security.config.bean.SecurityProperties;
import cn.com.dhcc.system.modules.security.security.TokenProvider;
import cn.com.dhcc.system.modules.security.service.OnlineUserService;
import cn.com.dhcc.system.modules.security.service.dto.AuthUserDto;
import cn.com.dhcc.system.modules.security.service.dto.JwtUserDto;
import cn.hutool.core.util.IdUtil;
import com.wf.captcha.base.Captcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 * ???????????????token????????????????????????
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Api(tags = "???????????????????????????")
public class AuthorizationController {
    private final SecurityProperties properties;
    private final RedisUtils redisUtils;
    private final OnlineUserService onlineUserService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    @Resource
    private LoginProperties loginProperties;

    @ApiOperation("????????????")
    @AnonymousPostMapping(value = "/login")
    public ResponseEntity<Object> login(@Validated @RequestBody AuthUserDto authUser, HttpServletRequest request) throws Exception {
        // ????????????
        String password = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, authUser.getPassword());
        // ???????????????
        String code = (String) redisUtils.get(authUser.getUuid());
        // ???????????????
        redisUtils.del(authUser.getUuid());
        if (StringUtils.isBlank(code)) {
            throw new BadRequestException("??????????????????????????????");
        }
        if (StringUtils.isBlank(authUser.getCode()) || !authUser.getCode().equalsIgnoreCase(code)) {
            throw new BadRequestException("???????????????");
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authUser.getUsername(), password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // ????????????
        String token = tokenProvider.createToken(authentication);
        final JwtUserDto jwtUserDto = (JwtUserDto) authentication.getPrincipal();
        // ??????????????????
        onlineUserService.save(jwtUserDto, token, request);
        // ?????? token ??? ????????????
        Map<String, Object> authInfo = new HashMap<String, Object>(2) {{
            put("token", properties.getTokenStartWith() + token);
            put("user", jwtUserDto);
        }};
        if (loginProperties.isSingleLogin()) {
            //???????????????????????????token
            onlineUserService.checkLoginOnUser(authUser.getUsername(), token);
        }
        return ResponseEntity.ok(authInfo);
    }

    @ApiOperation("??????????????????")
    @GetMapping(value = "/info")
    public ResponseEntity<Object> getUserInfo() {
        return ResponseEntity.ok(SecurityUtils.getCurrentUser());
    }

    @ApiOperation("???????????????")
    @AnonymousGetMapping(value = "/code")
    public ResponseEntity<Object> getCode() {
        // ?????????????????????
        Captcha captcha = loginProperties.getCaptcha();
        String uuid = properties.getCodeKey() + IdUtil.simpleUUID();
        //????????????????????? arithmetic???????????? >= 2 ??????captcha.text()??????????????????????????????
        String captchaValue = captcha.text();
        if (captcha.getCharType() - 1 == LoginCodeEnum.arithmetic.ordinal() && captchaValue.contains(".")) {
            captchaValue = captchaValue.split("\\.")[0];
        }
        // ??????
        redisUtils.set(uuid, captchaValue, loginProperties.getLoginCode().getExpiration(), TimeUnit.MINUTES);
        // ???????????????
        Map<String, Object> imgResult = new HashMap<String, Object>(2) {{
            put("img", captcha.toBase64());
            put("uuid", uuid);
        }};
        return ResponseEntity.ok(imgResult);
    }

    @ApiOperation("????????????")
    @AnonymousDeleteMapping(value = "/logout")
    public ResponseEntity<Object> logout(HttpServletRequest request) {
        onlineUserService.logout(tokenProvider.getToken(request));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
