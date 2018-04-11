package app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration 
//@EnableRedisRepositories
public class RedisConfiguration {
/*	
	@Bean
	JedisConnectionFactory jedisConnection() {
		RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
		config.setHostName("127.0.0.1");
		config.setPort(6379);
		return new JedisConnectionFactory(config);
	}
	
	@Bean
	JedisConnectionFactory jedisClusterConnection() {

	    List<String> clusterNodes = Arrays.asList(
	    								"127.0.0.1:7000",
	    								"127.0.0.1:7001",
	    								"127.0.0.1:7002",
	    								"127.0.0.1:7003",
	    								"127.0.0.1:7004",
	    								"127.0.0.1:7005");
	    RedisClusterConfiguration config = new RedisClusterConfiguration(clusterNodes);
	    return new JedisConnectionFactory(config);

	}
*/	
	
	@Bean
	JedisConnectionFactory jedisConnection() {
		JedisConnectionFactory jedisConnection =  new JedisConnectionFactory();
		jedisConnection.setHostName("127.0.0.1");
				jedisConnection.setPort(6379);
				return jedisConnection;
	}

	 
	@Bean
	public StringRedisTemplate redisTemplate() {
		StringRedisTemplate redisTemplate = new StringRedisTemplate();
	    redisTemplate.setConnectionFactory(jedisConnection() );
//	    redisTemplate.setConnectionFactory(jedisClusterConnection());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();

    return redisTemplate;
	}

}
