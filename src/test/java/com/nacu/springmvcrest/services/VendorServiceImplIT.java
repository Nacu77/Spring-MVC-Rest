package com.nacu.springmvcrest.services;

import com.nacu.springmvcrest.api.mapper.VendorMapper;
import com.nacu.springmvcrest.api.model.VendorDTO;
import com.nacu.springmvcrest.bootstrap.Bootstrap;
import com.nacu.springmvcrest.domain.Vendor;
import com.nacu.springmvcrest.repositories.CategoryRepository;
import com.nacu.springmvcrest.repositories.CustomerRepository;
import com.nacu.springmvcrest.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class VendorServiceImplIT {

    @Autowired CustomerRepository customerRepository;
    @Autowired CategoryRepository categoryRepository;
    @Autowired VendorRepository vendorRepository;

    VendorService vendorService;

    @BeforeEach
    void setUp() throws Exception {
        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository, vendorRepository);
        bootstrap.run();

        vendorService = new VendorServiceImpl(vendorRepository, VendorMapper.INSTANCE);
    }

    @Test
    void patchVendorUpdateName() {
        String updatedName = "Updated Name";
        Long id = getVendorIdValue();

        Vendor originalVendor = vendorRepository.getOne(id);
        assertNotNull(originalVendor);

        VendorDTO vendorDTO = VendorDTO.builder().name(updatedName).build();
        vendorService.patchVendor(id, vendorDTO);
        Vendor updatedVendor = vendorRepository.findById(id).orElse(null);
        assertNotNull(updatedVendor);

        assertEquals(originalVendor.getId(), updatedVendor.getId());
        assertEquals(updatedName, updatedVendor.getName());
    }

    private Long getVendorIdValue() {
        List<Vendor> vendors = vendorRepository.findAll();
        return vendors.get(0).getId();
    }
}
