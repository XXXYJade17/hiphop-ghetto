package com.xxxyjade.hiphopghetto.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Component
@AllArgsConstructor
public class JwtUtil {

    /**
     * 密钥
     */
    private static final String secretKey = "IY9dQlNBBvtIREGCFRm0FL6eu+zVVc9pzaJg7KLYkEk=IY9dQlNBBvtIREGCFRm0FL6eu+zVVc9pzaJg7KLYkEk=IY9dQlNBBvtIREGCFRm0FL6eu+zVVc9pzaJg7KLYkEk=";

    /**
     * 令牌生命
     */
    private static final long ttl = 7200000;

    /**
     * 令牌名
     */
    private static final String tokenName = "authentication";

    /**
     * 生成 JWT令牌
     * @param userId 用户Id
     * @return 令牌
     */
    public static String createAuthJwt(Long userId) {
        // 设置jwt的body
        JwtBuilder builder = Jwts.builder()
                // 设置jwt的body
                .setClaims(Map.of("userId", userId))
                // 设置签名使用的签名算法和签名使用的秘钥
                .signWith(Keys.hmacShaKeyFor(
                        secretKey.getBytes(StandardCharsets.UTF_8)),
                        SignatureAlgorithm.HS256)
                // 设置过期时间
                .setExpiration(new Date(System.currentTimeMillis() + ttl));
        return builder.compact();
    }

    /**
     * JWT令牌解密
     * @return 声明
     */
    public static Claims parseAuthJwt() {
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                .build();
        return parser.parseClaimsJws(tokenName).getBody();
    }

}
