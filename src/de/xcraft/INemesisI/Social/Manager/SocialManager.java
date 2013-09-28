package de.xcraft.INemesisI.Social.Manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import de.xcraft.INemesisI.Library.Manager.XcraftPluginManager;
import de.xcraft.INemesisI.Social.Msg;
import de.xcraft.INemesisI.Social.Msg.Replace;
import de.xcraft.INemesisI.Social.XcraftSocial;

public class SocialManager extends XcraftPluginManager {

	private Map<String, SocialGroup> groups = new HashMap<String, SocialGroup>();
	private final Map<String, SocialPlayer> players = new HashMap<String, SocialPlayer>();

	public SocialManager(XcraftSocial plugin) {
		super(plugin);
	}

	public void notifyPending() {
		for (Player player : plugin.getServer().getOnlinePlayers()) {
			List<String> friends = players.get(player.getName()).getFriends();
			List<String> pending = new ArrayList<String>();
			for (String friend : friends) {
				if (friend.startsWith("?")) {
					pending.add(friend);
				}
			}
			if (pending.size() > 0) {
				plugin.getMessenger().sendInfo(player, Msg.FRIEND_PENDING_NOTIFY.toString(Replace.LIST(pending.toString())), true);
			}
		}
		plugin.getConfigManager().save();
	}

	public Map<String, SocialGroup> getGroups() {
		return groups;
	}

	public void setGroups(Map<String, SocialGroup> groupData) {
		this.groups = groupData;
	}

	public SocialPlayer getPlayer(String player) {
		return players.get(player);
	}

	public Map<String, SocialPlayer> getPlayers() {
		return players;
	}

	public SocialPlayer loadPlayer(String player) {
		return ((ConfigManager) plugin.getConfigManager()).loadPlayerData(player);
	}

	public void unloadPlayer(String player) {
		((ConfigManager) plugin.getConfigManager()).savePlayerData(player);
		players.put(player, null);
	}

	public Player getBukkitPlayer(String name) {
		return plugin.getServer().getPlayer(name);
	}

	public String getPlayerName(String name) {
		OfflinePlayer player = plugin.getServer().getOfflinePlayer(name);
		if (player.hasPlayedBefore()) {
			return player.getName();
		}
		return null;
	}

	@Override
	public XcraftSocial getPlugin() {
		return (XcraftSocial) plugin;
	}
}
