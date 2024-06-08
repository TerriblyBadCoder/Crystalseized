package net.atired.crystalseized.entities;

import net.atired.crystalseized.Crystalseized;
import net.atired.crystalseized.entities.custom.StarFallEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Crystalseized.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CSentityRegistry {
    public static final DeferredRegister<EntityType<?>> DEF_REG
            = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Crystalseized.MODID);
    public static final RegistryObject<EntityType<StarFallEntity>> STARFALL = DEF_REG.register("starfall", () -> (EntityType) EntityType.Builder.of(StarFallEntity::new, MobCategory.MISC).sized(0.4F, 0.4F).noSave().build("starfall"));

}
