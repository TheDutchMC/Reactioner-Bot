package dev.array21.reactioner.commands.executors;

import java.util.regex.Pattern;

import com.vdurmont.emoji.EmojiParser;

import dev.array21.reactioner.commands.Command;
import dev.array21.reactioner.commands.CommandParameters;
import dev.array21.reactioner.commands.ICommand;

@Command(command = "emote")
public class GetEmoteNameExecutor implements ICommand {
	
	@Override
	public void runCommand(CommandParameters params) {
		String[] args = params.getArgs();
		if(args.length != 1) {
			params.getChannel().sendMessage(String.format("Invalid usage. Usage: ``%semote :emote:``", params.getConfig().commandPrefix)).queue();
			return;
		}
		
		String emoteName = EmojiParser.parseToAliases(args[0]).replaceAll(Pattern.quote(":"), "");
		String senderEmote = params.getRawArgs()[0];
		
		params.getChannel().sendMessage(String.format("The emote %s is called ``%s``.", senderEmote, emoteName)).queue();;	
	}
}
