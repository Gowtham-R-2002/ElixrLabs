package org.medx.elixrlabs.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.medx.elixrlabs.util.RoleEnum;

/**
 * <p>
 * This class holds the data related to an Role including their ID, name and
 * other relevant details.
 * The {@code Role} class represents a role that can be assigned
 * to users, such as customer, sample collector, or admin. Roles
 * are used to control access to various features and functionalities
 * within the system.
 * </p>
 *
 * @version 1.0
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(value = EnumType.STRING)
    @Column(unique = true)
    private RoleEnum name;
}
