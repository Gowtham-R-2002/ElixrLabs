package org.medx.elixrlabs.repository;

import org.medx.elixrlabs.model.TestPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Repository interface for accessing TestPackage data from the database.
 *
 * <p>
 * This interface extends JpaRepository, providing CRUD operations for TestPackage entities.
 * Custom queries can be defined by adding method signatures that follow the naming
 * convention understood by Spring Data JPA.
 * </p>
 */
public interface TestPackageRepository extends JpaRepository<TestPackage, Long> {

    @Query("FROM TestPackage t JOIN FETCH t.tests test WHERE test.isDeleted = false AND t.isDeleted = false")
    List<TestPackage> findByIsDeletedFalse();

    @Query("FROM TestPackage t JOIN FETCH t.tests test WHERE t.id = :id AND t.isDeleted = false AND test.isDeleted = false")
    TestPackage findByIdAndIsDeletedFalse(Long id);
}
