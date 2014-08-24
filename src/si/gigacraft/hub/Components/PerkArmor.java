package si.gigacraft.hub.Components;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import si.gigacraft.hub.GChub.GChub;
import si.gigacraft.hub.Helpers.Helper;

public class PerkArmor {
	
	/************************************************************************************************
	 * GIGA DISCO ARMOR
	 ***********************************************************************************************/
	public static int GIGADISCOARMOR = 0;
	
	public static void DiscoArmor() {
		GIGADISCOARMOR = Bukkit.getScheduler().scheduleSyncRepeatingTask(GChub.getPlugin(), new Runnable() {
			
			private Random r = new Random();
			
			@Override
			public void run() {
				
				Color c = Color.fromRGB(r.nextInt(255), r.nextInt(255), r.nextInt(255));
				
				for(Player p : Bukkit.getServer().getOnlinePlayers()) {
					if (p.getInventory().getBoots() != null && p.getInventory().getBoots().getType() == Material.LEATHER_BOOTS) {
						p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));
					}
				}
				
			}

		}, 0, 1);
	}
	
	// color leather armor
	private static ItemStack getColorArmor(Material m, Color c) {
		ItemStack i = new ItemStack(m, 1);
		LeatherArmorMeta meta = (LeatherArmorMeta) i.getItemMeta();
		meta.setColor(c);
		meta.setDisplayName(Helper.colors("&eGiga Fancy Armor"));
		i.setItemMeta(meta);
		return i;
	}
	
	
}
