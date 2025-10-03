package com.example.demo.repository;

import com.example.demo.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link Position} entities.
 */
@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {
}
