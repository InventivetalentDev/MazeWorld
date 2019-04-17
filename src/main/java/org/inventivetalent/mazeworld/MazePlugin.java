package org.inventivetalent.mazeworld;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class MazePlugin extends JavaPlugin {

	@Override
	public void onEnable() {
	}

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		return new MazeGenerator(id);
	}
}
