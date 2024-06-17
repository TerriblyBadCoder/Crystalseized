package net.atired.crystalseized.items.custom;

import net.atired.crystalseized.blocks.CSblockRegistry;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.ForgeEventFactory;

public class OzoneBottleItem extends Item {

    public OzoneBottleItem(Properties p_41383_) {
        super(p_41383_);
    }
    public ItemStack finishUsingItem(ItemStack p_41348_, Level p_41349_, LivingEntity p_41350_) {
        super.finishUsingItem(p_41348_, p_41349_, p_41350_);
        if (p_41350_ instanceof ServerPlayer $$3) {
            CriteriaTriggers.CONSUME_ITEM.trigger($$3, p_41348_);
            $$3.awardStat(Stats.ITEM_USED.get(this));
        }
        if (!p_41349_.isClientSide) {
            p_41350_.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
        }
        if (p_41348_.isEmpty()) {
            return new ItemStack(Items.GLASS_BOTTLE);
        } else {
            if (p_41350_ instanceof Player && !((Player)p_41350_).getAbilities().instabuild) {
                ItemStack $$4 = new ItemStack(Items.GLASS_BOTTLE);
                Player $$5 = (Player)p_41350_;
                if (!$$5.getInventory().add($$4)) {
                    $$5.drop($$4, false);
                }
            }

            return p_41348_;
        }
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        BlockHitResult blockhitresult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        InteractionResultHolder<ItemStack> ret = ForgeEventFactory.onBucketUse(player, level, itemstack, blockhitresult);
        if (ret != null) {
            return ItemUtils.startUsingInstantly(level,player,hand);
        } else if (blockhitresult.getType() == HitResult.Type.MISS) {
            return ItemUtils.startUsingInstantly(level,player,hand);
        } else if (blockhitresult.getType() != HitResult.Type.BLOCK) {
            return ItemUtils.startUsingInstantly(level,player,hand);
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
                level.setBlock(blockpos1, CSblockRegistry.OZONE_BLOCK.get().defaultBlockState(), 3);
                return InteractionResultHolder.success(itemstack);
            }
        }
        return ItemUtils.startUsingInstantly(level,player,hand);
    }

    public int getUseDuration(ItemStack p_41360_) {
        return 50;
    }

    public UseAnim getUseAnimation(ItemStack p_41358_) {
        return UseAnim.DRINK;
    }

    public SoundEvent getDrinkingSound() {
        return SoundEvents.HONEY_DRINK;
    }

    public SoundEvent getEatingSound() {
        return SoundEvents.HONEY_DRINK;
    }
}
