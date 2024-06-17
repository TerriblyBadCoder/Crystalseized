package net.atired.crystalseized.items;

import net.atired.crystalseized.effects.CSeffectRegistry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class CSfoodRegistry {
    public static final FoodProperties OZONE_BOTTLE = new FoodProperties.Builder().alwaysEat().nutrition(2).saturationMod(0.2f)
            .effect(() -> new MobEffectInstance(CSeffectRegistry.ACCELERANT.get(), 450), 1f).build();
}
