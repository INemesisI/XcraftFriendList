package de.xcraft.INemesisI.Social.Manager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.xcraft.INemesisI.Library.Manager.XcraftCommandManager;
import de.xcraft.INemesisI.Social.XcraftSocial;
import de.xcraft.INemesisI.Social.Commands.GroupCommandUsage;
import de.xcraft.INemesisI.Social.Commands.PlayerCommandUsage;
import de.xcraft.INemesisI.Social.Commands.Friend.AcceptFriendCommand;
import de.xcraft.INemesisI.Social.Commands.Friend.AddFriendCommand;
import de.xcraft.INemesisI.Social.Commands.Friend.DeclineFriendCommand;
import de.xcraft.INemesisI.Social.Commands.Friend.ListFriendsCommand;
import de.xcraft.INemesisI.Social.Commands.Friend.RemoveFriendCommand;
import de.xcraft.INemesisI.Social.Commands.Group.AddToGroupCommand;
import de.xcraft.INemesisI.Social.Commands.Group.ChatGroupCommand;
import de.xcraft.INemesisI.Social.Commands.Group.CreateGroupCommand;
import de.xcraft.INemesisI.Social.Commands.Group.DeleteGroupCommand;
import de.xcraft.INemesisI.Social.Commands.Group.InfoGroupCommand;
import de.xcraft.INemesisI.Social.Commands.Group.KickFromGroupCommand;
import de.xcraft.INemesisI.Social.Commands.Group.LeaveGroupCommand;
import de.xcraft.INemesisI.Social.Commands.Group.ListGroupsCommand;
import de.xcraft.INemesisI.Social.Commands.Group.ToggleGroupCommand;

public class CommandManager extends XcraftCommandManager {

	public CommandManager(XcraftSocial plugin) {
		super(plugin);
	}

	@Override
	protected void registerCommands() {
		registerBukkitCommand("friend");
		registerBukkitCommand("group");
		registerBukkitCommand("gc");
		// Friend Commands
		this.registerCommand(new ListFriendsCommand());
		this.registerCommand(new AddFriendCommand());
		this.registerCommand(new RemoveFriendCommand());
		this.registerCommand(new AcceptFriendCommand());
		this.registerCommand(new DeclineFriendCommand());
		// Group Commands
		this.registerCommand(new AddToGroupCommand());
		this.registerCommand(new ChatGroupCommand());
		this.registerCommand(new CreateGroupCommand());
		this.registerCommand(new DeleteGroupCommand());
		this.registerCommand(new InfoGroupCommand());
		this.registerCommand(new KickFromGroupCommand());
		this.registerCommand(new LeaveGroupCommand());
		this.registerCommand(new ListGroupsCommand());
		this.registerCommand(new ToggleGroupCommand());

		SocialManager sManager = (SocialManager) plugin.getPluginManager();
		registerUsage(new GroupCommandUsage(sManager));
		registerUsage(new PlayerCommandUsage(sManager));
	}

	@Override
	public boolean onCommand(CommandSender sender, Command bcmd, String label, String[] args) {
		if (bcmd.getName().equals("fl")) {
			return getCommands().get("list|l|l.*").execute(this.plugin.getPluginManager(), sender, args);
		}
		if (bcmd.getName().equals("gl")) {
			return getCommands().get("c|ch.*").execute(this.plugin.getPluginManager(), sender, args);
		}
		if (bcmd.getName().equals("gc")) {
			return getCommands().get("c|ch.*").execute(this.plugin.getPluginManager(), sender, args);
		}
		return super.onCommand(sender, bcmd, label, args);
	}

}
