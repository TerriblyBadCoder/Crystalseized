package net.atired.crystalseized.effects;

import net.atired.crystalseized.Crystalseized;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CSeffectRegistry {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Crystalseized.MODID);

    public static final RegistryObject<MobEffect> ACCELERANT = MOB_EFFECTS.register("accelerant",
            () -> new AccelerantEffect(MobEffectCategory.BENEFICIAL,  15579079));
    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
