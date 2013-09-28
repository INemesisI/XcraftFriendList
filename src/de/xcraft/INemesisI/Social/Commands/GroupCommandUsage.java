package de.xcraft.INemesisI.Social.Commands;

import java.util.List;

import org.bukkit.command.CommandSender;

import de.xcraft.INemesisI.Library.Command.XcraftUsage;
import de.xcraft.INemesisI.Social.Msg;
import de.xcraft.INemesisI.Social.Manager.SocialManager;

public class GroupCommandUsage extends XcraftUsage {
	SocialManager sManager;

	public GroupCommandUsage(SocialManager sManager) {
		super("Group", Msg.USAGE_GROUP.toString());
		this.sManager = sManager;
	}

	@Override
	public boolean validate(String arg) {
		return sManager.getGroups().keySet().contains(arg);
	}

	@Override
	public String getFailMessage() {
		return Msg.USAGE_NO_GROUP.toString();
	}

	@Override
	public List<String> onTabComplete(List<String> list, CommandSender sender) {
		for (String group : sManager.getPlayer(sender.getName()).getGroups()) {
			list.add(group);
		}
		return list;
	}
}
