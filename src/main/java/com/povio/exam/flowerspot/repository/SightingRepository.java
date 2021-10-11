package com.povio.exam.flowerspot.repository;

import com.povio.exam.flowerspot.model.Sighting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface SightingRepository extends JpaRepository<Sighting, Long> {

    public boolean existsByLikedByUsers_Username(String username);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_sighting_likes WHERE like_sighting_id = ?1 AND like_user_id = ?2", nativeQuery = true)
    public void removeLike(Long sightingId, Long userId);
}
