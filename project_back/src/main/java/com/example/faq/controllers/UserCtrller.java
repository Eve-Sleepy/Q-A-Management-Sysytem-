package com.example.faq.controllers;

import com.example.faq.dto.UserLoginDto;
import com.example.faq.models.ApiResult;
import com.example.faq.models.Token;
import com.example.faq.models.User;
import com.example.faq.persistences.TokenDao;
import com.example.faq.persistences.UserDao;
import com.example.faq.services.TokenService;
import com.example.faq.services.UserService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@Controller
@RequestMapping("/api/user")
public class UserCtrller {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;

    @RequestMapping("/auth")
    @ResponseBody
    public ApiResult auth(@RequestBody UserLoginDto userLoginDto) {
        ApiResult apiResult = new ApiResult();

        if (userLoginDto.getUsername().equals("") || userLoginDto.getPassword().equals("")) {
            apiResult.setStatus(201);
            apiResult.setMsg("传入的用户名/密码为空！");
            return apiResult;
        }

        User user = userService.findUserByUsername(userLoginDto.getUsername());
        if (user == null) {
            apiResult.setStatus(202);
            apiResult.setMsg("用户不存在");
            return apiResult;
        }
        if (!user.getPassword().equals(userLoginDto.getPassword())) {
            apiResult.setStatus(203);
            apiResult.setMsg("密码不正确");
            return apiResult;
        }
        apiResult.setStatus(200);


        //根据数据库的用户信息查询Token
        Token token = tokenService.findTokenByUserId(user.getId());
        //为生成Token准备
        String TokenStr = "";
        Date date = new Date();
        int nowtime = (int) (date.getTime() / 1000);
        //生成Token
        TokenStr = creatToken(user, date);
        int authorization;
        if (null == token) {
            //第一次登陆
            token = new Token();
            token.setToken(TokenStr);
            token.setBuildtime(nowtime);
            token.setUserid(user.getId());
            authorization = tokenService.updataToken(token);

        } else {
            //登陆就更新Token信息
            TokenStr = creatToken(user, date);
            token.setToken(TokenStr);
            token.setBuildtime(nowtime);
            token.setUserid(user.getId());
            authorization = tokenService.updataToken(token);
        }
        if (authorization != 1) {
            apiResult.setStatus(501);
            apiResult.setMsg("数据库token插入失败");
            return apiResult;
        }
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("userId", user.getId());
        map.put("realname", user.getRealname());
        map.put("isAdmin", user.getIsAdmin());
        map.put("Authorization", TokenStr);
        apiResult.setData(map);
        apiResult.setMsg("登录成功");
        return apiResult;
    }

    /**
     * @param user
     * @param date
     * @return
     */
    //生成Token信息方法（根据有效的用户信息）
    private String creatToken(User user, Date date) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        // 设置header
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                // 设置签发时间
                .setIssuedAt(date)
                // 设置过期时间
                .setExpiration(new Date(date.getTime() + 1000 * 60 * 60 * 24 * 3))
                // 设置内容
                .claim("userId", String.valueOf(user.getId()))
                .claim("isAdmin", String.valueOf(user.getIsAdmin()))
                .claim("realname", String.valueOf(user.getRealname()))
                // 设置签发人
                .setIssuer("cyx")
                // 签名，需要算法和key
                .signWith(signatureAlgorithm, "faqProject");
        String jwt = builder.compact();
        return jwt;
    }

    @RequestMapping("register")
    @ResponseBody
    public ApiResult register(@RequestBody Map<String, Object> params) {
        ApiResult apiResult = new ApiResult();

        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String tablename = dateFormat.format(now);

        if (userService.findUserByUsername((String) params.get("username")) != null) {
            apiResult.setStatus(201);
            apiResult.setMsg("用户名重复");
            return apiResult;
        }
        if (userService.findUserByUserRealname((String) params.get("realname")) != null) {
            apiResult.setStatus(201);
            apiResult.setMsg("真实姓名重复");
            return apiResult;
        }
        User user = new User();
        user.setUsername((String) params.get("username"));
        user.setRealname((String) params.get("realname"));
        user.setPassword("123456");
        if (userService.addUser(user) != 1) {
            apiResult.setStatus(500);
            apiResult.setMsg("新建用户失败");
            return apiResult;
        }

        apiResult.setStatus(200);
        apiResult.setMsg("新建用户成功");
        return apiResult;
    }

    @GetMapping("dropDownList")
    @ResponseBody
    public ApiResult userDropDownList() {
        ApiResult apiResult = new ApiResult();
        List<User> users = userService.findUsers();
        if (users == null || users.size() == 0) {
            apiResult.setStatus(500);
            apiResult.setMsg("列表返回失败或者不存在用户");
            return apiResult;
        }

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("total", users.size());
        ArrayList<Map<String, Object>> arr = new ArrayList<>();
        for (User user : users) {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("id", user.getId());
            item.put("username", user.getRealname());
            arr.add(item);
        }
        data.put("arr", arr);

        apiResult.setStatus(200);
        apiResult.setData(data);
        apiResult.setMsg("列表返回成功");
        return apiResult;
    }

    @RequestMapping("editPwd")
    @ResponseBody
    public ApiResult editPwd(@RequestBody Map<String, Object> params) {
        ApiResult apiResult = new ApiResult();

        User user = new User();
        // 验证原先的密码与用户id
        User user1 = userService.findUserById((Integer) params.get("userId"));
        System.out.println("-----------------" + user1.getPassword());
        System.out.println((String) params.get("oldPassword"));
        if (!user1.getPassword().equals((String) params.get("oldPassword"))) {
            apiResult.setStatus(501);
            apiResult.setMsg("旧密码验证失败");
            return apiResult;
        }
        user.setId((Integer) params.get("userId"));
        user.setPassword((String) params.get("editPassword"));

        Integer users = userService.editPwd(user);
        if (userService.editPwd(user) == 0) {
            apiResult.setStatus(501);
            apiResult.setMsg("密码修改失败");
            return apiResult;
        }

        apiResult.setStatus(200);
        apiResult.setMsg("密码修改成功");
        return apiResult;
    }

}
