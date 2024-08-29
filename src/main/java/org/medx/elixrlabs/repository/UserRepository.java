package org.medx.elixrlabs.repository;

import org.medx.elixrlabs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing User data from the database.
 *
 * <p>
 * This interface extends JpaRepository, providing CRUD operations for User entities.
 * Custom queries can be defined by adding method signatures that follow the naming
 * convention understood by Spring Data JPA.
 * </p>
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    String getUserWithRoles = "FROM User u LEFT JOIN FETCH u.roles WHERE u.email = :email AND u.isDeleted = false";
    String getAllPatientsQuery = "FROM User u LEFT JOIN FETCH u.roles r WHERE r.name = ROLE_PATIENT";
    String getPatientWithOrders = "FROM User u LEFT JOIN FETCH u.orders WHERE u.email = email";
    String getTestResultByPatient = "FROM User u LEFT JOIN FETCH u.orders o JOIN FETCH o.testResult WHERE u.email = :email";

    @Query(getUserWithRoles)
    User findByEmailWithRoles(@Param("email") String email);

    @Query(getAllPatientsQuery)
    List<User> fetchAllPatients();

    @Query(getPatientWithOrders)
    User getPatientOrders(@Param("email") String email);

    User findByEmailAndIsDeletedFalse(String email);

    @Query(getTestResultByPatient)
    User fetchTestResultByPatient(@Param("email") String email);
}
