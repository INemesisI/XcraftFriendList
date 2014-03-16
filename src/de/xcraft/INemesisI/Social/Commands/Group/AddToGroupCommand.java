package de.xcraft.INemesisI.Social.Commands.Group;

import org.bukkit.command.CommandSender;

import de.xcraft.INemesisI.Library.Command.XcraftCommand;
import de.xcraft.INemesisI.Library.Manager.XcraftCommandManager;
import de.xcraft.INemesisI.Library.Manager.XcraftPluginManager;
import de.xcraft.INemesisI.Social.Msg;
import de.xcraft.INemesisI.Social.Msg.Replace;
import de.xcraft.INemesisI.Social.Manager.SocialGroup;
import de.xcraft.INemesisI.Social.Manager.SocialManager;
import de.xcraft.INemesisI.Social.Manager.SocialPlayer;

public class AddToGroupCommand extends XcraftCommand {

	public AddToGroupCommand(XcraftCommandManager cManager, String command, String name, String pattern, String usage, String desc, String permission) {
		super(cManager, command, name, pattern, usage, desc, permission);
	}

	@Override
	public boolean execute(XcraftPluginManager manager, CommandSender sender, String[] args) {
		SocialManager sManager = (SocialManager) manager;
		if (!sManager.getGroups().containsKey(args[0])) {
			manager.plugin.getMessenger().sendInfo(sender,
					Msg.GROUP_WRONG_NAME.toString(Replace.PLAYER(args[0])), true);
			return false;
		}
		SocialGroup group = sManager.getGroups().get(args[0]);
		if (!group.getOwners().contains(sender.getName())) {
			manager.plugin.getMessenger().sendInfo(sender, Msg.ERR_GROUP_NOT_OWNER.toString(), true);
			return true;
		}
		String player = sManager.getPlayerName(args[1]);
		if (player == null) {
			manager.plugin.getMessenger().sendInfo(sender, Msg.ERR_PLAYER_NOT_FOUND.toString(Replace.PLAYER(player)), true);
			return false;
		}
		SocialPlayer data;
		boolean loaded = true;
		if (sManager.getPlayers().containsKey(player)) {
			data = sManager.getPlayer(player);
			loaded = false;
		} else {
			data = sManager.loadPlayer(player);
		}
		if (group.isPlayerInGroup(data)) {
			manager.plugin.getMessenger().sendInfo(sender,
					Msg.ERR_PLAYER_ALREADY_IN_GROUP.toString(Replace.PLAYER(player), Replace.GROUP(group.getName())), true);
			return false;
		}
		data.getGroups().add(group.getName());
		if (loaded) {
			sManager.unloadPlayer(player);
		}
		group.addPlayer(data, args[2].equals("owner"));
		group.chat(sManager, sender.getName(),
				Msg.GROUP_PLAYER_ADDED.toString(Replace.PLAYER(player),
						Replace.GROUP(group.getName())));
		return true;

	}
}
