package com.Task.Restaurant.Reservation.Entity;

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
public class MenuEntity {
    @Id
    public String cardNo;
    public String restaurantId;
    @OneToMany
    public List<RecipeEntity> biryanis;
    @OneToMany
    public List<RecipeEntity> starters;
    @OneToMany
    public List<RecipeEntity> deserts;
    @OneToMany
    public List<RecipeEntity> milkshakes;
    @OneToMany
    public List<RecipeEntity> chinese;
}
