package com.pyr0x3n.DeathSigns;

import me.libraryaddict.Hungergames.Events.PlayerKilledEvent;

import org.bukkit.Location;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class LibHungerGameListener implements Listener {
	Main plugin;

	public LibHungerGameListener(Main plugin) {
		this.plugin=plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onLibPlayerKilled(PlayerKilledEvent event) {
		Location loc = event.getKilled().getPlayer().getLocation();
		String killerName;
		String killedName =event.getKilled().getPlayer().getDisplayName();

		if(event.getKillerPlayer() == null){
			killerName = "mob";
		}else{
			killerName =event.getKillerPlayer().getPlayer().getDisplayName();
		}
		plugin.setSign(killedName, killerName, loc);
	}
}
