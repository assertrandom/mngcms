package com.mlk007.mngcms.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.joda.time.LocalDate;
import java.util.List;

import com.mlk007.mngcms.Application;
import com.mlk007.mngcms.domain.Cabinet;
import com.mlk007.mngcms.repository.CabinetRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CabinetResource REST controller.
 *
 * @see CabinetResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class CabinetResourceTest {

    private static final String DEFAULT_CABINET = "SAMPLE_TEXT";
    private static final String UPDATED_CABINET = "UPDATED_TEXT";
    
    private static final String DEFAULT_OWNER = "SAMPLE_TEXT";
    private static final String UPDATED_OWNER = "UPDATED_TEXT";
    
    private static final LocalDate DEFAULT_DATE_CREATED = new LocalDate(0L);
    private static final LocalDate UPDATED_DATE_CREATED = new LocalDate();
    
    private static final LocalDate DEFAULT_DATE_LAST_MODIFIED = new LocalDate(0L);
    private static final LocalDate UPDATED_DATE_LAST_MODIFIED = new LocalDate();
    

    @Inject
    private CabinetRepository cabinetRepository;

    private MockMvc restCabinetMockMvc;

    private Cabinet cabinet;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CabinetResource cabinetResource = new CabinetResource();
        ReflectionTestUtils.setField(cabinetResource, "cabinetRepository", cabinetRepository);
        this.restCabinetMockMvc = MockMvcBuilders.standaloneSetup(cabinetResource).build();
    }

    @Before
    public void initTest() {
        cabinetRepository.deleteAll();
        cabinet = new Cabinet();
        cabinet.setCabinet(DEFAULT_CABINET);
        cabinet.setOwner(DEFAULT_OWNER);
        cabinet.setDateCreated(DEFAULT_DATE_CREATED);
        cabinet.setDateLastModified(DEFAULT_DATE_LAST_MODIFIED);
    }

    @Test
    public void createCabinet() throws Exception {
        // Validate the database is empty
        assertThat(cabinetRepository.findAll()).hasSize(0);

        // Create the Cabinet
        restCabinetMockMvc.perform(post("/app/rest/cabinets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cabinet)))
                .andExpect(status().isOk());

        // Validate the Cabinet in the database
        List<Cabinet> cabinets = cabinetRepository.findAll();
        assertThat(cabinets).hasSize(1);
        Cabinet testCabinet = cabinets.iterator().next();
        assertThat(testCabinet.getCabinet()).isEqualTo(DEFAULT_CABINET);
        assertThat(testCabinet.getOwner()).isEqualTo(DEFAULT_OWNER);
        assertThat(testCabinet.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testCabinet.getDateLastModified()).isEqualTo(DEFAULT_DATE_LAST_MODIFIED);
    }

    @Test
    public void getAllCabinets() throws Exception {
        // Initialize the database
        cabinetRepository.save(cabinet);

        // Get all the cabinets
        restCabinetMockMvc.perform(get("/app/rest/cabinets"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(cabinet.getId()))
                .andExpect(jsonPath("$.[0].cabinet").value(DEFAULT_CABINET.toString()))
                .andExpect(jsonPath("$.[0].owner").value(DEFAULT_OWNER.toString()))
                .andExpect(jsonPath("$.[0].dateCreated").value(DEFAULT_DATE_CREATED.toString()))
                .andExpect(jsonPath("$.[0].dateLastModified").value(DEFAULT_DATE_LAST_MODIFIED.toString()));
    }

    @Test
    public void getCabinet() throws Exception {
        // Initialize the database
        cabinetRepository.save(cabinet);

        // Get the cabinet
        restCabinetMockMvc.perform(get("/app/rest/cabinets/{id}", cabinet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cabinet.getId()))
            .andExpect(jsonPath("$.cabinet").value(DEFAULT_CABINET.toString()))
            .andExpect(jsonPath("$.owner").value(DEFAULT_OWNER.toString()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateLastModified").value(DEFAULT_DATE_LAST_MODIFIED.toString()));
    }

    @Test
    public void getNonExistingCabinet() throws Exception {
        // Get the cabinet
        restCabinetMockMvc.perform(get("/app/rest/cabinets/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateCabinet() throws Exception {
        // Initialize the database
        cabinetRepository.save(cabinet);

        // Update the cabinet
        cabinet.setCabinet(UPDATED_CABINET);
        cabinet.setOwner(UPDATED_OWNER);
        cabinet.setDateCreated(UPDATED_DATE_CREATED);
        cabinet.setDateLastModified(UPDATED_DATE_LAST_MODIFIED);
        restCabinetMockMvc.perform(post("/app/rest/cabinets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cabinet)))
                .andExpect(status().isOk());

        // Validate the Cabinet in the database
        List<Cabinet> cabinets = cabinetRepository.findAll();
        assertThat(cabinets).hasSize(1);
        Cabinet testCabinet = cabinets.iterator().next();
        assertThat(testCabinet.getCabinet()).isEqualTo(UPDATED_CABINET);
        assertThat(testCabinet.getOwner()).isEqualTo(UPDATED_OWNER);
        assertThat(testCabinet.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testCabinet.getDateLastModified()).isEqualTo(UPDATED_DATE_LAST_MODIFIED);;
    }

    @Test
    public void deleteCabinet() throws Exception {
        // Initialize the database
        cabinetRepository.save(cabinet);

        // Get the cabinet
        restCabinetMockMvc.perform(delete("/app/rest/cabinets/{id}", cabinet.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Cabinet> cabinets = cabinetRepository.findAll();
        assertThat(cabinets).hasSize(0);
    }
}
