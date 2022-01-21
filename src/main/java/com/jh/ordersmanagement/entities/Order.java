package com.jh.ordersmanagement.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jh.ordersmanagement.constants.Fields;
import com.jh.ordersmanagement.constants.OrderStatus;
import com.jh.ordersmanagement.constants.Tables;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = Tables.ORDERS)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private OrderStatus status;

    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private LocalDateTime date;

    private String client;

    @JsonProperty(Fields.COUNCIL_TAX)
    private double councilTax;

    @JsonProperty(Fields.COUNTY_TAX)
    private double countyTax;

    @JsonProperty(Fields.STATE_TAX)
    private double stateTax;

    @JsonProperty(Fields.FEDERAL_TAX)
    private double federalTax;

    @JsonProperty(Fields.TOTAL_TAX)
    private double totalTax;

    @JsonProperty(Fields.SUB_TOTAL)
    private double subTotal;

    private double total;

    @OneToMany(mappedBy = Fields.ORDER)
    @JsonProperty(Fields.ORDER_DETAIL)
    @ToString.Exclude
    private List<OrderDetail> orderDetail = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Order order = (Order) o;
        return id != null && Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
