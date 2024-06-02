package net.atired.crystalseized.events;

import net.atired.crystalseized.Crystalseized;
import net.atired.crystalseized.blocks.CSblockRegistry;
import net.atired.crystalseized.blocks.custom.EndCrystalBlock;
import net.atired.crystalseized.items.CSitemRegistry;
import net.atired.crystalseized.items.DyeablePrismItem;
import net.atired.crystalseized.particles.CSparticleRegistry;
import net.atired.crystalseized.particles.custom.ShatterParticles;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;

@Mod.EventBusSubscriber(modid = Crystalseized.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ColorEvents {
    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event){
        event.getBlockColors().register((state, world, pos, tintIndex) ->
                        world != null && pos != null ? getEndCrystalColor(world, pos) : 0xFFFFFF,
               CSblockRegistry.END_CRYSTAL.get(),CSblockRegistry.METEOR_CATCHER.get());

    }
    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event){

        event.getItemColors().register((stack, tintIndex) -> 0xFF22FF,
                CSblockRegistry.END_CRYSTAL.get(),CSblockRegistry.METEOR_CATCHER.get());
    }


    //Based this off the Rainbow
    // Birch code from biomes of plenty,,, such a based mod,,, sometimes,,,
    public static int getEndCrystalColor(BlockAndTintGetter world, BlockPos pos)
    {

        float colourhue = (((float)pos.getX() +(float)pos.getY()+ Mth.sin(((float)pos.getZ() + (float)pos.getX()) / 35) * 35) % 200) / 200;
        Color colour = Color.getHSBColor(colourhue, 0.5F, 1F);
        return colour.getRGB();
    }
}
