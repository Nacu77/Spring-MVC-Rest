package com.nacu.springmvcrest.api.mapper;

import com.nacu.springmvcrest.api.model.VendorDTO;
import com.nacu.springmvcrest.domain.Vendor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VendorMapperTest {

    private final Long ID = 1L;
    private final String NAME = "Florin";

    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    @Test
    void vendorToVendorDTO() {
        Vendor vendor = Vendor.builder().id(ID).name(NAME).build();
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);

        assertEquals(NAME, vendorDTO.getName());
    }

    @Test
    void vendorDtoToVendor() {
        VendorDTO vendorDTO = VendorDTO.builder().name(NAME).build();
        Vendor vendor = vendorMapper.vendorDtoToVendor(vendorDTO);

        assertEquals(NAME, vendor.getName());
    }
}