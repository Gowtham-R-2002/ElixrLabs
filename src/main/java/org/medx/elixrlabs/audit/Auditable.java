package org.medx.elixrlabs.audit;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * <p>
 * The Auditable class is a base entity used to automatically capture and store
 * audit information such as the user who created or last modified the entity,
 * along with the corresponding timestamps. This class can be extended by other
 * entities to inherit audit-related fields.
 * </p>
 *
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
@NoArgsConstructor
public class Auditable {
    @CreatedBy
    @Column(name = "created_by", updatable = false)
    public String createdBy;
    @CreatedDate
    @Column(name = "creation_date", updatable = false)
    public LocalDateTime creationDate;
    @LastModifiedBy
    @Column(name = "modified_by")
    public String modifiedBy;
    @LastModifiedDate
    @Column(name = "modified_date")
    public LocalDateTime modifiedDate;
}
