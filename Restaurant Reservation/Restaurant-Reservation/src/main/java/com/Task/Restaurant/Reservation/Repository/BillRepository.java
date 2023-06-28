package com.Task.Restaurant.Reservation.Repository;

import com.Task.Restaurant.Reservation.Entity.BillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<BillEntity,String> {
}
