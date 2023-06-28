package com.Task.Restaurant.Reservation.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RestaurantModel {
    public String id;
    public String name;
    public String location;
    public String type;
    public List<RecipeModel> recipes;
    public List<TableModel> tables;
}
