package cn.com.dhcc.server.service;

import cn.com.dhcc.server.common.entity.QueryResult;
import cn.com.dhcc.server.domain.Project;


public interface ProjectService {

    void save(Project project);

    void update(Project project);

    QueryResult<Project> findByCondition(int page, int pageSize, Project project);

    Project findById(Long id);

    void deleteById(Long id);

}
