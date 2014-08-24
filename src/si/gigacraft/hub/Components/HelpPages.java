package si.gigacraft.hub.Components;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import si.gigacraft.hub.GChub.GChub;
import si.gigacraft.hub.Helpers.Helper;

public class HelpPages {

	static String pluginPrefix = "HelpPages";
	static String publicPath = GChub.FilesPath + "HelpPages/";

	public static void needHelp(CommandSender sender, String cmd, String label, String[] args) {
		// command
		String command = cmd.toLowerCase();
		// player
		Player player = (Player) sender;

		// root command split for public and moderator access!
		if (command.equalsIgnoreCase("help")) {
			try {
				showHelpPage(player, publicPath + "help.txt");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		else if (command.equalsIgnoreCase("vote")) {
			try {
				showHelpPage(player, publicPath + "vote.txt");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		else if (command.equalsIgnoreCase("banneditems")) {
			try {
				showHelpPage(player, publicPath + "banneditems.txt");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		else if (command.equalsIgnoreCase("rules")) {
			try {
				showHelpPage(player, publicPath + "rules.txt");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		else {
			// Unknown help page
			errMessage(player, "Unknown help page!");
		}

	}

	// show help page
	public static void showHelpPage(Player player, String path)
			throws FileNotFoundException {
		FileInputStream stream = new FileInputStream(path);
		@SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(stream));
		int NumLines = 0;
		try {
			NumLines = countLines(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < NumLines; i++) {
			try {
				ShowPage(player, reader.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// construct filename from arguments
	public static String FileName(String[] args) {
		String name = null;

		for (int i = 0; i < args.length; i++) {
			if (i == 0) {
				name = args[i];
			} else {
				name = name + "_" + args[i];
			}
		}
		return name.toLowerCase() + ".txt";

	}

	// check if help page exists
	public static Boolean FileExists(String path) {
		File helpPage = new File(path);
		return (helpPage.exists() ? true : false);

	}

	// count lines in help page file
	public static int countLines(String filename) throws IOException {
		LineNumberReader reader = new LineNumberReader(new FileReader(filename));
		int cnt = 0;
		@SuppressWarnings("unused")
		String lineRead = "";
		while ((lineRead = reader.readLine()) != null) {
		}

		cnt = reader.getLineNumber();
		reader.close();
		return cnt;
	}

	// help page messages
	public static void errMessage(Player player, String message) {
		player.sendMessage(Helper.colors("&c[&aHelp&c]&b " + message));
	}

	// show help page
	public static void ShowPage(Player player, String content) {
		player.sendMessage(Helper.colors(content));
	}

}