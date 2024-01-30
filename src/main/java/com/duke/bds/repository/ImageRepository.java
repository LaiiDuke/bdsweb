package com.duke.bds.repository;

import com.duke.bds.domain.Image;
import com.duke.bds.domain.Post;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Image entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findAllByPostIn(List<Post> lstPost);

    @Query("select image from Image image where image.post.id = :postId")
    List<Image> findAllByPostId(@Param("postId") Long postId);
}
