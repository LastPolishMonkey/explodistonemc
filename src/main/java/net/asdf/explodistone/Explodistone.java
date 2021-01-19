/* AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa */

package net.asdf.explodistone;

/* Item stuff */
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.BlockItem;

/* Block stuff */
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

/* Etc */
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.GenerationStep;

@SuppressWarnings("deprecation")
public class Explodistone implements ModInitializer
{
	public static final String MOD_ID = "explodistone";

	public static final ExplodiItem EXPLODI_ITEM = new ExplodiItem(new Item.Settings().group(ItemGroup.FOOD));

	public static final Block EXPLODI_ORE = new Block(FabricBlockSettings.of(Material.METAL).hardness(2.0f));
	public static final BlockItem EXPLODI_ORE_ITEM = new BlockItem(EXPLODI_ORE, new Item.Settings().group(ItemGroup.MATERIALS));

	private static ConfiguredFeature<?, ?> EXPLODI_ORE_VEIN = Feature.ORE
			    .configure(new OreFeatureConfig(
			      OreFeatureConfig.Rules.BASE_STONE_OVERWORLD,
			      EXPLODI_ORE.getDefaultState(),
			      8)) // vein size
			    .decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(
			      0, // bottom offset
			      0, // min y level
			      64))) // max y level
			    .spreadHorizontally()
			    .repeat(12); // number of veins per chunk

	@Override
	public void onInitialize()
	{
		System.out.println("explodistone init start");
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "explodi_ore"), EXPLODI_ORE);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "explodi_ore"), EXPLODI_ORE_ITEM);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "explodi_item"), EXPLODI_ITEM);
		System.out.println("explodistone init done");

		RegistryKey<ConfiguredFeature<?, ?>> Explodi_ore_vein = RegistryKey.of(Registry.CONFIGURED_FEATURE_WORLDGEN,
				new Identifier(MOD_ID, "explodi_ore_vein"));
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, Explodi_ore_vein.getValue(), EXPLODI_ORE_VEIN);
		BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, Explodi_ore_vein);
	}
}
