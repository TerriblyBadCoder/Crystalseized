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

public class DirectedSphereParticleOptions extends DustParticleOptionsBase {
    public static final Vector3f REDSTONE_PARTICLE_COLOR = Vec3.fromRGB24(16711680).toVector3f();

    public static final Codec<DirectedSphereParticleOptions> CODEC = RecordCodecBuilder.create((p_253370_) -> {
        return p_253370_.group(ExtraCodecs.VECTOR3F.fieldOf("color").forGetter((p_253371_) -> {
            return p_253371_.getColor();
        }), Codec.FLOAT.fieldOf("scale").forGetter((p_175795_) -> {
            return p_175795_.getScale();
        })).apply(p_253370_, DirectedSphereParticleOptions::new);
    });;
    public static final Deserializer<DirectedSphereParticleOptions> DESERIALIZER  = new Deserializer<DirectedSphereParticleOptions>() {
        public DirectedSphereParticleOptions
        fromCommand(ParticleType<DirectedSphereParticleOptions> p_123689_, StringReader p_123690_) throws CommandSyntaxException {
            Vector3f $$2 = DustParticleOptionsBase.readVector3f(p_123690_);
            p_123690_.expect(' ');
            float $$3 = p_123690_.readFloat();
            return new DirectedSphereParticleOptions($$2, $$3);
        }

        public DirectedSphereParticleOptions fromNetwork(ParticleType<DirectedSphereParticleOptions> p_123692_, FriendlyByteBuf p_123693_) {
            return new DirectedSphereParticleOptions(DustParticleOptionsBase.readVector3f(p_123693_), p_123693_.readFloat());
        }
    };;



    public DirectedSphereParticleOptions(Vector3f pColor, float pScale) {
        super(pColor, pScale);
    }

    public ParticleType<DirectedSphereParticleOptions> getType() {
        return CSparticleRegistry.SPHERE_PARTICLES.get();
    }
}