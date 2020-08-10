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
        // categoryRepository.deleteAll().block();
        // vendorRepository.deleteAll().block();

        if (categoryRepository.count().block() == 0) {
            loadCategories();
        }

        if (vendorRepository.count().block() == 0) {
            loadVendors();
        }

    }

    private void loadCategories() {
        Category fruits = new Category();
        fruits.setDescription("Fruits");
        categoryRepository.save(fruits).block();

        Category dried = new Category();
        dried.setDescription("Dried");
        categoryRepository.save(dried).block();

        Category fresh = new Category();
        fresh.setDescription("Fresh");
        categoryRepository.save(fresh).block();

        Category exotic = new Category();
        exotic.setDescription("Exotic");
        categoryRepository.save(exotic).block();

        Category nuts = new Category();
        nuts.setDescription("Nuts");
        categoryRepository.save(nuts).block();

        log.info("Loaded Categories: " + categoryRepository.count().block());
    }

    private void loadVendors() {
        Vendor michael = new Vendor();
        michael.setFirstName("Michael");
        michael.setLastName("Lachappele");
        vendorRepository.save(michael).block();

        Vendor ivan = new Vendor();
        ivan.setFirstName("Ivan");
        ivan.setLastName("Georgiev");
        vendorRepository.save(ivan).block();

        Vendor david = new Vendor();
        david.setFirstName("David");
        david.setLastName("Winter");
        vendorRepository.save(david).block();

        Vendor anne = new Vendor();
        anne.setFirstName("Anne");
        anne.setLastName("Hine");
        vendorRepository.save(anne).block();

        Vendor joe = new Vendor();
        joe.setFirstName("Joe");
        joe.setLastName("Buck");
        vendorRepository.save(joe).block();

        log.info("Loaded Vendors: " + vendorRepository.count().block());
    }

}
