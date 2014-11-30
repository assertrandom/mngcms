package com.mlk007.mngcms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mlk007.mngcms.domain.Cabinet;
import com.mlk007.mngcms.repository.CabinetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Cabinet.
 */
@RestController
@RequestMapping("/app")
public class CabinetResource {

    private final Logger log = LoggerFactory.getLogger(CabinetResource.class);

    @Inject
    private CabinetRepository cabinetRepository;

    /**
     * POST  /rest/cabinets -> Create a new cabinet.
     */
    @RequestMapping(value = "/rest/cabinets",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Cabinet cabinet) {
        log.debug("REST request to save Cabinet : {}", cabinet);
        cabinetRepository.save(cabinet);
    }

    /**
     * GET  /rest/cabinets -> get all the cabinets.
     */
    @RequestMapping(value = "/rest/cabinets",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Cabinet> getAll() {
        log.debug("REST request to get all Cabinets");
        return cabinetRepository.findAll();
    }

    /**
     * GET  /rest/cabinets/:id -> get the "id" cabinet.
     */
    @RequestMapping(value = "/rest/cabinets/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cabinet> get(@PathVariable String id) {
        log.debug("REST request to get Cabinet : {}", id);
        return Optional.ofNullable(cabinetRepository.findOne(id))
            .map(cabinet -> new ResponseEntity<>(
                cabinet,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rest/cabinets/:id -> delete the "id" cabinet.
     */
    @RequestMapping(value = "/rest/cabinets/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable String id) {
        log.debug("REST request to delete Cabinet : {}", id);
        cabinetRepository.delete(id);
    }
}
