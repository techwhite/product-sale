package com.lightspeed.practicaltest.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

// model used to interactive with db

@Entity
@ToString
public class ProductEntity {
    // auto increment id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // index column
    @Column(unique = true)
    private String name;
    private BigDecimal price;

    @CreationTimestamp
    @Column(name = "createtime", updatable = false)
    private LocalDateTime createtime;

    @UpdateTimestamp
    @Column(name = "updatetime")
    private LocalDateTime updatetime;

    public void setId(Integer id) {
        this.id = id;
    }

    public ProductEntity() {

    }
    public ProductEntity(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductEntity)) return false;
        ProductEntity entity = (ProductEntity) o;
        return Objects.equals(id, entity.id) && Objects.equals(name, entity.name) && price.compareTo(entity.price) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }
}
