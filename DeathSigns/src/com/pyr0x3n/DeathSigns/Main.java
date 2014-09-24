package com.pyr0x3n.DeathSigns;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.Listener;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{

	public World world;

	@Override
	public void onEnable(){

		saveDefaultConfig();	
		getServer().getPluginManager().registerEvents(this, this);
		this.log("is now enabled" , Level.INFO);
		world = Bukkit.getWorlds().get(0);
		new LibHungerGameListener(this);
		new SignListener(this);
	}


	@Override
	public void onDisable() {
		this.log("is finaly disabled" , Level.INFO);
	}

	public void log(String s, Level l){
		getLogger().log(l, s);
	}

	public void setSign(String killedName, String killerName, Location loc){	
		
		List<String> line = new ArrayList<String>();
		String killer = killerName;
		int y;
		Block b, b2;
		int x = loc.getBlockX();		
		int z = loc.getBlockZ();
		if(getConfig().getBoolean("getHighestBlockY")){
			 y = world.getHighestBlockYAt(x, z);		
		}else{
			y = loc.getBlockY();
		}
		
		loc.setY(y);
		b =  loc.getBlock();
		loc.setY(y-1);
		b2 =  loc.getBlock();
		
		if(killer.equals("mob")){
			killer = getConfig().getString("nonPlayerKiller");
		}
		
		for (int i = 0; i < 4; i++) {
			line.add(getConfig().getString("line"+ (i+1)));
		}
		
		for (int i = 0; i < line.size(); i++) {
			line.set(i,line.get(i).replaceAll("&", "§"));
			line.set(i,line.get(i).replaceAll("%Killed%", killedName));
			line.set(i,line.get(i).replaceAll("%Killer%", killer));
		}

		b2.setType(Material.STONE);			
		b.setType(Material.SIGN_POST);	
		Sign s = (Sign) b.getState();
		for (int i = 0; i < line.size(); i++) {
			s.setLine(i, line.get(i)); 
		}
		s.update();
		s.setMetadata("signName", new FixedMetadataValue(this, "deathSign")); 
	}	
}
