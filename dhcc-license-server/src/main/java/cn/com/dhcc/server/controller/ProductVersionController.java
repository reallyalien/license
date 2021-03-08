package cn.com.dhcc.server.controller;

import cn.com.dhcc.server.common.entity.QueryResult;
import cn.com.dhcc.server.common.entity.RequestEntity;
import cn.com.dhcc.server.common.entity.ResponseEntity;
import cn.com.dhcc.server.domain.LicenseApply;
import cn.com.dhcc.server.domain.ProductVersion;
import cn.com.dhcc.server.service.ProductVersionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/productVersion")
public class ProductVersionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductVersionController.class);

    @Autowired
    private ProductVersionService productVersionService;

    @PostMapping("/save")
    public ResponseEntity save(@RequestBody ProductVersion productVersion) {
        try {
            if (productVersion == null) {
                return new ResponseEntity(false, "请输入产品版本相关信息");
            }
            productVersionService.save(productVersion);
        } catch (Exception e) {
            LOGGER.error("产品版本保存异常:【{}】", e.toString());
            e.printStackTrace();
            return new ResponseEntity(false, "产品版本保存异常," + e.toString());
        }
        return new ResponseEntity(true, "产品版本保存成功");
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<ProductVersion> findById(@PathVariable("id") Long id) {
        ProductVersion productVersion;
        try {
            if (id == null || id == 0) {
                return new ResponseEntity<>(false, "id输入有误，请重新输入");
            }
            productVersion = productVersionService.findById(id);
        } catch (Exception e) {
            LOGGER.error("产品版本查询异常:【{}】", e.toString());
            e.printStackTrace();
            return new ResponseEntity<>(false, "产品版本查询异常," + e.toString());
        }
        return new ResponseEntity<>(true, "产品版本查询成功", productVersion);
    }

    @PostMapping("/findAllByCondition")
    public ResponseEntity<QueryResult<ProductVersion>> findAllByCondition(@RequestBody RequestEntity<ProductVersion> condition) {
        QueryResult<ProductVersion> result = null;
        try {
            if (condition == null) {
                return new ResponseEntity<>(false, "请输入查询条件");
            }
            result = productVersionService.findByCondition(condition.getPageCurrent(), condition.getPageSize(), condition.getQuery());
        } catch (Exception e) {
            LOGGER.error("产品查询异常:【{}】", e.toString());
            e.printStackTrace();
            return new ResponseEntity<>(false, "产品版本查询异常," + e.toString());
        }
        return new ResponseEntity<>(true, "产品版本查询成功", result);
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestBody ProductVersion productVersion) {
        try {
            if (productVersion == null) {
                return new ResponseEntity(false, "更新条件不能为空");
            }
            productVersionService.update(productVersion);
        } catch (Exception e) {
            LOGGER.error("产品版本更新异常:【{}】", e.toString());
            e.printStackTrace();
            return new ResponseEntity<LicenseApply>(false, "产品版本更新异常," + e.toString());
        }
        return new ResponseEntity(true, "产品版本更新成功");
    }

    @GetMapping("/deleteById/{id}")
    public ResponseEntity deleteById(@PathVariable("id") Long id) {
        try {
            if (id == null || id == 0) {
                return new ResponseEntity(false, "id输入有误，请重新输入");
            }
            productVersionService.deleteById(id);
        } catch (Exception e) {
            LOGGER.error("产品版本删除异常:【{}】", e.toString());
            e.printStackTrace();
            return new ResponseEntity(false, "产品版本删除异常," + e.toString());
        }
        return new ResponseEntity(true, "产品版本删除成功");
    }
}
