package org.bupt.www.repository;

import org.bupt.www.pojo.po.RelationReflect;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RelationReflectRepository extends JpaRepository<RelationReflect, Long> {
    Optional<RelationReflect> findByRelaNo(String relaNo);
}
