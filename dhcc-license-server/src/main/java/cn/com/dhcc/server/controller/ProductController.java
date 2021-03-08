package cn.com.dhcc.server.controller;

import cn.com.dhcc.server.common.entity.QueryResult;
import cn.com.dhcc.server.common.entity.RequestEntity;
import cn.com.dhcc.server.common.entity.ResponseEntity;
import cn.com.dhcc.server.domain.LicenseApply;
import cn.com.dhcc.server.domain.Product;
import cn.com.dhcc.server.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @PostMapping("/save")
    public ResponseEntity save(@RequestBody Product product) {
        try {
            if (product == null) {
                return new ResponseEntity(false, "请输入产品相关信息");
            }
            productService.save(product);
        } catch (Exception e) {
            LOGGER.error("产品保存异常:【{}】", e.toString());
            e.printStackTrace();
            return new ResponseEntity(false, "产品保存异常," + e.toString());
        }
        return new ResponseEntity(true, "产品保存成功");
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Product> findById(@PathVariable("id") Long id) {
        Product product;
        try {
            if (id == null || id == 0) {
                return new ResponseEntity<>(false, "id输入有误，请重新输入");
            }
            product = productService.findById(id);
        } catch (Exception e) {
            LOGGER.error("产品查询异常:【{}】", e.toString());
            e.printStackTrace();
            return new ResponseEntity<>(false, "产品查询异常," + e.toString());
        }
        return new ResponseEntity<>(true, "产品查询成功", product);
    }

    @PostMapping("/findAllByCondition")
    public ResponseEntity<QueryResult<Product>> findAllByCondition(@RequestBody RequestEntity<Product> condition) {
        QueryResult<Product> result = null;
        try {
            if (condition == null) {
                return new ResponseEntity<>(false, "请输入查询条件");
            }
            result = productService.findByCondition(condition.getPageCurrent(), condition.getPageSize(), condition.getQuery());
        } catch (Exception e) {
            LOGGER.error("产品查询异常:【{}】", e.toString());
            e.printStackTrace();
            return new ResponseEntity<>(false, "产品查询异常," + e.toString());
        }
        return new ResponseEntity<>(true, "产品查询成功", result);
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestBody Product product) {
        try {
            if (product == null) {
                return new ResponseEntity(false, "更新条件不能为空");
            }
            productService.update(product);
        } catch (Exception e) {
            LOGGER.error("产品更新异常:【{}】", e.toString());
            e.printStackTrace();
            return new ResponseEntity<LicenseApply>(false, "产品更新异常," + e.toString());
        }
        return new ResponseEntity(true, "产品更新成功");
    }

    @GetMapping("/deleteById/{id}")
    public ResponseEntity deleteById(@PathVariable("id") Long id) {
        try {
            if (id == null || id == 0) {
                return new ResponseEntity(false, "id输入有误，请重新输入");
            }
            productService.deleteById(id);
        } catch (Exception e) {
            LOGGER.error("产品删除异常:【{}】", e.toString());
            e.printStackTrace();
            return new ResponseEntity(false, "产品删除异常," + e.toString());
        }
        return new ResponseEntity(true, "产品删除成功");
    }
}
