package com.example.demo.service;

import com.example.demo.entity.Stop;
import com.example.demo.repository.StopRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class StopService {
  private final StopRepository stopRepository;

  public StopService(StopRepository stopRepository) {
    this.stopRepository = stopRepository;
  }

  public List<Stop> findAll() {
    return stopRepository.findAll();
  }

  public Stop save(Stop stop) {
    return stopRepository.save(stop);
  }

  public boolean deleteById(int id) {
    if (stopRepository.existsById(id)) {
      stopRepository.deleteById(id);
      return true;
    }
    return false;
  }

  public Optional<Stop> findById(int id) {
    return stopRepository.findById(id);
  }

  public Optional<Stop> update(int id, Stop updatedStop) {
    return stopRepository.findById(id).map((existing) -> {
      existing.setAddress(updatedStop.getAddress());
      existing.setStopName(updatedStop.getStopName());
      return existing;
    });
  }
}
