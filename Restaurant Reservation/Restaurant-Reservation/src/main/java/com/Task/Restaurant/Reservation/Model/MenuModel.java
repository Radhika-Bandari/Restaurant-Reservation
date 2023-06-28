package com.Task.Restaurant.Reservation.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuModel {
    public String cardNo;
    public String restaurantId;
    public List<RecipeModel> biryanis;
    public List<RecipeModel> starters;
    public List<RecipeModel> deserts;
    public List<RecipeModel> milkshakes;
    public List<RecipeModel> chinese;
}
