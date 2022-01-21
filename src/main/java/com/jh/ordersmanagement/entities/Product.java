package com.jh.ordersmanagement.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jh.ordersmanagement.constants.Fields;
import com.jh.ordersmanagement.constants.Tables;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = Tables.PRODUCTS)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String category;

    @JsonProperty(Fields.UNIT_PRICE)
    private double unitPrice;

    private boolean active;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Product product = (Product) o;
        return id != null && Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
