package de.xcraft.INemesisI.Social.Commands.Group;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.xcraft.INemesisI.Library.Command.XcraftCommand;
import de.xcraft.INemesisI.Library.Manager.XcraftCommandManager;
import de.xcraft.INemesisI.Library.Manager.XcraftPluginManager;
import de.xcraft.INemesisI.Social.Msg;
import de.xcraft.INemesisI.Social.Msg.Replace;
import de.xcraft.INemesisI.Social.Manager.SocialManager;

public class ToggleGroupCommand extends XcraftCommand {

	public ToggleGroupCommand(XcraftCommandManager cManager, String command, String name, String pattern, String usage, String desc, String permission) {
		super(cManager, command, name, pattern, usage, desc, permission);
	}

	@Override
	public boolean execute(XcraftPluginManager manager, CommandSender sender, String[] args) {
		SocialManager sManager = (SocialManager) manager;
		Player player = (Player) sender;
		if (args[0].equals("off")) {
			sManager.getPlayer(player.getName()).setActiveGroup(null);
			manager.plugin.getMessenger().sendInfo(sender, Msg.GROUP_TOGGLED_OFF.toString(), true);
			return true;
		} else if (sManager.getGroups().containsKey(args[0])) {
			sManager.getPlayer(player.getName()).setActiveGroup(sManager.getGroups().get(args[0]).getName());
			manager.plugin.getMessenger().sendInfo(sender,
					Msg.GROUP_TOGGLED.toString(Replace.GROUP(args[0])), true);
			return true;
		} else
			manager.plugin.getMessenger().sendInfo(sender,
					Msg.GROUP_WRONG_NAME.toString(Replace.GROUP(args[0])), true);
		return true;
	}
}
