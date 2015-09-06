package com.nao20010128nao;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public final class Events implements Listener {
	PlayerPrivacy plugin;

	public Events(PlayerPrivacy plugin) {
		// TODO 自動生成されたコンストラクター・スタブ
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}

	public void onCommandEvent(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		String username = player.getName();
		String text = event.getMessage();
		if (text.charAt(0) != '/') {/* The input is "chat", not a "command" */
			return;
		}
		if (plugin.config.denyCommand.contains(username)) {
			event.setCancelled(true);
			player.sendMessage("§cYou are DENIED to use ALL commands!");
		}
		if (plugin.config.commandCapture.containsKey(username)) {
			for (String s : plugin.config.commandCapture.get(username)) {
				player = player.getServer().getPlayerExact(s);
				if (player != null) {
					player.sendMessage("§a" + username + " sent: " + text);
				}
			}
		}
	}
}
