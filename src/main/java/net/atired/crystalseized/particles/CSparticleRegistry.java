package net.atired.crystalseized.particles;

import com.google.common.base.Function;
import com.mojang.serialization.Codec;
import net.atired.crystalseized.Crystalseized;
import net.atired.crystalseized.particletypes.DirectedParticleType;
import net.atired.crystalseized.particletypes.DirectedSphereParticleOptions;
import net.atired.crystalseized.particletypes.DirectedSphereParticleType;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static org.openjdk.nashorn.internal.objects.NativeFunction.apply;

public class CSparticleRegistry {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Crystalseized.MODID);
    public static final RegistryObject<SimpleParticleType> SHATTER_PARTICLES = PARTICLE_TYPES.register("shatter_particles", () -> new SimpleParticleType(true));
    public static final RegistryObject<ParticleType<BlockParticleOption>> SAND_PARTICLES = PARTICLE_TYPES.register("sand_particles", () -> new ParticleType<BlockParticleOption>(true, BlockParticleOption.DESERIALIZER) {
        @Override
        public Codec<BlockParticleOption> codec() {
            Function<ParticleType<BlockParticleOption>, Codec<BlockParticleOption>> codec = BlockParticleOption::codec;
            return (Codec)codec.apply(this);
        }
    });
    public static final RegistryObject<SimpleParticleType> METEOR_PARTICLES = PARTICLE_TYPES.register("meteor_particles", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> SCORCH_PARTICLES = PARTICLE_TYPES.register("scorch_particles", () -> new SimpleParticleType(true));
    public static final RegistryObject<DirectedParticleType> BOUNCE_PARTICLES = PARTICLE_TYPES.register("bounce_particles", () -> new DirectedParticleType(true));
    public static final RegistryObject<DirectedSphereParticleType> SPHERE_PARTICLES = PARTICLE_TYPES.register("sphere_particles", () -> new DirectedSphereParticleType(true));

    public static void register(IEventBus eventBus){
        PARTICLE_TYPES.register(eventBus);
    }
}