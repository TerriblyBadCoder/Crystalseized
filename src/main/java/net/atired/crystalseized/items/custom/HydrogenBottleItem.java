package net.atired.crystalseized.items.custom;

import net.atired.crystalseized.blocks.CSblockRegistry;
import net.atired.crystalseized.particles.CSparticleRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoneyBottleItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;

public class HydrogenBottleItem  extends Item {

    public HydrogenBottleItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void onDestroyed(ItemEntity itemEntity, DamageSource damageSource) {
        super.onDestroyed(itemEntity, damageSource);
        if(itemEntity.level() instanceof ServerLevel serverLevel)
        {
            Vec3 pos = itemEntity.position();
            serverLevel.sendParticles(CSparticleRegistry.BURNING_PARTICLES.get(),pos.x,pos.y,pos.z,(int)Math.pow(itemEntity.getItem().getCount(),0.8f),0.3,0.3,0.3,Math.pow(itemEntity.getItem().getCount(),0.5f)/8);
            itemEntity.discard();
            serverLevel.explode(null,serverLevel.damageSources().explosion(null,null), new ExplosionDamageCalculator(),pos.x,pos.y,pos.z,0.75f*(float)Math.pow(itemEntity.getItem().getCount(),0.6f),true, Level.ExplosionInteraction.TNT);
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        BlockHitResult blockhitresult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        InteractionResultHolder<ItemStack> ret = ForgeEventFactory.onBucketUse(player, level, itemstack, blockhitresult);
        if (ret != null) {
            return ret;
        } else if (blockhitresult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(itemstack);
        } else if (blockhitresult.getType() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.pass(itemstack);
        } else {
            BlockPos blockpos = blockhitresult.getBlockPos();
            Direction direction = blockhitresult.getDirection();
            BlockPos blockpos1 = blockpos.relative(direction);
            if (level.mayInteract(player, blockpos) && player.mayUseItemAt(blockpos1, direction, itemstack) && level.getBlockState(blockpos1).getBlock() instanceof AirBlock) {
                BlockState blockstate1;
                if ( !(player.getAbilities().instabuild)) {
                    itemstack.setCount(itemstack.getCount()-1);
                    ItemStack $$4 = new ItemStack(Items.GLASS_BOTTLE);
                    if (!player.getInventory().add($$4)) {
                        player.drop($$4, false);
                    }
                }
                player.swing(hand);
                level.setBlock(blockpos1, CSblockRegistry.HYDROGEN_BLOCK.get().defaultBlockState(), 3);
            }
        }
        return super.use(level, player, hand);
    }
}
