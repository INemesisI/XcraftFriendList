package de.xcraft.INemesisI.Social.Commands.Group;

import org.bukkit.command.CommandSender;

import de.xcraft.INemesisI.Library.Command.XcraftCommand;
import de.xcraft.INemesisI.Library.Manager.XcraftPluginManager;
import de.xcraft.INemesisI.Social.Msg;
import de.xcraft.INemesisI.Social.Msg.Replace;
import de.xcraft.INemesisI.Social.Manager.SocialGroup;
import de.xcraft.INemesisI.Social.Manager.SocialPlayer;
import de.xcraft.INemesisI.Social.Manager.SocialManager;

public class LeaveGroupCommand extends XcraftCommand {

	public LeaveGroupCommand() {
		super("group", "leave", "leave|le.*", "<Group>", Msg.COMMAND_GROUP_LEAVE.toString(),
				"XcraftSocial.Group.Leave");
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
