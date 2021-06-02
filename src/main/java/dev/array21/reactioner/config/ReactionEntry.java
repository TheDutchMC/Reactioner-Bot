package dev.array21.reactioner.config;

import dev.array21.classvalidator.annotations.Required;

public class ReactionEntry {
	
	@Required
	public String reactionName;
	
	@Required
	public String roleId;
}
