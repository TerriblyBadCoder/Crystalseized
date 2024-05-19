package net.atired.crystalseized.particletypes;

import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleType;

public class DirectedParticleType extends ParticleType<DirectedParticleOptions> {

    public DirectedParticleType(boolean pOverrideLimiter) {
        super(pOverrideLimiter, DirectedParticleOptions.DESERIALIZER);
    }

    @Override
    public Codec<DirectedParticleOptions> codec() {
        return DirectedParticleOptions.CODEC;
    }

}