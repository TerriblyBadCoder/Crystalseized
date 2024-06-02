package net.atired.crystalseized.networking.packets;

import net.atired.crystalseized.particletypes.DirectedParticleOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import org.joml.Vector3f;

import java.util.function.Supplier;

public class DirectedParticlesS2Cpacket  {
    private final double x;
    private final double y;
    private final double z;
    private final Vector3f direction;
    private final float scale;
    private final boolean overrideLimiter;
    private final ParticleOptions particle;

    public DirectedParticlesS2Cpacket(ParticleOptions p_132292_, boolean p_132293_, double p_132294_, double p_132295_, double p_132296_,  Vector3f vector3f, float p_132301_) {
        this.particle = p_132292_;
        this.overrideLimiter = p_132293_;
        this.x = p_132294_;
        this.y = p_132295_;
        this.z = p_132296_;

        this.direction = vector3f;
        this.scale = p_132301_;
    }

    public DirectedParticlesS2Cpacket(FriendlyByteBuf p_178910_) {
        ParticleType<?> $$1 = (ParticleType)p_178910_.readById(BuiltInRegistries.PARTICLE_TYPE);
        this.overrideLimiter = p_178910_.readBoolean();
        this.x = p_178910_.readDouble();
        this.y = p_178910_.readDouble();
        this.z = p_178910_.readDouble();
        this.direction = p_178910_.readVector3f();
        this.scale = p_178910_.readFloat();
        this.particle = this.readParticle(p_178910_, $$1);
    }



    private <T extends ParticleOptions> T readParticle(FriendlyByteBuf p_132305_, ParticleType<T> p_132306_) {
        return p_132306_.getDeserializer().fromNetwork(p_132306_, p_132305_);
    }



    public boolean isOverrideLimiter() {
        return this.overrideLimiter;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }


    public Vector3f getDirection() {
        return this.direction;
    }

    public float getScale() {
        return this.scale;
    }

    public ParticleOptions getParticle() {
        return this.particle;
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();

        context.enqueueWork(() -> {
            Vec3 view = new Vec3(getDirection()).normalize();
            Player playerSided = context.getSender();
            if (supplier.get().getDirection().getReceptionSide() == LogicalSide.CLIENT) {
                playerSided = Minecraft.getInstance().player;
            }
            if(playerSided!=null)
            {
                if(!this.isOverrideLimiter())
                {
                    playerSided.level().addParticle(new DirectedParticleOptions(getDirection(),getScale()),getX(),getY(),getZ(),view.x*-0.5*(1/getScale()),view.y*-0.5*(1/getScale()),view.z*-0.5*(1/getScale()));
                }
                else
                {
                    playerSided.level().addParticle(new DirectedParticleOptions(getDirection(),getScale()),getX(),getY(),getZ(),0,0,0);
                }
            }

        });
        return true;
    }


    public void write(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeId(BuiltInRegistries.PARTICLE_TYPE, this.particle.getType());
        friendlyByteBuf.writeBoolean(this.overrideLimiter);
        friendlyByteBuf.writeDouble(this.x);
        friendlyByteBuf.writeDouble(this.y);
        friendlyByteBuf.writeDouble(this.z);
        friendlyByteBuf.writeVector3f(this.direction);
        friendlyByteBuf.writeFloat(this.scale);
        this.particle.writeToNetwork(friendlyByteBuf);
    }

}
