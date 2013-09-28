package de.xcraft.INemesisI.Social.Commands.Friend;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

import de.xcraft.INemesisI.Library.Command.XcraftCommand;
import de.xcraft.INemesisI.Library.Manager.XcraftPluginManager;
import de.xcraft.INemesisI.Social.Msg;
import de.xcraft.INemesisI.Social.Msg.Replace;
import de.xcraft.INemesisI.Social.Manager.SocialManager;
import de.xcraft.INemesisI.Social.Manager.SocialPlayer;

public class ListFriendsCommand extends XcraftCommand {

	public ListFriendsCommand() {
		super("friend", "list", "list|l|l.*", "[offline/online/pending/all]", Msg.COMMAND_FRIEND_LIST.toString(), "XcraftSocial.Friend.List");
	}

	@Override
	public boolean execute(XcraftPluginManager manager, CommandSender sender, String[] args) {
		SocialPlayer data = ((SocialManager) manager).getPlayer(sender.getName());
		List<String> online = new ArrayList<String>();
		List<String> offline = new ArrayList<String>();
		List<String> pending = new ArrayList<String>();
		for (String name : data.getFriends()) {
			if (name.startsWith("?")) {
				pending.add(name.replace("?", ""));
			} else if (((SocialManager) manager).getBukkitPlayer(name) != null) {
				online.add(name);
			} else {
				offline.add(name);
			}
		}
		if ((args.length == 0) || args[0].matches("online") || args[0].matches("all")) {
			manager.plugin.getMessenger().sendInfo(sender,
					Msg.FRIENDS_LIST_ONLINE.toString(Replace.LIST(online.size() != 0 ? online.toString() : Msg.NONE.toString())), true);
		}
		if ((args.length == 0) || args[0].matches("offline") || args[0].matches("all")) {
			manager.plugin.getMessenger().sendInfo(sender,
					Msg.FRIENDS_LIST_OFFLINE.toString(Replace.LIST(offline.size() != 0 ? offline.toString() : Msg.NONE.toString())), true);
		}
		if ((args.length == 0) || args[0].matches("pending") || args[0].matches("all")) {
			manager.plugin.getMessenger().sendInfo(sender,
					Msg.FRIENDS_LIST_PENDING.toString(Replace.LIST(pending.size() != 0 ? pending.toString() : Msg.NONE.toString())), true);
		}
		return true;
	}
}