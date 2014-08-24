package si.gigacraft.hub.Components;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import si.gigacraft.hub.GChub.GChub;
import si.gigacraft.hub.Helpers.Helper;

public class PerkFunctions {
	
	/************************************************************************************************
	 * SPAWN COMMAND
	 ***********************************************************************************************/
	public static void onSpawnCommand(Player player) {
		
		// teleport player
		PerkFunctions.HubJoinTeleport(player);
		
		// set players hunger level
		player.setFoodLevel(18);
		
		// setup players inventory
		PerksInventory.PlayerPerksInventory(player);
		
		// give player a set of disco armor
		PerksInventory.GiveDiscoArmor(player);
		
	}
	
	/************************************************************************************************
	 * HUB JOIN TELEPORT
	 ***********************************************************************************************/
	public static void HubJoinTeleport(Player player) {
		
		// set fly allow for player
		player.setAllowFlight(true);
		
		// setup teleport location for player
		Location location = player.getLocation();
		location.setWorld(Bukkit.getWorld(player.getWorld().getName().toString()));
        location.setX(0);
        location.setY(250);
        location.setZ(0);
        
        // teleport player
        player.teleport(location);
		
	}
	
	/************************************************************************************************
	 * Effects
	 ***********************************************************************************************/
	public static void PerkEffects(Player player) {
		
		// test if player has effects applied
		if(player.hasPotionEffect(PotionEffectType.SPEED) || player.hasPotionEffect(PotionEffectType.JUMP)) {
			player.removePotionEffect(PotionEffectType.SPEED);
			player.removePotionEffect(PotionEffectType.JUMP);
		}
		else {
			player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999999, 1), true);
			player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999999, 1), true);
		}
		
	}
	
	/************************************************************************************************
	 * EatMe
	 ***********************************************************************************************/
	public static void PerkEatMe(Player player) {
		
		// get stack size of foods items
		int PerkAmount = GChub.getPlugin().getMainConfig().getInt("Perks.EatMe.amount");
		
		// reset qnt of items in players hand
		player.getInventory().getItemInHand().setAmount(PerkAmount);
		
		// reset players hunger bar
		player.setFoodLevel(18);
	}
	
	/************************************************************************************************
	 * Fart
	 ***********************************************************************************************/
	public static void PerkFart(Player player) {

		// smoke effect
		player.getWorld().playEffect(player.getLocation(), Effect.SMOKE, 0);
		
		// play random sound
		int random = (1 + (int)(Math.random() * ((18 - 1) + 1)));
		switch (Integer.toString(random)) {
		case "1":
			player.getWorld().playSound(player.getLocation(), Sound.ANVIL_BREAK, 1, 0);
			break;
		case "2":
			player.getWorld().playSound(player.getLocation(), Sound.ANVIL_LAND, 1, 0);
			break;
		case "3":
			player.getWorld().playSound(player.getLocation(), Sound.ANVIL_USE, 1, 0);
			break;
		case "4":
			player.getWorld().playSound(player.getLocation(), Sound.BLAZE_BREATH, 1, 0);
			break;
		case "5":
			player.getWorld().playSound(player.getLocation(), Sound.BAT_TAKEOFF, 1, 0);
			break;
		case "8":
			player.getWorld().playSound(player.getLocation(), Sound.CAT_HISS, 1, 0);
			break;
		case "9":
			player.getWorld().playSound(player.getLocation(), Sound.CAT_MEOW, 1, 0);
			break;
		case "10":
			player.getWorld().playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1, 0);
			break;
		case "11":
			player.getWorld().playSound(player.getLocation(), Sound.CREEPER_HISS, 1, 0);
			break;
		case "12":
			player.getWorld().playSound(player.getLocation(), Sound.DOOR_OPEN, 1, 0);
			break;
		case "13":
			player.getWorld().playSound(player.getLocation(), Sound.ENDERMAN_SCREAM, 1, 0);
			break;
		case "14":
			player.getWorld().playSound(player.getLocation(), Sound.GHAST_MOAN, 1, 0);
			break;
		case "15":
			player.getWorld().playSound(player.getLocation(), Sound.PORTAL, 1, 0);
			break;
		case "16":
			player.getWorld().playSound(player.getLocation(), Sound.WOLF_BARK, 1, 0);
			break;
		case "17":
			player.getWorld().playSound(player.getLocation(), Sound.ZOMBIE_PIG_IDLE, 1, 0);
			break;
		default:
			player.getWorld().playSound(player.getLocation(), Sound.CAT_HISS, 1, 0);
			break;
		}

	}
	
	/************************************************************************************************
	 * FreeFall
	 ***********************************************************************************************/
	public static void PerkFreeFall(Player player) {
		
		
		//List<?> FreeFallList = GChub.getPlugin().getMainConfig().getList("FreeFallList");
		//int CoordsRandom = new Random().nextInt(FreeFallList.size());
		//String Coords = FreeFallList.get(CoordsRandom).toString();
		
		int coordsnum = GChub.getPlugin().getMainConfig().getInt("FreeFallLocations");
		int CoordsRandom = new Random().nextInt(coordsnum);
		
		int getX = GChub.getPlugin().getMainConfig().getInt("FreeFall." + Integer.toString(CoordsRandom) + ".x");
		int getY = GChub.getPlugin().getMainConfig().getInt("FreeFall." + Integer.toString(CoordsRandom) + ".y");

		// clear player inventory
		player.getInventory().clear();
		
		// set allow fly and toggle fly on for player
		player.setAllowFlight(true);
		player.setFlying(false);
		
		// teleport player to a random location above spawn point
		Location location = player.getLocation();
		location.setWorld(Bukkit.getWorld("hub"));
        location.setX(getX);
        location.setY((250 + (int)(Math.random() * ((380 - 250) + 1))));
        location.setZ(getY);
        player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 0);
        player.teleport(location);
        player.playSound(location, Sound.ENDERMAN_TELEPORT, 1, 0);
        
        // reset player inventory - give a new random set of perk items
        PerksInventory.PlayerPerksInventory(player);
		
	}
	
	/************************************************************************************************
	 * Fly
	 ***********************************************************************************************/
	public static void PerkFly(Player player) {
		// clear player inventory
		player.getInventory().clear();
				
		// set fly allow for player
		player.setAllowFlight(true);
		
		// toggle player fly mode
		if(player.isFlying()) {
			player.setFlying(false);
			player.sendMessage(Helper.mss(GChub.pluginPrefix, GChub.getPlugin().getMainConfig().getString("Messages.FlyMode.disabled")));
		}
		else {
			player.setFlying(true);
			player.sendMessage(Helper.mss(GChub.pluginPrefix, GChub.getPlugin().getMainConfig().getString("Messages.FlyMode.enabled")));
		}
		
		 // reset player inventory - give a new random set of perk items
        PerksInventory.PlayerPerksInventory(player);
		
	}
	
	
}
