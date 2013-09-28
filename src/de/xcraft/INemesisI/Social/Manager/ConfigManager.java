package de.xcraft.INemesisI.Social.Manager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import de.xcraft.INemesisI.Library.Manager.XcraftConfigManager;
import de.xcraft.INemesisI.Library.Message.Messenger;
import de.xcraft.INemesisI.Social.XcraftSocial;

public class ConfigManager extends XcraftConfigManager{

	private SocialManager sManager;
	private FileConfiguration data;
	private File dataFile;

	public boolean notifyFriends;
	public boolean notifyGroups;
	public boolean notifyOthers;

	public ConfigManager(XcraftSocial plugin) {
		super(plugin);
		sManager = (SocialManager) plugin.getPluginManager();
	}

	public void load() {
		dataFile = new File(plugin.getDataFolder(), "data.yml");
		if (!dataFile.exists()) {
			try {
				dataFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		notifyFriends = config.getBoolean("Notify.Friends", true);
		notifyGroups = config.getBoolean("Notify.Groups", true);
		notifyOthers = config.getBoolean("Notify.Others", false);

		data = YamlConfiguration.loadConfiguration(dataFile);
		// load Groups
		Map<String, SocialGroup> groups = new HashMap<String, SocialGroup>();
		if (data.isConfigurationSection("Groups")) {
			ConfigurationSection cs = data.getConfigurationSection("Groups");
			for (String key : cs.getKeys(false)) {
				groups.put(key, new SocialGroup(cs.getString(key + ".Name"), cs.getStringList(key + ".Owners"), cs.getStringList(key + ".Members")));
			}
		}
		sManager.setGroups(groups);
		// load players
		for (Player player : plugin.getServer().getOnlinePlayers()) {
			loadPlayerData(player.getName());
		}
	}

	public void save() {
		plugin.reloadConfig();
		// save groups
		for (String gname : sManager.getGroups().keySet()) {
			SocialGroup g = sManager.getGroups().get(gname);
			data.set("Groups." + gname + ".Name", g.getName());
			data.set("Groups." + gname + ".Owners", g.getOwners());
			data.set("Groups." + gname + ".Members", g.getMembers());
		}
		// save players
		for (Player player : plugin.getServer().getOnlinePlayers()) {
			savePlayerData(player.getName());
		}
		try {
			data.save(dataFile);
		} catch (IOException e) {
			Messenger.warning(plugin.getDescription().getFullName()
					+ " Error while saving data.yml");
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public SocialPlayer loadPlayerData(String player) {
		if (data.isConfigurationSection("Players." + player)) {
			ConfigurationSection cs = data.getConfigurationSection("Players." + player);
			List<String> friends = (cs.isList(".Friends") ? (List<String>) cs.getList(".Friends") : new ArrayList<String>());
			List<String> groups = (cs.isList(".Groups") ? (List<String>) cs.getList(".Groups") : new ArrayList<String>());
			SocialPlayer data = new SocialPlayer(player, friends, groups, null);
			sManager.getPlayers().put(player, data);
			return data;
		} else {
			SocialPlayer data = new SocialPlayer(player, new ArrayList<String>(),
					new ArrayList<String>(), null);
			sManager.getPlayers().put(player, data);
			return data;
		}
	}

	public boolean savePlayerData(String player) {
		SocialPlayer pdata = sManager.getPlayer(player);
		List<String> friends = pdata.getFriends();
		List<String> groups = pdata.getGroups();
		if ((friends.size() == 0) && (groups.size() == 0)) {
			return false;
		} else {
			if (friends.size() != 0) {
				data.set("Players." + player + ".Friends", friends);
			}
			if (groups.size() != 0) {
				data.set("Players." + player + ".Groups", groups);
			}
			return true;
		}
	}

	public File getFolder() {
		return plugin.getDataFolder();
	}
}
