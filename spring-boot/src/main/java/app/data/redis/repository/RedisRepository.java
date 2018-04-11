package app.data.redis.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;

public class RedisRepository {

	protected static final String						KEY	= "CLOUD_WIFI";

	protected RedisTemplate<String, String>				redisTemplate;
	protected HashOperations<String, String, String>	hashOps;
	protected SetOperations<String, String>				setOps;
	protected ListOperations<String, String>			listOps;


	@Autowired
	public RedisRepository(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public String getMacAddressKey(String macAddress) {
		return KEY + ":MAC:" + macAddress;
	}

	public String getSessionKey(String sessionId) {
		return KEY + ":SESSION:" + sessionId;
	}
	
	public void deleteKey(String key) {
		redisTemplate.delete(key);
	}

}
