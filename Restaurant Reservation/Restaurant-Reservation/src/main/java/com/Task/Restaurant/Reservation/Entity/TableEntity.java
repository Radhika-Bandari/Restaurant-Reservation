package com.Task.Restaurant.Reservation.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table
@Getter
@Setter
public class TableEntity {
    @Id
    public String no;
    public Boolean available;
    @JsonIgnore
    @ManyToOne
    public RestaurantEntity restaurant;
    @OneToMany
    public List<OrderEntity> orders;
}
