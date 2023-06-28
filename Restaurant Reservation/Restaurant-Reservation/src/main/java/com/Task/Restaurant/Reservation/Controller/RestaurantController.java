package com.Task.Restaurant.Reservation.Controller;

import com.Task.Restaurant.Reservation.Model.*;
import com.Task.Restaurant.Reservation.Service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;
    @PostMapping("/RegisterRestaurant")
    public RestaurantModel registerRestaurant(@RequestBody final RestaurantModel restaurantModel){
        return restaurantService.registerRestaurant(restaurantModel);
    }
    @PutMapping("/addRecipes/{restaurantId}")
    public String addRecipes(@PathVariable(value = "restaurantId") final String restaurantId, @RequestBody final RecipesModel recipes){
        return restaurantService.addRecipes(restaurantId, recipes);
    }
    @GetMapping("/{location}/{type}")
    public List<RestaurantModel> getRestaurantByLocAndType(@PathVariable(value = "location") final String location, @PathVariable(value = "type") final String type){
        return restaurantService.getRestaurantByLocAndType(location, type);
    }
    @PostMapping("/createTable")
    public TableModel createTable(@RequestBody final TableModel tableModel){
        return restaurantService.createTable(tableModel);
    }
    @GetMapping("/generateBill/{orderId}")
    public String generateBill(@PathVariable("orderId") final String orderId) {
        return restaurantService.generateBill(orderId);
    }
    @PostMapping("/menu/{restaurantId}")
    public String getMenu(@PathVariable("restaurantId") final String restaurantId,@RequestBody final RecipesModel recipes){
        return restaurantService.getMenu(restaurantId,recipes);
    }
}
