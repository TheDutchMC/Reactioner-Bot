package dev.array21.reactioner.listeners;

import dev.array21.reactioner.config.ConfigManifest;
import dev.array21.reactioner.config.MessageEntry;
import dev.array21.reactioner.config.ReactionEntry;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildMessageReactionRemoveListener extends ListenerAdapter {
	
	private ConfigManifest config;
	
	public GuildMessageReactionRemoveListener(ConfigManifest config) {
		this.config = config;
	}
	
	@Override
 	public void onGuildMessageReactionRemove(GuildMessageReactionRemoveEvent event) {
		MessageEntry entry = null;
		
		for(MessageEntry e : this.config.messageEntries) {
			if(e.messageId.equals(event.getMessageId())) {
				entry = e;
			}
		}
		
		if(entry == null) {
			return;
		}
		
		String emote = Common.getEmoteName(event.getReactionEmote());
		
		for(ReactionEntry re : entry.reactionEntries) {
			if(re.reactionName.equals(emote)) {
				Guild g = event.getGuild();
				g.removeRoleFromMember(event.retrieveMember().complete(), g.getRoleById(re.roleId)).queue();
			}
		}
	}
}
