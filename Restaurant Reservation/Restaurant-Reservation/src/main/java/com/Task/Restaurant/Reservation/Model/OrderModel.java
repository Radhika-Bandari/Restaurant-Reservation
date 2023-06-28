package com.Task.Restaurant.Reservation.Model;

import com.Task.Restaurant.Reservation.Entity.CustomerEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderModel {
    public String orderId;
    public List<RecipesModel> recipes;
    public Double price;
    public Double discountPrice;
    public Double totalPrice;
    public CustomerModel customer;
}
