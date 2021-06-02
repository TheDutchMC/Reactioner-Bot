package dev.array21.reactioner.config;

import dev.array21.classvalidator.annotations.External;
import dev.array21.classvalidator.annotations.Required;

public class ConfigManifest {

	@Required
	public String botToken;
	
	@Required
	public String commandPrefix;
	
	@Required
	@External
	public MessageEntry[] messageEntries;
	
}
