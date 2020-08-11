package com.example.webfluxapi.controllers;

import com.example.webfluxapi.domain.Category;
import com.example.webfluxapi.domain.Vendor;
import com.example.webfluxapi.repositories.VendorRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/vendors")
public class VendorController {

    private final VendorRepository vendorRepository;

    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @GetMapping
    public Flux<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Vendor> getVendorById(@PathVariable String id) {
        return vendorRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Flux<Vendor> postVendor(@RequestBody Publisher<Vendor> vendorPublisher) {
        return vendorRepository.saveAll(vendorPublisher);
    }

    @PutMapping("/{id}")
    public Mono<Vendor> putVendor(@PathVariable String id ,@RequestBody Vendor vendor) {
        vendor.setId(id);
        return vendorRepository.save(vendor);
    }

    @PatchMapping("/{id}")
    public Flux<Vendor> patchVendor(@PathVariable String id ,@RequestBody Vendor vendor) {
        Mono<Vendor> updatedVendorMono = vendorRepository.findById(id)
                .map(foundVendor -> {
                    if (vendor.getFirstName() != null) {
                        foundVendor.setFirstName(vendor.getFirstName());
                    }
                    if (vendor.getLastName() != null) {
                        foundVendor.setLastName(vendor.getLastName());
                    }
                    return foundVendor;
                });

        return vendorRepository.saveAll(updatedVendorMono);
    }
}
