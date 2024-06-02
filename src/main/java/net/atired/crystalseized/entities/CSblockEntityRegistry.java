package net.atired.crystalseized.entities;

import net.atired.crystalseized.Crystalseized;
import net.atired.crystalseized.blocks.CSblockRegistry;
import net.atired.crystalseized.entities.custom.blockentity.StrikePointerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CSblockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> DEF_REG = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Crystalseized.MODID);
    public static final RegistryObject<BlockEntityType<StrikePointerBlockEntity>> STRIKE_POINTER = DEF_REG.register("strike_pointer", () -> BlockEntityType.Builder.of(StrikePointerBlockEntity::new, CSblockRegistry.STRIKE_POINTER.get()).build(null));
}
