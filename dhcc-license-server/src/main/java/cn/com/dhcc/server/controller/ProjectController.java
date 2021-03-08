package cn.com.dhcc.server.controller;

import cn.com.dhcc.server.common.entity.QueryResult;
import cn.com.dhcc.server.common.entity.RequestEntity;
import cn.com.dhcc.server.common.entity.ResponseEntity;
import cn.com.dhcc.server.domain.LicenseApply;
import cn.com.dhcc.server.domain.Project;
import cn.com.dhcc.server.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/project")
public class ProjectController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private ProjectService projectService;


    @PostMapping("/save")
    public ResponseEntity save(@RequestBody Project project) {
        try {
            if (project == null) {
                return new ResponseEntity(false, "请输入项目相关信息");
            }
            projectService.save(project);
        } catch (Exception e) {
            LOGGER.error("项目保存异常:【{}】", e.toString());
            e.printStackTrace();
            return new ResponseEntity(false, "项目保存异常," + e.toString());
        }
        return new ResponseEntity(true, "项目保存成功");
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Project> findById(@PathVariable("id") Long id) {
        Project project;
        try {
            if (id == null || id == 0) {
                return new ResponseEntity<>(false, "id输入有误，请重新输入");
            }
            project = projectService.findById(id);
        } catch (Exception e) {
            LOGGER.error("项目查询异常:【{}】", e.toString());
            e.printStackTrace();
            return new ResponseEntity<>(false, "项目查询异常," + e.toString());
        }
        return new ResponseEntity<>(true, "项目查询成功", project);
    }

    @PostMapping("/findAllByCondition")
    public ResponseEntity<QueryResult<Project>> findAllByCondition(@RequestBody RequestEntity<Project> condition) {
        QueryResult<Project> result = null;
        try {
            if (condition == null) {
                return new ResponseEntity<>(false, "请输入查询条件");
            }
            result = projectService.findByCondition(condition.getPageCurrent(), condition.getPageSize(), condition.getQuery());
        } catch (Exception e) {
            LOGGER.error("项目查询异常:【{}】", e.toString());
            e.printStackTrace();
            return new ResponseEntity<>(false, "项目查询异常," + e.toString());
        }
        return new ResponseEntity<>(true, "项目查询成功", result);
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestBody Project project) {
        try {
            if (project == null) {
                return new ResponseEntity(false, "更新条件不能为空");
            }
            projectService.update(project);
        } catch (Exception e) {
            LOGGER.error("项目更新异常:【{}】", e.toString());
            e.printStackTrace();
            return new ResponseEntity<LicenseApply>(false, "项目更新异常," + e.toString());
        }
        return new ResponseEntity(true, "项目更新成功");
    }

    @GetMapping("/deleteById/{id}")
    public ResponseEntity deleteById(@PathVariable("id") Long id) {
        try {
            if (id == null || id == 0) {
                return new ResponseEntity(false, "id输入有误，请重新输入");
            }
            projectService.deleteById(id);
        } catch (Exception e) {
            LOGGER.error("项目删除异常:【{}】", e.toString());
            e.printStackTrace();
            return new ResponseEntity(false, "项目删除异常," + e.toString());
        }
        return new ResponseEntity(true, "项目删除成功");
    }

}
