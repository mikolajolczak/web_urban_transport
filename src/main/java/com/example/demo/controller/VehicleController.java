package com.example.demo.controller;


import com.example.demo.entity.Vehicle;
import com.example.demo.service.VehicleService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/vehicle")
public class VehicleController {

  private final VehicleService vehicleService;

  public VehicleController(VehicleService vehicleService) {
    this.vehicleService = vehicleService;
  }

  @GetMapping
  public ResponseEntity<List<Vehicle>> findAllVehicles() {
    return ResponseEntity.ok(vehicleService.findAll());
  }

  @GetMapping
  @RequestMapping("/{id}")
  public ResponseEntity<Vehicle> findVehicleById(@PathVariable int id) {
    return vehicleService.findById(id).map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Vehicle> saveVehicle(@RequestBody Vehicle vehicle) {
    return ResponseEntity.ok(vehicleService.save(vehicle));
  }

  @PostMapping
  @RequestMapping("/{id}")
  public ResponseEntity<Vehicle> updateVehicle(@RequestBody Vehicle vehicle,
                                               @PathVariable int id) {
    return vehicleService.update(id, vehicle).map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping
  @RequestMapping("/{id}")
  public ResponseEntity<?> deleteVehicle(@PathVariable int id) {
    return vehicleService.deleteById(id) ? ResponseEntity.ok().build()
        : ResponseEntity.notFound().build();
  }

}
