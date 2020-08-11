package com.example.webfluxapi.bootstrap;

import com.example.webfluxapi.domain.Category;
import com.example.webfluxapi.domain.Vendor;
import com.example.webfluxapi.repositories.CategoryRepository;
import com.example.webfluxapi.repositories.VendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;

    public DataLoader(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        /*categoryRepository.deleteAll().block();
        vendorRepository.deleteAll().block();*/

        if (categoryRepository.count().block() == 0) {
            loadCategories();
        }

        if (vendorRepository.count().block() == 0) {
            loadVendors();
        }

    }

    private void loadCategories() {
        Category fruits = Category.builder().description("Fruits").build();
        categoryRepository.save(fruits).block();

        Category dried = Category.builder().description("Dried").build();
        categoryRepository.save(dried).block();

        Category fresh = Category.builder().description("Fresh").build();
        categoryRepository.save(fresh).block();

        Category exotic = Category.builder().description("Exotic").build();
        categoryRepository.save(exotic).block();

        Category nuts = Category.builder().description("Nuts").build();
        categoryRepository.save(nuts).block();

        log.info("Loaded Categories: " + categoryRepository.count().block());
    }

    private void loadVendors() {
        Vendor michael = Vendor.builder().firstName("Michael").lastName("Lachappele").build();
        vendorRepository.save(michael).block();

        Vendor ivan = Vendor.builder().firstName("Ivan").lastName("Georgiev").build();
        vendorRepository.save(ivan).block();

        Vendor david = Vendor.builder().firstName("David").lastName("Winter").build();
        vendorRepository.save(david).block();

        Vendor anne = Vendor.builder().firstName("Anne").lastName("Hine").build();
        vendorRepository.save(anne).block();

        Vendor joe = Vendor.builder().firstName("Joe").lastName("Buck").build();
        vendorRepository.save(joe).block();

        log.info("Loaded Vendors: " + vendorRepository.count().block());
    }

}
