package app.data.redis.repository;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import app.utils.JsonUtil;

@Repository
public class RedisHashRepository extends RedisRepository {

	@Autowired
	public RedisHashRepository(RedisTemplate<String, String> redisTemplate) {
		super(redisTemplate);
	}

	@PostConstruct
	private void init() {
		hashOps = redisTemplate.opsForHash();
	}

	public void saveHashData(String key, String hashKey, String data) {
		hashOps.put(key, hashKey, data);
	}

	public void saveHashData(String hashKey, String data) {
		saveHashData(KEY, hashKey, data);
	}

	public void updateNotification(String key, String hashKey, String data) {
		hashOps.put(key, hashKey, data);
	}

	public void updateNotification(String hashKey, String data) {
		updateNotification(KEY, hashKey, data);
	}

	/* FIND OPERATIONS */
	public String findByHashKey(String hashKey) {
		return findByHashKey(KEY, hashKey);
	}

	public String findByHashKey(String key, String hashKey) {
		String data = hashOps.get(key, hashKey);
		return data;
	}

	public <T> T findByHashKey(String key, String hashKey, Class<T> clazz) {
		String data = hashOps.get(KEY, hashKey);
		return JsonUtil.fromString(data, clazz);
	}

	public <T> T findByHashKey(String hashKey, Class<T> clazz) {
		return findByHashKey(KEY, hashKey, clazz);
	}

	public Map<String, String> findAll() {
		return findAll(KEY);
	}

	public Map<String, String> findAll(String key) {
		return hashOps.entries(key);
	}

	/* DELETE OPERATIONS */
	public void deleteByHashKey(String hashKey) {
		deleteByHashKey(KEY, hashKey);
	}

	public void deleteByHashKey(String key, String hashKey) {
		hashOps.delete(key, hashKey);
	}

}
