package com.example.demo.UnitTest;

import com.example.demo.Domain.Customer;
import com.example.demo.Repository.CustomerRepository;
import com.example.demo.Service.Data.CommonService;
import com.example.demo.Service.Data.Implementation.CommonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/***
 * - CommonServiceImplTest uses Customer entity to test the implementation of generic CRUD operations in CommonService.
 * - Since all repository extends from CrudRepository, the behaviors of CustomerRepository can be representative for all repositories.
 */
@ExtendWith(MockitoExtension.class)
public class CommonServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CommonServiceImpl<Customer, Long> service;

    @BeforeEach
    public void setUp() {}

    @Test
    public void findAll_ShouldReturnAllCustomers() {
        // Arrange
        List<Customer> expectedCustomers = List.of(
                new Customer("email1@gmail.com", "customer1", "lastname1", "", "", "", ""),
                new Customer("email2@gmail.com", "customer2", "lastname2", "", "", "", "")
        );
        when(customerRepository.findAll()).thenReturn(expectedCustomers);

        // Act
        List<Customer> actual = service.findAll();

        // Assert
        assertNotNull(actual);
        assertEquals(expectedCustomers, actual);
    }

    @Test
    public void findById_ShouldReturnCustomer_WhenCustomerFound() {
        // Arrange
        long customerId = 1L;
        Customer expected = new Customer("email1@gmail.com", "customer1", "lastname1", "", "", "", "");
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(expected));

        // Act
        Customer actual = service.findById(customerId);

        // Assert
        assertNotNull(actual);
        assertEquals(expected, actual);
    }
    @Test
    public void findById_ShouldThrowException_WhenCustomerNotFound() {
        // Arrange
        long customerId = 99L;

        // Act
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.findById(customerId));

        // Assert
        assertEquals(
                "Could not find object with id: " + customerId,
                exception.getMessage());
    }

    @Test
    public void deleteById_ShouldDeleteCustomer_WhenCustomerFound() {
        // Arrange
        long customerId = 1L;

        // Act

        // Assert
    }
    @Test
    public void deleteById_ShouldDoNothing_WhenCustomerNotFound() {
        // Arrange
        long customerId = 99L;

        // Act

        // Assert
    }

    @Test
    public void save_ShouldSaveCustomer() {
        // Arrange
        Customer expected = new Customer("email1@gmail.com", "customer1", "lastname1", "", "", "", "");

        // Act
        service.save(expected);

        // Assert
        verify(customerRepository).save(expected);
    }

    @Test
    public void saveAll_ShouldSaveAllCustomers() {
        // Arrange
        List<Customer> expectedCustomers = List.of(
                new Customer("email1@gmail.com", "customer1", "lastname1", "", "", "", ""),
                new Customer("email2@gmail.com", "customer2", "lastname2", "", "", "", "")
        );

        // Act
        service.saveAll(expectedCustomers);

        // Assert
        verify(customerRepository).saveAll(expectedCustomers);
    }
}
