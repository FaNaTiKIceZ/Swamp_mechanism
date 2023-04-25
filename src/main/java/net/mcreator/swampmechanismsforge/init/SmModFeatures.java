
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.swampmechanismsforge.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Holder;

import net.mcreator.swampmechanismsforge.world.features.BunchofrocksFeature;
import net.mcreator.swampmechanismsforge.world.features.BerriesFeature;
import net.mcreator.swampmechanismsforge.SmMod;

import java.util.function.Supplier;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

@Mod.EventBusSubscriber
public class SmModFeatures {
	public static final DeferredRegister<Feature<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.FEATURES, SmMod.MODID);
	private static final List<FeatureRegistration> FEATURE_REGISTRATIONS = new ArrayList<>();
	public static final RegistryObject<Feature<?>> BUNCHOFROCKS = register("bunchofrocks", BunchofrocksFeature::feature,
			new FeatureRegistration(GenerationStep.Decoration.LOCAL_MODIFICATIONS, BunchofrocksFeature.GENERATE_BIOMES, BunchofrocksFeature::placedFeature));
	public static final RegistryObject<Feature<?>> BERRIES = register("berries", BerriesFeature::feature, new FeatureRegistration(GenerationStep.Decoration.SURFACE_STRUCTURES, BerriesFeature.GENERATE_BIOMES, BerriesFeature::placedFeature));

	private static RegistryObject<Feature<?>> register(String registryname, Supplier<Feature<?>> feature, FeatureRegistration featureRegistration) {
		FEATURE_REGISTRATIONS.add(featureRegistration);
		return REGISTRY.register(registryname, feature);
	}

	@SubscribeEvent
	public static void addFeaturesToBiomes(BiomeLoadingEvent event) {
		for (FeatureRegistration registration : FEATURE_REGISTRATIONS) {
			if (registration.biomes() == null || registration.biomes().contains(event.getName()))
				event.getGeneration().getFeatures(registration.stage()).add(registration.placedFeature().get());
		}
	}

	private static record FeatureRegistration(GenerationStep.Decoration stage, Set<ResourceLocation> biomes, Supplier<Holder<PlacedFeature>> placedFeature) {
	}
}
