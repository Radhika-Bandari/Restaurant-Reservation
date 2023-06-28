package com.Task.Restaurant.Reservation.Repository;

import com.Task.Restaurant.Reservation.Entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity,String> {
}
