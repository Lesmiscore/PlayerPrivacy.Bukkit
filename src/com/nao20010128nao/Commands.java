package com.nao20010128nao;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public final class Commands {
	PlayerPrivacy plugin;

	public Commands(PlayerPrivacy plugin) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.plugin = plugin;
	}

	private boolean checkPerm(CommandSender sender) {
		if (!(sender.isOp() | sender.hasPermission("pp.all"))) {
			sender.sendMessage("§cYou don't have permission to use this command.");
			return false;
		}
		return true;
	}

	public boolean cbCmd(CommandSender sender, String label, String[] args) {
		if (!checkPerm(sender))
			return false;
		if (args.length == 1) {
			if (!"list".equalsIgnoreCase(args[0])) {
				sender.sendMessage("§aUsage: /cb <add|remove|list> <player>");
				return false;
			}
			HashSet<String> people = plugin.config.chatBlock;
			StringBuilder listBuilder = new StringBuilder(people.size() * 16);
			for (String player : people) {
				listBuilder.append(player).append(", §r");
			}
			String toShow = listBuilder.substring(0, listBuilder.length() - 5);
			sender.sendMessage("There are " + people.size()
					+ " people in the list:");
			sender.sendMessage(toShow);
			return true;
		}
		if (args.length != 2) {
			sender.sendMessage("§aUsage: /cb <add|remove|list> <player>");
			return false;
		}
		Player player;
		try {
			UUID uuid = UUID.fromString(args[1]);
			player = plugin.getServer().getPlayer(uuid);
		} catch (Throwable ex) {
			player = plugin.getServer().getPlayer(args[1]);
		}
		String playerName;
		if (player == null) {
			playerName = args[1];
		} else {
			playerName = player.getName();
		}
		switch (args[0]) {
		case "add":
			plugin.config.chatBlock.add(playerName);
			sender.sendMessage("§aAdded " + playerName
					+ " into Chat Block List.");
			return true;
		case "remove":
			plugin.config.chatBlock.remove(playerName);
			sender.sendMessage("§Removed " + playerName
					+ " from Chat Block List.");
			return true;
		default:
			sender.sendMessage("§aUsage: /cb <add|remove|list> <player>");
			return false;
		}
	}

	public boolean ccCmd(CommandSender sender, String label, String[] args) {
		if (!checkPerm(sender))
			return false;
		if (args.length == 1) {
			if (!"list".equalsIgnoreCase(args[0])) {
				sender.sendMessage("§aUsage: /cc <add|remove|list> <player>");
				return false;
			}
			HashMap<String, PlayerPrivacy.PPConfig.ComparerHS> people = plugin.config.commandCapture;
			StringBuilder listBuilder = new StringBuilder(people.size() * 16);
			for (String player : people.keySet()) {
				listBuilder.append(player).append(", §r");
			}
			String toShow = listBuilder.substring(0, listBuilder.length() - 5);
			sender.sendMessage("There are " + people.size()
					+ " people in the list:");
			sender.sendMessage(toShow);
			return true;
		}
		if (args.length == 2) {
			if (!"list".equalsIgnoreCase(args[0])) {
				sender.sendMessage("§aUsage: /cc <add|remove|list> <player>");
				return false;
			}
			HashMap<String, PlayerPrivacy.PPConfig.ComparerHS> data = plugin.config.commandCapture;
			Set<String> people;
			if (data.containsKey(args[1])) {
				people = data.get(args[1]);
			} else {
				people = Collections.emptySet();
			}
			StringBuilder listBuilder = new StringBuilder(people.size() * 16);
			for (String player : people) {
				listBuilder.append(player).append(", §r");
			}
			String toShow = listBuilder.substring(0, listBuilder.length() - 5);
			sender.sendMessage("There are " + people.size()
					+ " people in the list:");
			sender.sendMessage(toShow);
			return true;
		}
		if (args.length != 2) {
			sender.sendMessage("§aUsage: /cc <add|remove|list> <player>");
			return false;
		}
		Player player;
		try {
			UUID uuid = UUID.fromString(args[1]);
			player = plugin.getServer().getPlayer(uuid);
		} catch (Throwable ex) {
			player = plugin.getServer().getPlayer(args[1]);
		}
		String playerName;
		if (player == null) {
			playerName = args[1];
		} else {
			playerName = player.getName();
		}
		PlayerPrivacy.PPConfig.ComparerHS toEdit;
		if (plugin.config.commandCapture.containsKey(playerName)) {
			toEdit = plugin.config.commandCapture.get(playerName);
		} else {
			toEdit = plugin.config.new ComparerHS();
			plugin.config.commandCapture.put(playerName, toEdit);
		}
		switch (args[0]) {
		case "add":
			toEdit.add(playerName);
			sender.sendMessage("§aRegeistered redirect request from "
					+ sender.getName() + " to " + playerName + " .");
			return true;
		case "remove":
			toEdit.remove(playerName);
			sender.sendMessage("§cUnregeistered redirect request from "
					+ sender.getName() + " to " + playerName + " .");
			return true;
		default:
			sender.sendMessage("§aUsage: /cc <add|remove|list> <player>");
			return false;
		}
	}

	public boolean dcCmd(CommandSender sender, String label, String[] args) {
		if (!checkPerm(sender))
			return false;
		if (args.length == 1) {
			if (!"list".equalsIgnoreCase(args[0])) {
				sender.sendMessage("§aUsage: /dc <add|remove|list> <player>");
				return false;
			}
			HashSet<String> people = plugin.config.denyCommand;
			StringBuilder listBuilder = new StringBuilder(people.size() * 16);
			for (String player : people) {
				listBuilder.append(player).append(", §r");
			}
			String toShow = listBuilder.substring(0, listBuilder.length() - 5);
			sender.sendMessage("There are " + people.size()
					+ " people in the list:");
			sender.sendMessage(toShow);
			return true;
		}
		if (args.length != 2) {
			sender.sendMessage("§aUsage: /dc <add|remove|list> <player>");
			return false;
		}
		Player player;
		try {
			UUID uuid = UUID.fromString(args[1]);
			player = plugin.getServer().getPlayer(uuid);
		} catch (Throwable ex) {
			player = plugin.getServer().getPlayer(args[1]);
		}
		String playerName;
		if (player == null) {
			playerName = args[1];
		} else {
			playerName = player.getName();
		}
		switch (args[0]) {
		case "add":
			plugin.config.denyCommand.add(playerName);
			sender.sendMessage("§aAdded " + playerName
					+ " into Deny Command List.");
			return true;
		case "remove":
			plugin.config.denyCommand.remove(playerName);
			sender.sendMessage("§cRemoved " + playerName
					+ " from Deny Command List.");
			return true;
		default:
			sender.sendMessage("§aUsage: /dc <add|remove|list> <player>");
			return false;
		}
	}

	public boolean mlCmd(CommandSender sender, String label, String[] args) {
		if (!checkPerm(sender))
			return false;
		if (args.length == 1) {
			if (!"list".equalsIgnoreCase(args[0])) {
				sender.sendMessage("§aUsage: /ml <add|remove|list> <player>");
				return false;
			}
			HashSet<String> people = plugin.config.moveLock;
			StringBuilder listBuilder = new StringBuilder(people.size() * 16);
			for (String player : people) {
				listBuilder.append(player).append(", §r");
			}
			String toShow = listBuilder.substring(0, listBuilder.length() - 5);
			sender.sendMessage("There are " + people.size()
					+ " people in the list:");
			sender.sendMessage(toShow);
			return true;
		}
		if (args.length != 2) {
			sender.sendMessage("§aUsage: /ml <add|remove|list> <player>");
			return false;
		}
		Player player;
		try {
			UUID uuid = UUID.fromString(args[1]);
			player = plugin.getServer().getPlayer(uuid);
		} catch (Throwable ex) {
			player = plugin.getServer().getPlayer(args[1]);
		}
		String playerName;
		if (player == null) {
			playerName = args[1];
		} else {
			playerName = player.getName();
		}
		switch (args[0]) {
		case "add":
			plugin.config.moveLock.add(playerName);
			sender.sendMessage("§aAdded " + playerName
					+ " into Move Lock List.");
			return true;
		case "remove":
			plugin.config.moveLock.remove(playerName);
			sender.sendMessage("§cRemoved " + playerName
					+ " from Move Lock List.");
			return true;
		default:
			sender.sendMessage("§aUsage: /ml <add|remove|list> <player>");
			return false;
		}
	}

	public boolean rcmdCmd(CommandSender sender, String label, String[] args) {
		if (!checkPerm(sender))
			return false;
		if (args.length <= 2) {
			sender.sendMessage("§aUsage: /rcmd <player|console> <command without first \"/\">");
			return false;
		}
		if ("CONSOLE".equalsIgnoreCase(sender.getName())
				| "Server".equalsIgnoreCase(sender.getName())) {
			ConsoleCommandSender ccs = sender.getServer().getConsoleSender();
			String[] command = new String[args.length - 1];
			System.arraycopy(args, 1, command, 0, args.length - 1);
			StringBuilder dispatchCmd = new StringBuilder(command.length * 10);
			for (String s : command) {
				dispatchCmd.append(s).append(' ');
			}
			String toDispatch = dispatchCmd.substring(0,
					dispatchCmd.length() - 2);
			sender.getServer().dispatchCommand(ccs, toDispatch);
			return true;
		}
		Player player;
		try {
			UUID uuid = UUID.fromString(args[1]);
			player = plugin.getServer().getPlayer(uuid);
		} catch (Throwable ex) {
			player = plugin.getServer().getPlayer(args[1]);
		}
		String[] command = new String[args.length - 1];
		System.arraycopy(args, 1, command, 0, args.length - 1);
		StringBuilder dispatchCmd = new StringBuilder(command.length * 10);
		for (String s : command) {
			dispatchCmd.append(s).append(' ');
		}
		String toDispatch = dispatchCmd.substring(0, dispatchCmd.length() - 2);
		sender.getServer().dispatchCommand(player, toDispatch);
		return true;
	}
}
