
package mod.gottsch.fabric.magic_treasures.core.util;

import mod.gottsch.fabric.gottschcore.spatial.Coords;
import mod.gottsch.fabric.gottschcore.spatial.ICoords;
import mod.gottsch.fabric.magic_treasures.MagicTreasures;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.biome.Biome;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 
 * @author Mark Gottschling Jun 2, 2023
 *
 */
public class ModUtil {
	/*
	MC 1.18.2: net/minecraft/server/MinecraftServer.storageSource
	Name: l => f_129744_ => storageSource
	Side: BOTH
	AT: public net.minecraft.server.MinecraftServer f_129744_ # storageSource
	Type: net/minecraft/world/level/storage/LevelStorageSource$LevelStorageAccess
	 */
	private static final String SAVE_FORMAT_LEVEL_SAVE_SRG_NAME = "f_129744_";
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public static Identifier asLocation(String name) {
		return hasDomain(name) ? Identifier.of(name) : Identifier.of(MagicTreasures.MOD_ID, name);
	}

	public static boolean hasDomain(String name) {
		return name.indexOf(":") >= 0;
	}

	public static Identifier getName(Block block) {
		// don't bother checking optional - if it is empty, then the block isn't registered and this shouldn't run anyway.
        return Registries.BLOCK.getId(block);
	}

	public static Identifier getName(Item item) {
		// don't bother checking optional - if it is empty, then the block isn't registered and this shouldn't run anyway.
        return Registries.ITEM.getId(item);
	}

	public static List<Identifier> getNames(List<Item> items) {
		List<Identifier> ids = new ArrayList<>();
		items.forEach(item -> ids.add(getName(item)));
		return ids;
	}

	public static List<Identifier> getNames(Item... items) {
		List<Identifier> ids = new ArrayList<>();
		Arrays.stream(items).forEach(item -> ids.add(getName(item)));
		return ids;
	}

//	public static Identifier getName(Holder<Biome> biome) {
//		return biome.unwrapKey().get().location();
//	}

	/**
	 * 
	 * @param level
	 * @return
	 */
//	public static Optional<Path> getWorldSaveFolder(ServerWorld level) {
//		Object save = ObfuscationReflectionHelper.getPrivateValue(MinecraftServer.class, level.getServer(), SAVE_FORMAT_LEVEL_SAVE_SRG_NAME);
//		if (save instanceof LevelStorageSource.LevelStorageAccess) {
//			Path path = ((LevelStorageSource.LevelStorageAccess) save).getWorldDir().resolve("datapacks");
//			return Optional.of(path);
//		}
//		return Optional.empty();
//	}
	
	/**
	 *  Get all paths from a folder that inside the JAR file
	 * @param jarPath
	 * @param folder
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public static  List<Path> getPathsFromResourceJAR(Path jarPath, String folder)
			throws URISyntaxException, IOException {

		List<Path> result;

		/*
		 * This block of code would be used if the jar file was unknown and it had to be discovered.
		 * 
        // get path of the current running JAR
        String jarPathOriginal = Treasure.class.getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .toURI()
                .getPath();
        Treasure.LOGGER.debug("JAR Path Original -> {}", jarPathOriginal);

        // file walks JAR
        URI uri = URI.create("jar:file:" + jarPath);
		 */

		try (FileSystem fs = FileSystems.newFileSystem(jarPath, Collections.emptyMap())) {
			result = Files.walk(fs.getPath(folder))
					.filter(Files::isRegularFile)
					.collect(Collectors.toList());
		}
		return result;
	}

	/**
	 * 
	 * @param fileName
	 * @return
	 */
	public static InputStream getFileFromResourceAsStream(String fileName) {

		// The class loader that loaded the class
		ClassLoader classLoader = MagicTreasures.class.getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream(fileName);

		// the stream holding the file content
		if (inputStream == null) {
			throw new IllegalArgumentException("file not found! " + fileName);
		} else {
			return inputStream;
		}
	}

	/**
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static List<Path> getPathsFromFlatDatapacks(Path path) throws IOException {
		List<Path> result;
		try (Stream<Path> walk = Files.walk(path)) {
			result = walk.filter(Files::isRegularFile)
					.collect(Collectors.toList());
		}
		return result;
	}

	/**
	 * 
	 * @author Mark Gottschling on Jul 25, 2021
	 *
	 */
	public static class SpawnEntityHelper {

		/**
		 *
		 * @param level
		 * @param random
		 * @param entityType
		 * @param mob
		 * @param coords
		 * @return
		 */
		public static Entity spawn(ServerWorld level, Random random, EntityType<?> entityType, Entity mob, ICoords coords) {
			for (int i = 0; i < 20; i++) { // 20 tries
				int spawnX = coords.getX() + MathHelper.nextInt(random, 1, 2) * MathHelper.nextInt(random, -1, 1);
				int spawnY = coords.getY() + MathHelper.nextInt(random, 1, 2) * MathHelper.nextInt(random, -1, 1);
				int spawnZ = coords.getZ() + MathHelper.nextInt(random, 1, 2) * MathHelper.nextInt(random, -1, 1);

				ICoords spawnCoords = new Coords(spawnX, spawnY, spawnZ);

				boolean isSpawned = false;
				if (level.isClient()) {
					if(SpawnRestriction.canSpawn(entityType, level, SpawnReason.SPAWNER, spawnCoords.toPos(), level.getRandom())) {
						if (mob != null) {
							mob.setPos(spawnX, spawnY, spawnZ);
							// add entity into the level
							level.spawnEntity(mob);
							isSpawned = true;
						}
					}
					if (isSpawned) {
						break;
					}
				}
			}
			return mob;
		}
	}
}
