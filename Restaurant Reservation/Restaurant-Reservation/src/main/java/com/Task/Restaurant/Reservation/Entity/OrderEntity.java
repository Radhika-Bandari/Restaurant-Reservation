package com.Task.Restaurant.Reservation.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table
@Getter
@Setter
public class OrderEntity {
    @Id
    @GeneratedValue
    public Integer orderId;
    @OneToMany
    public List<RecipeEntity> recipes;
    public Double price;
    public Double discountPrice;
    public Double totalPrice;
    @ManyToOne(cascade = CascadeType.ALL)
    public CustomerEntity customer;
    @OneToOne
    public BillEntity bill;
    @ManyToOne
    public TableEntity table;
}
