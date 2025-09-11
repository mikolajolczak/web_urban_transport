package com.example.demo.service;

import com.example.demo.entity.Vehicle;
import com.example.demo.repository.VehicleRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {
  private final VehicleRepository vehicleRepository;

  public VehicleService(VehicleRepository vehicleRepository) {
    this.vehicleRepository = vehicleRepository;
  }

  public List<Vehicle> findAll() {
    return vehicleRepository.findAll();
  }

  public Optional<Vehicle> findById(Integer id) {
    return vehicleRepository.findById(id);
  }

  public Vehicle save(Vehicle vehicle) {
    return vehicleRepository.save(vehicle);
  }

  public Optional<Vehicle> update(int id, Vehicle updatedVehicle) {
    return vehicleRepository.findById(id).map((existing) -> {
      existing.setBrand(updatedVehicle.getBrand());
      existing.setModel(updatedVehicle.getModel());
      existing.setInsuranceDate(updatedVehicle.getInsuranceDate());
      existing.setProductionYear(updatedVehicle.getProductionYear());
      existing.setRegistrationNumber(updatedVehicle.getRegistrationNumber());
      existing.setPurchaseDate(updatedVehicle.getPurchaseDate());
      existing.setOffice(updatedVehicle.getOffice());
      return existing;
    });
  }

  public boolean deleteById(int id) {
    if (vehicleRepository.existsById(id)) {
      vehicleRepository.deleteById(id);
      return true;
    }
    return false;
  }

}
