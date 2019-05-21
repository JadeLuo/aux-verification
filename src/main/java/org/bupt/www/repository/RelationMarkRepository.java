package org.bupt.www.repository;

import org.bupt.www.pojo.po.RelationMark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;

public interface RelationMarkRepository extends JpaRepository<RelationMark, Long> {

    //    @Query("from RelationMark r where r.reviewed = false")
    Optional<RelationMark> findFirstByReviewed(boolean viewed);

    Optional<RelationMark> findByVerDateAndUserId(Date date, long userId);

    long countByReviewed(boolean reviewed);

    long countByPassed(boolean passed);

    @Modifying
    @Query("update RelationMark set reviewed = :reviewed, verDate = :verDate, userId = :userId where passed = :passed")
    void initForTest(@Param("verDate") Date verData, @Param("userId") Long userId, @Param("reviewed") Boolean reviewed,
                     @Param("passed") Boolean passed);

}
