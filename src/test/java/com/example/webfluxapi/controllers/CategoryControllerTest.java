package com.example.webfluxapi.controllers;

import com.example.webfluxapi.domain.Category;
import com.example.webfluxapi.repositories.CategoryRepository;
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

    @Test
    void postCategory() {
        final String id = "id";
        final String description = "description";

        given(categoryRepository.saveAll(any(Publisher.class)))
            .willReturn(Flux.just(Category.builder().id(id).description(description).build()));

        Mono<Category> categoryMono = Mono.just(Category.builder().description(description).build());

        webTestClient.post()
                .uri("/api/v1/categories")
                .body(categoryMono, Category.class)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    void putCategory() {
        final String id = "id";
        final String description = "description";

        given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().id(id).description(description).build()));

        Mono<Category> categoryMono = Mono.just(Category.builder().description(description).build());

        webTestClient.put()
                .uri("/api/v1/categories/" + id)
                .body(categoryMono, Category.class)
                .exchange()
                .expectStatus().isOk();
    }
}