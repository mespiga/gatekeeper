package app.data.cmx.notification;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;

import app.data.cmx.notification.Rules.RulesBuilder.Condition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rules {
	List<Condition> conditions = new ArrayList<>();

	public static RulesBuilder builder() {
		return new RulesBuilder();
	}

	public static class RulesBuilder {

		private List<Condition> conditions = new ArrayList<>();

		public RulesBuilder conditions(String... conds) {
			for (String condition : conds) {
				Condition c = new Condition(condition);
				this.conditions.add(c);

			}
			return this;
		}

		public Rules build() {
			return new Rules(this.conditions);
		}

		@Data
		class Condition {
			private String condition;

			public Condition(String condition) {
				this.condition = condition;
			}

		}

	}

}
