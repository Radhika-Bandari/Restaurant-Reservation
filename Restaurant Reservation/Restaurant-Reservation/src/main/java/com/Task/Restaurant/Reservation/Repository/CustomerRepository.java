package com.Task.Restaurant.Reservation.Repository;

import com.Task.Restaurant.Reservation.Entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity,String> {
}
