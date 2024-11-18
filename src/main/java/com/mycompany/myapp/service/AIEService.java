package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.AIE;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.AIE}.
 */
public interface AIEService {
    /**
     * Save a aIE.
     *
     * @param aIE the entity to save.
     * @return the persisted entity.
     */
    AIE save(AIE aIE);

    /**
     * Updates a aIE.
     *
     * @param aIE the entity to update.
     * @return the persisted entity.
     */
    AIE update(AIE aIE);

    /**
     * Partially updates a aIE.
     *
     * @param aIE the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AIE> partialUpdate(AIE aIE);

    /**
     * Get all the aIES.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AIE> findAll(Pageable pageable);

    /**
     * Get the "id" aIE.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AIE> findOne(String id);

    /**
     * Delete the "id" aIE.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
