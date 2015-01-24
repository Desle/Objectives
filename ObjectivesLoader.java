
package com.desle.Objectives;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

/*

By Desle

*/

public class ObjectivesLoader {
	
	
	
	
	
	//
	
	private static ObjectivesLoader instance;
	public static ObjectivesLoader getInstance() {
		
		if (instance == null)
			instance = new ObjectivesLoader();
		
		return instance;
	}
	
	//
	
	
	
	
	public void loadObjectives() {
		
		FileConfiguration fc = Main.getInstance().getConfig();
		
		for (String key : fc.getConfigurationSection("objectives").getKeys(false)) {
			
			String name = key;
			String summary = fc.getString("objectives." + key + ".summary");
			
			ObjectiveType type = ObjectiveType.valueOf(fc.getString("objectives." + key + ".type").toUpperCase());
			Trigger trigger = Trigger.valueOf(fc.getString("objectives." + key + ".trigger").toUpperCase());
			String toComplete = fc.getString("objectives." + key + ".tocomplete");
			List<String> rawUUIDs = fc.getStringList("objectives." + key + ".players");
			String world = fc.getString("objectives." + key + ".world");
			
			String commandoncomplete = fc.getString("objectives." + key + ".commandoncomplete");
			Location destination = convertString(fc.getString("objectives." + key + ".destination"));
			int delay = fc.getInt("objectives." + key + ".delay");
			//
			//
			//
			
			Objective ob = new Objective(name, summary, delay, commandoncomplete, type, trigger, world, destination, toComplete);
			
			ArrayList<UUID> obs = new ArrayList<UUID>();
			
			for (String uu : rawUUIDs) {
				obs.add(UUID.fromString(uu));
			}
			
			ob.setPlayers(obs);
		}
	}
	
	
	
	public void saveObjectives() {
		
		FileConfiguration fc = Main.getInstance().getConfig();
		
		for (Objective ob : Objective.list) {
			
			String path = "objectives." + ob.getName() + ".";
			
			List<String> rawUUIDs = new ArrayList<String>();
			
			for (UUID uuid : ob.getPlayers()) {
				rawUUIDs.add(uuid.toString());
			}
			
			
			fc.set(path + "players", rawUUIDs);
		}
		
		Objective.list.clear();
		
		Main.getInstance().saveConfig();
	}
	
	
	
	
	// RES //
	
	  public static Location convertString(String str) {
		  
		  if (str == null) return null;		  
		  
		    String[] string = str.split(",");
		    String world = string[0];
		    double x = Integer.parseInt(string[1]);
		    double y = Integer.parseInt(string[2]);
		    double z = Integer.parseInt(string[3]);
		    float yaw = Integer.parseInt(string[4]);
		    Location loc = new Location(Bukkit.getWorld(world), x + 0.5, y + 0.5, z + 0.5, 0, yaw);
		    return loc;
		  }

	  
	  
	  
		  public static String convertLoc(Location loc) {
			  
			  if (loc == null) return null;
			  
		    String world = loc.getWorld().getName();
		    int x = loc.getBlockX();
		    int y = loc.getBlockY();
		    int z = loc.getBlockZ();
		    int pitch = (int)loc.getPitch();
		    int yaw = (int)loc.getYaw();
		    return new String(world + "," + x + "," + y + "," + z + "," + pitch + "," + yaw);
		  }
}
