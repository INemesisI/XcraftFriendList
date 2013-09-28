package de.xcraft.INemesisI.Social.Commands.Group;

import org.bukkit.command.CommandSender;

import de.xcraft.INemesisI.Library.Command.XcraftCommand;
import de.xcraft.INemesisI.Library.Manager.XcraftPluginManager;
import de.xcraft.INemesisI.Social.Msg;
import de.xcraft.INemesisI.Social.Msg.Replace;
import de.xcraft.INemesisI.Social.Manager.SocialGroup;
import de.xcraft.INemesisI.Social.Manager.SocialManager;
import de.xcraft.INemesisI.Social.Manager.SocialPlayer;

public class KickFromGroupCommand extends XcraftCommand {

	public KickFromGroupCommand() {
		super("group", "kick", "kick|k.*", "<Group> <PLAYER>", Msg.COMMAND_GROUP_KICK.toString(),
				"XcraftSocial.Group.Kick");
	}

	@Override
	public boolean execute(XcraftPluginManager manager, CommandSender sender, String[] args) {
		SocialManager sManager = (SocialManager) manager;
		if (!sManager.getGroups().containsKey(args[0])) {
			manager.plugin.getMessenger().sendInfo(sender,
					Msg.GROUP_WRONG_NAME.toString(Replace.GROUP(args[0])), true);
			return false;
		}
		SocialGroup group = sManager.getGroups().get(args[0]);
		String player = sManager.getPlayerName(args[1]);
		if (player == null) {
			manager.plugin.getMessenger().sendInfo(sender,
					Msg.ERR_PLAYER_NOT_FOUND.toString(Replace.PLAYER(args[1])), true);
			return false;
		}
		if (!group.getOwners().contains(sender.getName())) {
			manager.plugin.getMessenger().sendInfo(sender,
					Msg.GROUP_NOT_OWNER.toString(Replace.PLAYER(args[0])), true);
		}
		SocialPlayer data = sManager.loadPlayer(player);
		if (!group.removePlayer(data)) {
			manager.plugin.getMessenger().sendInfo(sender,
					Msg.GROUP_KICK_NOT_IN_GROUP.toString(Replace.PLAYER(player), Replace.GROUP(group.getName())), true);
			return false;

		}
		group.chat(
				sManager,
				sender.getName(),
				Msg.GROUP_PLAYER_KICKED.toString(Replace.PLAYER(player),
						Replace.GROUP(group.getName())));

		return true;
	}
}