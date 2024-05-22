package net.atired.crystalseized.networking.packets;

import net.atired.crystalseized.networking.ModMessages;
import net.atired.crystalseized.particles.CSparticleRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import org.joml.Vector3f;

import java.util.function.Supplier;

public class PantsC2Spacket {
    public PantsC2Spacket(){

    }
    public PantsC2Spacket(FriendlyByteBuf buf){

    }
    public void toBytes(FriendlyByteBuf buf){

    }
    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if(player!=null)
            {
                ItemStack stack = player.getItemBySlot(EquipmentSlot.LEGS);
                stack.hurtAndBreak(3, player, (e) -> {
                    e.broadcastBreakEvent(EquipmentSlot.LEGS);
                });
                CompoundTag compound = new CompoundTag();

                compound.putBoolean("crystalseized:prismpants_onground",false);
                stack.addTagElement("crystalseized:prismpants_onground",compound.get("crystalseized:prismpants_onground"));
                compound.putBoolean("crystalseized:prismpants_held",false);
                stack.addTagElement("crystalseized:prismpants_held",compound.get("crystalseized:prismpants_held"));
                compound.putInt("crystalseized:prismpants_cd",8);
                stack.addTagElement("crystalseized:prismpants_cd",compound.get("crystalseized:prismpants_cd"));
                if(player.level() instanceof ServerLevel level)
                {

                    int i = 0;
                    level.sendParticles(CSparticleRegistry.SHATTER_PARTICLES.get(),player.getX(),player.getY(),player.getZ(),8,0.2,0.2,0.2,0.9);
                    level.playSound(null,new BlockPos((int) player.getX(), (int) player.getY(), (int) player.getZ()), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS,5,1);
                    for(int j = 0; j < level.players().size(); ++j) {
                        ServerPlayer serverplayer = (ServerPlayer)level.players().get(j);
                        ModMessages.sendToPlayer(new DirectedParticlesS2Cpacket(ParticleTypes.FLASH, false, player.getX(),player.getY(),player.getZ(), serverplayer.getViewVector(1).toVector3f(), 2.5f),serverplayer);
                        ModMessages.sendToPlayer(new DirectedParticlesS2Cpacket(ParticleTypes.FLASH, false, player.getX(),player.getY(),player.getZ(), serverplayer.getViewVector(1).toVector3f(), 1.5f),serverplayer);
                    }
                }
            }


        });
        return true;
    }

}
