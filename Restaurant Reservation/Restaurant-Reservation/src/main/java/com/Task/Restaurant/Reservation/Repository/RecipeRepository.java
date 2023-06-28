package com.Task.Restaurant.Reservation.Repository;

import com.Task.Restaurant.Reservation.Entity.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<RecipeEntity,String> {
    List<RecipeEntity> findByName(String recipeName);
}
