package xin.spring.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@SpringBootTest
class RedisApplicationTests {

    @Autowired
    JedisPool jedisPool;

    @Test
    void contextLoads() {
        System.out.println(jedisPool);
    }

    @Test
    void testJedis(){
        Jedis jedis = jedisPool.getResource();
        jedis.close();
    }

}
