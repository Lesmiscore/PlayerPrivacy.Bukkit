package com.nao20010128nao;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

/**
 * Ported from: {@link http://github.com/nao20010128nao/PlayerPrivacy}
 */
public class PlayerPrivacy extends JavaPlugin {
	public PPConfig config = new PPConfig();
	Commands cmd = new Commands(this);

	public PlayerPrivacy() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public PlayerPrivacy(JavaPluginLoader loader,
			PluginDescriptionFile description, File dataFolder, File file) {
		super(loader, description, dataFolder, file);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void onEnable() {
		// TODO 自動生成されたメソッド・スタブ
		getDataFolder().mkdir();
		config.load(new File(getDataFolder(), "config.gz"));
	}

	@Override
	public void onDisable() {
		// TODO 自動生成されたメソッド・スタブ
		config.save(new File(getDataFolder(), "config.gz"));
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		if ("cb".equalsIgnoreCase(label)) {
			return cmd.cbCmd(sender, label, args);
		}
		if ("cc".equalsIgnoreCase(label)) {
			return cmd.ccCmd(sender, label, args);
		}
		if ("dc".equalsIgnoreCase(label)) {
			return cmd.dcCmd(sender, label, args);
		}
		if ("ml".equalsIgnoreCase(label)) {
			return cmd.mlCmd(sender, label, args);
		}
		if ("rcmd".equalsIgnoreCase(label)) {
			return cmd.rcmdCmd(sender, label, args);
		}
		return false;
	}

	class PPConfig {
		public HashSet<String> chatBlock = new ComparerHS();
		public HashMap<String, ComparerHS> commandCapture = new HashMap<>();
		public HashSet<String> denyCommand = new ComparerHS();
		public HashSet<String> moveLock = new ComparerHS();

		public void save(File file) {
			DataOutputStream dos = null;
			try {
				dos = new DataOutputStream(new GZIPOutputStream(
						new FileOutputStream(file)));
				dos.writeInt(chatBlock.size());
				for (String name : chatBlock) {
					dos.writeUTF(name);
				}
				dos.writeInt(commandCapture.size());
				for (String name : commandCapture.keySet()) {
					dos.writeUTF(name);
					dos.writeInt(commandCapture.get(name).size());
					for (String player : commandCapture.get(name)) {
						dos.writeUTF(player);
					}
				}
				dos.writeInt(denyCommand.size());
				for (String name : denyCommand) {
					dos.writeUTF(name);
				}
				dos.writeInt(moveLock.size());
				for (String name : moveLock) {
					dos.writeUTF(name);
				}
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} finally {
				try {
					dos.close();
				} catch (IOException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			}
		}

		public void load(File file) {
			if (!file.exists())
				return;
			DataInputStream dis = null;
			try {
				dis = new DataInputStream(new GZIPInputStream(
						new FileInputStream(file)));
				int cbSize = dis.readInt();
				for (int i = 0; i < cbSize; i++) {
					chatBlock.add(dis.readUTF());
				}
				int ccSize = dis.readInt();
				for (int i = 0; i < ccSize; i++) {
					String key = dis.readUTF();
					int cckSize = dis.readInt();
					if (!commandCapture.containsKey(key)) {
						commandCapture.put(key, new ComparerHS());
					}
					for (int j = 0; j < cckSize; j++) {
						commandCapture.get(key).add(dis.readUTF());
					}
				}
				int dcSize = dis.readInt();
				for (int i = 0; i < dcSize; i++) {
					denyCommand.add(dis.readUTF());
				}
				int mlSize = dis.readInt();
				for (int i = 0; i < mlSize; i++) {
					moveLock.add(dis.readUTF());
				}
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} finally {
				try {
					dis.close();
				} catch (IOException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			}
		}

		public class ComparerHS extends HashSet<String> {
			@Override
			public boolean add(String e) {
				// TODO 自動生成されたメソッド・スタブ
				return super.add(e.toLowerCase());
			}

			@Override
			public boolean contains(Object o) {
				// TODO 自動生成されたメソッド・スタブ
				if (!(o instanceof String)) {
					return false;
				}
				String str = (String) o;
				return super.contains(str.toLowerCase());
			}
		}
	}
}
