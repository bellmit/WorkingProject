package com.ruoyi.homewifi.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;
/**
 * @Author:Z
 * @Data:2021/8/31 15:43
 * @Description: redis工具类
 * @Version:1.0
 */

@Component
public class RedisUtils {

    protected static final Logger logger = LoggerFactory.getLogger(RedisUtils.class);

    /**
     * JedisPool 无法通过@Autowired注入，可能由于是方法bean的原因，此处可以先注入RedisConfig，
     * 然后通过@PostConstruct初始化的时候将factory直接赋给jedisPool
     */
    /*private static JedisPool jedisPool;
    @Autowired
    private RedisConfig redisConfig;
    @PostConstruct
    public void init() {
        jedisPool = redisConfig.redisPoolFactory();
    }*/

    @Autowired
    private static JedisPool jedisPool;

    private static Integer indexdb = null;

    @Value("${spring.redis.fl-database}")
    public void setIndexdb(Integer districtIndexdb) {
        RedisUtils.indexdb = districtIndexdb;
    }

    public static String get(String key) {
        Jedis jedis = null;
        String value = null;

        try {
            jedis = jedisPool.getResource();//获取一个jedis实例
            jedis.select(indexdb);
            value = jedis.get(key);
        } catch (Exception e) {
            logger.error("错误日志："+e.getMessage());
        } finally {
            jedis.close();
        }
        return value;
    }
}
