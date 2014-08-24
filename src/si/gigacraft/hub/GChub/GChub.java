package si.gigacraft.hub.GChub;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import si.gigacraft.hub.Components.HelpPages;
import si.gigacraft.hub.Components.PerkArmor;
import si.gigacraft.hub.Components.PerkFunctions;
import si.gigacraft.hub.Components.PerksInventory;
import si.gigacraft.hub.Components.PlayerEvents;
import si.gigacraft.hub.Config.DefaultOptions;
import si.gigacraft.hub.Helpers.Helper;
import si.gigacraft.hub.Helpers.YAMLConfig;
import si.gigacraft.hub.Helpers.YAMLConfigManager;


public class GChub extends JavaPlugin {
	
	// this plugin
	public static GChub plugin;
	public static String pluginPrefix = "CN";
	public static String pluginName = "CNhub";
	
	// helper class
	public final Helper functions = new Helper();
	
	// permission
	public net.milkbowl.vault.permission.Permission permission = null;
	
	// config manager
	private YAMLConfigManager configManager;
	private YAMLConfig mainConfig;
	public DefaultOptions defaultoptions;
	
	// file paths
	public static String FilesPath = "plugins/GChub/";
	
	// components
	public HelpPages helppages;
	public PerksInventory perksinventory;
	public PlayerEvents playerevents;
	public PerkFunctions perkfunctions;
	public PerkArmor perkarmor;
	
	
	/************************************************************************************************
	 * ON PLUGIN :: ENABLE / RELOAD
	 ***********************************************************************************************/
	@Override
	public void onEnable() {
		
		// set plugin
		plugin = this;
		
		// Plugin manager
		PluginManager pluginmanager = this.getServer().getPluginManager();
		
		// get plugin.yml file contents
		PluginDescriptionFile description = this.getDescription();
		
		/************************************************************************************************
		 * PLUGIN CONFIG
		 ***********************************************************************************************/
		configManager = new YAMLConfigManager(this);
		String[] header = { pluginPrefix, "----- GC HUB Tools by GigaCraft -----"};
		
		// default config file (config.yml)
		defaultoptions = new DefaultOptions(this);
		try {
			mainConfig = configManager.getNewConfig("config.yml", header);
			defaultoptions.setDefaultValues(mainConfig);
		} 
		catch (Exception e) {}
		mainConfig.reloadConfig();
		
		/************************************************************************************************
		 * BUNGEECORD CHAT
		 ***********************************************************************************************/
		// this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		// this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
		
		/************************************************************************************************
		 * START COMPONENTS
		 ***********************************************************************************************/
		plugin.setupPermissions();
		
		playerevents = new PlayerEvents();
		pluginmanager.registerEvents(playerevents, this);
		
		perksinventory = new PerksInventory();
		perkfunctions = new PerkFunctions();
		perkarmor = new PerkArmor();
		PerkArmor.DiscoArmor();
		
		helppages = new HelpPages();

		/************************************************************************************************
		 * START TIMERS
		 ***********************************************************************************************/
		
		
		
		/************************************************************************************************
		 * LOG PLUGIN INIT
		 ***********************************************************************************************/
		Helper.log(pluginPrefix, "&e Version: " + description.getVersion() + "&a Has Been Enabled!");
		
	}
	
	/************************************************************************************************
	 * ON PLUGIN :: DISABLE
	 ***********************************************************************************************/
	@Override
	public void onDisable() {
		// console message on disable
		Helper.log(pluginPrefix, "&aHas Been Disabled!");
	}
	
	/************************************************************************************************
	 * COMMANDS
	 ***********************************************************************************************/
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// lowercase incomming command
		String command = cmd.getName().toLowerCase();
		
		// test for command origin
		if (sender instanceof Player) {
			// sender is a player
			
			// get player
			Player player = (Player) sender;
			
			// loop thru all valid commands that can be used by players
			switch (command) {
			case "fly":
				player.sendMessage(Helper.mss(pluginPrefix, GChub.getPlugin().getMainConfig().getString("Messages.FlyMode.enabled")));
				player.setAllowFlight(true);
				player.setFlying(true);
				break;
			case "spawn":
				PerkFunctions.onSpawnCommand(player);
				break;
			case "vote":
				HelpPages.needHelp(sender, "vote", label, args);
				break;
			case "banneditems":
				HelpPages.needHelp(sender, "banneditems", label, args);
				break;
			case "help":
				HelpPages.needHelp(sender, "help", label, args);
				break;
			case "rules":
				HelpPages.needHelp(sender, "rules", label, args);
				break;
			case "gmc":
				if(player.isOp()) {
					player.setGameMode(GameMode.CREATIVE);
					player.sendMessage(Helper.mss(pluginPrefix, GChub.getPlugin().getMainConfig().getString("Messages.Gamemode.creative")));
				}
				else {
					player.setGameMode(GameMode.ADVENTURE);
					player.setAllowFlight(true);
					player.setFlying(true);
					player.sendMessage(Helper.mss(pluginPrefix, GChub.getPlugin().getMainConfig().getString("Messages.Gamemode.adventure")));
				}
				break;
			case "gma":
				player.setGameMode(GameMode.ADVENTURE);
				player.setAllowFlight(true);
				player.setFlying(true);
				player.sendMessage(Helper.mss(pluginPrefix, GChub.getPlugin().getMainConfig().getString("Messages.Gamemode.adventure")));
				
				break;
			case "plugins":
				player.sendMessage(Helper.mss(pluginPrefix, GChub.getPlugin().getMainConfig().getString("Messages.Plugins")));
				break;
			}
			
		}
		else {
			// sender is console or another plugin
			
		}
		return false;
	}
	
	/************************************************************************************************
	 * Permissions
	 ***********************************************************************************************/
	private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }
	
	/************************************************************************************************
	 * GET SERVER NAME FROM server.properties
	 ***********************************************************************************************/
	public static String getServerName() {
		return Bukkit.getServer().getServerId().toString().toLowerCase();
	}
	
	/************************************************************************************************
	 * GET SERVER DATA FROM CONFIG
	 ***********************************************************************************************/
	
	
	/************************************************************************************************
	 * GET PLUGIN
	 ***********************************************************************************************/
	public static GChub getPlugin() {
		return plugin;
	}

	/************************************************************************************************
	 * PLUGIN CONFIG FILES
	 ***********************************************************************************************/
	public YAMLConfig getMainConfig() {
		return mainConfig;
	}
	
	
	
}