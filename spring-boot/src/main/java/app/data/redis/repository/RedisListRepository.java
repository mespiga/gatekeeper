package app.data.redis.repository;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisListRepository extends RedisRepository {

	@Autowired
	public RedisListRepository(RedisTemplate<String, String> redisTemplate) {
		super(redisTemplate);
	}

	@PostConstruct
	private void init() {
		listOps = redisTemplate.opsForList();
	}

	/* SAVE OPERATIONS */
	/* Save register at the end of the list */
	public void saveRData(String key, String data) {
		listOps.rightPush(key, data);
	}

	public void saveRData(String data) {
		saveRData(KEY, data);
	}

	public void saveRAllData(String key, String... data) {
		listOps.rightPushAll(key, data);
	}

	public void saveRAllData(String... data) {
		saveRAllData(KEY, data);
	}

	/* Save register at the begin of the list */
	public void saveLData(String key, String data) {
		listOps.leftPushAll(key, data);
	}

	public void saveLData(String data) {
		saveLData(KEY, data);
	}

	public void saveLAllData(String key, String... data) {
		listOps.leftPushAll(key, data);
	}

	public void saveLAllData(String... data) {
		saveLAllData(KEY, data);
	}

	/* GET OPERATIONS */
	/* Get last element and remove it */
	public String getRData(String key) {
		return listOps.rightPop(key);
	}

	public String getRData() {
		return getRData(KEY);
	}

	/* Get first element and remove it */

	public String getLData(String key) {
		return listOps.leftPop(key);
	}

	public String getLData() {
		return getLData(KEY);
	}

	/* Get Last element but don't remove it from the list */
	public String getLastElement(String key) {
		return listOps.index(key, -1);
	}

	public String getLastElement() {
		return getLastElement(KEY);
	}

	/* Get All elements but don't remove them from the list */
	public List<String> getAllElements() {
		return getAllElements(KEY);
	}

	public List<String> getAllElements(String key) {
		return listOps.range(key, 0, -1);
	}

}
