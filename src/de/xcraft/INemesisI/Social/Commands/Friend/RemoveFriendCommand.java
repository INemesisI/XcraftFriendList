package de.xcraft.INemesisI.Social.Commands.Friend;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.xcraft.INemesisI.Library.Command.XcraftCommand;
import de.xcraft.INemesisI.Library.Manager.XcraftPluginManager;
import de.xcraft.INemesisI.Social.Msg;
import de.xcraft.INemesisI.Social.Msg.Replace;
import de.xcraft.INemesisI.Social.Manager.SocialManager;
import de.xcraft.INemesisI.Social.Manager.SocialPlayer;

public class RemoveFriendCommand extends XcraftCommand {

	public RemoveFriendCommand() {
		super("friend", "remove", "remove|r|r.*", "<PLAYER>", Msg.COMMAND_FRIEND_REMOVE.toString(),
				"XcraftSocial.Friend.Remove");
	}

	@Override
	public boolean execute(XcraftPluginManager manager, CommandSender sender, String[] args) {
		SocialManager sManager = (SocialManager) manager;
		SocialPlayer data = sManager.getPlayer(sender.getName());
		String player = null;
		for (String f : data.getFriends()) {
			if (f.toLowerCase().startsWith(args[0].toLowerCase())) {
				player = f;
				break;
			}
		}
		if (player == null) {
			manager.plugin.getMessenger().sendInfo(sender,
					Msg.ERR_PLAYER_NOT_IN_FRIENDLIST.toString(Replace.PLAYER(args[0])), true);
			return true;
		}
		Player p = sManager.getBukkitPlayer(player);
		if (p != null) {
			sManager.getPlayer(player).getFriends().remove(sender.getName());
			manager.plugin.getMessenger().sendInfo(p,
					Msg.FRIEND_REMOVE_NOTIFY.toString(Replace.PLAYER(sender.getName())), true);
		} else {
			sManager.loadPlayer(player).getFriends().remove(sender.getName());
			sManager.unloadPlayer(player);
		}
		data.getFriends().remove(player);
		manager.plugin.getMessenger().sendInfo(sender,
				Msg.FRIEND_REMOVE_SUCCESSFUL.toString(Replace.PLAYER(player)), true);
		return true;
	}
}