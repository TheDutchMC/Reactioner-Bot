package dev.array21.reactioner.listeners;

import java.util.regex.Pattern;

import com.vdurmont.emoji.EmojiParser;

import net.dv8tion.jda.api.entities.MessageReaction.ReactionEmote;

public class Common {

	public static String getEmoteName(ReactionEmote e) {
		String emojiNameTextual;
		if(e.isEmoji()) {
			emojiNameTextual = EmojiParser.parseToAliases(e.getName()).replaceAll(Pattern.quote(":"), "");
		} else {
			emojiNameTextual = e.getEmote().getName().replaceAll(Pattern.quote(":"), "");
		}
		
		return emojiNameTextual;
	}	
}
