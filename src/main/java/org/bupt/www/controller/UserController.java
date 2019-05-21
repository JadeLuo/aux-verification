package org.bupt.www.controller;

import org.bupt.www.pojo.CommonResult;
import org.bupt.www.pojo.Identity;
import org.bupt.www.pojo.jo.LoginJo;
import org.bupt.www.pojo.jo.RegisterJo;
import org.bupt.www.pojo.po.User;
import org.bupt.www.service.UserService;
import org.bupt.www.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("login")
    public String loginView() {
        return "login";
    }

    @PostMapping("login")
    @ResponseBody
    public CommonResult login(@RequestBody LoginJo params, HttpSession session) {
        if (Validator.checkEmpty(params.getEmail()) || Validator.checkEmpty(params.getPwd())) {
            return CommonResult.failure("请求参数不完整");
        } else {
            User user = null;
//            if (Validator.checkEmail(loginParam)) {
//                user = userService.login("", loginParam, password);
//            } else {
//                user = userService.login(loginParam, "", password);
//            }
            user = userService.login("", params.getEmail(), params.getPwd());
            if (user == null) return CommonResult.failure("邮箱或密码错误");
            else {
                Identity identity = new Identity(user.getId(), user.getPhone(), user.getEmail());
                session.setAttribute("identity", identity);
                return CommonResult.success();
            }
        }
    }

    @GetMapping("register")
    public String registerView() {
        return "register";
    }

    @PostMapping("register")
    @ResponseBody
    public CommonResult register(@RequestBody RegisterJo params) {
        if (Validator.checkEmpty(params.getEmail()) || Validator.checkEmpty(params.getPwd())) {
            return CommonResult.failure("请求参数不完整!");
        }
        if (!Validator.checkEmail(params.getEmail())) {
            return CommonResult.failure("邮箱格式不正确!");
        }
        User user = userService.register("", params.getEmail(), params.getPwd());
        if (user == null) return CommonResult.failure("邮箱已存在");
        else return CommonResult.success();
    }

    @PostMapping("logout")
    @ResponseBody
    public CommonResult logout(HttpSession session) {
        session.removeAttribute("identity");
        return CommonResult.success();
    }
}
