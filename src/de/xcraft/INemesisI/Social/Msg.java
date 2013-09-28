package de.xcraft.INemesisI.Social;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

import de.xcraft.INemesisI.Library.XcraftPlugin;
import de.xcraft.INemesisI.Library.Message.Messenger;

public enum Msg {
	//@formatter:off
	HELP("&eAvaliable commands:"), 
	NONE("None"), 
	RELOAD_SUCCESSFUL("Successfully loaded from disc."), 
	SAVE_SUCCESSFUL("Successfully saved to disc"), 

	ERR_PLAYER_IS_SENDER("&cYou cant use that command on yourselve!"), 
	ERR_PLAYER_NOT_ONLINE("&e$PLAYER$ &cneeds to be online to use that command!"), 
	ERR_PLAYER_NOT_FOUND("&e$PLAYER$ &cwas not found on the server!"), 
	ERR_PLAYER_NOT_IN_FRIENDLIST("&cYou have no friend called &e$PLAYER$ &cin your friendlist."), 
	ERR_PLAYER_ALREADY_IN_GROUP("&c$PLAYER$ is already in your group!"),
	ERR_PLAYER_ALREADY_IN_FRIENDLIST("&cYou and $PLAYER$ are already friends!"), 
	ERR_PLAYER_NOT_IN_GROUP("&cYou are not in that group!"),
	ERR_GROUP_ALREADY_EXIST("&cThat groupname already exists!"),
	ERR_GROUP_NOT_OWNER("&cYou are not the owner of the group!"),

	PLAYER_CONNECTED("&e$PLAYER$ joined the game"),
	PLAYER_DISCONNECTED("&e$PLAYER$ left the game"),
	FRIEND_PLAYER_CONNECTED("&e$PLAYER$ &2joined the game"),
	FRIEND_PLAYER_DISCONNECTED("&e$PLAYER$ &4left the game"),
	GROUP_PLAYER_CONNECTED("&7<&3$GROUP$&7> &e$PLAYER$ &2joined the game"),
	GROUP_PLAYER_DISCONNECTED("&7<&3$GROUP$&7> &e$PLAYER$ &4left the game"),

	FRIENDS_LIST_ONLINE("&2Online Friends: &f$LIST$"), 
	FRIENDS_LIST_OFFLINE("&4Offline Friends: &f$LIST$"), 
	FRIENDS_LIST_PENDING("&7Pending Friends: &f$LIST$"), 
	FRIEND_PENDING_NOTIFY("&eYou have pending friend request from: &f$PLAYER$. &7You should /f accept or /f decline them!"), 
	FRIEND_ADD_ALREADY_ADDED("&eYou have already sent a friend reqeust to $PLAYER$."), 
	FRIEND_ADD_WRONG_CMD("&e$PLAYER$ &chas already sent you a request! &7Use /f accept or /f decline instead"), 
	FRIEND_ADD_SUCCESSFUL("&e$PLAYER$ &aneeds now to accept your friend request!"), 
	FRIEND_ADD_EXEMPT("&e$PLAYER$&c does not accept any friend request."), 
	FRIEND_ADD_NOTIFY("&3You have recieved a friend-request from &e$PLAYER$. &7Use /f accept or /f decline to accept/decline it!"), 
	FRIEND_REMOVE_SUCCESSFUL("&aYou have successfully removed &e$PLAYER$ &afrom your friendlist!"), 
	FRIEND_REMOVE_NOTIFY("&e$PLAYER$ &3Has removed you from his friendlist. He will be no longer in your friendlist aswell!"), 
	FRIEND_REQUEST_NOT_FOUND("&e$PLAYER$ &ehas not sent you a request! &7Use /friend add $PLAYER$ to add him as a friend!"), 
	FRIEND_ACCEPT_SUCCESSUFL("&aYou have successfully accepted &e$PLAYER$'s &afriendrequest!"), 
	FRIEND_ACCEPT_NOTIFY("&e$PLAYER$ &ahas accepted your friend request!"), 
	FRIEND__SUCCESSUFL("&aYou have successfully declined &e$PLAYER$'s &afriendrequest"), 
	FRIEND_DECLINE_NOTIFY("&e$PLAYER$ &chast declined your friend request!"), 

	GROUP_LIST("Your groups: $LIST$"),
	GROUP_CREATED("&aYou successfully created the group &e$GROUP$&a!"), 
	GROUP_DELETED("&aYou successfully deleted the group &e$GROUP$&a!"), 
	GROUP_NOT_OWNER("&cYou are not a owner of the group &e$GROUP$&a!"), 
	GROUP_PLAYER_ADDED("&e$PLAYER$&a was added to your group!"), 
	GROUP_ADD_ALREADY_ADDED("&e$PLAYER$&a is already in your group &e$GROUP$&a!"), 
	GROUP_PLAYER_KICKED("&e$PLAYER$&a was kicked from the group!"), 
	GROUP_KICK_NOT_IN_GROUP("&e$PLAYER$&c is not in your group &e$GROUP$&a!"), 
	GROUP_PLAYER_LEFT("&e$PLAYER$&a has left your group!"),
	GROUP_LEFT("&aYou successfully left the group &e$GROUP$&a!"),
	GROUP_WRONG_NAME("&ccould not find a group with the name '&e$GROUP$&c'!"), 
	GROUP_NAME_NO_SPACES("&cYou cannot use space in your group name"),
	GROUP_TOGGLED_OFF("You now toggle the group off!"),
	GROUP_TOGGLED("You now toggle the group $GROUP$ in group chat!"),
	GROUP_CHAT_OWNER("&7<&3$GROUP$&7> &c$PLAYER$&f: $MESSAGE$"),
	GROUP_CHAT_MEMBER("&7<&3$GROUP$&7> &e$PLAYER$&f: $MESSAGE$"),
	GROUP_LIST_ONLINE("&7Online Players in &3$GROUP$&7: $LIST$"),
	
	COMMAND_FRIEND_ADD("Add the player <Name> to your friendlist. The other player needs to confirm the command"), 
	COMMAND_FRIEND_REMOVE("Remove the player <Name> from your friendlist"), 
	COMMAND_FRIEND_ACCEPT("Accepts a friend request from the player <Name>"), 
	COMMAND_FRIEND_DECLINE("Declines a friend request from the player <Name>"), 
	COMMAND_FRIEND_LIST("List all Players in your friendlist"), 
	COMMAND_GROUP_CREATE("Create a new group with the given name"), 
	COMMAND_GROUP_DELETE("Delete the group with the given name"), 
	COMMAND_GROUP_ADD("Add a player to the group with the given name"), 
	COMMAND_GROUP_KICK("kick a player from the group with the given name"),
	COMMAND_GROUP_LEAVE("Leave a group"),
	COMMAND_GROUP_INFO("Shows all members and owners of a group"),
	COMMAND_GROUP_LIST("Show all groups you are currently in"),
	COMMAND_GROUP_CHAT("Writes your message in the group-chat"),
	COMMAND_GROUP_TOGGLE("Toggles auto-group-chat on/off"), 
	
	USAGE_GROUP("Group"),
	USAGE_NO_GROUP("You need to provide a group! /group list"),
	USAGE_PLAYER("Player"),
	USAGE_NO_PLAYER("You need to provide an online player"),
	
	GROUP_NO_GROUP("You currently have no groups!");
	//@formatter:on

	public enum Replace {
		$PLAYER$("Name of a Player"), $GROUP$("a Group"), $MESSAGE$("Message provided in a command"), $LIST$(
				"a List");

		private String key;

		Replace(String key) {
			this.set(key);
		}

		private void set(String output) {
			key = output;
		}

		private String get() {
			return key;
		}

		public static Replace PLAYER(String replace) {
			$PLAYER$.set(replace);
			return $PLAYER$;
		}

		public static Replace GROUP(String replace) {
			$GROUP$.set(replace);
			return $GROUP$;
		}

		public static Replace MESSAGE(String replace) {
			$MESSAGE$.set(replace);
			return $MESSAGE$;
		}

		public static Replace LIST(String replace) {
			$LIST$.set(replace);
			return $LIST$;
		}
	}

	private String msg;

	Msg(String msg) {
		this.set(msg);
	}

	private void set(String output) {
		msg = output;
	}

	private String get() {
		return msg;
	}

	@Override
	public String toString() {
		String message = msg.replaceAll("&([0-9a-z])", "\u00a7$1");
		message = message.replace("\\n", "\n");
		return message;
	}

	public String toString(Replace r1) {
		String message = toString();
		message = message.replace(r1.name(), r1.get());
		return message;
	}

	public String toString(Replace r1, Replace r2) {
		String message = toString();
		message = message.replace(r1.name(), r1.get());
		message = message.replace(r2.name(), r2.get());
		return message;
	}

	public String toString(Replace[] repl) {
		String message = toString();
		for (Replace r : repl) {
			message = message.replace(r.name(), r.get());
		}
		return message;
	}

	public static void init(XcraftPlugin plugin) {
		File msgFile = new File(plugin.getDataFolder(), "locale.yml");
		if (!load(msgFile)) {
			return;
		}
		parseFile(msgFile);
	}

	private static boolean load(File file) {
		if (file.exists()) {
			return true;
		}
		try {
			file.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			for (Msg m : Msg.values()) {
				String msg = m.get();
				if (msg.contains("\n")) {
					msg = msg.replace("\n", "\\n");
				}
				bw.write(m.name() + ": " + msg);
				bw.newLine();
			}
			bw.close();
			return true;
		} catch (Exception e) {
			Messenger.warning("Couldn't initialize locale.yml. Using defaults.");
			return false;
		}
	}

	private static void parseFile(File file) {
		try {
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			// Check for BOM character.
			br.mark(1);
			int bom = br.read();
			if (bom != 65279) {
				br.reset();
			}
			String s;
			while ((s = br.readLine()) != null) {
				process(s);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
			Messenger.warning("Problem with locale.yml. Using defaults.");
			return;
		}
	}

	/**
	 * Helper-method for parsing the strings from the announcements-file.
	 */
	private static void process(String s) {
		String[] split = s.split(": ", 2);
		try {
			Msg msg = Msg.valueOf(split[0]);
			msg.set(split[1]);
		} catch (Exception e) {
			Messenger.warning(split[0] + " is not a valid key. Check locale.yml.");
			return;
		}
	}

}
