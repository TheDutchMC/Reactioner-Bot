package dev.array21.reactioner.config;

import dev.array21.classvalidator.annotations.External;
import dev.array21.classvalidator.annotations.Required;

public class MessageEntry {

	@Required
	public String messageId;
	
	@Required
	@External
	public ReactionEntry[] reactionEntries;
}
