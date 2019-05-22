package org.bupt.www.repository;

import org.bupt.www.pojo.po.EntityMark;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;

public interface EntityMarkRepository extends JpaRepository<EntityMark, Long> {

    //    @Query("from EntityMark e where e.reviewed = false")
    Optional<EntityMark> findFirstByReviewed(boolean viewed);

    Optional<EntityMark> findByVerDateAndUserId(Date date, long userId);

    long countByReviewed(boolean reviewed);

    long countByPassed(boolean passed);

    @Query(value = "SELECT count(1) FROM entity_mark WHERE passed = b'0' AND reviewed = b'1' AND ver_date IS NOT NULL ",
            nativeQuery = true)
    long countDenied();

    @Modifying
    @Query("update EntityMark set verDate = :verDate, userId = :userId, reviewed = :reviewed where passed = :passed")
    void initForTest(@Param("verDate") Date verData, @Param("userId") Long userId, @Param("reviewed") Boolean reviewed,
                     @Param("passed") Boolean passed);

}