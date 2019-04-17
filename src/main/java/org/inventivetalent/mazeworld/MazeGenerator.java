package org.inventivetalent.mazeworld;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

public class MazeGenerator extends ChunkGenerator {

	double wallThreshold = 0.1;
	int    wallHeight    = 4;

	Material floorMaterial = Material.STONE;
	Material wallMaterial  = Material.STONE;

	public MazeGenerator(String id) {
		if (id != null && id.length() > 0) {
			String[] split = id.split(",");

			if (split.length > 0) {
				Material matched = Material.matchMaterial(split[0]);
				if (matched == null) {
					System.err.println("Unknown material: " + split[0]);
				} else {
					wallMaterial = matched;
				}
			}
			if (split.length > 1) {
				Material matched = Material.matchMaterial(split[1]);
				if (matched == null) {
					System.err.println("Unknown material: " + split[1]);
				} else {
					floorMaterial = matched;
				}
			}

			try {
				if (split.length > 2) {
					wallHeight = Integer.parseInt(split[2]);
				}
				if (split.length > 3) {
					wallThreshold = Integer.parseInt(split[3]);
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome) {
		ChunkData chunkData = Bukkit.createChunkData(world);
		chunkData.setRegion(0, 0, 0, 16, 0, 16, Material.BEDROCK);
		chunkData.setRegion(0, 1, 0, 16, 3, 16, Material.STONE);
		chunkData.setRegion(0, 4, 0, 16, 8, 16, floorMaterial);

		for (int x = 0; x < 16; x += 2) {
			for (int z = 0; z < 16; z += 2) {
				if (random.nextDouble() > wallThreshold) {
					makeWall(chunkData, x, z);

					int a = random.nextBoolean() ? 0 : (random.nextBoolean() ? -1 : 1);
					int b = a != 0 ? 0 : (random.nextBoolean() ? -1 : 1);
					makeWall(chunkData, x + a, z + b);
				}
			}
		}

		return chunkData;
	}

	void makeWall(ChunkData chunkData, int x, int z) {
		for (int y = 8; y < 8 + wallHeight; y++) {
			chunkData.setBlock(x, y, z, wallMaterial);
		}
	}

}

