package de.xcraft.INemesisI.Social.Commands.Group;

import java.util.List;

import org.bukkit.command.CommandSender;

import de.xcraft.INemesisI.Library.Command.XcraftCommand;
import de.xcraft.INemesisI.Library.Manager.XcraftPluginManager;
import de.xcraft.INemesisI.Social.Msg;
import de.xcraft.INemesisI.Social.Msg.Replace;
import de.xcraft.INemesisI.Social.Manager.SocialManager;

public class ListGroupsCommand extends XcraftCommand {

	public ListGroupsCommand() {
		super("group", "list", "list|l|li.*", "", Msg.COMMAND_GROUP_LIST.toString(),
				"XcraftSocial.Group.List");
	}

	@Override
	public boolean execute(XcraftPluginManager manager, CommandSender sender, String[] args) {
		List<String> groups = ((SocialManager) manager).getPlayer(sender.getName()).getGroups();
		if (groups.size() == 0) {
			manager.plugin.getMessenger().sendInfo(sender, Msg.GROUP_NO_GROUP.toString(), false);
			return true;
		}
		manager.plugin.getMessenger().sendInfo(sender,
				Msg.GROUP_LIST.toString(Replace.LIST(groups.toString())), false);
		return true;
	}
}
