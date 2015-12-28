package me.emilianomaccaferri.cheatcontrol;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class Events implements Listener {
	

	
	
	
	public Main pl;
	public Events(Main pl){
		
		this.pl = pl;
		
} 
	
	Map<String, Long> cooldowns = new HashMap<String, Long>();
	long cooldown = 5;
	long last = 0;

	@EventHandler
	public void Join(PlayerJoinEvent e){
		
		Player getCheater = e.getPlayer();
		List<String> getCheaterList = (List<String>) pl.getConfig().getStringList("cheaters");
			if(getCheaterList.contains(getCheater.getName())){
				
				double controlX = pl.getConfig().getInt("cLocation.controlX");
				double controlY = pl.getConfig().getInt("cLocation.controlY");
				double controlZ = pl.getConfig().getInt("cLocation.controlZ");
				String worldName = pl.getConfig().getString("cLocation.world");
				World newWorld = Bukkit.getWorld(worldName);
				Location controlLocation = new Location(newWorld, controlX, controlY, controlZ);
				
				getCheater.teleport(controlLocation);
				
				return;
				
			}
    	
		
	}
	
	@EventHandler
	public void Move(PlayerMoveEvent em){	
		Player cheater = em.getPlayer();
		List<String> cheaterList = (List<String>) pl.getConfig().getStringList("cheaters");
    	if(cheaterList.contains(cheater.getName())){
    		
    		if (cooldowns.containsKey(em.getPlayer().getName())) {
    			  last = cooldowns.get(em.getPlayer().getName());
    			}
    		if (System.currentTimeMillis() - last >= cooldown * 1000) {
    			  cooldowns.put(em.getPlayer().getName(), System.currentTimeMillis());
    			  cheater.sendMessage("§cScrivi il tuo nome di Skype per essere controllato.");
    		
    		    	
    		return;
    		}
    		em.setCancelled(true);
    	}else{
    		
    	 em.setCancelled(false);
    	 return;
    		
    	}
		
	}
}
			
	
	

