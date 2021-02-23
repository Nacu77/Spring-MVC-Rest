package com.nacu.springmvcrest.services;

import com.nacu.springmvcrest.api.mapper.VendorMapper;
import com.nacu.springmvcrest.api.model.VendorDTO;
import com.nacu.springmvcrest.controllers.VendorController;
import com.nacu.springmvcrest.domain.Vendor;
import com.nacu.springmvcrest.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VendorServiceImplTest {

    private final String BASE_URL = VendorController.BASE_URL;
    private final Long ID = 1L;
    private final String NAME = "Florin Vendor SRL";

    @Mock
    VendorRepository vendorRepository;

    VendorService vendorService;

    @BeforeEach
    void setUp() {
        vendorService = new VendorServiceImpl(vendorRepository, VendorMapper.INSTANCE);
    }

    @Test
    void getAllVendors() {
        List<Vendor> vendors = Arrays.asList(
                Vendor.builder().id(ID).name(NAME).build(),
                Vendor.builder().id(2L).name("George Vendor SRL").build()
        );
        when(vendorRepository.findAll()).thenReturn(vendors);
        List<VendorDTO> vendorDTOS = vendorService.getAllVendors();

        assertEquals(2, vendorDTOS.size());
        assertEquals(NAME, vendorDTOS.get(0).getName());
        assertEquals(BASE_URL + "/" + ID, vendorDTOS.get(0).getVendorUrl());
        assertEquals("George Vendor SRL", vendorDTOS.get(1).getName());
    }

    @Test
    void getVendorById() {
        Vendor vendor = Vendor.builder().id(ID).name(NAME).build();
        Optional<Vendor> vendorOptional = Optional.of(vendor);
        when(vendorRepository.findById(anyLong())).thenReturn(vendorOptional);
        VendorDTO vendorDTO = vendorService.getVendorById(ID);

        assertEquals(NAME, vendorDTO.getName());
        assertEquals(BASE_URL + "/" + ID, vendorDTO.getVendorUrl());
    }

    @Test
    void createNewVendor() {
        VendorDTO vendorDTO = VendorDTO.builder().name(NAME).build();
        Vendor savedVendor = Vendor.builder().id(ID).name(NAME).build();
        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);
        VendorDTO returnDTO = vendorService.createNewVendor(vendorDTO);

        assertEquals(vendorDTO.getName(), returnDTO.getName());
        assertEquals(BASE_URL + "/" + ID, returnDTO.getVendorUrl());
    }

    @Test
    void saveVendorById() {
        VendorDTO vendorDTO = VendorDTO.builder().name(NAME).build();
        Vendor savedVendor = Vendor.builder().id(ID).name(NAME).build();
        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);
        VendorDTO returnDTO = vendorService.saveVendorById(ID, vendorDTO);

        assertEquals(vendorDTO.getName(), returnDTO.getName());
        assertEquals(BASE_URL + "/" + ID, returnDTO.getVendorUrl());
    }

    @Test
    void deleteVendorById() {
        vendorService.deleteVendorById(ID);
        verify(vendorRepository, times(1)).deleteById(ID);
    }
}