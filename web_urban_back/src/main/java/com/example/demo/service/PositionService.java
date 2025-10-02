package com.example.demo.service;

import com.example.demo.entity.Position;
import com.example.demo.repository.PositionRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PositionService {
  private final PositionRepository positionRepository;

  public PositionService(PositionRepository positionRepository) {
    this.positionRepository = positionRepository;
  }

  public List<Position> findAll() {
    return positionRepository.findAll();
  }

}
