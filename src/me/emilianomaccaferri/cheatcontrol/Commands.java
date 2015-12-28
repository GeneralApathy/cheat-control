package me.emilianomaccaferri.cheatcontrol;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor{
	
	public Main plugin;
	
	public Commands(Main plugin){
		
		this.plugin = plugin;
		
	}
	
@SuppressWarnings("deprecation")
public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
	
	    if(s instanceof Player){
	    	
	    	Player p = (Player) s;
	    	
			if(cmd.getName().equalsIgnoreCase("setcontrollo")){
				
				double x = p.getLocation().getX();
				double y = p.getLocation().getY();
				double z = p.getLocation().getZ();	
				String world = p.getLocation().getWorld().getName();
				
				plugin.getConfig().set("cLocation.controlX", x);
				plugin.getConfig().set("cLocation.controlY", y);
				plugin.getConfig().set("cLocation.controlZ", z);
				plugin.getConfig().set("cLocation.world", world);
				
				plugin.saveConfig();
				
				p.sendMessage("§4[CheatControl] §6§lPunto di controllo settato correttamente!");
				return true;
				
			}
			
			if(cmd.getName().equalsIgnoreCase("tpcontrollo")){
				
				double controlX = plugin.getConfig().getInt("cLocation.controlX");
				double controlY = plugin.getConfig().getInt("cLocation.controlY");
				double controlZ = plugin.getConfig().getInt("cLocation.controlZ");
				String wName = plugin.getConfig().getString("cLocation.world");
				World newWorld = Bukkit.getWorld(wName);
				Location controlLocation = new Location(newWorld, controlX, controlY, controlZ);
				
				p.teleport(controlLocation);
				p.sendMessage("§4[CheatControl] §cTeleportato al punto di controllo");
				return true;
				
			}
			
			if(cmd.getName().equalsIgnoreCase("controllo")){
							
				if(args.length == 0){
					
					p.sendMessage("§4[CheatControl] §aUtilizzo: /controllo <nome>");
					return false;
					
				}
				List<String> cheaterList = (List<String>) plugin.getConfig().getStringList("cheaters");
		    	String name = args[0];
				Player cheater = Bukkit.getPlayer(name);
				
				if(plugin.getConfig().getConfigurationSection("cLocation") == null || plugin.getConfig().getString("cLocation.world") == null){
					
					p.sendMessage("§4[CheatControl] §cIl punto di controllo non è stato ancora creato!");
					
					return true;
				}
									
				cheaterList.add(name);
				plugin.getConfig().set("cheaters", cheaterList);
				double controlX = plugin.getConfig().getInt("cLocation.controlX");
				double controlY = plugin.getConfig().getInt("cLocation.controlY");
				double controlZ = plugin.getConfig().getInt("cLocation.controlZ");
				String worldName = plugin.getConfig().getString("cLocation.world");				
				World newWorld = Bukkit.getWorld(worldName);
				Location controlLocation = new Location(newWorld, controlX, controlY, controlZ);
				
				for(Player on : Bukkit.getOnlinePlayers()){
					
					if(on.hasPermission("cc.notify")){
						
						on.sendMessage("§4[CheatControl] §b" + on.getDisplayName() + " sta facendo un controllo hack a " + cheater.getDisplayName());
						
					}
					
				}
				
				plugin.saveConfig();
				
				cheater.teleport(controlLocation);
				p.sendMessage("§4[CheatControl] §bStai controllando: " + cheater.getDisplayName());
				
				for(Player onCheat : Bukkit.getOnlinePlayers()){
				if(!onCheat.isOnline()){
					
					p.sendMessage("§4[CheatControl] §bIl player definito non è online");
					return true;
				}
				}
				return true;
			}
			
			if(cmd.getName().equalsIgnoreCase("legit")){
				
				if(args.length == 0){
					
					p.sendMessage("§4[CheatControl] §aUtilizzo: /legit <nome>");
					return true;
					
				}
				
				
				List<String> notCheaterList = (List<String>) plugin.getConfig().getStringList("cheaters");
		    	String notCheaterName = args[0];
				Player notCheater = Bukkit.getPlayer(notCheaterName);
				
				notCheaterList.remove(notCheaterName);
				plugin.getConfig().set("cheaters", notCheaterList);
				notCheater.sendMessage("§4[CheatControl]§eSei stato liberato");
				notCheater.teleport(notCheater.getWorld().getSpawnLocation());
				Bukkit.getServer().broadcastMessage("§4[CheatControl] §l" + notCheater.getDisplayName() + "§r§b non utilizza hacks o cheats!");
				plugin.saveConfig();
				return true;

				
			}
			
			if(cmd.getName().equalsIgnoreCase("hack")){
				
				if(args.length == 0){
					
					p.sendMessage("§4[CheatControl] §aUtilizzo: /hack <nome>");
					return true;
					
				}
				
				String cheaterName = args[0];
				Player isCheater = Bukkit.getPlayer(cheaterName);
				
				isCheater.setBanned(true);
				isCheater.kickPlayer("Bannato per utilizzo di hack.");
				Bukkit.getServer().broadcastMessage("§4[CheatControl] §l" + isCheater.getDisplayName() + "§r§b è stato bannato per l'utilizzo di hacks!");
				return true;
				
			}
	
	    }else{
		
	    s.sendMessage("[CheatControl] Questo comando puo' essere eseguito solo in-game");
		return true;
	    }
		return false;
	    
	}
}