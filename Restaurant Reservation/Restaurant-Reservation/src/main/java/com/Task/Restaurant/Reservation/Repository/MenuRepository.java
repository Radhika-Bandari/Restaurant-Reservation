package com.Task.Restaurant.Reservation.Repository;

import com.Task.Restaurant.Reservation.Entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity,String> {
}
