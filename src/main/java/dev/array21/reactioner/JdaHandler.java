package dev.array21.reactioner;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class JdaHandler {

	private JDA jda;
	
	public JDA init(String token) {
		JDABuilder builder = JDABuilder.createDefault(token)
				.setActivity(Activity.listening("Listening for reactions"))
				.enableIntents(GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.GUILD_MESSAGES);

		try {
			jda = builder.build();
		} catch(LoginException e) {
			Reactioner.logWarn("Failed to create JDA instance: " + e.getMessage());
			System.exit(1);
		}
		
		return jda;
	}
	
	public void shutdownJda() {
		try {
			jda.shutdownNow();
		} catch(Exception e) {}
	}
}
