package cn.com.dhcc.server.controller;

import cn.com.dhcc.server.common.dto.LicenseDto;
import cn.com.dhcc.server.common.entity.QueryResult;
import cn.com.dhcc.server.common.entity.RequestEntity;
import cn.com.dhcc.server.common.entity.ResponseEntity;
import cn.com.dhcc.server.common.vo.LicenseVo;
import cn.com.dhcc.server.domain.LicenseApply;
import cn.com.dhcc.server.service.LicenseApplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/licenseApply")
public class LicenseApplyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LicenseApplyController.class);

    @Autowired
    private LicenseApplyService licenseApplyService;

    /**
     * LicenseApplyDto的authenticationFile,前端传入Base64编码之后的字节数组
     *
     * @param licenseDto
     * @return
     */
    @PostMapping("/save")
    public ResponseEntity save(@RequestBody LicenseDto licenseDto) {
        try {
            if (licenseDto == null) {
                return new ResponseEntity(false, "请输入授权申请相关信息");
            }
            LicenseApply licenseApply = new LicenseApply();
            BeanUtils.copyProperties(licenseDto, licenseApply);
            licenseApplyService.save(licenseApply);
        } catch (Exception e) {
            LOGGER.error("授权申请保存异常:【{}】", e.toString());
            e.printStackTrace();
            return new ResponseEntity(false, "授权申请保存异常," + e.toString());
        }
        return new ResponseEntity(true, "授权申请保存成功");
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<LicenseApply> findById(@PathVariable("id") Long id) {
        LicenseApply licenseApply;
        try {
            if (id == null || id == 0) {
                return new ResponseEntity<>(false, "id输入有误，请重新输入");
            }
            licenseApply = licenseApplyService.findById(id);
        } catch (Exception e) {
            LOGGER.error("授权申请查询异常:【{}】", e.toString());
            e.printStackTrace();
            return new ResponseEntity<>(false, "授权申请查询异常," + e.toString());
        }
        return new ResponseEntity<>(true, "授权申请查询成功", licenseApply);
    }

    @PostMapping("/findAllByCondition")
    public ResponseEntity<QueryResult<LicenseVo>> findAllByCondition(@RequestBody RequestEntity<LicenseDto> condition) {
        QueryResult<LicenseVo> result = null;
        try {
            if (condition == null) {
                return new ResponseEntity<>(false, "请输入查询条件");
            }
            result = licenseApplyService.findByCondition(condition.getPageCurrent(), condition.getPageSize(), condition.getQuery());
        } catch (Exception e) {
            LOGGER.error("授权申请查询异常:【{}】", e.toString());
            e.printStackTrace();
            return new ResponseEntity<>(false, "授权申请查询异常," + e.toString());
        }
        return new ResponseEntity<>(true, "授权申请查询成功", result);
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestBody LicenseDto licenseDto) {
        try {
            if (licenseDto == null) {
                return new ResponseEntity(false, "更新条件不能为空");
            }
            licenseApplyService.update(licenseDto);
        } catch (Exception e) {
            LOGGER.error("授权申请更新异常:【{}】", e.toString());
            e.printStackTrace();
            return new ResponseEntity<LicenseApply>(false, "授权申请更新异常," + e.toString());
        }
        return new ResponseEntity(true, "授权申请更新成功");
    }

    @GetMapping("/deleteById/{id}")
    public ResponseEntity deleteById(@PathVariable("id") Long id) {
        try {
            if (id == null || id == 0) {
                return new ResponseEntity(false, "id输入有误，请重新输入");
            }
            licenseApplyService.deleteById(id);
        } catch (Exception e) {
            LOGGER.error("授权申请删除异常:【{}】", e.toString());
            e.printStackTrace();
            return new ResponseEntity(false, "授权申请删除异常," + e.toString());
        }
        return new ResponseEntity(true, "授权申请删除成功");
    }
}
