package de.xcraft.INemesisI.Social.Commands.Group;

import org.bukkit.command.CommandSender;

import de.xcraft.INemesisI.Library.Command.XcraftCommand;
import de.xcraft.INemesisI.Library.Manager.XcraftCommandManager;
import de.xcraft.INemesisI.Library.Manager.XcraftPluginManager;
import de.xcraft.INemesisI.Social.Msg;
import de.xcraft.INemesisI.Social.Msg.Replace;
import de.xcraft.INemesisI.Social.Manager.SocialGroup;
import de.xcraft.INemesisI.Social.Manager.SocialManager;


public class InfoGroupCommand extends XcraftCommand {

	public InfoGroupCommand(XcraftCommandManager cManager, String command, String name, String pattern, String usage, String desc, String permission) {
		super(cManager, command, name, pattern, usage, desc, permission);
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
		manager.plugin.getMessenger().sendInfo(sender, "Group info of " + group.getName(), false);
		manager.plugin.getMessenger().sendInfo(sender, "Owner:" + group.getOwners().toString(), false);
		manager.plugin.getMessenger().sendInfo(sender, "Member:" + group.getMembers().toString(), false);
		return true;
	}
}
