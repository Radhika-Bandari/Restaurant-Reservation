package com.Task.Restaurant.Reservation.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class RecipeEntity {
    @Id
    public String code;
    public String name;
    public String type;
    public String choice;
    public Double price;
    @ManyToOne
    public OrderEntity order;
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    public MenuEntity menu;
}
