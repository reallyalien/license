package cn.com.dhcc.server.domain;

import cn.com.dhcc.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 产品版本表
 */
@Entity
@Table(name = "product_version")
public class ProductVersion extends BaseEntity implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //所属产品id
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    //版本号
    @Column(name = "version", length = 50)
    private String version;

    //版本描述
    @Column(name = "describer")
    private String describer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescriber() {
        return describer;
    }

    public void setDescriber(String describer) {
        this.describer = describer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "ProductVersion{" +
                "id=" + id +
                ", version='" + version + '\'' +
                ", describer='" + describer + '\'' +
                '}';
    }
}
