package com.Task.Restaurant.Reservation.Controller;

import com.Task.Restaurant.Reservation.Model.*;
import com.Task.Restaurant.Reservation.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/createCustomer")
    public CustomerModel createCustomer(@RequestBody final CustomerModel customerModel) {
        return customerService.createCustomer(customerModel);
    }

    @PostMapping("/bookOrder/{customer}")
    public String bookOrder(@PathVariable(value = "customer") final String customerId, @RequestParam final String tableNo, @RequestParam final String recipes) {
        return customerService.bookOrder(customerId, tableNo, recipes);
    }

    @PostMapping("/updateOrder/{orderId}")
    public String updateOrder(@PathVariable("orderId") final String orderId, @RequestParam final String recipes) {
        return customerService.updateOrder(orderId, recipes);
    }

    @PostMapping("/applyReward/{orderId}")
    public String applyRewardPointsToOrder(@PathVariable(value = "orderId") final String orderId, @RequestParam final Double rewardPoints) {
        return customerService.applyRewards(orderId, rewardPoints);
    }

    @PutMapping("/payment/{orderId}")
    public String payTheBill(@PathVariable(value = "orderId") final String orderId, @RequestParam String paymentMethod) {
        return customerService.payTheBill(orderId, paymentMethod);
    }
}
