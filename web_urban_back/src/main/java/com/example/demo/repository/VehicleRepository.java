package com.example.demo.repository;


import com.example.demo.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link Vehicle} entities.
 */
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

}
