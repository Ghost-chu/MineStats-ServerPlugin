package com.mcsunnyside.minestatic;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

@SuppressWarnings("deprecation")
public class Main extends JavaPlugin implements Listener {
	FileConfiguration config = null;
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		getLogger().info("Our memory, Our home, The sunnyside.");
		saveDefaultConfig();
		config = getConfig();
		new BukkitRunnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				saveConfig();
			}
		}.runTaskTimerAsynchronously(this, 5000, 5000);
	}
	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		saveConfig();
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void BlockBreak(BlockBreakEvent e) {
		new BukkitRunnable() {
			@Override
			public void run() {
				String blockname = e.getBlock().getType().name();
				String playername = e.getPlayer().getName();
				plusone("block.break.total", 1);
				plusone("block.break.type."+blockname+".count", 1);
				plusone("block.break.player."+playername+".count", 1);
				plusone("block.break.player."+playername+".type."+blockname+".count", 1);
			}
		}.runTaskAsynchronously(this);
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void BlockPlace(BlockPlaceEvent e) {
		new BukkitRunnable() {
			@Override
			public void run() {
				String blockname = e.getBlock().getType().name();
				String playername = e.getPlayer().getName();
				plusone("block.place.total", 1);
				plusone("block.place.type."+blockname+".count", 1);
				plusone("block.place.player."+playername+".count", 1);
				plusone("block.place.player."+playername+".type."+blockname+".count", 1);
			}
		}.runTaskAsynchronously(this);
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void EntitySpawn(CreatureSpawnEvent e) {
		new BukkitRunnable() {
			@Override
			public void run() {
				String entityname =e.getEntityType().name();
				String reason = e.getSpawnReason().name();
				plusone("entity.spawn.total", 1);
				plusone("eneity.spawn.reason."+reason+".type."+entityname+".count", 1);
			}
		}.runTaskAsynchronously(this);
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void EntityDamageByEntity(EntityDamageByEntityEvent e) {
		new BukkitRunnable() {
			@Override
			public void run() {
				String attacker = e.getCause().name();
				if(attacker == DamageCause.ENTITY_ATTACK.name()||attacker == DamageCause.ENTITY_EXPLOSION.name()||attacker == DamageCause.ENTITY_SWEEP_ATTACK.name()) {
					attacker =e.getDamager().getName();
				}
				plusone("entity.damagebyentity.total", 1);
				plusone("entity.damagebyentity.attacker."+attacker+".count", 1);
				plusone("entity.damagebyentity.attacker."+attacker+".totaloutput", e.getFinalDamage());
			}
		}.runTaskAsynchronously(this);
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void PlayerDeath(PlayerDeathEvent e) {
		new BukkitRunnable() {
			@Override
			public void run() {
				String playername = e.getEntity().getName();
				String reason = e.getDeathMessage().replaceAll(e.getEntity().getName(), "");
				plusone("player.death.total", 1);
				plusone("player.death.reason."+reason+".count", 1);
				plusone("player.death."+playername+".count", 1);
				plusone("player.death."+playername+".reason."+reason+".count", 1);
			}
		}.runTaskAsynchronously(this);
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void PlayerJoin(PlayerJoinEvent e) {
		new BukkitRunnable() {
			@Override
			public void run() {
				String playername = e.getPlayer().getName();
				plusone("player.join.total", 1);
				plusone("player.join."+playername+".count", 1);
			}
				
		}.runTaskAsynchronously(this);
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void InventoryOpen(InventoryOpenEvent e) {
		new BukkitRunnable() {
			@Override
			public void run() {
				String playername = e.getPlayer().getName();
				plusone("player.openinv.total", 1);
				plusone("player.openinv."+playername+".count", 1);
			}
		}.runTaskAsynchronously(this);
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void Chating(AsyncPlayerChatEvent e) {
		new BukkitRunnable() {
			@Override
			public void run() {
				String playername = e.getPlayer().getName();
				plusone("player.chat.total", 1);
				plusone("player.chat."+playername+".count", 1);
			}
		}.runTaskAsynchronously(this);
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void Walking(PlayerMoveEvent e) {
		new BukkitRunnable() {
			@Override
			public void run() {
				int fromX = e.getFrom().getBlockX();
				int fromY = e.getFrom().getBlockY();
				int fromZ = e.getFrom().getBlockZ();
				int toX = e.getTo().getBlockX();
				int toY = e.getTo().getBlockY();
				int toZ = e.getTo().getBlockZ();
				if(fromX == toX && fromY==toY && fromZ == toZ) {
					return;
				}
				String playername = e.getPlayer().getName();
				plusone("player.move.total", 1);
				plusone("player.move."+playername+".count", 1);
			}
		}.runTaskAsynchronously(this);
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void Sleeping(PlayerBedEnterEvent e) {
		new BukkitRunnable() {
			@Override
			public void run() {
				String playername = e.getPlayer().getName();
				plusone("player.bedenter.total", 1);
				plusone("player.bedenter."+playername+".count", 1);
			}
		}.runTaskAsynchronously(this);
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void ChatingTab(PlayerChatTabCompleteEvent e) {
		new BukkitRunnable() {
			@Override
			public void run() {
				String playername = e.getPlayer().getName();
				plusone("player.chatingtab.total", 1);
				plusone("player.chatingtab."+playername+".count", 1);
			}
		}.runTaskAsynchronously(this);
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void ChatingTab(PlayerCommandSendEvent e) {
		new BukkitRunnable() {
			@Override
			public void run() {
				String playername = e.getPlayer().getName();
				plusone("player.sendcommand.total", 1);
				plusone("player.sendcommand."+playername+".count", 1);
			}
		}.runTaskAsynchronously(this);
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void ItemBroken(PlayerItemBreakEvent e) {
		new BukkitRunnable() {
			@Override
			public void run() {
				String playername = e.getPlayer().getName();
				String itemname = e.getBrokenItem().getType().name();
				plusone("player.itembreak.total", 1);
				plusone("player.itembreak."+playername+".count", 1);
				plusone("player.itembreak."+playername+".item."+itemname+".count", 1);
			}
		}.runTaskAsynchronously(this);
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void toggleSneak(PlayerToggleSneakEvent e) {
		new BukkitRunnable() {
			@Override
			public void run() {
				String playername = e.getPlayer().getName();
				plusone("player.togglesneak.total", 1);
				plusone("player.togglesneak."+playername+".count", 1);
			}
		}.runTaskAsynchronously(this);
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void crossPortal(PlayerPortalEvent e) {
		new BukkitRunnable() {
			@Override
			public void run() {
				String playername = e.getPlayer().getName();
				plusone("player.portal.total", 1);
				plusone("player.portal."+playername+".count", 1);
			}
		}.runTaskAsynchronously(this);
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void toggleSprint(PlayerToggleSprintEvent e) {
		new BukkitRunnable() {
			@Override
			public void run() {
				String playername = e.getPlayer().getName();
				plusone("player.sprint.total", 1);
				plusone("player.sprint."+playername+".count", 1);
			}
		}.runTaskAsynchronously(this);
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void blockPhysics(BlockPhysicsEvent e) {
		new BukkitRunnable() {
			@Override
			public void run() {
				plusone("block.physics.total", 1);
			}
		}.runTaskAsynchronously(this);
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void crafting(CraftItemEvent e) {
		new BukkitRunnable() {
			@Override
			public void run() {
					String playername = e.getWhoClicked().getName();
					String result = e.getCurrentItem().getType().name();
					plusone("player.craft.total", 1);
					plusone("player.craft."+playername+".count", 1);
					plusone("player.craft."+playername+".type."+result+".count", 1);
				
			}
		}.runTaskAsynchronously(this);
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void pickup(PlayerPickupItemEvent e) {
		new BukkitRunnable() {
			@Override
			public void run() {
					String playername = e.getPlayer().getName();
					String result = e.getItem().getItemStack().getType().name();
					plusone("player.pickup.total", 1);
					plusone("player.pickup."+playername+".count", 1);
					plusone("player.pickup."+playername+".type."+result+".count", 1);
				
			}
		}.runTaskAsynchronously(this);
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void pickup(PlayerDropItemEvent e) {
		new BukkitRunnable() {
			@Override
			public void run() {
					String playername = e.getPlayer().getName();
					String result = e.getItemDrop().getItemStack().getType().name();
					plusone("player.drop.total", 1);
					plusone("player.drop."+playername+".count", 1);
					plusone("player.drop."+playername+".type."+result+".count", 1);
				
			}
		}.runTaskAsynchronously(this);
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void blockRedstone(BlockRedstoneEvent e) {
		new BukkitRunnable() {
			@Override
			public void run() {
					plusone("block.redstone.total", 1);
				
			}
		}.runTaskAsynchronously(this);
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void blockGrow(BlockGrowEvent e) {
		new BukkitRunnable() {
			@Override
			public void run() {
					plusone("block.grow.total", 1);
				
			}
		}.runTaskAsynchronously(this);
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void blockSignChange(SignChangeEvent e) {
		new BukkitRunnable() {
			@Override
			public void run() {
					plusone("block.signchange.total", 1);
				
			}
		}.runTaskAsynchronously(this);
	}
	public void plusone(String node,double is) {
		config.set(node, config.getDouble(node)+is);
	}
}
