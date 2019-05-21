package org.bupt.www.controller;

import org.bupt.www.pojo.CommonResult;
import org.bupt.www.pojo.Identity;
import org.bupt.www.pojo.po.EntityMark;
import org.bupt.www.pojo.po.RelationMark;
import org.bupt.www.pojo.po.User;
import org.bupt.www.pojo.vo.RelationVo;
import org.bupt.www.service.VerificationService;
import org.bupt.www.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("ver")
public class VerificationController {

    @Autowired
    private VerificationService verificationService;

    @GetMapping("entity")
    public String getVerificationEntityView(HttpSession session, ModelMap map) {
        Identity identity = (Identity) session.getAttribute("identity");
        map.put("email", identity.getEmai());
        return "ver-entity";
    }

    @GetMapping("relation")
    public String getVerificationRelationView(HttpSession session, ModelMap map) {
        Identity identity = (Identity) session.getAttribute("identity");
        map.put("email", identity.getEmai());
        return "ver-relation";
    }

    @RequestMapping("entity/accept")
    @ResponseBody
    public CommonResult acceptEntity(@RequestBody EntityMark entityMark, HttpSession session) {
        if (Validator.checkEmpty(entityMark.getContent())) {
            return CommonResult.failure("提交信息不完整");
        }
        Identity identity = (Identity) session.getAttribute("identity");
        User user = new User();
        user.setId(identity.getId());
        user.setPhone(identity.getPhone());
        user.setEmail(identity.getEmai());
        if (verificationService.acceptEntityMark(user, entityMark.getId(), entityMark.getContent())) {
            return CommonResult.success("success");
        } else {
            return CommonResult.failure("failure");
        }
    }

    @RequestMapping("entity/denied")
    @ResponseBody
    public CommonResult deniedEntity(@RequestBody EntityMark entityMark, HttpSession session) {
        if (Validator.checkEmpty(entityMark.getContent())) {
            return CommonResult.failure("提交信息不完整");
        }
        Identity identity = (Identity) session.getAttribute("identity");
        User user = new User();
        user.setId(identity.getId());
        user.setPhone(identity.getPhone());
        user.setEmail(identity.getEmai());
        if (verificationService.deniedEntityMark(user, entityMark.getId(), entityMark.getContent())) {
            return CommonResult.success("success");
        } else {
            return CommonResult.failure("failure");
        }
    }

    @RequestMapping("relation/accept")
    @ResponseBody
    public CommonResult acceptRelation(@RequestBody RelationMark relationMark, HttpSession session) {
        if (Validator.checkEmpty(relationMark.getContent()) || relationMark.getRelationId() == null) {
            return CommonResult.failure("提交信息不完整");
        }
        Identity identity = (Identity) session.getAttribute("identity");
        User user = new User();
        user.setId(identity.getId());
        user.setPhone(identity.getPhone());
        user.setEmail(identity.getEmai());
        if (verificationService.acceptRelationMark(user, relationMark.getId(), relationMark.getContent(), relationMark.getRelationId())) {
            return CommonResult.success("success");
        } else {
            return CommonResult.failure("failure");
        }
    }

    @RequestMapping("relation/denied")
    @ResponseBody
    public CommonResult deniedRelation(@RequestBody RelationMark relationMark, HttpSession session) {
        if (Validator.checkEmpty(relationMark.getContent()) || relationMark.getRelationId() == null) {
            return CommonResult.failure("提交信息不完整");
        }
        Identity identity = (Identity) session.getAttribute("identity");
        User user = new User();
        user.setId(identity.getId());
        user.setPhone(identity.getPhone());
        user.setEmail(identity.getEmai());
        if (verificationService.deniedRelationMark(user, relationMark.getId(), relationMark.getContent(), relationMark.getRelationId())) {
            return CommonResult.success();
        } else {
            return CommonResult.failure("failure");
        }
    }

    @RequestMapping("entity/next")
    @ResponseBody
    public CommonResult getNextEntity(HttpSession session) {
        Identity identity = (Identity) session.getAttribute("identity");
        User user = new User();
        user.setId(identity.getId());
        user.setPhone(identity.getPhone());
        user.setEmail(identity.getEmai());

        EntityMark mark = verificationService.getNextEntityMark(user);
        if (mark == null) {
            return CommonResult.failure("没有需要审核的实体类型");
        } else {
            return CommonResult.success("success", mark);
        }
    }

    @RequestMapping("relation/next")
    @ResponseBody
    public CommonResult getNextRelation(HttpSession session) {
        Identity identity = (Identity) session.getAttribute("identity");
        User user = new User();
        user.setId(identity.getId());
        user.setPhone(identity.getPhone());
        user.setEmail(identity.getEmai());

        RelationVo mark = verificationService.getNextRelationMark(user);
        if (mark == null) {
            return CommonResult.failure("没有需要审核的关系类型");
        } else {
            return CommonResult.success("success", mark);
        }
    }

}
