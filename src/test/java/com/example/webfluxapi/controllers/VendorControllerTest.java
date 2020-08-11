package com.example.webfluxapi.controllers;

import com.example.webfluxapi.domain.Category;
import com.example.webfluxapi.domain.Vendor;
import com.example.webfluxapi.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class VendorControllerTest {

    @Mock
    VendorRepository vendorRepository;

    @InjectMocks
    VendorController vendorController;

    WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    void getAllVendors() {
        given(vendorRepository.findAll())
                .willReturn(Flux.just(Vendor.builder().id("id1").build(),
                        Vendor.builder().id("id2").build()));

        webTestClient.get()
                .uri("/api/v1/vendors")
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    void getVendorById() {
        final String id = "id";

        given(vendorRepository.findById(id))
                .willReturn(Mono.just(Vendor.builder().id(id).build()));

        webTestClient.get()
                .uri("/api/v1/vendors/" + id)
                .exchange()
                .expectBody(Vendor.class);
    }

    @Test
    void postVendor() {
        final String id = "id";

        given(vendorRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Vendor.builder().id(id).build()));

        Mono<Vendor> vendorMono = Mono.just(Vendor.builder().build());

        webTestClient.post()
                .uri("/api/v1/vendors")
                .body(vendorMono, Vendor.class)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    void putVendor() {
        final String id = "id";

        given(vendorRepository.save(any()))
                .willReturn(Mono.just(Vendor.builder().id(id).build()));

        Mono<Vendor> vendorMono = Mono.just(Vendor.builder().build());

        webTestClient.put()
                .uri("/api/v1/vendors/" + id)
                .body(vendorMono, Vendor.class)
                .exchange()
                .expectStatus().isOk();
    }
}