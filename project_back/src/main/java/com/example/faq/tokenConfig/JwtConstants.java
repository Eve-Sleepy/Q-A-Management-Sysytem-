package com.example.faq.tokenConfig;

public interface JwtConstants {
    /**
     * AUTH_HEADER    是HTTPHeader请求的参数
     * SECRET         是具体的加密算法
     * AUTH_PATH      是提供给客户端获取JWT参数的接口/需要提供正确的用户名以及密码
     * EXPIRATION     是计算JWT过期时间需要用到的
     */
    String AUTH_HEADER = "Authorization";
    String SECRET = "defaultSecret";
    String AUTH_PATH = "/api/user/auth";
    Long EXPIRATION = 604800L;
}
