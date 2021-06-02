package dev.array21.reactioner.commands;

import java.util.Arrays;

import dev.array21.reactioner.config.ConfigManifest;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class CommandParameters {

	private Member m;
	private Guild g;
	private Message msg;
	private TextChannel c;
	
	private String[] args;
	private String[] argsRaw;
	private ConfigManifest config;
	
	public CommandParameters(Message msg, ConfigManifest config) {
		this.m = msg.getMember();
		this.g = msg.getGuild();
		this.c = msg.getTextChannel();
		this.msg = msg;
		this.config = config;
		
		String[] msgDisplayParts = msg.getContentDisplay().split(" ");
		if(msgDisplayParts.length >= 2) {
			this.args = Arrays.copyOfRange(msgDisplayParts, 1, msgDisplayParts.length);
		} else {
			this.args = new String[] {};
		}
		
		String[] msgRawParts = msg.getContentRaw().split(" ");
		if(msgRawParts.length >= 2) {
			this.argsRaw = Arrays.copyOfRange(msgRawParts, 1, msgRawParts.length);
		} else {
			this.argsRaw = new String[] {};
		}
	}
	
	public Member getMember() {
		return this.m;
	}
	
	public Guild getGuilder() {
		return this.g;
	}
	
	public Message getMessage() {
		return this.msg;
	}
	
	public TextChannel getChannel() {
		return this.c;
	}
	
	public String[] getArgs() {
		return this.args;
	}
	
	public String[] getRawArgs() {
		return this.argsRaw;
	}
	
	public ConfigManifest getConfig() {
		return this.config;
	}
}
