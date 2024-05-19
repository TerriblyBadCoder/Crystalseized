package net.atired.crystalseized.particletypes;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.atired.crystalseized.particles.CSparticleRegistry;
import net.minecraft.core.particles.DustParticleOptionsBase;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class DirectedParticleOptions extends DustParticleOptionsBase {
    public static final Vector3f REDSTONE_PARTICLE_COLOR = Vec3.fromRGB24(16711680).toVector3f();

    public static final Codec<DirectedParticleOptions> CODEC = RecordCodecBuilder.create((p_253370_) -> {
        return p_253370_.group(ExtraCodecs.VECTOR3F.fieldOf("color").forGetter((p_253371_) -> {
            return p_253371_.getColor();
        }), Codec.FLOAT.fieldOf("scale").forGetter((p_175795_) -> {
            return p_175795_.getScale();
        })).apply(p_253370_, DirectedParticleOptions::new);
    });;
    public static final Deserializer<DirectedParticleOptions> DESERIALIZER  = new Deserializer<DirectedParticleOptions>() {
        public DirectedParticleOptions
        fromCommand(ParticleType<DirectedParticleOptions> p_123689_, StringReader p_123690_) throws CommandSyntaxException {
            Vector3f $$2 = DustParticleOptionsBase.readVector3f(p_123690_);
            p_123690_.expect(' ');
            float $$3 = p_123690_.readFloat();
            return new DirectedParticleOptions($$2, $$3);
        }

        public DirectedParticleOptions fromNetwork(ParticleType<DirectedParticleOptions> p_123692_, FriendlyByteBuf p_123693_) {
            return new DirectedParticleOptions(DustParticleOptionsBase.readVector3f(p_123693_), p_123693_.readFloat());
        }
    };;



    public DirectedParticleOptions(Vector3f pColor, float pScale) {
        super(pColor, pScale);
    }

    public ParticleType<DirectedParticleOptions> getType() {
        return CSparticleRegistry.BOUNCE_PARTICLES.get();
    }
}