package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.AIE;
import com.mycompany.myapp.repository.AIERepository;
import com.mycompany.myapp.service.AIEService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.AIE}.
 */
@Service
@Transactional
public class AIEServiceImpl implements AIEService {

    private static final Logger LOG = LoggerFactory.getLogger(AIEServiceImpl.class);

    private final AIERepository aIERepository;

    public AIEServiceImpl(AIERepository aIERepository) {
        this.aIERepository = aIERepository;
    }

    @Override
    public AIE save(AIE aIE) {
        LOG.debug("Request to save AIE : {}", aIE);
        return aIERepository.save(aIE);
    }

    @Override
    public AIE update(AIE aIE) {
        LOG.debug("Request to update AIE : {}", aIE);
        return aIERepository.save(aIE);
    }

    @Override
    public Optional<AIE> partialUpdate(AIE aIE) {
        LOG.debug("Request to partially update AIE : {}", aIE);

        return aIERepository
            .findById(aIE.getId())
            .map(existingAIE -> {
                if (aIE.getName() != null) {
                    existingAIE.setName(aIE.getName());
                }
                if (aIE.getType() != null) {
                    existingAIE.setType(aIE.getType());
                }
                if (aIE.getDescription() != null) {
                    existingAIE.setDescription(aIE.getDescription());
                }
                if (aIE.getCreatedAt() != null) {
                    existingAIE.setCreatedAt(aIE.getCreatedAt());
                }
                if (aIE.getCreatedBy() != null) {
                    existingAIE.setCreatedBy(aIE.getCreatedBy());
                }
                if (aIE.getIcon() != null) {
                    existingAIE.setIcon(aIE.getIcon());
                }
                if (aIE.getVersion() != null) {
                    existingAIE.setVersion(aIE.getVersion());
                }
                if (aIE.getCategory() != null) {
                    existingAIE.setCategory(aIE.getCategory());
                }
                if (aIE.getRate() != null) {
                    existingAIE.setRate(aIE.getRate());
                }
                if (aIE.getAieMetadata() != null) {
                    existingAIE.setAieMetadata(aIE.getAieMetadata());
                }
                if (aIE.getUserID() != null) {
                    existingAIE.setUserID(aIE.getUserID());
                }
                if (aIE.getIsPublic() != null) {
                    existingAIE.setIsPublic(aIE.getIsPublic());
                }
                if (aIE.getOrganizationName() != null) {
                    existingAIE.setOrganizationName(aIE.getOrganizationName());
                }
                if (aIE.getTenantID() != null) {
                    existingAIE.setTenantID(aIE.getTenantID());
                }

                return existingAIE;
            })
            .map(aIERepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AIE> findAll(Pageable pageable) {
        LOG.debug("Request to get all AIES");
        return aIERepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AIE> findOne(String id) {
        LOG.debug("Request to get AIE : {}", id);
        return aIERepository.findById(id);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete AIE : {}", id);
        aIERepository.deleteById(id);
    }
}
