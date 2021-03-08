package cn.com.dhcc.server.service.impl;

import cn.com.dhcc.common.utils.SecurityUtils;
import cn.com.dhcc.server.common.dto.LicenseDto;
import cn.com.dhcc.server.common.entity.QueryResult;
import cn.com.dhcc.server.common.vo.LicenseVo;
import cn.com.dhcc.server.domain.LicenseApply;
import cn.com.dhcc.server.domain.LicenseManage;
import cn.com.dhcc.server.enums.AuthorizeStatus;
import cn.com.dhcc.server.enums.AuthorizeType;
import cn.com.dhcc.server.repository.LicenseApplyRepository;
import cn.com.dhcc.server.repository.LicenseManageRepository;
import cn.com.dhcc.server.service.LicenseApplyService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.sql.Timestamp;
import java.util.*;

@Service
public class LicenseApplyServiceImpl implements LicenseApplyService {

    @Autowired
    private LicenseApplyRepository licenseApplyRepository;

    @Autowired
    private LicenseManageRepository licenseManageRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void save(LicenseApply licenseApply) {
        //获取当前时间
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        //获取当前登录人的信息,处理公共字段
        UserDetails user = SecurityUtils.getCurrentUser();
        licenseApply.setCreateBy(user.getUsername());
        licenseApply.setCreateTime(currentTime);
        licenseApply.setUpdatedBy(user.getUsername());
        licenseApply.setUpdateTime(currentTime);
        LicenseApply newLicenseApply = licenseApplyRepository.save(licenseApply);
        //创建licenseManage
        LicenseManage licenseManage = new LicenseManage();
        licenseManage.setLicenseApplyId(newLicenseApply.getId());
        //设置license的状态，为未提交状态
        licenseManage.setAuthorizeStatus(AuthorizeStatus.UN_COMMITTED.getCode());
        licenseManage.setCreateBy(user.getUsername());
        licenseManage.setCreateTime(currentTime);
        licenseManage.setUpdatedBy(user.getUsername());
        licenseManage.setUpdateTime(currentTime);
        licenseManageRepository.save(licenseManage);
    }

    @Override
    public void update(LicenseDto licenseDto) {
        LicenseApply licenseApply = new LicenseApply();
        BeanUtils.copyProperties(licenseDto, licenseApply);
        String currentUsername = SecurityUtils.getCurrentUsername();
        licenseApply.setUpdatedBy(currentUsername);
        //更新时间已有相应注解支持，此处不需要填写
        licenseApplyRepository.save(licenseApply);
    }

    @Override
    public QueryResult<LicenseVo> findByCondition(int page, int pageSize, LicenseDto licenseDto) {
        int limitIndex = (page - 1) * pageSize;
        //查询具体数据
        List<Map<String, Object>> list = licenseApplyRepository.findByCondition(
                licenseDto.getUserName(),
                licenseDto.getProjectName(),
                licenseDto.getApplyProduct(),
                licenseDto.getApplyProductVersion(),
                licenseDto.getResponsibleSale(),
                licenseDto.getAuthorizeStatus(),
                limitIndex, pageSize);
        //查询总数
        long count = licenseApplyRepository.count(licenseDto.getUserName(),
                licenseDto.getProjectName(),
                licenseDto.getApplyProduct(),
                licenseDto.getApplyProductVersion(),
                licenseDto.getResponsibleSale(),
                licenseDto.getAuthorizeStatus());
        //将map转成对象
        LicenseVo licenseVo = null;
        ArrayList<LicenseVo> licenseVoList = new ArrayList<>();
        for (Map<String, Object> map : list) {
            licenseVo = JSON.parseObject(JSON.toJSONString(map), LicenseVo.class);
            licenseVoList.add(licenseVo);
        }
        //封装最终发送的数据格式
        QueryResult<LicenseVo> result = new QueryResult<>();
        result.setList(licenseVoList);
        result.setTotal(count);
        return result;
    }

    @Override
    public LicenseApply findById(Long id) {
        LicenseApply licenseApply = new LicenseApply();
        Optional<LicenseApply> optional = licenseApplyRepository.findById(id);
        if (optional.isPresent()) {
            licenseApply = optional.get();
        }
        return licenseApply;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteById(Long id) {
        //删除授权申请的同时删除相应的授权文件，此操作不可撤回
        licenseApplyRepository.deleteById(id);
        licenseManageRepository.deleteByLicenseApplyId(id);
    }

    /**
     * 生成授权文件
     *
     * @param id
     * @return
     */
    @Override
    public LicenseManage generateLicense(Long id) {
        LicenseManage licenseManage = new LicenseManage();
        Optional<LicenseApply> optional = licenseApplyRepository.findById(id);
        LicenseApply licenseApply = optional.get();
        //todo 根据用户提交授权文件生成license和license源文件
        //获取授权申请当中填写的时长
        int authorizeType = licenseApply.getAuthorizeType();
        int authorizeTime = licenseApply.getAuthorizeTime();
        long millSeconds = 0;
        if (AuthorizeType.H.getCode() == authorizeType) {
            millSeconds = 24 * 60 * 60 * 1000;
        } else if (AuthorizeType.M.getCode() == authorizeType) {
            millSeconds = 30 * 24 * 60 * 60 * 1000;
        } else if (AuthorizeType.Y.getCode() == authorizeType) {
            millSeconds = 365 * 30 * 24 * 60 * 60 * 1000;
        } else {
            throw new IllegalStateException("Unexpected value: " + authorizeType);
        }
        licenseManage.setAuthorizeValidTime(new Date());
        licenseManage.setAuthorizeInvalidTime(new Date(System.currentTimeMillis() + authorizeTime * millSeconds));
        return licenseManage;
    }
}
