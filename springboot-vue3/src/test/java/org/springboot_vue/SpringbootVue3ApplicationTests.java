package org.springboot_vue;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@SpringBootTest
class SpringbootVue3ApplicationTests {

    @Test
    void contextLoads() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", "1");
        claims.put("username", "張三");
        String token = JWT.create()
                .withClaim("user", claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 3))
                .sign(Algorithm.HMAC256("itheima"));
        System.out.println(token);
    }

}
