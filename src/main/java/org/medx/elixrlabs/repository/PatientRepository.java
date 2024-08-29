package org.medx.elixrlabs.repository;

import java.util.List;

import org.medx.elixrlabs.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing patient data from the database.
 *
 * <p>
 * This interface extends JpaRepository, providing CRUD operations for Role entities.
 * Custom queries can be defined by adding method signatures that follow the naming
 * convention understood by Spring Data JPA.
 * </p>
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    String getAllPatientsQuery = "FROM Patient p LEFT JOIN FETCH p.user WHERE p.isDeleted = false";
    String getPatientWithOrders = "FROM Patient p LEFT JOIN FETCH p.orders o JOIN FETCH p.user u WHERE u.email = email AND p.isDeleted = false";
    String getTestResultOfPatient = "FROM Patient p LEFT JOIN FETCH p.orders o JOIN FETCH o.testResult JOIN FETCH p.user u WHERE u.email = :email AND p.isDeleted = false";
    String getPatientQuery = "FROM Patient p LEFT JOIN FETCH p.user u WHERE u.email = :email AND p.isDeleted = false";

    @Query(getAllPatientsQuery)
    List<Patient> fetchAllPatients();

    @Query(getPatientWithOrders)
    Patient getPatientOrders(@Param("email") String email);

    @Query(getPatientQuery)
    Patient findByEmailAndIsDeletedFalse(@Param("email") String email);

    @Query(getTestResultOfPatient)
    Patient fetchTestResultByPatient(@Param("email") String email);
}
