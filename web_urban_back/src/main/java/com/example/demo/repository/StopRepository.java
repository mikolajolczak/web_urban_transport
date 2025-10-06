package com.example.demo.repository;

import com.example.demo.entity.Stop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link Stop} entities.
 */
@Repository
public interface StopRepository extends JpaRepository<Stop, Integer> {

}
