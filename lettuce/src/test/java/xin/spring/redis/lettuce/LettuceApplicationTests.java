package xin.spring.redis.lettuce;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import xin.spring.redis.lettuce.pojo.User;

import java.util.ArrayList;
import java.util.HashMap;

@SpringBootTest
class LettuceApplicationTests {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    void contextLoads() {
        for (int i=0; i< 10; i++) {
            long l = System.currentTimeMillis();
            String s = String.valueOf(l);
            String key = "user:" + s.substring(s.length() - 1);
            redisTemplate.opsForValue().set(key, "董小姐");
            // 获取数据
            String value = (String) redisTemplate.opsForValue().get(key);
            System.out.println("获取缓存中key为" + key + "的值为：" + value);

            User user = new User();
            user.setUsername("董小姐");
            user.setSex(18);
            user.setId(1L);
            String userKey = "dxj:" + s.substring(s.length() - 1);
            redisTemplate.opsForValue().set(userKey, user);
            User newUser = (User) redisTemplate.opsForValue().get(userKey);
            System.out.println("获取缓存中key为" + userKey + "的值为：" + newUser);
        }
    }

    /**
     * opsForValue： 对应 String（字符串）
     * opsForZSet： 对应 ZSet（有序集合）
     * opsForHash： 对应 Hash（哈希）
     * opsForList： 对应 List（列表）
     * opsForSet： 对应 Set（集合）
     */


    @Test
    void opsForList(){
        ArrayList<User> users = new ArrayList<>();
        String key = "users:" + 1;
        for (int i=0;i<10;i++){
            User user = new User();
            user.setUsername("董小姐");
            user.setSex(18);
            user.setId((long)i);
            users.add(user);
        }
        redisTemplate.opsForList().rightPush(key, users);

        //取出
        Object index = redisTemplate.opsForList().index(key, 0);
        System.out.println(index);
    }

    @Test
    void opsForHash(){
        HashMap<Object, Object> map = new HashMap<>();
        String key = "user:map:";
        map.put("username", "董小姐");
        map.put("age", 18);
        map.put("sex", "女");
        redisTemplate.opsForHash().put(key, "1", map);
        Object o = redisTemplate.opsForHash().get(key, "1");
        System.out.println("redis结果===》");
        System.out.println(o);
    }

}
