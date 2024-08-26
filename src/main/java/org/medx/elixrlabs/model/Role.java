package org.medx.elixrlabs.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.medx.elixrlabs.util.RoleEnum;

import java.util.List;

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
 * @version  1.0
 */
@Data
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
