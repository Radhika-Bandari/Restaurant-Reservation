package com.Task.Restaurant.Reservation.Repository;

import com.Task.Restaurant.Reservation.Entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity,String> {
    List<RestaurantEntity> findByLocation(String location);
    List<RestaurantEntity> findByLocationAndType(String location, String type);
}
