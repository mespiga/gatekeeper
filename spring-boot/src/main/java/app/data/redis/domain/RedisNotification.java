package app.data.redis.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


@Builder
@ToString
public class RedisNotification {
	
	@Getter public boolean wasSent = false;
	@Getter public String notification=null;

}
