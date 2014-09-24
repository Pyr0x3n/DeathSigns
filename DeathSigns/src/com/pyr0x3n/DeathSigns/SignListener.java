package com.pyr0x3n.DeathSigns;


import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.entity.Player;


public class SignListener implements Listener {
	Main plugin;
	Block b;

	public SignListener(Main plugin) {
		this.plugin=plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}


	@EventHandler
	public void onSignBreak(BlockBreakEvent event){
		this.b = event.getBlock();
		if(b.getType() != Material.SIGN_POST )
			return;
		if(! plugin.getConfig().getBoolean("signProtected"))
			return;
		Sign s = (Sign) b.getState();	

		try{
			if (s.getMetadata("signName").get(0).asString().equals("deathSign")){
				event.setCancelled(true);
				event.getPlayer().sendMessage(
						ChatColor.translateAlternateColorCodes(
								'&', plugin.getConfig().getString("griefingMessage")));
				if(plugin.getConfig().getBoolean("punishGriefer")){
					Player p = event.getPlayer();
					p.damage((double) plugin.getConfig().getInt("punishment"));
				}
			}
		} catch (IndexOutOfBoundsException e){}
	}
}
