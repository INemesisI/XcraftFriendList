package de.xcraft.INemesisI.Social.Commands.Friend;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.xcraft.INemesisI.Library.Command.XcraftCommand;
import de.xcraft.INemesisI.Library.Manager.XcraftPluginManager;
import de.xcraft.INemesisI.Social.Msg;
import de.xcraft.INemesisI.Social.Msg.Replace;
import de.xcraft.INemesisI.Social.Manager.SocialPlayer;
import de.xcraft.INemesisI.Social.Manager.SocialManager;

public class AddFriendCommand extends XcraftCommand {

	public AddFriendCommand() {
		super("friend", "add", "add|a|ad.*", "<PLAYER>", Msg.COMMAND_FRIEND_ADD.toString(), "XcraftSocial.Friend.Add");
	}
	
	@Override
	public boolean execute(XcraftPluginManager manager, CommandSender sender, String[] args) {
		SocialManager sManager = (SocialManager) manager;
		SocialPlayer data = sManager.getPlayer(sender.getName());
		Player player = sManager.getBukkitPlayer(args[0]);
		if (player == null) {
			manager.plugin.getMessenger().sendInfo(sender,
					Msg.ERR_PLAYER_NOT_ONLINE.toString(Replace.PLAYER(args[0])), true);
			return true;
		}
		if (player.hasPermission("XcraftSocial.Friend.Add.exempt")) {
			manager.plugin.getMessenger().sendInfo(sender,
					Msg.FRIEND_ADD_EXEMPT.toString(Replace.PLAYER(player.getName())), true);
			return true;
		}
		if (player.getName().equals(sender.getName())) {
			manager.plugin.getMessenger().sendInfo(sender, Msg.ERR_PLAYER_IS_SENDER.toString(), true);
			return true;
		} else {
			SocialPlayer friend = sManager.getPlayer(player.getName());
			if (data.getFriends().contains("?" + player.getName())) {
				manager.plugin.getMessenger().sendInfo(sender,
						Msg.FRIEND_ADD_WRONG_CMD.toString(Replace.PLAYER(player.getName())), true);
				return true;
			}
			if (friend.getFriends().contains("?" + sender.getName())) {
				manager.plugin.getMessenger()
						.sendInfo(sender, Msg.FRIEND_ADD_ALREADY_ADDED.toString(Replace.PLAYER(player
								.getName())), true);
				manager.plugin.getMessenger().sendInfo(player,
						Msg.FRIEND_ADD_NOTIFY.toString(Replace.PLAYER(sender.getName())), true);
				return true;
			}
			if (friend.getFriends().contains(sender.getName())) {
				manager.plugin.getMessenger().sendInfo(sender, Msg.ERR_PLAYER_ALREADY_IN_FRIENDLIST
						.toString(Replace.PLAYER(player.getName())), true);
				return true;
			} else {
				friend.getFriends().add("?" + sender.getName());
				manager.plugin.getMessenger().sendInfo(sender,
						Msg.FRIEND_ADD_SUCCESSFUL.toString(Replace.PLAYER(player.getName())), true);
				manager.plugin.getMessenger().sendInfo(player,
						Msg.FRIEND_ADD_NOTIFY.toString(Replace.PLAYER(player.getName())), true);
				return true;
			}
		}
	}
}
