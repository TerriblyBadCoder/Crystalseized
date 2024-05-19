package net.atired.crystalseized;

import com.mojang.logging.LogUtils;
import net.atired.crystalseized.blocks.CSblockRegistry;
import net.atired.crystalseized.events.ColorEvents;
import net.atired.crystalseized.events.PlayerEvents;
import net.atired.crystalseized.items.CSitemRegistry;
import net.atired.crystalseized.networking.ModMessages;
import net.atired.crystalseized.particles.CSparticleRegistry;
import net.atired.crystalseized.worldgen.custom.CSbaseFeatures;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
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
        CSbaseFeatures.register(modEventBus);
        CSparticleRegistry.register(modEventBus);
        modEventBus.addListener(ColorEvents::registerBlockColors);
        modEventBus.addListener(ColorEvents::registerItemColors);
        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new PlayerEvents());
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
            ItemBlockRenderTypes.setRenderLayer(CSblockRegistry.END_CRYSTAL.get(), RenderType.translucent());
        }

    }
}
