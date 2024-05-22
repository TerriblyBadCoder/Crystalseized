package net.atired.crystalseized.events;

import net.atired.crystalseized.blocks.custom.EndCrystalBlock;
import net.atired.crystalseized.items.CSitemRegistry;
import net.atired.crystalseized.items.custom.PrismaticPantsItem;
import net.atired.crystalseized.networking.ModMessages;
import net.atired.crystalseized.networking.packets.PantsC2Spacket;
import net.atired.crystalseized.particles.CSparticleRegistry;
import net.atired.crystalseized.particles.custom.BounceParticles;
import net.atired.crystalseized.particles.custom.BounceParticlesBase;
import net.atired.crystalseized.particletypes.DirectedParticleOptions;
import net.minecraft.advancements.critereon.LightningStrikeTrigger;
import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.joml.Vector3f;

import java.util.Iterator;
import java.util.Random;

public class PlayerEvents {
    @SubscribeEvent
    public void sneak (MovementInputUpdateEvent event)
    {

        if(event.getEntity().getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof PrismaticPantsItem && !event.getEntity().onGround())
        {
            if(event.getInput().jumping)
            {
                Player player = event.getEntity();

                ItemStack stack = event.getEntity().getItemBySlot(EquipmentSlot.LEGS);
                boolean loadedjump = ((CompoundTag)stack.serializeNBT().get("tag")).getBoolean("crystalseized:prismpants_held");
                boolean wasonground = ((CompoundTag)stack.serializeNBT().get("tag")).getBoolean("crystalseized:prismpants_onground");
                if(loadedjump && wasonground)
                {
                    Vec3 view = player.getViewVector(1);
                    CompoundTag compound = new CompoundTag();

                    compound.putBoolean("crystalseized:prismpants_onground",false);
                    stack.addTagElement("crystalseized:prismpants_onground",compound.get("crystalseized:prismpants_onground"));
                    compound.putBoolean("crystalseized:prismpants_held",false);
                    stack.addTagElement("crystalseized:prismpants_held",compound.get("crystalseized:prismpants_held"));
                    CompoundTag compound2 = new CompoundTag();
                    compound.putInt("crystalseized:prismpants_cd",8);
                    stack.addTagElement("crystalseized:prismpants_cd",compound.get("crystalseized:prismpants_cd"));

                    ModMessages.sendToServer(new PantsC2Spacket());
                    event.getEntity().setDeltaMovement(event.getEntity().getViewVector(0).scale(event.getEntity().getDeltaMovement().length()+0.2));
                }
            }
            else
            {
                CompoundTag compound = new CompoundTag();
                ItemStack stack = event.getEntity().getItemBySlot(EquipmentSlot.LEGS);
                compound.putBoolean("crystalseized:prismpants_held",true);
                stack.addTagElement("crystalseized:prismpants_held",compound.get("crystalseized:prismpants_held"));


            }
        }

    }

    @SubscribeEvent
    public void entityTick(LivingEvent.LivingTickEvent event)
    {

        Random random = new Random();
        LivingEntity livingEntity = event.getEntity();
        if(livingEntity instanceof Player player && ForgeEventFactory.getMobGriefingEvent(livingEntity.level(), livingEntity) && livingEntity.getDeltaMovement().length() > 0.5)
        {

            Vec3 movement = livingEntity.getDeltaMovement();
            AABB aabb = player.getBoundingBox().inflate(0.25
            ).move(movement);
            boolean flag = false;
            Iterator var8 = BlockPos.betweenClosed(Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ)).iterator();
            label:
            while(true) {
                BlockPos blockpos;
                Block block;
                do {
                    if (!var8.hasNext()) {
                        break label;
                    }
                    blockpos = (BlockPos)var8.next();
                    BlockState blockstate = livingEntity.level().getBlockState(blockpos);
                    block = blockstate.getBlock();
                } while(!(block instanceof EndCrystalBlock));
                livingEntity.hurt(livingEntity.level().damageSources().flyIntoWall(),4F*(float)livingEntity.getDeltaMovement().length());
                if(livingEntity.getItemBySlot(EquipmentSlot.LEGS).getItem() == Items.CHAINMAIL_LEGGINGS )
                {

                    ItemStack prismPants = new ItemStack(CSitemRegistry.PRISMATIC_LEGGINGS.get(),1,livingEntity.getItemBySlot(EquipmentSlot.LEGS).getTag());
                    livingEntity.setItemSlot(EquipmentSlot.LEGS,prismPants);
                }
                if(livingEntity.level() instanceof ServerLevel serverLevel)
                {
                    serverLevel.sendParticles(CSparticleRegistry.SHATTER_PARTICLES.get(),blockpos.getX()+random.nextFloat()-0.5F,blockpos.getY()+random.nextFloat()-0.5F,blockpos.getZ()+random.nextFloat()-0.5F,3,0,0,0,1.5);
                    flag = livingEntity.level().destroyBlock(blockpos, true, livingEntity) || flag;
                }

            }

        }
    }

}
