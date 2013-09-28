package de.xcraft.INemesisI.Social.Manager;

import java.util.ArrayList;
import java.util.List;

import de.xcraft.INemesisI.Social.Msg;
import de.xcraft.INemesisI.Social.Msg.Replace;

public class SocialGroup {

	private String name;
	private List<String> owners;
	private List<String> members;

	public SocialGroup(String name, List<String> owners, List<String> members) {
		this.setName(name);
		this.setOwners(owners);
		this.setMembers(members);
	}

	public SocialGroup(String name, String player) {
		this.setName(name);
		this.setOwners(new ArrayList<String>());
		this.getOwners().add(player);
		this.setMembers(new ArrayList<String>());
	}

	public void addPlayer(SocialPlayer player, boolean owner) {
		if (owner)
			this.getOwners().add(player.getName());
		else
			this.getMembers().add(player.getName());
		player.getGroups().add(this.getName());
	}

	public boolean removePlayer(SocialPlayer player) {
		if (members.contains(player.getName())) {
			members.remove(player.getName());
			return !player.getGroups().remove(name);
		} else if (owners.contains(player.getName())) {
			owners.remove(player.getName());
			return !player.getGroups().remove(name);
		} else
			return false;
	}

	public boolean isMember(SocialPlayer player) {
		return members.contains(player);
	}

	public boolean isOwner(SocialPlayer player) {
		return owners.contains(player);
	}

	public boolean isPlayerInGroup(SocialPlayer player) {
		return (isMember(player) || isOwner(player));
	}

	public void chat(SocialManager manager, String sender, String message) {
		Replace[] replace = { Replace.PLAYER(sender), Replace.GROUP(getName()),
				Replace.MESSAGE(message) };
		for (String owner : getOwners()) {
			if (manager.getBukkitPlayer(owner) != null)
				manager.plugin.getMessenger().sendInfo(manager.getBukkitPlayer(owner),
						Msg.GROUP_CHAT_OWNER.toString(replace), false);
		}
		for (String member : getMembers()) {
			if (manager.getBukkitPlayer(member) != null)
				manager.plugin.getMessenger().sendInfo(manager.getBukkitPlayer(member),
						Msg.GROUP_CHAT_MEMBER.toString(replace), false);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getMembers() {
		return members;
	}

	public void setMembers(List<String> members) {
		this.members = members;
	}

	public List<String> getOwners() {
		return owners;
	}

	public void setOwners(List<String> owners) {
		this.owners = owners;
	}

	public List<String> getAllMembers() {
		List<String> members = new ArrayList<String>();
		members.addAll(this.members);
		members.addAll(this.owners);
		return members;
	}
}
