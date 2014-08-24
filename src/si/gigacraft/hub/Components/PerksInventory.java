package si.gigacraft.hub.Components;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import si.gigacraft.hub.GChub.GChub;
import si.gigacraft.hub.Helpers.Helper;

public class PerksInventory {
	
	/************************************************************************************************
	 * PLAYER PERKS INVENTORY INIT
	 ***********************************************************************************************/
	public static void PlayerPerksInventory(Player player) {
		
		// get a list of all perks
		List<?> PerkList = GChub.getPlugin().getMainConfig().getList("PerkList");
		
		// get player name
		String PlayerName = player.getName().toString();
		
		// get player rank
		String AllPlayerRanks = "";
		String PlayerRank = null;
		String[] playerGroups = GChub.getPlugin().permission.getPlayerGroups(player);  
		List<?> RankLevel = GChub.getPlugin().getMainConfig().getList("RankLevel");
		
		for(int i = 0; i < playerGroups.length; i++) {
			AllPlayerRanks = AllPlayerRanks + ", " + playerGroups[i];
		}

		for (int i = 0; i < RankLevel.size(); i++) {
			String gName = RankLevel.get(i).toString().toLowerCase();
			// test if current group is the highest one
			if(AllPlayerRanks.toString().toLowerCase().contains(gName)) {
				PlayerRank = RankLevel.get(i).toString();
				break;
			}			
		}
		
		// clear player inventory before generating a new random one
		player.getInventory().clear();
		
		// create random inventory for player
		PlayerCreateRandomInventory(player, PlayerName, PlayerRank, PerkList);
		
	}
	
	/************************************************************************************************
	 * PLAYER PERKS INVENTORY - CREATE RANDOM INVENTORY
	 ***********************************************************************************************/
	public static void PlayerCreateRandomInventory(Player player, String PlayerName, String PlayerRank, List<?> PerkList) {
		
		// loop thru all perks and prepeare / give items to player
		for (int i = 0; i < PerkList.size(); i++) {
			
			// item description - get all lines
			String Line1 = GChub.getPlugin().getMainConfig().getString("DescSetup.Line1");
			String Line2 = GChub.getPlugin().getMainConfig().getString("DescSetup.Line2");
			String Line3 = GChub.getPlugin().getMainConfig().getString("DescSetup.Line3");
			String Line4 = GChub.getPlugin().getMainConfig().getString("DescSetup.Line4");
			
			// get some basic data for current perk
			int PerkSlot = GChub.getPlugin().getMainConfig().getInt("Perks." + PerkList.get(i) + ".slot");
			int PerkAmount = GChub.getPlugin().getMainConfig().getInt("Perks." + PerkList.get(i) + ".amount");
			
			// get list of names for current perk and select a random name
			List<?> PerkNameList = GChub.getPlugin().getMainConfig().getList("Perks." + PerkList.get(i) + ".name");
			int PerkNameRandom = new Random().nextInt(PerkNameList.size());
			String PerkName = PerkNameList.get(PerkNameRandom).toString();
			PerkName = Helper.colors(PerkName);
			
			// get list of item ID's for current perk and select a random item id
			List<?> PerkItemIDList = GChub.getPlugin().getMainConfig().getList("Perks." + PerkList.get(i) + ".id");
			int PerkItemIDRandom = new Random().nextInt(PerkItemIDList.size());
			String PerkItemID = PerkItemIDList.get(PerkItemIDRandom).toString();
			int PerkID = Integer.parseInt(PerkItemID);
			
			// get list of description messages and select a random message
			List<?> DescMessagesList = GChub.getPlugin().getMainConfig().getList("DescMessage");
			int DescMessagesListRandom = new Random().nextInt(DescMessagesList.size());
			String DescMessage = DescMessagesList.get(DescMessagesListRandom).toString();
			
			// setup custom item name and descript
			ArrayList<String> descLines = new ArrayList<String>();
			descLines.add(ItemDescriptionSetup(Line1, PlayerName, PlayerRank, DescMessage));
			descLines.add(ItemDescriptionSetup(Line2, PlayerName, PlayerRank, DescMessage));
			descLines.add(ItemDescriptionSetup(Line3, PlayerName, PlayerRank, DescMessage));
			descLines.add(ItemDescriptionSetup(Line4, PlayerName, PlayerRank, DescMessage));
			List<String> itemLore = new ArrayList<String>(descLines);
			
			// create customized item
			ItemStack PerkItem = new ItemStack(Material.getMaterial(PerkID), PerkAmount);
			PerkItem = setItemMeta(PerkItem, PerkName, itemLore);
			
			// give player newly created item
			player.getInventory().setItem(PerkSlot, PerkItem);	
			
		}
		
	}
	
	/************************************************************************************************
	 * DISCO BOOTS
	 ***********************************************************************************************/
	public static void GiveDiscoArmor(Player player) {
				
		// create customized item
		ItemStack DiscoArmorItem = new ItemStack(Material.LEATHER_BOOTS, 1);
		
		player.getInventory().setBoots(DiscoArmorItem);
		
	}
	
	
	/************************************************************************************************
	 * ITEM DESCRIPTION SETUP
	 ***********************************************************************************************/
	public static String ItemDescriptionSetup(String Line, String PlayerName, String PlayerRank, String Message) {
		String newLine = Line.replace("%player%", PlayerName);
		newLine = newLine.replace("%rank%", PlayerRank);
		newLine = newLine.replace("%descmsg%", Message);
		newLine = Helper.colors(newLine);
		return newLine;
	}
	
	/************************************************************************************************
	 * ITEM NAME AND LORE SETUP
	 ***********************************************************************************************/
	@SuppressWarnings("unchecked")
	public static ItemStack setItemMeta(ItemStack item, String data, List<?> description) {
        ItemMeta meta = (ItemMeta) item.getItemMeta();
        meta.setDisplayName(data);
        meta.setLore(null);
        meta.setLore((List<String>) description);
        item.setItemMeta(meta);
        return item;
	}
	
}
