package com.example.demo.repository;

import com.example.demo.entity.RouteStop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteStopRepository extends JpaRepository<RouteStop, Integer> {
}
