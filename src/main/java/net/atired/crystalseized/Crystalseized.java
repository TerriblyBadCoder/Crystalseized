package net.atired.crystalseized;

import com.mojang.logging.LogUtils;
import net.atired.crystalseized.blocks.CSblockRegistry;
import net.atired.crystalseized.entities.CSblockEntityRegistry;
import net.atired.crystalseized.entities.CSentityRegistry;
import net.atired.crystalseized.events.ClientEvents;
import net.atired.crystalseized.events.ColorEvents;
import net.atired.crystalseized.events.PlayerEvents;
import net.atired.crystalseized.items.CSitemRegistry;
import net.atired.crystalseized.networking.ModMessages;
import net.atired.crystalseized.particles.CSparticleRegistry;
import net.atired.crystalseized.renderer.blockentity.StrikePointerRenderer;
import net.atired.crystalseized.renderer.entity.StarFallRenderer;
import net.atired.crystalseized.worldgen.custom.CSbaseFeatures;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.chunk.RenderChunkRegion;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Crystalseized.MODID)
public class Crystalseized {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "crystalseized";

    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public Crystalseized() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        CSitemRegistry.register(modEventBus);
        CSblockRegistry.register(modEventBus);
        CSentityRegistry.DEF_REG.register(modEventBus);
        CSblockEntityRegistry.DEF_REG.register(modEventBus);
        CSbaseFeatures.register(modEventBus);
        CSparticleRegistry.register(modEventBus);
        modEventBus.addListener(ColorEvents::registerBlockColors);
        modEventBus.addListener(ColorEvents::registerItemColors);
        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new ClientModEvents());
        MinecraftForge.EVENT_BUS.register(new PlayerEvents());
        MinecraftForge.EVENT_BUS.register(new ClientEvents());
        modEventBus.addListener(this::addCreative);

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        ModMessages.register();

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS)
        {
            event.accept(CSblockRegistry.END_CRYSTAL.get());
            event.accept(CSblockRegistry.LUNAR_BLOCK.get());
            event.accept(CSblockRegistry.LUNAR_SAND.get());
        }
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS)
        {
            event.accept(CSitemRegistry.LUNAR_SHARD.get());
        }
        if(event.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS)
        {
            event.accept(CSblockRegistry.STRIKE_POINTER);
        }
        if(event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS)
        {
            event.accept(CSblockRegistry.METEOR_CATCHER);
        }
        if (event.getTabKey() == CreativeModeTabs.COMBAT)
        {
            event.accept(CSitemRegistry.PRISMATIC_LEGGINGS.get());
        }

    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            EntityRenderers.register(CSentityRegistry.STARFALL.get(), (context) -> {
                        return new StarFallRenderer(context,1);
                    }
            );
            BlockEntityRenderers.register(CSblockEntityRegistry.STRIKE_POINTER.get(), StrikePointerRenderer::new);
        }

    }
}
