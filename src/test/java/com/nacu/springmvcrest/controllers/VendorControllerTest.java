package com.nacu.springmvcrest.controllers;

import com.nacu.springmvcrest.api.model.VendorDTO;
import com.nacu.springmvcrest.services.VendorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static com.nacu.springmvcrest.controllers.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class VendorControllerTest {

    private final String BASE_URL = VendorController.BASE_URL;
    private final String NAME = "Florin Vendor SRL";

    @Mock
    VendorService vendorService;

    @InjectMocks
    VendorController vendorController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    void getAllVendors() throws Exception {
        List<VendorDTO> vendors = Arrays.asList(
                VendorDTO.builder().name(NAME).build(),
                VendorDTO.builder().name("George Vendor SRL").build()
        );
        when(vendorService.getAllVendors()).thenReturn(vendors);

        mockMvc.perform(get(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)))
                .andExpect(jsonPath("$.vendors[0].name", equalTo(NAME)));
    }

    @Test
    void getVendorById() throws Exception {
        VendorDTO vendorDTO = VendorDTO.builder().name(NAME).vendorUrl(BASE_URL + "/1").build();
        when(vendorService.getVendorById(anyLong())).thenReturn(vendorDTO);

        mockMvc.perform(get(BASE_URL + "/1")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.vendorUrl", equalTo(BASE_URL + "/1")));
    }

    @Test
    void createNewVendor() throws Exception {
        VendorDTO vendorDTO = VendorDTO.builder().name(NAME).build();
        VendorDTO returnDTO = VendorDTO.builder().name(NAME).vendorUrl(BASE_URL + "/1").build();
        when(vendorService.createNewVendor(vendorDTO)).thenReturn(returnDTO);

        mockMvc.perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(vendorDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.vendorUrl", equalTo(BASE_URL + "/1")));
    }

    @Test
    void updateVendor() throws Exception {
        VendorDTO vendorDTO = VendorDTO.builder().name(NAME).build();
        VendorDTO returnDTO = VendorDTO.builder().name(NAME).vendorUrl(BASE_URL + "/1").build();
        when(vendorService.saveVendorById(anyLong(), any(VendorDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(put(BASE_URL + "/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(vendorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.vendorUrl", equalTo(BASE_URL + "/1")));
    }

    @Test
    void patchVendor() throws Exception {
        VendorDTO vendorDTO = VendorDTO.builder().name(NAME).build();
        VendorDTO returnDTO = VendorDTO.builder().name(NAME).vendorUrl(BASE_URL + "/1").build();
        when(vendorService.patchVendor(anyLong(), any(VendorDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(patch(BASE_URL + "/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(vendorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.vendorUrl", equalTo(BASE_URL + "/1")));
    }

    @Test
    void deleteVendor() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/1")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(vendorService, times(1)).deleteVendorById(anyLong());
    }
}