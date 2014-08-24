package si.gigacraft.hub.Config;

import si.gigacraft.hub.GChub.GChub;
import si.gigacraft.hub.Helpers.Helper;
import si.gigacraft.hub.Helpers.YAMLConfig;

public class DefaultOptions {
	private GChub ec;

	public DefaultOptions(GChub ec) {
		this.ec = ec;
	}

	public Object getConfigOption(String s) {
		return ec.getMainConfig().get(s);
	}

	public Object getConfigOption(String s, Object def) {
		return ec.getMainConfig().get(s, def);
	}

	public void setDefaultValues(YAMLConfig mainConfig) {
		try {
			/************************************************************************************************
			 * MESSAGES
			 ***********************************************************************************************/
			mainConfig.set("Messages.NoPermission", mainConfig.get("Messages.NoPermission", "&aCheck out the commands available to donors @ &e/donate"), "Messages");
			/************************************************************************************************
			 * WRITE CONFIG FILE
			 ***********************************************************************************************/
			mainConfig.saveConfig();
		} catch (Exception e) {
			Helper.warning("FTBtools", "Failed to generate default Configuration File.");
		}
	}
}
