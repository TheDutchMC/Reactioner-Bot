package dev.array21.reactioner.listeners;

import dev.array21.reactioner.commands.CommandManager;
import dev.array21.reactioner.commands.CommandParameters;
import dev.array21.reactioner.config.ConfigManifest;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildMessageReceivedEventListener extends ListenerAdapter {

	private ConfigManifest config;
	private CommandManager manager;
	
	public GuildMessageReceivedEventListener(ConfigManifest config, CommandManager manager) {
		this.config = config;
		this.manager = manager;
	}
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String msg = event.getMessage().getContentDisplay();
		if(msg.startsWith(this.config.commandPrefix)) {
			CommandParameters cmdParams = new CommandParameters(event.getMessage(), this.config);
			String cmd = msg.split(" ")[0].replace(this.config.commandPrefix, "");
			this.manager.fireCommand(cmd, cmdParams);
		}
	}
}
