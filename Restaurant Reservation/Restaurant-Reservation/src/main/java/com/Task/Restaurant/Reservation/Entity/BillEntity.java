package com.Task.Restaurant.Reservation.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class BillEntity {
    @Id
    public String transactionId;
    public String paymentMethod;
    public String PaymentStatus;
    public Double price;
    public Double discountPrice;
    public Double totalPrice;
    @OneToOne(cascade = CascadeType.ALL)
    public OrderEntity order;
    public String customerId;
}
