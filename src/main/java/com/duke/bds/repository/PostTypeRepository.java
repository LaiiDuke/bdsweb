package com.duke.bds.repository;

import com.duke.bds.domain.PostType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PostType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PostTypeRepository extends JpaRepository<PostType, Long> {}
