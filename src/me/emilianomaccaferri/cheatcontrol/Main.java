package me.emilianomaccaferri.cheatcontrol;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

	public void onEnable(){
		
		this.getServer().getPluginManager().registerEvents(new Events(this), this);
		this.saveDefaultConfig();
		
		this.getCommand("setcontrollo").setExecutor(new Commands(this));
		this.getCommand("controllo").setExecutor(new Commands(this));
		this.getCommand("legit").setExecutor(new Commands(this));
		this.getCommand("hack").setExecutor(new Commands(this));
		this.getCommand("tpcontrollo").setExecutor(new Commands(this));
				
	}
	
	public void onDisable(){}
	
}
