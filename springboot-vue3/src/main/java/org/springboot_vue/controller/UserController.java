package org.springboot_vue.controller;

import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.URL;
import org.springboot_vue.pojo.User;
import org.springboot_vue.service.UserService;
import org.springboot_vue.utils.JwtUtil;
import org.springboot_vue.utils.Md5Util;
import org.springboot_vue.utils.Result;
import org.springboot_vue.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register") /*註冊*/
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        log.info("註冊訊息 username:{}, password:{}", username, password);
        User u = userService.findByUserName(username);
        if (u == null) { /*用戶不存在*/
            userService.register(username, password);
            return Result.success();
        } else {
            return Result.error("用戶名已被佔用");
        }
    }

    @PostMapping("/login")/*登陸*/
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        log.info("執行登陸操作 username:{} password:{}", username, password);
        /*用戶輸入用戶名 如果不存在請註冊*/
        User u = userService.findByUserName(username);
        if (u == null) {
            return Result.error("用戶名錯誤");
        }
        if (Md5Util.getMD5String(password).equals(u.getPassword())) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", u.getId());
            claims.put("username", u.getUsername());
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        }

        return Result.error("密碼錯誤");
    }

    @GetMapping("/userInfo")/*查詢用戶數據*/
    public Result<User> userInfo() {
        log.info("查詢user數據");
        Map<String, Object> map = ThreadLocalUtil.get();/*解析過的token*/
        String username = map.get("username").toString();/*獲取username.ToString*/
        return Result.success(userService.findByUserName(username));
    }

    @PutMapping("/update")/*更新用戶數據*/
    public Result updateInfo(@RequestBody @Validated User user) {
        log.info("更新用戶數據:{}", user);
        userService.update(user);
        return Result.success();
    }

    @PatchMapping("/updateAvatar")/*更新用戶頭像*/
    public Result updateAvatar(@RequestParam @URL String avatarUrl) {
        log.info("更新用戶圖像:{}", avatarUrl);
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")/*更新用戶密碼*/
    public Result updatePwd(@RequestBody Map<String, String> params) {
        log.info("用戶更新密碼:{}", params);
        /*校驗參數*/
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");
        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)) {
            return Result.error("缺少必要參數");
        }
        /*解析原密碼是否正確*/
        Map<String, Object> map = ThreadLocalUtil.get(); /*從token拿出map集合*/
        String username = (String) map.get("username");/*得到username*/
        User loginUser = userService.findByUserName(username);
        if(!loginUser.getPassword().equals(Md5Util.getMD5String(oldPwd))){
            return Result.error("原密碼填寫不正確");
        }
        if(!rePwd.equals(newPwd)){ /*檢驗兩次輸入密碼是否正確*/
            return Result.error("兩次輸入密碼不正確");
        }
        userService.updatePwd(newPwd);
        return Result.success();
    }
}
