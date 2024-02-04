package com.duke.bds.repository;

import com.duke.bds.domain.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Post entity.
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    @Query("select post from Post post where post.user.login = ?#{principal.username}")
    List<Post> findByUserIsCurrentUser();

    default Optional<Post> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Post> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Post> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct post from Post post left join fetch post.type left join fetch post.category left join fetch post.province left join fetch post.district left join fetch post.ward left join fetch post.street",
        countQuery = "select count(distinct post) from Post post"
    )
    Page<Post> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct post from Post post left join fetch post.type left join fetch post.category left join fetch post.province left join fetch post.district left join fetch post.ward left join fetch post.street"
    )
    List<Post> findAllWithToOneRelationships();

    @Query(
        "select post from Post post left join fetch post.type left join fetch post.category left join fetch post.province left join fetch post.district left join fetch post.ward left join fetch post.street where post.id =:id"
    )
    Optional<Post> findOneWithToOneRelationships(@Param("id") Long id);

    @Query("select post from Post post where post.isVip = true order by post.postingTime desc")
    Page<Post> findVipPost(Pageable pageable);

    @Query("select post from Post post where post.type.id = :postTypeId order by post.isVip, post.postingTime desc")
    Page<Post> findByPostTypeId(Pageable pageable, @Param("postTypeId") Long postTypeId);
}
