package app.data.cmx.notification;

import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;

@Builder
public class Subscribers{
	
	@Getter public List<Map<String, String>> receivers;
	
}
