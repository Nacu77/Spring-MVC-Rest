package com.nacu.springmvcrest.bootstrap;

import com.nacu.springmvcrest.domain.Category;
import com.nacu.springmvcrest.domain.Customer;
import com.nacu.springmvcrest.repositories.CategoryRepository;
import com.nacu.springmvcrest.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCategories();
        loadCustomers();
    }

    private void loadCustomers() {
        customerRepository.save(Customer.builder().firstName("Florin").lastName("Nacu").build());
        customerRepository.save(Customer.builder().firstName("Cristiano").lastName("Ronaldo").build());
        customerRepository.save(Customer.builder().firstName("Paulo").lastName("Dybala").build());

        log.info("Customers Loaded = " + customerRepository.count());
    }

    private void loadCategories() {
        categoryRepository.save(Category.builder().name("Fruits").build());
        categoryRepository.save(Category.builder().name("Dried").build());
        categoryRepository.save(Category.builder().name("Fresh").build());
        categoryRepository.save(Category.builder().name("Exotic").build());
        categoryRepository.save(Category.builder().name("Nuts").build());

        log.info("Categories Loaded = " + categoryRepository.count());
    }

}
