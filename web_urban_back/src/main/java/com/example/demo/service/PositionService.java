package com.example.demo.service;

import com.example.demo.entity.Position;
import com.example.demo.repository.PositionRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for handling operations related to
 * {@link Position} entities.
 *
 * <p>This class provides business logic for retrieving all positions from the
 * underlying data store.
 * </p>
 */
@Service
public class PositionService {

  /**
   * Repository providing access to {@link Position} data.
   */
  private final PositionRepository positionRepository;

  /**
   * Constructs a new {@code PositionService} with the given
   * {@link PositionRepository}.
   *
   * @param positionRepositoryParam the repository used to access position data
   */
  public PositionService(final PositionRepository positionRepositoryParam) {
    this.positionRepository = positionRepositoryParam;
  }

  /**
   * Retrieves all {@link Position} entities from the repository.
   *
   * @return a list of all positions; never {@code null}, may be empty
   */
  public List<Position> findAll() {
    return positionRepository.findAll();
  }
}
