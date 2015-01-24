package com.desle.Objectives;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;

/*

By Desle

*/

public class Objective {

	
	
	
	
	
	public static List<Objective> list = new ArrayList<Objective>();
	
	private String name;
	private String summary;
	private String commands;
	
	private ObjectiveType type;
	private Trigger trigger;
	private ArrayList<UUID> players;
	
	private String world;
	private Location destination;
	private String toComplete;
	private int delay;
	
	
	public Objective (String name, String summary, int delay, String commandoncomplete, ObjectiveType type, Trigger trigger, String world, Location destination, String toComplete) {
		this.players = new ArrayList<UUID>();
		this.destination = destination;
		this.name = name;
		this.summary = summary;
		this.type = type;
		this.toComplete = toComplete;
		this.trigger = trigger;
		this.commands = commandoncomplete;
		this.delay = delay;
		this.world = world;
		
		list.add(this);
	}
	
	
	//
	
	public String getWorld() {
		return this.world;
	}
	
	public int getDelay() {
		return this.delay;
	}
	
	public String getToComplete() {
		return this.toComplete;
	}
	
	public String getCommands() {
		return this.commands;
	}
	
	public String getSummary() {
		return this.summary;
	}
	
	public String getName() {
		return this.name;
	}
	
	public ObjectiveType getType() {
		return this.type;
	}
	
	public Trigger getTrigger() {
		return this.trigger;
	}
	
	public ArrayList<UUID> getPlayers() {
		return this.players;
	}
	
	public void setPlayers(ArrayList<UUID> newpl) {
		this.players = newpl;
	}
	
	public Location getDestination() {
		return this.destination;
	}
	
	
	
	
}
