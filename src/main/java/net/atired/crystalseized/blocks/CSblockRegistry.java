package net.atired.crystalseized.blocks;

import net.atired.crystalseized.Crystalseized;
import net.atired.crystalseized.blocks.custom.EndCrystalBlock;
import net.atired.crystalseized.blocks.custom.LunarSandBlock;
import net.atired.crystalseized.blocks.custom.MeteorCatcherBlock;
import net.atired.crystalseized.blocks.custom.StrikePointerBlock;
import net.atired.crystalseized.items.CSitemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class CSblockRegistry {

    public static final DeferredRegister<Block>
            BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Crystalseized.MODID);
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;

    }


    public static final RegistryObject<Block> END_CRYSTAL = registerBlock("end_crystal",
            () -> new EndCrystalBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK).noOcclusion().requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> LUNAR_SAND = registerBlock("lunar_sand",
            () -> new LunarSandBlock(BlockBehaviour.Properties.copy(Blocks.SAND)));
    public static final RegistryObject<Block> METEOR_CATCHER = registerBlock("meteor_catcher",
            () -> new MeteorCatcherBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK).noOcclusion().requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> LUNAR_BLOCK = registerBlock("lunar_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BLACKSTONE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> STRIKE_POINTER = registerBlock("strike_pointer",
            () -> new StrikePointerBlock(BlockBehaviour.Properties.copy(Blocks.BLACKSTONE).requiresCorrectToolForDrops()));

    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block)
    {
        return CSitemRegistry.ITEMS.register(name, () -> new BlockItem(block.get(),new Item.Properties()));
    }
    public static void register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);
    }
}
