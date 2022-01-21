package com.jh.ordersmanagement.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = Tables.ORDERS_DETAIL)
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = Fields.ORDER_ID)
    @JsonIgnore
    private Order order;

    @ManyToOne
    @JoinColumn(name = Fields.PRODUCT_ID)
    private Product product;

    private double quantity;

    @JsonProperty(Fields.ITEM_TOTAL)
    private double itemTotal;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OrderDetail that = (OrderDetail) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
