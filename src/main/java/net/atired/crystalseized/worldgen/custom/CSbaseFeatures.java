package net.atired.crystalseized.worldgen.custom;

import net.atired.crystalseized.Crystalseized;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

public class CSbaseFeatures {
    public static final DeferredRegister<Feature<?>> FEATURE_REGISTER = DeferredRegister.create(Registries.FEATURE, Crystalseized.MODID);
    public static final Feature<NoneFeatureConfiguration> ENDPRISMPILLAR = register("end_prism", new EndPrismPillarFeature(NoneFeatureConfiguration.CODEC));
    private static <C extends FeatureConfiguration, F extends Feature<C>> F register(String key, F value)
    {
        FEATURE_REGISTER.register(key, () -> value);
        return value;
    }
    public static void register(IEventBus eventBus)
    {
        FEATURE_REGISTER.register(eventBus);
    }

    public static void setup() {}
}
