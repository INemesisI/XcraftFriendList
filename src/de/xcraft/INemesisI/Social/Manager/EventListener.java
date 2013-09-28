package de.xcraft.INemesisI.Social.Manager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import de.xcraft.INemesisI.Library.XcraftEventListener;
import de.xcraft.INemesisI.Social.Msg;
import de.xcraft.INemesisI.Social.Msg.Replace;
import de.xcraft.INemesisI.Social.XcraftSocial;

public class EventListener extends XcraftEventListener {

	private SocialManager sManager;
	private ConfigManager cManager;

	public EventListener(XcraftSocial plugin) {
		super(plugin);
		this.sManager = (SocialManager) plugin.getPluginManager();
		this.cManager = (ConfigManager) plugin.getConfigManager();
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if (e.getJoinMessage() == null) {
			return;
		}
		e.setJoinMessage(null);
		System.out.println(cManager.notifyFriends);
		System.out.println(cManager.notifyGroups);
		System.out.println(cManager.notifyOthers);
		Player player = e.getPlayer();
		List<String> onlineFriends = new ArrayList<String>();
		SocialPlayer pdata = sManager.loadPlayer(player.getName());

		// Notify all friends!
		if (cManager.notifyFriends) {
			for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()) {
				if (pdata.getFriends().contains(onlinePlayer.getName())) {
					plugin.getMessenger().sendInfo(onlinePlayer, Msg.FRIEND_PLAYER_CONNECTED.toString(Replace.PLAYER(player.getName())), false);
					onlineFriends.add(onlinePlayer.getName());
				}
			}
			String list = onlineFriends.size() != 0 ? onlineFriends.toString() : Msg.NONE.toString();
			plugin.getMessenger().sendInfo(player, Msg.FRIENDS_LIST_ONLINE.toString(Replace.LIST(list)), false);
		}

		// Notify all groups!
		if (cManager.notifyGroups) {
			for (String gname : pdata.getGroups()) {
				SocialGroup group = sManager.getGroups().get(gname);
				List<String> onlineMembers = new ArrayList<String>();
				for (String member : group.getAllMembers()) {
					Player p = sManager.getBukkitPlayer(member);
					if (p != null) {
						onlineMembers.add(member);
						if (!onlineFriends.contains(member) && !member.equals(player.getName())) {
							plugin.getMessenger().sendInfo(p,
									Msg.GROUP_PLAYER_CONNECTED.toString(Replace.PLAYER(player.getName()), Replace.GROUP(gname)), false);
							onlineFriends.add(member);
						}
					}
				}
				String list = onlineMembers.size() != 0 ? onlineMembers.toString() : Msg.NONE.toString();
				plugin.getMessenger().sendInfo(player, Msg.GROUP_LIST_ONLINE.toString(Replace.GROUP(gname), Replace.LIST(list)), false);
			}
		}
		if (cManager.notifyOthers) {
			for (Player p : plugin.getServer().getOnlinePlayers()) {
				if (!onlineFriends.contains(p.getName()) && !p.equals(player)) {
					plugin.getMessenger().sendInfo(p, Msg.PLAYER_CONNECTED.toString(Replace.PLAYER(player.getName())), false);
				}
			}
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		if (e.getQuitMessage() == null) {
			return;
		}
		e.setQuitMessage(null);

		Player player = e.getPlayer();
		List<String> onlineFriends = new ArrayList<String>();
		SocialPlayer pdata = sManager.getPlayer(player.getName());

		// Notify all friends!
		if (cManager.notifyFriends) {
			for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()) {
				if (pdata.getFriends().contains(onlinePlayer.getName())) {
					plugin.getMessenger().sendInfo(onlinePlayer, Msg.FRIEND_PLAYER_DISCONNECTED.toString(Replace.PLAYER(player.getName())), false);
					onlineFriends.add(onlinePlayer.getName());
				}
			}
		}

		// Notify all groups!
		if (cManager.notifyGroups) {
			for (String gname : pdata.getGroups()) {
				SocialGroup group = sManager.getGroups().get(gname);
				for (String member : group.getAllMembers()) {
					Player p = sManager.getBukkitPlayer(member);
					if (p != null) {
						if (!onlineFriends.contains(member) && !member.equals(player.getName())) {
							plugin.getMessenger().sendInfo(p,
									Msg.GROUP_PLAYER_DISCONNECTED.toString(Replace.PLAYER(player.getName()), Replace.GROUP(gname)), false);
							onlineFriends.add(member);
						}
					}
				}
			}
		}

		if (cManager.notifyOthers) {
			for (Player p : plugin.getServer().getOnlinePlayers()) {
				if (!onlineFriends.contains(p.getName()) && !p.equals(player)) {
					plugin.getMessenger().sendInfo(p, Msg.PLAYER_DISCONNECTED.toString(Replace.PLAYER(player.getName())), false);
				}
			}
		}
		sManager.unloadPlayer(player.getName());
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		if (e.getDeathMessage() == null) {
			return;
		}
		Player player = e.getEntity();
		sManager.loadPlayer(player.getName());
		SocialPlayer pdata = sManager.getPlayer(player.getName());
		// Notify all friends!
		for (String friend : pdata.getFriends()) {
			if (sManager.getBukkitPlayer(friend) != null) {
				Player p = plugin.getServer().getPlayerExact(friend);
				plugin.getMessenger().sendInfo(p, "&c" + e.getDeathMessage(), false);
			}
		}
		// Notify all groups!
		for (String gname : pdata.getGroups()) {
			SocialGroup g = sManager.getGroups().get(gname);
			for (String member : g.getAllMembers()) {
				Player p = plugin.getServer().getPlayerExact(member);
				if (p != null) {
					plugin.getMessenger().sendInfo(p, "&c" + e.getDeathMessage(), false);
				}
			}
		}
		e.setDeathMessage(null);
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		if (sManager.getPlayer(e.getPlayer().getName()).getActiveGroup() != null) {
			SocialGroup g = sManager.getGroups().get(sManager.getPlayer(e.getPlayer().getName()).getActiveGroup());
			e.setCancelled(true);
			g.chat(sManager, e.getPlayer().getName(), e.getMessage());
		}
	}
}
