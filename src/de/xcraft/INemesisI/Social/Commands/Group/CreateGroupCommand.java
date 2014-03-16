package de.xcraft.INemesisI.Social.Commands.Group;

import org.bukkit.command.CommandSender;

import de.xcraft.INemesisI.Library.Command.XcraftCommand;
import de.xcraft.INemesisI.Library.Manager.XcraftCommandManager;
import de.xcraft.INemesisI.Library.Manager.XcraftPluginManager;
import de.xcraft.INemesisI.Social.Msg;
import de.xcraft.INemesisI.Social.Msg.Replace;
import de.xcraft.INemesisI.Social.Manager.SocialGroup;
import de.xcraft.INemesisI.Social.Manager.SocialManager;

public class CreateGroupCommand extends XcraftCommand {

	public CreateGroupCommand(XcraftCommandManager cManager, String command, String name, String pattern, String usage, String desc, String permission) {
		super(cManager, command, name, pattern, usage, desc, permission);
	}

	@Override
	public boolean execute(XcraftPluginManager manager, CommandSender sender, String[] args) {
		SocialManager sManager = (SocialManager) manager;
		if (sManager.getGroups().containsKey(args[0])) {
			manager.plugin.getMessenger().sendInfo(sender, Msg.ERR_GROUP_ALREADY_EXIST.toString(), true);
			return true;
		}
		String name = args[0];
		sManager.getGroups().put(name, new SocialGroup(name, sender.getName()));
		sManager.getPlayer(sender.getName()).getGroups().add(name);
		manager.plugin.getMessenger().sendInfo(sender, Msg.GROUP_CREATED.toString(Replace.GROUP(name)),
				true);
		return true;
	}
}