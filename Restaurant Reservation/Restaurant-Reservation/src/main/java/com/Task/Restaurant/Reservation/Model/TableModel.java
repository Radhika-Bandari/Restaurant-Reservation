package com.Task.Restaurant.Reservation.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TableModel {
    public String tableNo;
    public Boolean available;
    public List<OrderModel> orders;
}
