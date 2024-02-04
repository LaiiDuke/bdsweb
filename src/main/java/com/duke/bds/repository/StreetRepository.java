package com.duke.bds.repository;

import com.duke.bds.domain.Street;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Street entity.
 */
@Repository
public interface StreetRepository extends JpaRepository<Street, Long>, JpaSpecificationExecutor<Street> {
    default Optional<Street> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Street> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Street> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct street from Street street left join fetch street.ward left join fetch street.district",
        countQuery = "select count(distinct street) from Street street"
    )
    Page<Street> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct street from Street street left join fetch street.ward left join fetch street.district")
    List<Street> findAllWithToOneRelationships();

    @Query("select street from Street street left join fetch street.ward left join fetch street.district where street.id =:id")
    Optional<Street> findOneWithToOneRelationships(@Param("id") Long id);
}
