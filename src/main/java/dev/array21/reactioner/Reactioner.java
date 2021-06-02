package dev.array21.reactioner;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import dev.array21.reactioner.commands.CommandManager;
import dev.array21.reactioner.commands.executors.GetEmoteNameExecutor;
import dev.array21.reactioner.config.Config;
import dev.array21.reactioner.config.ConfigManifest;
import dev.array21.reactioner.listeners.GuildMessageReactionAddListener;
import dev.array21.reactioner.listeners.GuildMessageReactionRemoveListener;
import dev.array21.reactioner.listeners.GuildMessageReceivedEventListener;
import net.dv8tion.jda.api.JDA;

public class Reactioner {
	
	private static final Logger LOGGER = LogManager.getLogger(Reactioner.class);
	
	public static void main(String[] args) {
		new Reactioner().init();
	}
	
	private void init() {
		logInfo("Starting Reactioner");
		logInfo("Loading config...");
		
		String isContainerEnv = System.getenv("IS_CONTAINER");
		boolean isContainer = isContainerEnv != null && isContainerEnv.equalsIgnoreCase("TRUE");
		ConfigManifest config = new Config().loadConfig(isContainer);
		
		if(config == null) {
			logWarn("Failed to load config.");
			System.exit(1);
		}
		
		logInfo("Config loaded.");
		logInfo("Registering commands...");
		
		CommandManager cmdManager = new CommandManager();
		cmdManager.registerCommand(new GetEmoteNameExecutor());
		
		logInfo("Commands registered.");
		logInfo("Loading JDA...");
		
		String token;
		if(config.botToken.equals("FROM_ENV")) {
			String tokenEnv = System.getenv("BOT_TOKEN");
			if(tokenEnv == null) {
				logWarn("Config key 'botToken' is equal to 'FROM_ENV', but the environmental variable 'BOT_TOKEN' is not set.");
				System.exit(1);
			}
			token = tokenEnv;
		} else {
			token = config.botToken;
		}
		
		JdaHandler jdaHandler = new JdaHandler();
		JDA jda = jdaHandler.init(token);
		
		jda.addEventListener(
				new GuildMessageReactionAddListener(config),
				new GuildMessageReactionRemoveListener(config),
				new GuildMessageReceivedEventListener(config, cmdManager));
		
		logInfo("JDA loaded.");
		shutdownHook(jdaHandler);
		logInfo("Startup complete.");
	}
	
	private void shutdownHook(JdaHandler jdaHandler) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				logInfo("Shutting down Reactioner.");
				jdaHandler.shutdownJda();
			}
		});
	}
	
	public static void logInfo(Object o) {
		LOGGER.info(o);
	}
	
	public static void logWarn(Object o) {
		LOGGER.warn(o);
	}
}
