package com.Task.Restaurant.Reservation.Model;


import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class BillModel {
   public String transactionId;
   public String paymentMethod;
   public String PaymentStatus;
   public Double price;
   public Double discountPrice;
   public Double totalPrice;
   public Map<String, Double> orders;
   public String customer;

}
