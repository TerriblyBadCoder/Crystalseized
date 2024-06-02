package net.atired.crystalseized.particletypes;

import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleType;

public class DirectedSphereParticleType extends ParticleType<DirectedSphereParticleOptions> {

    public DirectedSphereParticleType(boolean pOverrideLimiter) {
        super(pOverrideLimiter, DirectedSphereParticleOptions.DESERIALIZER);
    }

    @Override
    public Codec<DirectedSphereParticleOptions> codec() {
        return DirectedSphereParticleOptions.CODEC;
    }

}