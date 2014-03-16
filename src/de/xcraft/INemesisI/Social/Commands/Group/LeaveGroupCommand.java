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

public class LeaveGroupCommand extends XcraftCommand {

	public LeaveGroupCommand(XcraftCommandManager cManager, String command, String name, String pattern, String usage, String desc, String permission) {
		super(cManager, command, name, pattern, usage, desc, permission);
	}

	@Override
	public boolean execute(XcraftPluginManager manager, CommandSender sender, String[] args) {
		SocialManager sManager = (SocialManager) manager;
		if (!sManager.getGroups().containsKey(args[0])) {
			manager.plugin.getMessenger().sendInfo(sender, Msg.GROUP_WRONG_NAME.toString(), true);
			return false;
		}
		SocialGroup group = sManager.getGroups().get(args[0]);
		SocialPlayer player = sManager.getPlayer(sender.getName());
		if (!group.removePlayer(player)) {
			manager.plugin.getMessenger().sendInfo(sender, Msg.ERR_PLAYER_NOT_IN_GROUP.toString(), true);
			return true;
		}
		manager.plugin.getMessenger().sendInfo(sender,
				Msg.GROUP_LEFT.toString(Replace.GROUP(group.getName())), true);
		group.chat(sManager, sender.getName(),
				Msg.GROUP_PLAYER_LEFT.toString(Replace.PLAYER(sender.getName()),
						Replace.GROUP(group.getName())));
		return true;
	}
}
