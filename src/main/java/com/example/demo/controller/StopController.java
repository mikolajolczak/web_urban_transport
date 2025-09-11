package com.example.demo.controller;

import com.example.demo.entity.Stop;
import com.example.demo.service.StopService;
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
@RequestMapping("/api/stop")
public class StopController {
  private final StopService stopService;

  public StopController(StopService stopService) {
    this.stopService = stopService;
  }

  @GetMapping
  public ResponseEntity<List<Stop>> getStops() {
    return ResponseEntity.ok(stopService.findAll());
  }

  @PostMapping
  public ResponseEntity<Stop> saveStop(@RequestBody Stop stop) {
    return ResponseEntity.ok(stopService.save(stop));
  }

  @DeleteMapping
  @RequestMapping("/{id}")
  public ResponseEntity<Stop> deleteStop(@PathVariable int id) {
    return stopService.deleteById(id) ? ResponseEntity.ok().build()
        : ResponseEntity.notFound().build();
  }

  @GetMapping
  @RequestMapping("/{id}")
  public ResponseEntity<Stop> getStopById(@PathVariable int id) {
    return stopService.findById(id).map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  @RequestMapping("/{id}")
  public ResponseEntity<Stop> updateStop(@RequestBody Stop stop,
                                         @PathVariable int id) {
    return stopService.update(id, stop).map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }
}
