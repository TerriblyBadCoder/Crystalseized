package net.atired.crystalseized.events;

import net.atired.crystalseized.Crystalseized;
import net.atired.crystalseized.particles.CSparticleRegistry;
import net.atired.crystalseized.particles.custom.*;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Crystalseized.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBus {
    @SubscribeEvent
    public static void registerParticleFactories(final RegisterParticleProvidersEvent event){
        event.registerSpriteSet(CSparticleRegistry.SHATTER_PARTICLES.get(), ShatterParticles.Provider::new);
        event.registerSpriteSet(CSparticleRegistry.METEOR_PARTICLES.get(), MeteorParticles.Provider::new);
        event.registerSpriteSet(CSparticleRegistry.BOUNCE_PARTICLES.get(), BounceParticles.Provider::new);
        event.registerSpriteSet(CSparticleRegistry.SPHERE_PARTICLES.get(), SphereBlockParticles.Provider::new);
        event.registerSpriteSet(CSparticleRegistry.SCORCH_PARTICLES.get(), ScorchParticles.Provider::new);
    }
}
