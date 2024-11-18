package com.mycompany.myapp.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AIE.
 */
@Entity
@Table(name = "aie")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AIE implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private String id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @NotNull
    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "icon")
    private String icon;

    @Column(name = "version")
    private String version;

    @Column(name = "category")
    private String category;

    @Column(name = "rate")
    private Double rate;

    @NotNull
    @Column(name = "aie_metadata", nullable = false)
    private String aieMetadata;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private String userID;

    @NotNull
    @Column(name = "is_public", nullable = false)
    private Boolean isPublic;

    @Column(name = "organization_name")
    private String organizationName;

    @Column(name = "tenant_id")
    private String tenantID;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public AIE id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public AIE name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public AIE type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return this.description;
    }

    public AIE description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public AIE createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public AIE createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getIcon() {
        return this.icon;
    }

    public AIE icon(String icon) {
        this.setIcon(icon);
        return this;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getVersion() {
        return this.version;
    }

    public AIE version(String version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCategory() {
        return this.category;
    }

    public AIE category(String category) {
        this.setCategory(category);
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getRate() {
        return this.rate;
    }

    public AIE rate(Double rate) {
        this.setRate(rate);
        return this;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getAieMetadata() {
        return this.aieMetadata;
    }

    public AIE aieMetadata(String aieMetadata) {
        this.setAieMetadata(aieMetadata);
        return this;
    }

    public void setAieMetadata(String aieMetadata) {
        this.aieMetadata = aieMetadata;
    }

    public String getUserID() {
        return this.userID;
    }

    public AIE userID(String userID) {
        this.setUserID(userID);
        return this;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Boolean getIsPublic() {
        return this.isPublic;
    }

    public AIE isPublic(Boolean isPublic) {
        this.setIsPublic(isPublic);
        return this;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getOrganizationName() {
        return this.organizationName;
    }

    public AIE organizationName(String organizationName) {
        this.setOrganizationName(organizationName);
        return this;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getTenantID() {
        return this.tenantID;
    }

    public AIE tenantID(String tenantID) {
        this.setTenantID(tenantID);
        return this;
    }

    public void setTenantID(String tenantID) {
        this.tenantID = tenantID;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AIE)) {
            return false;
        }
        return getId() != null && getId().equals(((AIE) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AIE{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", icon='" + getIcon() + "'" +
            ", version='" + getVersion() + "'" +
            ", category='" + getCategory() + "'" +
            ", rate=" + getRate() +
            ", aieMetadata='" + getAieMetadata() + "'" +
            ", userID='" + getUserID() + "'" +
            ", isPublic='" + getIsPublic() + "'" +
            ", organizationName='" + getOrganizationName() + "'" +
            ", tenantID='" + getTenantID() + "'" +
            "}";
    }
}
