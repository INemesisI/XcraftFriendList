package de.xcraft.INemesisI.Social.Commands.Group;

import org.bukkit.command.CommandSender;

import de.xcraft.INemesisI.Library.Command.XcraftCommand;
import de.xcraft.INemesisI.Library.Manager.XcraftCommandManager;
import de.xcraft.INemesisI.Library.Manager.XcraftPluginManager;
import de.xcraft.INemesisI.Social.Msg;
import de.xcraft.INemesisI.Social.Manager.SocialGroup;
import de.xcraft.INemesisI.Social.Manager.SocialManager;

public class ChatGroupCommand extends XcraftCommand {

	public ChatGroupCommand(XcraftCommandManager cManager, String command, String name, String pattern, String usage, String desc, String permission) {
		super(cManager, command, name, pattern, usage, desc, permission);
	}

	@Override
	public boolean execute(XcraftPluginManager manager, CommandSender sender, String[] args) {
		SocialManager sManager = (SocialManager) manager;
		SocialGroup group = null;
		if (args.length == 0) {
			return false;
		}
		String name = sManager.getPlayer(sender.getName()).getActiveGroup();
		if (name != null) {
			group = sManager.getGroups().get(name);
		}
		if (sManager.getGroups().containsKey(args[0])) {
			group = sManager.getGroups().get(args[0]);
		} else {
			manager.plugin.getMessenger().sendInfo(sender, Msg.GROUP_WRONG_NAME.toString(), true);
			return true;
		}
		String message = "";
		for (int i = 1; i < args.length; i++) {
			message += args[i] + " ";
		}
		message = message.trim();
		group.chat(sManager, sender.getName(), message);
		return true;
	}

}
