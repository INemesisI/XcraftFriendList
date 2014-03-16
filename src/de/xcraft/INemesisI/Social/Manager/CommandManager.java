package de.xcraft.INemesisI.Social.Manager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.xcraft.INemesisI.Library.Manager.XcraftCommandManager;
import de.xcraft.INemesisI.Social.Msg;
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
		registerBukkitCommand("social");
		registerBukkitCommand("friend");
		registerBukkitCommand("group");
		registerBukkitCommand("gc");
		registerBukkitCommand("fl");
		registerBukkitCommand("gl");
		// Friend Commands
		this.registerCommand(new ListFriendsCommand(this, "friend", "accept", "accept|ac.*", "<PLAYER>", Msg.COMMAND_FRIEND_ACCEPT.toString(), "XcraftSocial.friend.accept"));
		this.registerCommand(new AddFriendCommand(this, "friend", "add", "add|a|ad.*", "<PLAYER>", Msg.COMMAND_FRIEND_ADD.toString(), "XcraftSocial.friend.add"));
		this.registerCommand(new RemoveFriendCommand(this, "friend", "decline", "decline|d|d.*", "<PLAYER>", Msg.COMMAND_FRIEND_DECLINE.toString(), "XcraftSocial.friend.decline"));
		this.registerCommand(new AcceptFriendCommand(this, "friend", "list", "list|l|l.*", "[offline/online/pending/all]", Msg.COMMAND_FRIEND_LIST.toString(), "XcraftSocial.friend.list"));
		this.registerCommand(new DeclineFriendCommand(this, "friend", "remove", "remove|r|r.*", "<PLAYER>", Msg.COMMAND_FRIEND_REMOVE.toString(), "XcraftSocial.friend.remove"));
		// Group Commands
		this.registerCommand(new AddToGroupCommand(this, "group", "add", "add|a|a.*", "<Group> <PLAYER> <owner/member>", Msg.COMMAND_GROUP_ADD.toString(), "xcraftsocial.group.add"));
		this.registerCommand(new ChatGroupCommand(this, "group", "chat", "c|ch.*", "[Group] <Message>", Msg.COMMAND_GROUP_CHAT.toString(), "xcraftsocial.group.chat"));
		this.registerCommand(new CreateGroupCommand(this, "group", "create", "create|cr.*", "<Name>", Msg.COMMAND_GROUP_CREATE.toString(), "xcraftsocial.group.create"));
		this.registerCommand(new DeleteGroupCommand(this, "group", "delete", "delete|d.*", "<Group>", Msg.COMMAND_GROUP_DELETE.toString(), "xcraftsocial.group.delete"));
		this.registerCommand(new InfoGroupCommand(this, "group", "info", "info|i.*", "<Group>", Msg.COMMAND_GROUP_INFO.toString(), "xcraftsocial.group.info"));
		this.registerCommand(new KickFromGroupCommand(this, "group", "kick", "kick|k.*", "<Group> <PLAYER>", Msg.COMMAND_GROUP_KICK.toString(), "xcraftsocial.group.kick"));
		this.registerCommand(new LeaveGroupCommand(this, "group", "leave", "leave|le.*", "<Group>", Msg.COMMAND_GROUP_LEAVE.toString(), "xcraftsocial.group.leave"));
		this.registerCommand(new ListGroupsCommand(this, "group", "list", "list|l|li.*", "", Msg.COMMAND_GROUP_LIST.toString(), "xcraftsocial.group.list"));
		this.registerCommand(new ToggleGroupCommand(this, "group", "toggle", "toggle|t.*", "<Group/off>", Msg.COMMAND_GROUP_TOGGLE.toString(), "xcraftsocial.group.toggle"));

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
