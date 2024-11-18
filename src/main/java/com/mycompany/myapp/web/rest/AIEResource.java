package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.AIE;
import com.mycompany.myapp.repository.AIERepository;
import com.mycompany.myapp.service.AIEService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.AIE}.
 */
@RestController
@RequestMapping("/api/aies")
public class AIEResource {

    private static final Logger LOG = LoggerFactory.getLogger(AIEResource.class);

    private static final String ENTITY_NAME = "jhipsterSampleApplication1118Aie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AIEService aIEService;

    private final AIERepository aIERepository;

    public AIEResource(AIEService aIEService, AIERepository aIERepository) {
        this.aIEService = aIEService;
        this.aIERepository = aIERepository;
    }

    /**
     * {@code POST  /aies} : Create a new aIE.
     *
     * @param aIE the aIE to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aIE, or with status {@code 400 (Bad Request)} if the aIE has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AIE> createAIE(@Valid @RequestBody AIE aIE) throws URISyntaxException {
        LOG.debug("REST request to save AIE : {}", aIE);
        if (aIE.getId() != null) {
            throw new BadRequestAlertException("A new aIE cannot already have an ID", ENTITY_NAME, "idexists");
        }
        aIE = aIEService.save(aIE);
        return ResponseEntity.created(new URI("/api/aies/" + aIE.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, aIE.getId()))
            .body(aIE);
    }

    /**
     * {@code PUT  /aies/:id} : Updates an existing aIE.
     *
     * @param id the id of the aIE to save.
     * @param aIE the aIE to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aIE,
     * or with status {@code 400 (Bad Request)} if the aIE is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aIE couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AIE> updateAIE(@PathVariable(value = "id", required = false) final String id, @Valid @RequestBody AIE aIE)
        throws URISyntaxException {
        LOG.debug("REST request to update AIE : {}, {}", id, aIE);
        if (aIE.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aIE.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aIERepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        aIE = aIEService.update(aIE);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, aIE.getId())).body(aIE);
    }

    /**
     * {@code PATCH  /aies/:id} : Partial updates given fields of an existing aIE, field will ignore if it is null
     *
     * @param id the id of the aIE to save.
     * @param aIE the aIE to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aIE,
     * or with status {@code 400 (Bad Request)} if the aIE is not valid,
     * or with status {@code 404 (Not Found)} if the aIE is not found,
     * or with status {@code 500 (Internal Server Error)} if the aIE couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AIE> partialUpdateAIE(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody AIE aIE
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update AIE partially : {}, {}", id, aIE);
        if (aIE.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aIE.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aIERepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AIE> result = aIEService.partialUpdate(aIE);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, aIE.getId()));
    }

    /**
     * {@code GET  /aies} : get all the aIES.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aIES in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AIE>> getAllAIES(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of AIES");
        Page<AIE> page = aIEService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /aies/:id} : get the "id" aIE.
     *
     * @param id the id of the aIE to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aIE, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AIE> getAIE(@PathVariable("id") String id) {
        LOG.debug("REST request to get AIE : {}", id);
        Optional<AIE> aIE = aIEService.findOne(id);
        return ResponseUtil.wrapOrNotFound(aIE);
    }

    /**
     * {@code DELETE  /aies/:id} : delete the "id" aIE.
     *
     * @param id the id of the aIE to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAIE(@PathVariable("id") String id) {
        LOG.debug("REST request to delete AIE : {}", id);
        aIEService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
