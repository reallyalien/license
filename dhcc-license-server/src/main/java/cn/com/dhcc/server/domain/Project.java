package cn.com.dhcc.server.domain;

import cn.com.dhcc.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目表
 */
@Entity
@Table(name = "project")
public class Project extends BaseEntity implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //项目名称
    @Column(name = "project_name")
    private String projectName;

    //项目描述
    @Column(name = "describer")
    private String describer;

    //产品列表
    @OneToMany(targetEntity = Product.class,mappedBy = "project",fetch = FetchType.LAZY)
    private List<Product> productList = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDescriber() {
        return describer;
    }

    public void setDescriber(String describer) {
        this.describer = describer;
    }

//    public List<Product> getProductList() {
//        return productList;
//    }
//
//    public void setProductList(List<Product> productList) {
//        this.productList = productList;
//    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", projectName='" + projectName + '\'' +
                ", describer='" + describer + '\'' +
                '}';
    }
}
