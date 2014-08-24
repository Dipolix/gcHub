package si.gigacraft.hub.Components;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import si.gigacraft.hub.GChub.GChub;

public class PlayerEvents implements Listener {

	/************************************************************************************************
	 * PLAYER JOIN HUB EVENT
	 ***********************************************************************************************/
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerJoin(PlayerJoinEvent event) {
		
		// get player
		Player player = event.getPlayer();
		
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
	 * PLAYER PERK USE EVENT
	 ***********************************************************************************************/
	@EventHandler(priority=EventPriority.HIGH)
	public void onPlayerUse(PlayerInteractEvent event) {
		
		// make sure event is real
		if(event.getItem() != null && event.getItem().getTypeId() != 0) {
			
			// get player
			Player player = event.getPlayer();
			
			// get list of available perks
			List<?> PerkList = GChub.getPlugin().getMainConfig().getList("PerkList");
			
			// loop thru list of available perk
			for (int i = 0; i < PerkList.size(); i++) {
				
				// gel list of IDs for current perk
				List<?> PerkIDs = GChub.getPlugin().getMainConfig().getList("Perks." + PerkList.get(i) + ".id");

				
				if(PerkIDs.contains(player.getItemInHand().getTypeId())) {
					
					switch (PerkList.get(i).toString()) {
					case "Servers":
						// -> do nothing: compassnav is handling this

						break;
					case "Pets":
						// open up pets GUI
						player.performCommand("pet select");
						break;
					case "Effects":
						// toggle player speed and jump boost
						PerkFunctions.PerkEffects(player);
						break;
					case "EatMe":
						// player is hungry
						PerkFunctions.PerkEatMe(player);
						break;
					case "Fart":
						// player wants to fart
						PerkFunctions.PerkFart(player);
						break;
					case "FreeFall":
						// player want to get some fresh air
						PerkFunctions.PerkFreeFall(player);
						break;
					case "Fly":
						// player wants to fly
						PerkFunctions.PerkFly(player);
						break;
					}
					
				}
				
			}
			
		}
		
	}
	
}
