package com.example.demo.service;


import com.example.demo.entity.Office;
import com.example.demo.repository.OfficeRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class OfficeService {
  private final OfficeRepository officeRepository;

  public OfficeService(OfficeRepository officeRepository) {
    this.officeRepository = officeRepository;
  }

  public List<Office> findAll() {
    return officeRepository.findAll();
  }

  public boolean deleteById(Integer id) {
    if (officeRepository.existsById(id)) {
      officeRepository.deleteById(id);
      return true;
    }
    return false;
  }

  public Optional<Office> findById(Integer id) {
    return officeRepository.findById(id);
  }

  public Optional<Office> updateOffice(int id, Office updatedOffice) {
    return officeRepository.findById(id).map((existing) -> {
      existing.setAddress(updatedOffice.getAddress());
      existing.setAddress(updatedOffice.getAddress());
      return officeRepository.save(existing);
    });
  }

  public Office save(Office office) {
    return officeRepository.save(office);
  }
}
