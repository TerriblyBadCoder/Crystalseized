package net.atired.crystalseized.networking.packets;

import net.atired.crystalseized.entities.custom.blockentity.StrikePointerBlockEntity;
import net.atired.crystalseized.particletypes.DirectedParticleOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import org.joml.Vector3f;

import java.util.function.Supplier;

public class RangeStarerS2Cpacket {
    private final int range;
    private final int x;
    private final int y;
    private final int z;


    public RangeStarerS2Cpacket(int range, int x, int y, int z) {
        this.range = range;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public RangeStarerS2Cpacket(FriendlyByteBuf p_178910_) {
        this.range = p_178910_.readInt();
        this.x = p_178910_.readInt();
        this.y = p_178910_.readInt();
        this.z = p_178910_.readInt();
    }


    public double getRange() {
        return this.range;
    }


    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();

        context.enqueueWork(() -> {
            Player playerSided = context.getSender();
            if (supplier.get().getDirection().getReceptionSide() == LogicalSide.CLIENT) {
                playerSided = Minecraft.getInstance().player;
            }
            if(playerSided!=null)
            {
                BlockEntity block = playerSided.level().getBlockEntity(new BlockPos(this.x,this.y,this.z));
                if(block instanceof StrikePointerBlockEntity strikePointerBlockEntity)
                {
                    strikePointerBlockEntity.range = (int) this.getRange();
                }
            }
        });
        return true;
    }


    public void write(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeInt(this.range);
    }

}
