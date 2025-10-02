package com.example.demo.controller;

import com.example.demo.entity.Office;
import com.example.demo.service.OfficeService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/office")
public class OfficeController {
  private final OfficeService officeService;

  public OfficeController(OfficeService officeService) {
    this.officeService = officeService;
  }

  @GetMapping
  public ResponseEntity<List<Office>> findAll() {
    return ResponseEntity.ok(officeService.findAll());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteOffice(@PathVariable int id) {
    return officeService.deleteById(id) ? ResponseEntity.ok().build()
        : ResponseEntity.notFound().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Office> findById(@PathVariable int id) {
    return officeService.findById(id).map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PutMapping("/{id}")
  public ResponseEntity<Office> updateOffice(@PathVariable int id, @RequestBody
  Office updatedOffice) {
    return officeService.updateOffice(id, updatedOffice).map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Office> saveOffice(@RequestBody Office office) {
    return ResponseEntity.ok(officeService.save(office));
  }

}
