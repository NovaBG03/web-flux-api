package com.example.webfluxapi.controllers;

import com.example.webfluxapi.domain.Category;
import com.example.webfluxapi.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith({MockitoExtension.class})
class CategoryControllerTest {

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryController categoryController;

    WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    void getAllCategories() {
        given(categoryRepository.findAll())
                .willReturn(Flux.just(Category.builder().id("id1").build(),
                        Category.builder().id("id2").build()));

        webTestClient.get().uri("/api/v1/categories")
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    void getCategoryById() {
        final String id = "id";

        given(categoryRepository.findById(id))
                .willReturn(Mono.just(Category.builder().id(id).build()));

        webTestClient.get().uri("/api/v1/categories/" + id)
                .exchange()
                .expectBody(Category.class);
    }
}