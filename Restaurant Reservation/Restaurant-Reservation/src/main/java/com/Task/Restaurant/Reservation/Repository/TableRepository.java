package com.Task.Restaurant.Reservation.Repository;

import com.Task.Restaurant.Reservation.Entity.TableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableRepository extends JpaRepository<TableEntity,String> {
}
