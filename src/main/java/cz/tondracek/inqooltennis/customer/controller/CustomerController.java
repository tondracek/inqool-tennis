package cz.tondracek.inqooltennis.customer.controller;

import cz.tondracek.inqooltennis.customer.dto.CreateCustomerDto;
import cz.tondracek.inqooltennis.customer.dto.CustomerDetailDto;
import cz.tondracek.inqooltennis.customer.dto.UpdateCustomerDto;
import cz.tondracek.inqooltennis.customer.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/customers")
@RestController
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    ResponseEntity<CustomerDetailDto> createCustomer(
            @Valid @RequestBody CreateCustomerDto dto) {
        return ResponseEntity.ok(customerService.createCustomer(dto));
    }

    @PutMapping("/{id}")
    ResponseEntity<CustomerDetailDto> updateCustomer(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateCustomerDto dto
    ) {
        return ResponseEntity.ok(customerService.updateCustomer(id, dto));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteCustomer(
            @PathVariable UUID id
    ) {
        customerService.softDeleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    ResponseEntity<CustomerDetailDto> getCustomerById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @GetMapping
    ResponseEntity<List<CustomerDetailDto>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }
}
