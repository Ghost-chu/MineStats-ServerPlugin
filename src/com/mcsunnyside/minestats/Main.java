package com.mcsunnyside.minestats;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.simple.JSONObject;

public class Main extends JavaPlugin implements Listener {
	FileConfiguration config = null;
	public Main instance = this;
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		getLogger().info("Our memory, Our home, The sunnyside.");
		saveDefaultConfig();
		config = getConfig();
		new BukkitRunnable() {
			@Override
			public void run() {
				updateAllPlayersData();
			}
		}.runTaskTimerAsynchronously(this, 1, 20*60*60*12);
	}
	protected void updateAllPlayersData() {
		getLogger().info(getServer().getWorldContainer().toString());
		OfflinePlayer[] players = getServer().getOfflinePlayers();
		String mainWorld = getServer().getWorlds().get(0).getName();
		File worldsFolder = new File(getServer().getWorldContainer().getPath(), mainWorld);
		File statsFolder =  new File(worldsFolder,"stats");
		File advancementsFolder =  new File(worldsFolder,"advancements");
		File playerDataFolder = new File(worldsFolder,"playerdata");
		for (OfflinePlayer offlinePlayer : players) {
			//Stats
			UUID uuid = offlinePlayer.getUniqueId();
			String uuidtext = uuid.toString().toLowerCase();
			File statsPath = new File(statsFolder,uuidtext+".json");
			File playerDataPath =  new File(playerDataFolder,uuidtext+".dat");
			File advancementsPath = new File(advancementsFolder,uuidtext+".json");
			if (statsPath.exists()&&statsPath.canRead()&&playerDataPath.exists()&& playerDataPath.canRead()&&advancementsPath.exists()&& advancementsPath.canRead()) {
				String statsString = readFile(statsPath);
				String advancementsString = readFile(advancementsPath);
				String profileString = null;
				try {
					profileString = readFileByBytes(playerDataPath);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Map<String,String> dataMap = new HashMap<>();
				dataMap.put("type", "updateData");
				dataMap.put("uuid", uuidtext);
				dataMap.put("name", offlinePlayer.getName());
				dataMap.put("data", statsString);
				dataMap.put("profile", profileString);
				dataMap.put("advancement", advancementsString);
				dataMap.put("key", "KEY");
				if(offlinePlayer.isBanned()) {
					dataMap.put("banned", "1");
				}else {
					dataMap.put("banned", "0");
				}
				getLogger().info("Uploading "+offlinePlayer.getName()+"'s stats...");
				getLogger().info("Upload result: "+HttpUtils.sendPost("http://api.mcsunnyside.com/game/mojang/minecraft/gameprofile/sunnyside/submit.php", JSONObject.toJSONString(dataMap)));
			}else {
				getLogger().warning("Failed loading file "+statsPath.toString());
			}
		}
	}
	@Override
	public void onDisable() {

	}
	    public static String readFileByBytes(File file) throws IOException {
	    	return Base64.getEncoder().encodeToString(Files.readAllBytes(file.toPath()));
	    }
	protected String readFile(File file) {
		StringBuilder result = new StringBuilder();
		try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result.append(System.lineSeparator()+s);
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
	}
}
