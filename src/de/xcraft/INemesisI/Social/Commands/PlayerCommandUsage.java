package de.xcraft.INemesisI.Social.Commands;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.xcraft.INemesisI.Library.Command.XcraftUsage;
import de.xcraft.INemesisI.Social.Msg;
import de.xcraft.INemesisI.Social.Manager.SocialManager;

public class PlayerCommandUsage extends XcraftUsage {
	SocialManager sManager;

	public PlayerCommandUsage(SocialManager sManager) {
		super("PLAYER", Msg.USAGE_PLAYER.toString());
		this.sManager = sManager;
	}

	@Override
	public boolean validate(String arg) {
		return sManager.plugin.getServer().getPlayer(arg).isOnline();
	}

	@Override
	public String getFailMessage() {
		return Msg.USAGE_NO_PLAYER.toString();
	}

	@Override
	public List<String> onTabComplete(List<String> list, CommandSender sender) {
		for (Player player : sManager.plugin.getServer().getOnlinePlayers()) {
			list.add(player.getName());
		}
		return list;
	}

}
