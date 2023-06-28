package com.Task.Restaurant.Reservation.Service;

import com.Task.Restaurant.Reservation.Entity.*;
import com.Task.Restaurant.Reservation.Exception.ExistedException;
import com.Task.Restaurant.Reservation.Exception.NotFoundException;
import com.Task.Restaurant.Reservation.Model.*;
import com.Task.Restaurant.Reservation.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private TableRepository tableRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private MenuRepository menuRepository;

    public RestaurantModel registerRestaurant(RestaurantModel restaurantModel) {
        Optional<RestaurantEntity> restaurant = restaurantRepository.findById(restaurantModel.getId());
        if (restaurant.isEmpty()) {
            RestaurantEntity restaurantEntity = new RestaurantEntity();
            restaurantEntity.setId(restaurantModel.getId());
            restaurantEntity.setName(restaurantModel.getName());
            restaurantEntity.setLocation(restaurantModel.getLocation());
            restaurantEntity.setType(restaurantModel.getType());
            List<RecipeModel> recipeModels = restaurantModel.getRecipes();
            List<RecipeEntity> vegList = new ArrayList<>();
            List<RecipeEntity> nonVegList = new ArrayList<>();
            for (RecipeModel recipe : recipeModels) {
                RecipeEntity recipeEntity = new RecipeEntity();
                recipeEntity.setCode(recipe.getCode());
                recipeEntity.setName(recipe.getName());
                recipeEntity.setPrice(recipe.getPrice());
                recipeEntity.setType(recipe.getType());
                if (recipeEntity.getType().equalsIgnoreCase("nonVeg")) {
                    nonVegList.add(recipeEntity);
                } else {
                    vegList.add(recipeEntity);
                }
                recipeRepository.save(recipeEntity);
            }
            restaurantEntity.setVegRecipes(vegList);
            restaurantEntity.setNon_VegRecipes(nonVegList);
            List<TableModel> tableModels = restaurantModel.getTables();
            List<TableEntity> tableEntities = new ArrayList<>();
            for (TableModel table : tableModels) {
                TableEntity tableEntity = new TableEntity();
                tableEntity.setNo(table.getTableNo());
                tableEntity.setAvailable(table.getAvailable());
                tableEntities.add(tableEntity);
                tableRepository.save(tableEntity);
            }
            restaurantEntity.setTables(tableEntities);
            restaurantRepository.save(restaurantEntity);
            return restaurantModel;
        }
        throw new ExistedException("restaurant already existed with " + restaurantModel.getId() + " id");
    }

    public String addRecipes(String restaurantId, RecipesModel recipes) {
        Optional<RestaurantEntity> restaurant = restaurantRepository.findById(restaurantId);
        if (restaurant.isPresent()) {
            if (!recipes.getRecipes().isEmpty()) {
                RestaurantEntity restaurantEntity = restaurant.get();
                List<RecipeEntity> vegList = restaurantEntity.getVegRecipes();
                List<RecipeEntity> nonVegList = restaurantEntity.getNon_VegRecipes();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("<table border:'1' ><tr><th>code<th/><th>name<th/><th>price<th/><tr/>");
                for (RecipeModel recipe : recipes.getRecipes()) {
                    RecipeEntity recipeEntity = new RecipeEntity();
                    if (recipeRepository.findById(recipe.getCode()).isEmpty()) {
                        recipeEntity.setCode(recipe.getCode());
                        recipeEntity.setName(recipe.getName());
                        recipeEntity.setPrice(recipe.getPrice());
                        recipeEntity.setType(recipe.getType());
                        recipeEntity.setChoice(recipe.getChoice());
                        if (recipeEntity.getType().equalsIgnoreCase("nonVeg")) {
                            nonVegList.add(recipeEntity);
                        } else {
                            vegList.add(recipeEntity);
                        }
                        stringBuilder.append("<tr><td>" + recipeEntity.getCode() + "<td/><td>" + recipeEntity.getName() + "<td/><td>" + recipeEntity.getPrice() + "<td/><td><tr/>");
                        recipeRepository.save(recipeEntity);
                    }
                }
                stringBuilder.append("<table/>");
                restaurantEntity.setVegRecipes(vegList);
                restaurantEntity.setNon_VegRecipes(nonVegList);
                restaurantRepository.save(restaurantEntity);
                return stringBuilder.toString();
            }
        }
        throw new NotFoundException("Invalid restaurant Id");
    }

    public List<RestaurantModel> getRestaurantByLocAndType(String location, String type) {
        List<RestaurantEntity> restaurants = restaurantRepository.findByLocationAndType(location, type);
        if (type.equals("veg") || type.equals("nonVeg")) {
            restaurants.addAll(restaurantRepository.findByLocationAndType(location, "both"));
        }
        if (type.equals("both")) {
            restaurants.addAll(restaurantRepository.findByLocationAndType(location, "veg"));
            restaurants.addAll(restaurantRepository.findByLocationAndType(location, "nonVeg"));
        }
        if (!restaurants.isEmpty()) {
            List<RestaurantModel> restaurantModels = new ArrayList<>();
            for (RestaurantEntity restaurant : restaurants) {
                RestaurantModel restaurantModel = new RestaurantModel();
                restaurantModel.setId(restaurant.getId());
                restaurantModel.setName(restaurant.getName());
                restaurantModel.setType(restaurant.getType());
                restaurantModel.setLocation(restaurant.getLocation());
                List<TableModel> tableModels = new ArrayList<>();
                for (TableEntity table : restaurant.getTables()) {
                    TableModel tableModel = new TableModel();
                    tableModel.setTableNo(table.getNo());
                    tableModel.setAvailable(table.getAvailable());
                    tableModels.add(tableModel);
                }
                restaurantModel.setTables(tableModels);
                restaurantModels.add(restaurantModel);
            }
            return restaurantModels;
        }
        throw new NotFoundException("Please check the Details");
    }

    public TableModel createTable(TableModel tableModel) {
        Optional<TableEntity> table = tableRepository.findById(tableModel.getTableNo());
        if (table.isEmpty()) {
            TableEntity tableEntity = new TableEntity();
            tableEntity.setNo(tableModel.getTableNo());
            tableEntity.setAvailable(tableModel.getAvailable());
            tableRepository.save(tableEntity);
            return tableModel;
        }
        return null;
    }

    public String generateBill(String orderId) {
        Optional<OrderEntity> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            OrderEntity orderEntity = order.get();
            if (null == orderEntity.getBill()) {
                BillEntity bill = new BillEntity();
                bill.setTransactionId("TR" + orderEntity.getOrderId());
                bill.setOrder(orderEntity);
                bill.setPrice(orderEntity.getPrice());
                bill.setCustomerId(orderEntity.getCustomer().getEmail());
                bill.setPaymentStatus("PENDING");
                bill.setTotalPrice(orderEntity.getTotalPrice());
                billRepository.save(bill);
                orderEntity.setBill(bill);
                orderRepository.save(orderEntity);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("<table> border:1 <tr><th>TransactionId<th/><th>customerId<th/><th>Price<th/><th>PaymentStatus<th/><th>TotalPrice<th/><th>Orders<th/><tr/>");
                BillModel billModel = new BillModel();
                billModel.setTransactionId(bill.getTransactionId());
                billModel.setCustomer(bill.getCustomerId());
                billModel.setPrice(bill.getPrice());
                billModel.setPaymentStatus("PENDING");
                billModel.setTotalPrice(bill.getTotalPrice());
                Map<String, Double> orders = new HashMap<>();
                orderEntity.getRecipes().forEach(recipeEntity -> orders.put(recipeEntity.getCode() + " - " + recipeEntity.getName(), recipeEntity.getPrice()));
                billModel.setOrders(orders);
                stringBuilder.append("<tr><td>" + billModel.getTransactionId() + "<td/><td>" + billModel.getCustomer() + "<td/><td>" + billModel.getPrice() + "<td/><td>" + billModel.getPaymentStatus() + "<td/><td>" + billModel.getTotalPrice() + "<td/><br><td>" + billModel.getOrders() + "<td/><tr/>");
                stringBuilder.append("<table/>");
                return stringBuilder.toString();
            }
            throw new ExistedException("Bill already generated");
        }
        throw new NotFoundException("There is no such order with Id : " + orderId);
    }

    public String getMenu(String restaurantId, RecipesModel recipes) {
        Optional<RestaurantEntity> restaurant = restaurantRepository.findById(restaurantId);
        if (restaurant.isPresent()) {
            if (!recipes.getRecipes().isEmpty()) {
                MenuEntity menuEntity = new MenuEntity();
                List<RecipeEntity> biryanis = new ArrayList<>();
                List<RecipeEntity> starters = new ArrayList<>();
                List<RecipeEntity> deserts = new ArrayList<>();
                List<RecipeEntity> milkshakes = new ArrayList<>();
                List<RecipeEntity> chinese = new ArrayList<>();
                StringBuilder stringBuilder = new StringBuilder();
                for (RecipeModel recipe : recipes.getRecipes()) {
                    RecipeEntity recipeEntity = new RecipeEntity();
                    recipeEntity.setCode(recipe.getCode());
                    recipeEntity.setName(recipe.getName());
                    recipeEntity.setPrice(recipe.getPrice());
                    recipeEntity.setType(recipe.getType());
                    recipeEntity.setChoice(recipe.getChoice());
                    if (recipeEntity.getChoice().equalsIgnoreCase("biryani")) {
                        stringBuilder.append("<table border:'2' ><tr><th>biryanis<th/><tr/>");
                        stringBuilder.append("<table border:'1' ><tr><th>code<th/><th>name<th/><th>price<th/><th>type<th/><tr/>");
                        biryanis.add(recipeEntity);
                        stringBuilder.append("<tr><td>" + recipeEntity.getCode() + "<td/><td>" + recipeEntity.getName() + "<td/><td>" + recipeEntity.getPrice() + "<td/><td>" + recipeEntity.getType() + "<td/><td><tr/>");
                    }
                    if (recipeEntity.getChoice().equalsIgnoreCase("starter")) {
                        stringBuilder.append("<table border:'2' ><tr><th>starters<th/><tr/>");
                        stringBuilder.append("<table border:'1' ><tr><th>code<th/><th>name<th/><th>price<th/><th>type<th/><tr/>");
                        starters.add(recipeEntity);
                        stringBuilder.append("<tr><td>" + recipeEntity.getCode() + "<td/><td>" + recipeEntity.getName() + "<td/><td>" + recipeEntity.getPrice() + "<td/><td>" + recipeEntity.getType() + "<td/><td><tr/>");
                    }
                    if (recipeEntity.getChoice().equalsIgnoreCase("desert")) {
                        stringBuilder.append("<table border:'2' ><tr><th>deserts<th/><tr/>");
                        stringBuilder.append("<table border:'1' ><tr><th>code<th/><th>name<th/><th>price<th/><th>type<th/><tr/>");
                        deserts.add(recipeEntity);
                        stringBuilder.append("<tr><td>" + recipeEntity.getCode() + "<td/><td>" + recipeEntity.getName() + "<td/><td>" + recipeEntity.getPrice() + "<td/><td>" + recipeEntity.getType() + "<td/><td><tr/>");
                    }
                    if (recipeEntity.getChoice().equalsIgnoreCase("milkshake")) {
                        stringBuilder.append("<table border:'2' ><tr><th>milkshakes<th/><tr/>");
                        stringBuilder.append("<table border:'1' ><tr><th>code<th/><th>name<th/><th>price<th/><th>type<th/><tr/>");
                        milkshakes.add(recipeEntity);
                        stringBuilder.append("<tr><td>" + recipeEntity.getCode() + "<td/><td>" + recipeEntity.getName() + "<td/><td>" + recipeEntity.getPrice() + "<td/><td>" + recipeEntity.getType() + "<td/><td><tr/>");
                    }
                    if (recipeEntity.getChoice().equalsIgnoreCase("chinese")) {
                        stringBuilder.append("<table border:'1' ><tr><th>chinese<th/><tr/>");
                        stringBuilder.append("<table border:'1' ><tr><th>code<th/><th>name<th/><th>price<th/><th>type<th/><tr/>");
                        chinese.add(recipeEntity);
                        stringBuilder.append("<tr><td>" + recipeEntity.getCode() + "<td/><td>" + recipeEntity.getName() + "<td/><td>" + recipeEntity.getPrice() + "<td/><td>" + recipeEntity.getType() + "<td/><td><tr/>");
                    }
                    menuEntity.setBiryanis(biryanis);
                    menuEntity.setStarters(starters);
                    menuEntity.setDeserts(deserts);
                    menuEntity.setMilkshakes(milkshakes);
                    menuEntity.setChinese(chinese);
                    recipeRepository.save(recipeEntity);
                }
                stringBuilder.append("<table/>");
                return stringBuilder.toString();
            }
        }
        throw new NotFoundException("Please provide valid Restaurant Id");
    }
}
