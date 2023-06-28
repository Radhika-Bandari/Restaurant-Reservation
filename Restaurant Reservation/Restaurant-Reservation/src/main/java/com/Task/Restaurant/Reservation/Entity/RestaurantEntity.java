package com.Task.Restaurant.Reservation.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table
@Getter
@Setter
public class RestaurantEntity {
    @Id
    public String id;
    public String name;
    public String location;
    public String type;
    @OneToMany
    public List<RecipeEntity> vegRecipes;
    @OneToMany
    public List<RecipeEntity> non_VegRecipes;
    @OneToMany
    public List<TableEntity> tables;
}
