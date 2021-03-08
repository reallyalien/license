package cn.com.dhcc.server.domain;

import cn.com.dhcc.common.base.BaseEntity;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 产品表
 */
@Entity
@Table(name = "product")
public class Product extends BaseEntity implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //所属项目id
    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    //产品名称
    @Column(name = "produce_name")
    private String productName;

    //产品描述
    @Column(name = "describer")
    private String describer;

    //产品版本号
    @OneToMany(targetEntity = ProductVersion.class, mappedBy = "product")
    private Set<ProductVersion> productVersionSet = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescriber() {
        return describer;
    }

    public void setDescriber(String describer) {
        this.describer = describer;
    }

//    public Set<ProductVersion> getProductVersionSet() {
//        return productVersionSet;
//    }
//
//    public void setProductVersionSet(Set<ProductVersion> productVersionSet) {
//        this.productVersionSet = productVersionSet;
//    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", describer='" + describer + '\'' +
                '}';
    }
}
