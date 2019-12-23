package com.example.faq.tokenConfig;

import com.example.faq.models.ApiResult;
import com.example.faq.models.Token;
import com.example.faq.persistences.TokenDao;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

//token拦截器
public class TokenInterceptor implements HandlerInterceptor {
    @Autowired
    private TokenDao TokenMapper;

    //提供查询
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3)
            throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView arg3)
            throws Exception {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
        //普通路径放行  此处是指获取Token的接口
        if (request.getRequestURI().equals(JwtConstants.AUTH_PATH) || request.getRequestURI().equals("")) {
            return true;
        }

        //权限路径拦截
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        JSONObject result = new JSONObject(new LinkedHashMap());
        final String requestHeaderToken = request.getHeader(JwtConstants.AUTH_HEADER);
        //判断请求信息
        if (null == requestHeaderToken || requestHeaderToken.trim().equals("")) {
            returnErrorMessage(response, 202, "你没有token,需要登录", result);
            return false;
        }
        //解析Token信息
        try {
            Claims claims = Jwts.parser().setSigningKey("faqProject").parseClaimsJws(requestHeaderToken).getBody();
            String tokenUserId = (String) claims.get("userId");
            String isAdmin = (String) claims.get("isAdmin");


            int itokenUserId = Integer.parseInt(tokenUserId);
            //根据客户Token查找数据库Token
            Token myToken = TokenMapper.findTokenByUserId(itokenUserId);

            //数据库没有Token记录
            if (null == myToken) {
                returnErrorMessage(response, 202, "数据库没有你的token,需要登录", result);
                return false;
            }
            //数据库Token与客户Token比较
            if (!requestHeaderToken.equals(myToken.getToken())) {
                returnErrorMessage(response, 203, "token与数据库不一致", result);
                return false;
            }
            //判断Token过期
            Date tokenDate = (Date) claims.getExpiration();
            int interval = (int) (System.currentTimeMillis() - tokenDate.getTime()) / 1000;
            if (interval > 60 * 60 * 24 * 3) {
                returnErrorMessage(response, 204, "token过期需要重新登录", result);
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            returnErrorMessage(response, 205, "遇到了异常错误", result);
            return false;
        }
        //最后才放行
        return true;
    }

    private void returnErrorMessage(HttpServletResponse response, Integer status, String msg, JSONObject result) throws IOException, JSONException {
        PrintWriter out = response.getWriter();
        result.put("status", status);
        result.put("msg", msg);
        out.print(result.toString());
        out.close();
    }

}
