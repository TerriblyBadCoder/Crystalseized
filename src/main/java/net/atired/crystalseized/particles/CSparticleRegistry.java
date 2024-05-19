package net.atired.crystalseized.particles;

import net.atired.crystalseized.Crystalseized;
import net.atired.crystalseized.particletypes.DirectedParticleType;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CSparticleRegistry {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Crystalseized.MODID);
    public static final RegistryObject<SimpleParticleType> SHATTER_PARTICLES = PARTICLE_TYPES.register("shatter_particles", () -> new SimpleParticleType(true));
    public static final RegistryObject<DirectedParticleType> BOUNCE_PARTICLES = PARTICLE_TYPES.register("bounce_particles", () -> new DirectedParticleType(true));

    public static void register(IEventBus eventBus){
        PARTICLE_TYPES.register(eventBus);
    }
}