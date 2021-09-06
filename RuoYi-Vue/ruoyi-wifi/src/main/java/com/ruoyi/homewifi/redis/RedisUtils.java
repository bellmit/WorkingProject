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


    private static JedisPool jedisPool;

    @Autowired
    public void setJedisPool(JedisPool jedisPool){
        RedisUtils.jedisPool = jedisPool;
    }


    private static Integer flIndex = null;
    @Value("${spring.redis.fl-database}")
    public void setIndexdb(Integer districtIndexdb) {
        RedisUtils.flIndex = districtIndexdb;
    }

    private static Integer fileNameIndex;
    @Value("${spring.redis.database}")
    public void setfileNameIndexdb(Integer fileNameIndexdb) {
        RedisUtils.fileNameIndex = fileNameIndexdb;
    }

    //从27服务器8号库获取丰联城市编码
    public static String getFlCityCode(String key) {
        Jedis jedis = null;
        String value = null;
        try {
            jedis = jedisPool.getResource();//获取一个jedis实例
            jedis.select(flIndex);
            value = jedis.get(key);
        } catch (Exception e) {
            logger.error("redis连接错误,错误日志："+e.getMessage());
        } finally {
            if(jedis != null){
                jedis.close();
            }
        }
        return value;
    }

    //将下发文件名存入27服务器redis缓存7号库,10天过期
    public static void saveFileName(String fileName){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();//获取一个jedis实例
            jedis.select(fileNameIndex);
            jedis.set(fileName,"");
            jedis.expire(fileName,864000);
        } catch (Exception e) {
            logger.error("redis连接错误,错误日志："+e.getMessage());
        } finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }

    //查询存入redis的文件名是否存在
    public static String getFileName(String fileName){
        Jedis jedis = null;
        String value = null;
        try {
            jedis = jedisPool.getResource();//获取一个jedis实例
            jedis.select(fileNameIndex);
            value = jedis.get(fileName);
        } catch (Exception e) {
            logger.error("redis连接错误,错误日志："+e.getMessage());
        } finally {
            if(jedis != null){
                jedis.close();
            }
        }
        return value;
    }
}
