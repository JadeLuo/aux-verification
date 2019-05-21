package org.bupt.www.service;

import lombok.extern.slf4j.Slf4j;
import org.bupt.www.pojo.po.EntityMark;
import org.bupt.www.pojo.po.RelationMark;
import org.bupt.www.pojo.po.RelationReflect;
import org.bupt.www.pojo.po.User;
import org.bupt.www.pojo.vo.EntityVo;
import org.bupt.www.pojo.vo.RelationVo;
import org.bupt.www.repository.EntityMarkRepository;
import org.bupt.www.repository.RelationMarkRepository;
import org.bupt.www.repository.RelationReflectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class VerificationService {

    @Autowired
    private EntityMarkRepository entityMarkRepository;

    @Autowired
    private RelationMarkRepository relationMarkRepository;

    @Autowired
    private RelationReflectRepository relationReflectRepository;

    @Transactional
    public EntityVo getNextEntityMark(User user) {
        // 处理未完整审核的文本
        Optional<EntityMark> lastMark = entityMarkRepository.findByVerDateAndUserId(null, user.getId());
        if (lastMark.isPresent()) {
            EntityVo vo = new EntityVo(lastMark.get());
            fillEntityCount(vo);
            return vo;
        }

        Optional<EntityMark> nextMarkOptional = entityMarkRepository.findFirstByReviewed(false);
        if (nextMarkOptional.isPresent()) {
            EntityMark nextMark = nextMarkOptional.get();
            nextMark.setUserId(user.getId());
            nextMark.setReviewed(true);
            entityMarkRepository.save(nextMark);
            EntityVo vo = new EntityVo(nextMark);
            fillEntityCount(vo);
            return vo;
        } else {
            return null;
        }
    }

    @Transactional
    public void fillEntityCount(EntityVo vo) {
        vo.setTotalCount(entityMarkRepository.count());
        vo.setPassedCount(entityMarkRepository.countByPassed(true));
        vo.setDeniedCount(entityMarkRepository.countByReviewed(true) - vo.getPassedCount());
    }

    @Transactional
    public RelationVo getNextRelationMark(User user) {
        Optional<RelationMark> lastMark = relationMarkRepository.findByVerDateAndUserId(null, user.getId());
        if (lastMark.isPresent()) {
            RelationVo vo = new RelationVo(lastMark.get());
            fillRelationVo(vo);
            return vo;
        }
        Optional<RelationMark> nextMarkOptional = relationMarkRepository.findFirstByReviewed(false);
        if (nextMarkOptional.isPresent()) {
            RelationMark nextMark = nextMarkOptional.get();
            nextMark.setUserId(user.getId());
            nextMark.setReviewed(true);
            relationMarkRepository.save(nextMark);

            RelationVo vo = new RelationVo(nextMark);
            fillRelationVo(vo);
            return vo;
        } else {
            return null;
        }
    }

    @Transactional
    public void fillRelationVo(RelationVo vo) {
        List<RelationReflect> reflects = relationReflectRepository.findAll();
        vo.setReflects(reflects);

        Optional<RelationReflect> reflect = relationReflectRepository.findByRelaNo(String.valueOf(vo.getRelationId()));
        if (reflect.isPresent()) {
            vo.setRelationName(reflect.get().getRelaName());
            vo.setRelationNo(reflect.get().getRelaNo());
        }

        vo.setTotalCount(relationMarkRepository.count());
        vo.setPassedCount(relationMarkRepository.countByPassed(true));
        vo.setDeniedCount(relationMarkRepository.countByReviewed(true) - vo.getPassedCount());
    }

    @Transactional
    public boolean acceptEntityMark(User user, long entityId, String content) {
        Optional<EntityMark> markOptional = entityMarkRepository.findById(entityId);
        if (!markOptional.isPresent()) {
            log.error("EntityMark: {}记录不存在", entityId);
            return false;
        }
        EntityMark mark = markOptional.get();
        if (mark.getVerDate() != null) {
            log.error("EntityMark: {}已被审核， 审核人：{}", entityId, mark.getUserId());
            return false;
        } else {
            mark.setContent(content);
            mark.setUserId(user.getId());
            mark.setPassed(true);
            mark.setVerDate(new Date());
            entityMarkRepository.save(mark);
            return true;
        }
    }

    @Transactional
    public boolean deniedEntityMark(User user, long entityId, String content) {
        Optional<EntityMark> markOptional = entityMarkRepository.findById(entityId);
        if (!markOptional.isPresent()) {
            log.error("EntityMark: {}记录不存在", entityId);
            return false;
        }
        EntityMark mark = markOptional.get();
        if (mark.getVerDate() != null) {
            log.error("EntityMark: {}已被审核， 审核人：{}", entityId, mark.getUserId());
            return false;
        } else {
            mark.setContent(content);
            mark.setUserId(user.getId());
            mark.setPassed(false);
            mark.setVerDate(new Date());
            entityMarkRepository.save(mark);
            return true;
        }
    }

    @Transactional
    public boolean acceptRelationMark(User user, long relationId, String content, Long relaId) {
        Optional<RelationMark> markOptional = relationMarkRepository.findById(relationId);
        if (!markOptional.isPresent()) {
            log.error("EntityMark: {}记录不存在", relationId);
            return false;
        }
        RelationMark mark = markOptional.get();
        if (mark.getVerDate() != null) {
            log.error("EntityMark: {}已被审核， 审核人：{}", relationId, mark.getUserId());
            return false;
        } else {
            mark.setContent(content);
//            mark.setRelationName(relationName);
//            mark.setRelationNo(relationNo);
            mark.setRelationId(relaId);
            mark.setUserId(user.getId());
            mark.setPassed(true);
            mark.setVerDate(new Date());
            relationMarkRepository.save(mark);
            return true;
        }
    }

    @Transactional
    public boolean deniedRelationMark(User user, long relationId, String content, Long relaId) {
        Optional<RelationMark> markOptional = relationMarkRepository.findById(relationId);
        if (!markOptional.isPresent()) {
            log.error("EntityMark: {}记录不存在", relationId);
            return false;
        }
        RelationMark mark = markOptional.get();
        if (mark.getVerDate() != null) {
            log.error("EntityMark: {}已被审核， 审核人：{}", relationId, mark.getUserId());
            return false;
        } else {
            mark.setContent(content);
//            mark.setRelationNo(relationNo);
//            mark.setRelationName(relationName);
            mark.setRelationId(relaId);
            mark.setUserId(user.getId());
            mark.setPassed(false);
            mark.setVerDate(new Date());
            relationMarkRepository.save(mark);
            return true;
        }
    }

}
