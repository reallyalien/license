package cn.com.dhcc.server.controller;

import cn.com.dhcc.common.utils.FileUtil;
import cn.com.dhcc.common.utils.SecurityUtils;
import cn.com.dhcc.server.common.entity.ResponseEntity;
import cn.com.dhcc.server.domain.LicenseManage;
import cn.com.dhcc.server.enums.AuthorizeStatus;
import cn.com.dhcc.server.service.LicenseApplyService;
import cn.com.dhcc.server.service.LicenseManageService;
import cn.hutool.core.util.IdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;


@RestController
@RequestMapping("/licenseManage")
public class LicenseManageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LicenseManageController.class);

    @Autowired
    private LicenseManageService licenseManageService;

    @Autowired
    private LicenseApplyService licenseApplyService;

    /**
     * 用户操作提交授权申请
     *
     * @param licenseApplyId 用户对应授权申请的id
     * @return
     */
    @GetMapping
    @RequestMapping("/commitLicenseApply/{licenseApplyId}")
    public ResponseEntity commitLicenseApply(@PathVariable("licenseApplyId") Long licenseApplyId) {
        try {
            LicenseManage licenseManage = licenseManageService.findByLicenseApplyId(licenseApplyId);
            if (licenseManage.getAuthorizeStatus() == AuthorizeStatus.UN_COMMITTED.getCode()) {
                licenseManage.setUpdatedBy(SecurityUtils.getCurrentUsername());
                //变更状态为已提交申请,产品尚未授权
                licenseManage.setAuthorizeStatus(AuthorizeStatus.COMMITTED.getCode());
                licenseManageService.update(licenseManage);
            } else {
                return new ResponseEntity(false, "提交授权申请失败,请检查申请状态");
            }
        } catch (Exception e) {
            LOGGER.error("提交授权申请异常:【{}】", e.toString());
            e.printStackTrace();
            return new ResponseEntity(false, "提交授权申请异常," + e.toString());
        }
        return new ResponseEntity(true, "提交授权申请成功");
    }

    /**
     * 审批员对用户申请进行审批
     *
     * @param licenseApplyId 授权申请的id
     * @param approvalResult 审批具体结果
     * @return
     */
    @GetMapping
    @RequestMapping("/approvalLicenseApply/{licenseApplyId}/{approvalResult}")
    public ResponseEntity approvalLicenseApply(@PathVariable("licenseApplyId") Long licenseApplyId, @PathVariable("approvalResult") int approvalResult) {
        try {
            LicenseManage licenseManage = licenseManageService.findByLicenseApplyId(licenseApplyId);
            //已提交状态才允许审批操作
            if (licenseManage.getAuthorizeStatus() == AuthorizeStatus.COMMITTED.getCode()) {
                licenseManage.setUpdatedBy(SecurityUtils.getCurrentUsername());
                //根据审批结果同步产品授权状态
                if (approvalResult == AuthorizeStatus.AUTHORIZED.getCode()) {
                    //审批通过
                    licenseManage.setAuthorizeStatus(AuthorizeStatus.AUTHORIZED.getCode());
                    //生成license
                    LicenseManage license = licenseApplyService.generateLicense(licenseApplyId);
                    licenseManage.setAuthorizeValidTime(license.getAuthorizeValidTime());
                    licenseManage.setAuthorizeInvalidTime(license.getAuthorizeInvalidTime());
                    licenseManage.setLicenseFile(license.getLicenseFile());
                    licenseManage.setSourceFile(license.getSourceFile());
                } else if (approvalResult == AuthorizeStatus.AUTHORIZED_FAILED.getCode()) {
                    //审批未通过
                    licenseManage.setAuthorizeStatus(AuthorizeStatus.AUTHORIZED_FAILED.getCode());
                }
                licenseManageService.update(licenseManage);
            } else {
                return new ResponseEntity(false, "授权申请审批失败，请检查申请状态");
            }
        } catch (Exception e) {
            LOGGER.error("提交授权申请异常:【{}】", e.toString());
            e.printStackTrace();
            return new ResponseEntity(false, "提交授权申请异常," + e.toString());
        }
        return new ResponseEntity(true, "授权申请审批成功");
    }


    /**
     * 下载授权文件
     *
     * @param licenseApplyId
     */
    @GetMapping("/downLoadLicenseFile/{licenseApplyId}")
    public void downLoadLicenseFile(@PathVariable("licenseApplyId") Long licenseApplyId, HttpServletRequest request, HttpServletResponse response) {
        OutputStream outputStream = null;
        try {
            LicenseManage licenseManage = licenseManageService.findByLicenseApplyId(licenseApplyId);
            byte[] licenseFile = licenseManage.getLicenseFile();
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Type", "application/octet-stream");
            String filename = IdUtil.simpleUUID() + "-license" + ".txt";
            response.setHeader("Content-disposition",
                    "attachment;filename=" + FileUtil.setFileDownloadHeader(request, filename));
            outputStream = response.getOutputStream();
            outputStream.write(licenseFile, 0, licenseFile.length);
            outputStream.flush();
        } catch (Exception e) {
            LOGGER.error("授权文件下载失败：【{}】", e.toString());
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
