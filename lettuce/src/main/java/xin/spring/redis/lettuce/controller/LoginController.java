package xin.spring.redis.lettuce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xin.spring.redis.lettuce.pojo.User;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
public class LoginController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @RequestMapping("/login")
    public Map<Object, Object> login(HttpServletRequest request, User user){
        HashMap<Object, Object> map = new HashMap<>();
        map.put(user.getId(), user);
        redisTemplate.boundValueOps("SESSION_USER:"+user.getId()).set(user,10, TimeUnit.HOURS);//.put(user.getId()+"", user);
        //redisTemplate.expire("SESSION_USER", 10, TimeUnit.HOURS);
        Long expire = redisTemplate.getExpire("SESSION_USER:" + user.getId(), TimeUnit.SECONDS);
        map.put("timeout", expire);
        return map;
    }

    @RequestMapping("/cache/{id}")
    public Map<Object, Object> getRedisCache(HttpServletRequest request, @PathVariable("id")String id){
        HashMap<Object, Object> map = new HashMap<>();
        User user = (User)redisTemplate.opsForValue().get("SESSION_USER:"+id);
        map.put("SESSION_USER", user);
        Long expire = redisTemplate.getExpire("SESSION_USER:" + id, TimeUnit.SECONDS);
        map.put("timeout", expire);
        return map;
    }

    @RequestMapping("/flush/{id}")
    public Map<Object, Object> flush(@PathVariable("id")String id){
        HashMap<Object, Object> map = new HashMap<>();
        User user = (User)redisTemplate.opsForValue().get("SESSION_USER:" + id);
        redisTemplate.expire("SESSION_USER:" + id, 10, TimeUnit.HOURS);
        map.put("SESSION_USER", user);
        Long expire = redisTemplate.getExpire("SESSION_USER:" + id, TimeUnit.SECONDS);
        map.put("timeout", expire);
        return map;
    }

}
