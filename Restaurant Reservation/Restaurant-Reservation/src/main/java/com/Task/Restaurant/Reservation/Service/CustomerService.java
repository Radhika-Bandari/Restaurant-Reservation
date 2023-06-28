package com.Task.Restaurant.Reservation.Service;

import com.Task.Restaurant.Reservation.Entity.*;
import com.Task.Restaurant.Reservation.Exception.ExistedException;
import com.Task.Restaurant.Reservation.Model.*;
import com.Task.Restaurant.Reservation.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private TableRepository tableRepository;

    public CustomerModel createCustomer(CustomerModel customerModel) {
        Optional<CustomerEntity> customer = customerRepository.findById(customerModel.getId());
        if (customer.isEmpty()) {
            CustomerEntity customerEntity = new CustomerEntity();
            customerEntity.setId(customerModel.getId());
            customerEntity.setFirstName(customerModel.getFirstName());
            customerEntity.setLastName(customerModel.getLastName());
            customerEntity.setEmail(customerModel.getEmail());
            customerEntity.setPhoneNumber(customerModel.getPhoneNumber());
            customerRepository.save(customerEntity);
            return customerModel;
        }
        throw new ExistedException("Customer already existed with Id : "+customerModel.getId());
    }

    public String updateOrder(String orderId, String recipes) {
        Optional<OrderEntity> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            if (!recipes.isEmpty()) {
                OrderEntity orderEntity = order.get();
                String[] recipeNames = recipes.split(",");
                List<RecipeEntity> recipeEntities = orderEntity.getRecipes();
                for (String recipe : recipeNames) {
                    recipeEntities.addAll(recipeRepository.findByName(recipe));
                }
                Double price = recipeEntities.stream().mapToDouble(recipeEntity -> recipeEntity.getPrice()).sum();
                orderEntity.setPrice(price);
                orderEntity.setTotalPrice(price);
                orderRepository.save(orderEntity);
                return "order updated";
            }
            return "sorry! recipe doesn't exist";
        }
        return "there is no such order with Id : "+orderId;
    }
    public String bookOrder(String customerId, String tableNo, String recipes) {
        Optional<CustomerEntity> customer = customerRepository.findById(customerId);
        if (customer.isPresent()) {
            Optional<TableEntity> table = tableRepository.findById(tableNo);
            if (table.isPresent()) {
                TableEntity tableEntity = table.get();
                if (tableEntity.getAvailable().equals(true)) {
                    List<OrderEntity> orderEntities = tableEntity.getOrders();
                    OrderEntity orderEntity = new OrderEntity();
                    orderEntity.setCustomer(customer.get());
                    orderEntity.setTable(tableEntity);
                    String[] recipeNames = recipes.split(",");
                    List<RecipeEntity> recipeEntities = new ArrayList<>();
                    for (String recipe : recipeNames) {
                        recipeEntities.addAll(recipeRepository.findByName(recipe));
                    }
                    Double price = recipeEntities.stream().mapToDouble(RecipeEntity::getPrice).sum();
                    orderEntity.setPrice(price);
                    orderEntity.setTotalPrice(price);
                    orderEntity.setRecipes(recipeEntities);
                    orderEntities.add(orderEntity);
                    orderRepository.saveAll(orderEntities);
                    tableEntity.setOrders(orderEntities);
                    tableEntity.setAvailable(false);
                    tableRepository.save(tableEntity);
                    return "order placed with ID: " + orderEntity.getOrderId();
                }
                return "table is taken";
            }
            return "please provide valid table number";
        }
        return "customer not exists";
    }

    public String payTheBill(String orderId, String paymentMethod) {
        Optional<OrderEntity> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            OrderEntity orderEntity = order.get();
            BillEntity bill = orderEntity.getBill();
            bill.setPaymentMethod(paymentMethod);
            bill.setPaymentStatus("success");
            billRepository.save(bill);
            TableEntity table = orderEntity.getTable();
            table.setAvailable(true);
            tableRepository.save(table);
            CustomerEntity customerEntity = orderEntity.getCustomer();
            if (null == customerEntity.getRewardPoints()) {
                customerEntity.setRewardPoints(5.0);
            }
            else {
                customerEntity.setRewardPoints(customerEntity.getRewardPoints()+5.0);
            }
            customerRepository.save(customerEntity);
            return "congratulations! you won 5 rewards";
        }
        return "Please provide valid order Id";
    }

    public String applyRewards(String orderId, Double rewardPoints){
        Optional<OrderEntity> order = orderRepository.findById(orderId);
        if (order.isPresent()){
            OrderEntity orderEntity = order.get();
            orderEntity.setDiscountPrice(rewardPoints);
            orderEntity.setTotalPrice(orderEntity.getPrice()-orderEntity.getDiscountPrice());
            orderRepository.save(orderEntity);
            return "reward applied with "+rewardPoints+" your total amount is : "+orderEntity.getTotalPrice();
        }
        return "please provide valid order ID";
    }
}
