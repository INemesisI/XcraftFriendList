package de.xcraft.INemesisI.Social.Commands.Friend;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.xcraft.INemesisI.Library.Command.XcraftCommand;
import de.xcraft.INemesisI.Library.Manager.XcraftPluginManager;
import de.xcraft.INemesisI.Social.Msg;
import de.xcraft.INemesisI.Social.Msg.Replace;
import de.xcraft.INemesisI.Social.Manager.SocialManager;
import de.xcraft.INemesisI.Social.Manager.SocialPlayer;

public class AcceptFriendCommand extends XcraftCommand {

	public AcceptFriendCommand() {
		super("friend", "accept", "accept|ac.*", "<PLAYER>", Msg.COMMAND_FRIEND_ACCEPT.toString(), "XcraftSocial.Friend.Accept");
	}

	@Override
	public boolean execute(XcraftPluginManager manager, CommandSender sender, String[] args) {
		SocialManager sManager = ((SocialManager) manager);
		SocialPlayer data = sManager.getPlayer(sender.getName());
		String player = null;
		for (String f : data.getFriends()) {
			if (f.startsWith("?") && f.toLowerCase().contains(args[0].toLowerCase())) {
				player = f.replace("?", "");
				break;
			}
		}
		if (player == null) {
			manager.plugin.getMessenger().sendInfo(sender,
					Msg.FRIEND_REQUEST_NOT_FOUND.toString(Replace.PLAYER(args[0])), true);
			return true;
		}
		if (player.equals(sender.getName())) {
			manager.plugin.getMessenger().sendInfo(sender, Msg.ERR_PLAYER_IS_SENDER.toString(), true);
			return true;
		} else {
			data.getFriends().remove("?" + player);
			data.getFriends().add(player);
			Player p = sManager.getBukkitPlayer(player);
			if (p != null) {
				manager.plugin.getMessenger().sendInfo(p,
						Msg.FRIEND_ACCEPT_NOTIFY.toString(Replace.PLAYER(sender.getName())),
						true);
				sManager.getPlayer(player).getFriends().add(sender.getName());
			} else {
				sManager.loadPlayer(player).getFriends().add(sender.getName());
				sManager.unloadPlayer(player);
			}
			manager.plugin.getMessenger().sendInfo(sender,
					Msg.FRIEND_ACCEPT_SUCCESSUFL.toString(Replace.PLAYER(player)), true);
			return true;
		}
	}
}
