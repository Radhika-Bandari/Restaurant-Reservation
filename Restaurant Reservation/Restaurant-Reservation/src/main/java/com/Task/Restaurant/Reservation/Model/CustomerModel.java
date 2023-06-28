package com.Task.Restaurant.Reservation.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomerModel {
    public String id;
    public String firstName;
    public String lastName;
    public String email;
    public String phoneNumber;
    public List<OrderModel> orders;
    public Double rewards;
    public List<BillModel> bill;
}
