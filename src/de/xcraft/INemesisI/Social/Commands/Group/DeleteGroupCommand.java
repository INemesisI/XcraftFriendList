package de.xcraft.INemesisI.Social.Commands.Group;

import org.bukkit.command.CommandSender;

import de.xcraft.INemesisI.Library.Command.XcraftCommand;
import de.xcraft.INemesisI.Library.Manager.XcraftPluginManager;
import de.xcraft.INemesisI.Social.Msg;
import de.xcraft.INemesisI.Social.Msg.Replace;
import de.xcraft.INemesisI.Social.Manager.SocialGroup;
import de.xcraft.INemesisI.Social.Manager.SocialManager;

public class DeleteGroupCommand extends XcraftCommand {

	public DeleteGroupCommand() {
		super("group", "delete", "delete|d.*", "<Group>", Msg.COMMAND_GROUP_DELETE.toString(),
				"XcraftSocial.Group.Delete");
	}

	@Override
	public boolean execute(XcraftPluginManager manager, CommandSender sender, String[] args) {
		SocialManager sManager = (SocialManager) manager;
		if (!sManager.getGroups().containsKey(args[0])) {
			manager.plugin.getMessenger().sendInfo(sender,
					Msg.GROUP_WRONG_NAME.toString(Replace.GROUP(args[0])), true);
			return true;
		}
		SocialGroup group = sManager.getGroups().get(args[0]);
		if (group.getOwners().contains(sender.getName())) {
			sManager.getGroups().remove(args[0]);
			manager.plugin.getMessenger().sendInfo(sender,
					Msg.GROUP_DELETED.toString(Replace.GROUP(group.getName())), true);
		} else {
			manager.plugin.getMessenger().sendInfo(sender,
					Msg.GROUP_NOT_OWNER.toString(Replace.GROUP(group.getName())), true);
		}
		return true;
	}
}
