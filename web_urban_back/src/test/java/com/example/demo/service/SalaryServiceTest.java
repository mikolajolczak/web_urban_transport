package com.example.demo.service;

import com.example.demo.entity.Salary;
import com.example.demo.repository.SalaryRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class SalaryServiceTest {

  @Mock
  private SalaryRepository salaryRepository;

  @InjectMocks
  private SalaryService salaryService;

  private AutoCloseable mocks;

  @BeforeEach
  void setUp() {
    mocks = MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  void tearDown() throws Exception {
    mocks.close();
  }

  @Test
  @DisplayName("Should return all salaries when repository has data")
  void shouldReturnAllSalaries() {
    Salary s1 = new Salary();
    s1.setId(1);
    s1.setAmount(5000);

    Salary s2 = new Salary();
    s2.setId(2);
    s2.setAmount(7000);

    when(salaryRepository.findAll()).thenReturn(Arrays.asList(s1, s2));

    List<Salary> result = salaryService.findAll();

    assertThat(result)
        .isNotNull()
        .hasSize(2)
        .containsExactly(s1, s2);
    verify(salaryRepository, times(1)).findAll();
    verifyNoMoreInteractions(salaryRepository);
  }

  @Test
  @DisplayName("Should return empty list when repository has no data")
  void shouldReturnEmptyList() {
    when(salaryRepository.findAll()).thenReturn(Collections.emptyList());

    List<Salary> result = salaryService.findAll();

    assertThat(result)
        .isNotNull()
        .isEmpty();
    verify(salaryRepository, times(1)).findAll();
    verifyNoMoreInteractions(salaryRepository);
  }

  @Test
  @DisplayName("Should handle null result from repository gracefully")
  void shouldHandleNullResultFromRepository() {
    when(salaryRepository.findAll()).thenReturn(null);

    List<Salary> result = salaryService.findAll();

    assertThat(result).isNull();
    verify(salaryRepository, times(1)).findAll();
    verifyNoMoreInteractions(salaryRepository);
  }
}
