package com.edupress.web.rest;

import com.edupress.repository.AssignmentRepository;
import com.edupress.service.AssignmentQueryService;
import com.edupress.service.AssignmentService;
import com.edupress.service.criteria.AssignmentCriteria;
import com.edupress.service.dto.AssignmentDTO;
import com.edupress.web.rest.errors.BadRequestAlertException;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.edupress.domain.Assignment}.
 */
@RestController
@RequestMapping("/api/assignments")
public class AssignmentResource {

    private static final Logger LOG = LoggerFactory.getLogger(AssignmentResource.class);

    private static final String ENTITY_NAME = "assignment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AssignmentService assignmentService;

    private final AssignmentRepository assignmentRepository;

    private final AssignmentQueryService assignmentQueryService;

    public AssignmentResource(
        AssignmentService assignmentService,
        AssignmentRepository assignmentRepository,
        AssignmentQueryService assignmentQueryService
    ) {
        this.assignmentService = assignmentService;
        this.assignmentRepository = assignmentRepository;
        this.assignmentQueryService = assignmentQueryService;
    }

    /**
     * {@code POST  /assignments} : Create a new assignment.
     *
     * @param assignmentDTO the assignmentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new assignmentDTO, or with status {@code 400 (Bad Request)} if the assignment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<AssignmentDTO> createAssignment(@Valid @RequestBody AssignmentDTO assignmentDTO) throws URISyntaxException {
        LOG.debug("REST request to save Assignment : {}", assignmentDTO);
        if (assignmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new assignment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        assignmentDTO = assignmentService.save(assignmentDTO);
        return ResponseEntity.created(new URI("/api/assignments/" + assignmentDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, assignmentDTO.getId().toString()))
            .body(assignmentDTO);
    }

    /**
     * {@code PUT  /assignments/:id} : Updates an existing assignment.
     *
     * @param id the id of the assignmentDTO to save.
     * @param assignmentDTO the assignmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assignmentDTO,
     * or with status {@code 400 (Bad Request)} if the assignmentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the assignmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<AssignmentDTO> updateAssignment(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AssignmentDTO assignmentDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Assignment : {}, {}", id, assignmentDTO);
        if (assignmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assignmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assignmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        assignmentDTO = assignmentService.update(assignmentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, assignmentDTO.getId().toString()))
            .body(assignmentDTO);
    }

    /**
     * {@code PATCH  /assignments/:id} : Partial updates given fields of an existing assignment, field will ignore if it is null
     *
     * @param id the id of the assignmentDTO to save.
     * @param assignmentDTO the assignmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assignmentDTO,
     * or with status {@code 400 (Bad Request)} if the assignmentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the assignmentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the assignmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<AssignmentDTO> partialUpdateAssignment(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AssignmentDTO assignmentDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Assignment partially : {}, {}", id, assignmentDTO);
        if (assignmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assignmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assignmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AssignmentDTO> result = assignmentService.partialUpdate(assignmentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, assignmentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /assignments} : get all the assignments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of assignments in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AssignmentDTO>> getAllAssignments(
        AssignmentCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Assignments by criteria: {}", criteria);

        Page<AssignmentDTO> page = assignmentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /assignments/count} : count all the assignments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countAssignments(AssignmentCriteria criteria) {
        LOG.debug("REST request to count Assignments by criteria: {}", criteria);
        return ResponseEntity.ok().body(assignmentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /assignments/:id} : get the "id" assignment.
     *
     * @param id the id of the assignmentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the assignmentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AssignmentDTO> getAssignment(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Assignment : {}", id);
        Optional<AssignmentDTO> assignmentDTO = assignmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(assignmentDTO);
    }

    /**
     * {@code DELETE  /assignments/:id} : delete the "id" assignment.
     *
     * @param id the id of the assignmentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteAssignment(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Assignment : {}", id);
        assignmentService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
