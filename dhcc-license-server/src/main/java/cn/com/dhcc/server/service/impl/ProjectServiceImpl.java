package cn.com.dhcc.server.service.impl;

import cn.com.dhcc.server.common.entity.QueryResult;
import cn.com.dhcc.server.domain.Project;
import cn.com.dhcc.server.repository.ProjectRepository;
import cn.com.dhcc.server.service.ProjectService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public void save(Project project) {
        projectRepository.save(project);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void update(Project project) {
        projectRepository.save(project);
    }

    @Override
    @Transactional(readOnly = true)
    public QueryResult<Project> findByCondition(int page, int pageSize, Project project) {
        Specification<Project> specification = new Specification<Project>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Expression projectName = root.get("projectName").as(String.class);
                Expression describer = root.get("describer").as(String.class);
                Expression createBy = root.get("createBy").as(String.class);
                Expression updatedBy = root.get("updatedBy").as(String.class);
                Predicate p1;
                Predicate p2;
                Predicate p3;
                Predicate p4;
                Predicate and;
                List<Predicate> list = new ArrayList<>(4);
                if (StringUtils.isNotBlank(project.getProjectName())) {
                    p1 = criteriaBuilder.like(projectName, "%" + project.getProjectName() + "%");
                    list.add(p1);
                }
                if (StringUtils.isNotBlank(project.getDescriber())) {
                    p2 = criteriaBuilder.like(describer, "%" + project.getDescriber() + "%");
                    list.add(p2);
                }
                if (StringUtils.isNotBlank(project.getCreateBy())) {
                    p3 = criteriaBuilder.like(createBy, "%" + project.getCreateBy() + "%");
                    list.add(p3);
                }
                if (StringUtils.isNotBlank(project.getUpdatedBy())) {
                    p4 = criteriaBuilder.like(updatedBy, "%" + project.getUpdatedBy() + "%");
                    list.add(p4);
                }
                and = criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
                return and;
            }
        };
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, sort);
        Page<Project> all = projectRepository.findAll(specification, pageRequest);
        QueryResult<Project> result = new QueryResult<>();
        result.setTotal(all.getTotalElements());
        result.setList(all.getContent());
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Project findById(Long id) {
        Project project = null;
        Optional<Project> optionalProject = projectRepository.findById(id);
        if (optionalProject.isPresent()) {
            project = optionalProject.get();
        }
        return project;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteById(Long id) {
        projectRepository.deleteById(id);
    }
}
