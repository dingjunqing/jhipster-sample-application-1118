package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.AIEAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.AIE;
import com.mycompany.myapp.repository.AIERepository;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AIEResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AIEResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_ICON = "AAAAAAAAAA";
    private static final String UPDATED_ICON = "BBBBBBBBBB";

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final Double DEFAULT_RATE = 1D;
    private static final Double UPDATED_RATE = 2D;

    private static final String DEFAULT_AIE_METADATA = "AAAAAAAAAA";
    private static final String UPDATED_AIE_METADATA = "BBBBBBBBBB";

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_PUBLIC = false;
    private static final Boolean UPDATED_IS_PUBLIC = true;

    private static final String DEFAULT_ORGANIZATION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZATION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TENANT_ID = "AAAAAAAAAA";
    private static final String UPDATED_TENANT_ID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/aies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AIERepository aIERepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAIEMockMvc;

    private AIE aIE;

    private AIE insertedAIE;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AIE createEntity() {
        return new AIE()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .createdAt(DEFAULT_CREATED_AT)
            .createdBy(DEFAULT_CREATED_BY)
            .icon(DEFAULT_ICON)
            .version(DEFAULT_VERSION)
            .category(DEFAULT_CATEGORY)
            .rate(DEFAULT_RATE)
            .aieMetadata(DEFAULT_AIE_METADATA)
            .userID(DEFAULT_USER_ID)
            .isPublic(DEFAULT_IS_PUBLIC)
            .organizationName(DEFAULT_ORGANIZATION_NAME)
            .tenantID(DEFAULT_TENANT_ID);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AIE createUpdatedEntity() {
        return new AIE()
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION)
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .icon(UPDATED_ICON)
            .version(UPDATED_VERSION)
            .category(UPDATED_CATEGORY)
            .rate(UPDATED_RATE)
            .aieMetadata(UPDATED_AIE_METADATA)
            .userID(UPDATED_USER_ID)
            .isPublic(UPDATED_IS_PUBLIC)
            .organizationName(UPDATED_ORGANIZATION_NAME)
            .tenantID(UPDATED_TENANT_ID);
    }

    @BeforeEach
    public void initTest() {
        aIE = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedAIE != null) {
            aIERepository.delete(insertedAIE);
            insertedAIE = null;
        }
    }

    @Test
    @Transactional
    void createAIE() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AIE
        var returnedAIE = om.readValue(
            restAIEMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aIE)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AIE.class
        );

        // Validate the AIE in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAIEUpdatableFieldsEquals(returnedAIE, getPersistedAIE(returnedAIE));

        insertedAIE = returnedAIE;
    }

    @Test
    @Transactional
    void createAIEWithExistingId() throws Exception {
        // Create the AIE with an existing ID
        aIE.setId("existing_id");

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAIEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aIE)))
            .andExpect(status().isBadRequest());

        // Validate the AIE in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        aIE.setName(null);

        // Create the AIE, which fails.

        restAIEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aIE)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        aIE.setType(null);

        // Create the AIE, which fails.

        restAIEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aIE)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        aIE.setCreatedAt(null);

        // Create the AIE, which fails.

        restAIEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aIE)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        aIE.setCreatedBy(null);

        // Create the AIE, which fails.

        restAIEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aIE)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAieMetadataIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        aIE.setAieMetadata(null);

        // Create the AIE, which fails.

        restAIEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aIE)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUserIDIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        aIE.setUserID(null);

        // Create the AIE, which fails.

        restAIEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aIE)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsPublicIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        aIE.setIsPublic(null);

        // Create the AIE, which fails.

        restAIEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aIE)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAIES() throws Exception {
        // Initialize the database
        insertedAIE = aIERepository.saveAndFlush(aIE);

        // Get all the aIEList
        restAIEMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aIE.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].aieMetadata").value(hasItem(DEFAULT_AIE_METADATA)))
            .andExpect(jsonPath("$.[*].userID").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].isPublic").value(hasItem(DEFAULT_IS_PUBLIC.booleanValue())))
            .andExpect(jsonPath("$.[*].organizationName").value(hasItem(DEFAULT_ORGANIZATION_NAME)))
            .andExpect(jsonPath("$.[*].tenantID").value(hasItem(DEFAULT_TENANT_ID)));
    }

    @Test
    @Transactional
    void getAIE() throws Exception {
        // Initialize the database
        insertedAIE = aIERepository.saveAndFlush(aIE);

        // Get the aIE
        restAIEMockMvc
            .perform(get(ENTITY_API_URL_ID, aIE.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aIE.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE.doubleValue()))
            .andExpect(jsonPath("$.aieMetadata").value(DEFAULT_AIE_METADATA))
            .andExpect(jsonPath("$.userID").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.isPublic").value(DEFAULT_IS_PUBLIC.booleanValue()))
            .andExpect(jsonPath("$.organizationName").value(DEFAULT_ORGANIZATION_NAME))
            .andExpect(jsonPath("$.tenantID").value(DEFAULT_TENANT_ID));
    }

    @Test
    @Transactional
    void getNonExistingAIE() throws Exception {
        // Get the aIE
        restAIEMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAIE() throws Exception {
        // Initialize the database
        insertedAIE = aIERepository.saveAndFlush(aIE);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aIE
        AIE updatedAIE = aIERepository.findById(aIE.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAIE are not directly saved in db
        em.detach(updatedAIE);
        updatedAIE
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION)
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .icon(UPDATED_ICON)
            .version(UPDATED_VERSION)
            .category(UPDATED_CATEGORY)
            .rate(UPDATED_RATE)
            .aieMetadata(UPDATED_AIE_METADATA)
            .userID(UPDATED_USER_ID)
            .isPublic(UPDATED_IS_PUBLIC)
            .organizationName(UPDATED_ORGANIZATION_NAME)
            .tenantID(UPDATED_TENANT_ID);

        restAIEMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAIE.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(updatedAIE))
            )
            .andExpect(status().isOk());

        // Validate the AIE in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAIEToMatchAllProperties(updatedAIE);
    }

    @Test
    @Transactional
    void putNonExistingAIE() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aIE.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAIEMockMvc
            .perform(put(ENTITY_API_URL_ID, aIE.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aIE)))
            .andExpect(status().isBadRequest());

        // Validate the AIE in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAIE() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aIE.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAIEMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(aIE))
            )
            .andExpect(status().isBadRequest());

        // Validate the AIE in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAIE() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aIE.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAIEMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aIE)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AIE in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAIEWithPatch() throws Exception {
        // Initialize the database
        insertedAIE = aIERepository.saveAndFlush(aIE);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aIE using partial update
        AIE partialUpdatedAIE = new AIE();
        partialUpdatedAIE.setId(aIE.getId());

        partialUpdatedAIE
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .version(UPDATED_VERSION)
            .userID(UPDATED_USER_ID)
            .tenantID(UPDATED_TENANT_ID);

        restAIEMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAIE.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAIE))
            )
            .andExpect(status().isOk());

        // Validate the AIE in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAIEUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedAIE, aIE), getPersistedAIE(aIE));
    }

    @Test
    @Transactional
    void fullUpdateAIEWithPatch() throws Exception {
        // Initialize the database
        insertedAIE = aIERepository.saveAndFlush(aIE);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aIE using partial update
        AIE partialUpdatedAIE = new AIE();
        partialUpdatedAIE.setId(aIE.getId());

        partialUpdatedAIE
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION)
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .icon(UPDATED_ICON)
            .version(UPDATED_VERSION)
            .category(UPDATED_CATEGORY)
            .rate(UPDATED_RATE)
            .aieMetadata(UPDATED_AIE_METADATA)
            .userID(UPDATED_USER_ID)
            .isPublic(UPDATED_IS_PUBLIC)
            .organizationName(UPDATED_ORGANIZATION_NAME)
            .tenantID(UPDATED_TENANT_ID);

        restAIEMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAIE.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAIE))
            )
            .andExpect(status().isOk());

        // Validate the AIE in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAIEUpdatableFieldsEquals(partialUpdatedAIE, getPersistedAIE(partialUpdatedAIE));
    }

    @Test
    @Transactional
    void patchNonExistingAIE() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aIE.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAIEMockMvc
            .perform(patch(ENTITY_API_URL_ID, aIE.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(aIE)))
            .andExpect(status().isBadRequest());

        // Validate the AIE in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAIE() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aIE.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAIEMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(aIE))
            )
            .andExpect(status().isBadRequest());

        // Validate the AIE in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAIE() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aIE.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAIEMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(aIE)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AIE in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAIE() throws Exception {
        // Initialize the database
        insertedAIE = aIERepository.saveAndFlush(aIE);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the aIE
        restAIEMockMvc.perform(delete(ENTITY_API_URL_ID, aIE.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return aIERepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected AIE getPersistedAIE(AIE aIE) {
        return aIERepository.findById(aIE.getId()).orElseThrow();
    }

    protected void assertPersistedAIEToMatchAllProperties(AIE expectedAIE) {
        assertAIEAllPropertiesEquals(expectedAIE, getPersistedAIE(expectedAIE));
    }

    protected void assertPersistedAIEToMatchUpdatableProperties(AIE expectedAIE) {
        assertAIEAllUpdatablePropertiesEquals(expectedAIE, getPersistedAIE(expectedAIE));
    }
}
