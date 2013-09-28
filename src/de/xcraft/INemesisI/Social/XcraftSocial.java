package de.xcraft.INemesisI.Social;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.xcraft.INemesisI.Library.XcraftPlugin;
import de.xcraft.INemesisI.Library.Message.Messenger;
import de.xcraft.INemesisI.Social.Manager.CommandManager;
import de.xcraft.INemesisI.Social.Manager.ConfigManager;
import de.xcraft.INemesisI.Social.Manager.EventListener;
import de.xcraft.INemesisI.Social.Manager.SocialManager;

public class XcraftSocial extends XcraftPlugin {
	private Messenger messenger;
	private SocialManager socialManager;
	private ConfigManager configManager;
	private CommandManager commandManager;
	private EventListener eventListener;
	
	@Override
	protected void setup() {
		Msg.init(this);
		this.messenger = Messenger.getInstance(this);
		this.socialManager = new SocialManager(this);
		this.configManager = new ConfigManager(this);
		this.commandManager = new CommandManager(this);
		this.configManager.load();
		this.eventListener = new EventListener(this);
		this.startTimer();
	}

	private void startTimer() {
		SimpleDateFormat d = new SimpleDateFormat();
		d.applyPattern("mm:ss");
		String current = d.format(new Date());
		String[] split = current.split(":");
		int min = Integer.parseInt(split[0]);
		int sec = Integer.parseInt(split[1]);
		int delay = 5; // mins
		min = delay - (min % delay) - 1;
		if (min == -1) {
			min = delay--;
		}
		sec = 60 - sec;
		Runnable task = new Runnable() {

			@Override
			public void run() {
				socialManager.notifyPending();
			}
		};
		getServer().getScheduler().runTaskTimerAsynchronously(this, task, ((min * 60) + sec) * 20, 60 * delay * 20);
	}

	@Override
	public SocialManager getPluginManager() {
		return socialManager;
	}

	@Override
	public ConfigManager getConfigManager() {
		return configManager;
	}

	@Override
	public CommandManager getCommandManager() {
		return commandManager;
	}

	@Override
	public EventListener getEventListener() {
		return eventListener;
	}

	@Override
	public Messenger getMessenger() {
		return messenger;
	}

}
