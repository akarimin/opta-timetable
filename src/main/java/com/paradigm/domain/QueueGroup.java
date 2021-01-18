package com.paradigm.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

/**
 * Because no Room instances change during solving, a Room is called a problem fact.
 * Such classes do not require any OptaPlanner specific annotations.
 */
@Entity
public class QueueGroup extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String brand;

    public QueueGroup() {
    }

    public QueueGroup(String name, String brand) {
        this.name = name;
        this.brand = brand;
    }

    public Long getId() {
        return id;
    }

    public QueueGroup setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueueGroup that = (QueueGroup) o;
        return Objects.equals(name, that.name) &&
            Objects.equals(brand, that.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, brand);
    }

    @Override
    public String toString() {
        return "QueueGroup{" +
            ", name='" + name + '\'' +
            ", brand='" + brand + '\'' +
            '}';
    }
}
