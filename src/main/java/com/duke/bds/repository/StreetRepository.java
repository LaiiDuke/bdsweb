package com.duke.bds.repository;

import com.duke.bds.domain.Street;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Street entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StreetRepository extends JpaRepository<Street, Long> {}
