package com.Task.Restaurant.Reservation.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table
@Getter
@Setter
public class CustomerEntity {
    @Id
    @NotBlank
    public String id;
    public String firstName;
    public String lastName;
    @Email
    public String email;
    public String phoneNumber;
    @OneToMany(mappedBy = "customer")
    public List<OrderEntity> orders;
    public Double rewardPoints;
}
