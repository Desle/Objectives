package com.desle.Objectives;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/*

By Desle

*/

public class ObjectivesAPI {
	
	
	
	
	
	//
	
	private static ObjectivesAPI instance;
	public static ObjectivesAPI getInstance() {
		
		if (instance == null)
			instance = new ObjectivesAPI();
		
		return instance;
	}
	
	//
	
	
	
	
	// objective management //
	
	
	public List<Objective> getAllObjectives() {
		return Objective.list;
	}
	
	
	
	
	
	
	
	
	public Objective getObjectiveByName(String name) {
		
		for (Objective ob : getAllObjectives()) {
			if (ob.getName().equalsIgnoreCase(name)) 
				return ob;
		}
		
		return null;
	}
	
	
	
	
	
	
	
	
	public void giveObjective(final Player player, final Objective objective) {
		if (objective.getPlayers().contains(player.getUniqueId())) return;
		if (hasCompleted(player, objective)) return;
				
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new BukkitRunnable() {
			
			@Override
			public void run() {
				
				objective.getPlayers().add(player.getUniqueId());
				
				player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1, 1);
				player.sendMessage(ChatColor.AQUA + "" + ChatColor.STRIKETHROUGH + "═══════════════════════════════");
				player.sendMessage(ChatColor.AQUA + "New objective!");
				player.sendMessage("");
				player.sendMessage(objective.getSummary());
				player.sendMessage("");
				player.sendMessage(ChatColor.AQUA + "" + ChatColor.STRIKETHROUGH + "═══════════════════════════════");
				
			}
		}, objective.getDelay() * 20);
	}
	
	
	
	
	
	// player-objective management //
	
	public void completeObjective(Player player, Objective objective) {
		
		if (!objective.getPlayers().contains(player.getUniqueId())) return;
		
		objective.getPlayers().remove(player.getUniqueId());
		
		player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1, 1);
		player.sendMessage(ChatColor.AQUA + "" + ChatColor.STRIKETHROUGH + "═══════════════════════════════");
		player.sendMessage(ChatColor.AQUA + "Objective complete!");
		player.sendMessage("");
		player.sendMessage(ChatColor.STRIKETHROUGH + ChatColor.stripColor(objective.getSummary()));
		player.sendMessage("");
		player.sendMessage(ChatColor.AQUA + "" + ChatColor.STRIKETHROUGH + "═══════════════════════════════");
		
		EventManager.onObjectiveComplete(player, objective);
		
		//
		Main main = Main.getInstance();
		List<String> completed = main.getPlayers().getStringList(player.getUniqueId() + ".completed");
		completed.add(objective.getName());
		main.getPlayers().set(player.getUniqueId() + ".completed", completed);
		main.savePlayers();
		//
		
		String rawcommand = objective.getCommands();
		
		rawcommand = rawcommand.replaceAll("%player%", player.getName());
		
		if (rawcommand.contains("@")) {
			
			String[] splitted = rawcommand.split("@");
			
			for (String cmds : splitted) {
				
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmds);
			}
			
		} else {
			
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), rawcommand);
		}
	}

	
	
	
	
	
	
	
	
	public List<Objective> getObjectives(Player player) {
		
		List<Objective> list = new ArrayList<Objective>();
		
		for (Objective ob : getAllObjectives()) {
			if (ob.getPlayers().contains(player.getUniqueId()))
				list.add(ob);
		}
		
		// else //
		return list;
	}
	
	public boolean hasObjective(Player player) {
		
		// checks //
		if (getObjectives(player).isEmpty()) return false;
		
		// else //
		return true;
	}
	
	public boolean hasCompleted(Player player, Objective objective) {
		if (Main.getInstance().getPlayers().getStringList(player.getUniqueId() + ".completed").contains(objective.getName())) return true;
		return false;
	}
}
