package com.xdong.ripple.mvc;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xdong.ripple.common.utils.MD5Utils;
import com.xdong.ripple.common.utils.ResponseResult;
import com.xdong.ripple.common.utils.ShiroUtils;
import com.xdong.ripple.commonservice.annotation.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController extends BaseController {

    private final Logger        logger = LoggerFactory.getLogger(this.getClass());

    private static final String prefix = "user";

    @GetMapping({ "/", "" })
    @ResponseBody
    public Object portalIndex(Model model) {
        return "success";
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        return getUrl(prefix, "login");
    }

    @Log("登录")
    @PostMapping("/login")
    @ResponseBody
    public ResponseResult login(String username, String password) {
        password = MD5Utils.encrypt(username, password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            return ResponseResult.ok();
        } catch (AuthenticationException e) {
            logger.error("用户登陆校验异常,username:{}, password:{}", username, password, e);
            return ResponseResult.error("用户或密码错误");
        }
    }

    @GetMapping("/logout")
    public String logout() {
        ShiroUtils.logout();
        return "redirect:/login";
    }

    @GetMapping("/main")
    public String main() {
        return "main";
    }

    @GetMapping("/403")
    public String error403() {
        return "403";
    }

}
