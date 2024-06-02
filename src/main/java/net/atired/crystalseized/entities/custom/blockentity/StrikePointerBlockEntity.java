package net.atired.crystalseized.entities.custom.blockentity;

import net.atired.crystalseized.blocks.custom.StrikePointerBlock;
import net.atired.crystalseized.entities.CSblockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.function.BooleanSupplier;

public class StrikePointerBlockEntity extends BlockEntity {

    public float yrot;
    public float prot;
    public float trans;
    public float pos;
    public Vec3 actualpos;
    public int age;
    public StrikePointerBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(CSblockEntityRegistry.STRIKE_POINTER.get(),p_155229_, p_155230_);
    }
    public static void tick(Level level, BlockPos blockPos, BlockState state, StrikePointerBlockEntity entity)
    {
        entity.age +=1;
        LivingEntity $$4 = level.getNearestEntity(LivingEntity.class, TargetingConditions.DEFAULT,null,(double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5, new AABB(blockPos).inflate(10));
        StrikePointerBlock strikePointerBlock = (StrikePointerBlock) state.getBlock();

        if ($$4 != null) {
            Vec3 pos = $$4.getPosition(1).add(0,1.2,0).subtract(blockPos.getCenter()).multiply(1,1,1);
            Vec3 otherPos = pos.normalize().scale(0.5);
            BlockHitResult hitResult = level.clip(new ClipContext(blockPos.getCenter().add(otherPos),$$4.getPosition(0).add(0,1.2,0), net.minecraft.world.level.ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, null));
            if(hitResult.getLocation().subtract($$4.getPosition(0).add(0,1.2,0)).length()<0.2 && ($$4.getPosition(0).subtract(blockPos.getCenter())).length()<8)
            {
                entity.yrot = (float)Math.atan2(pos.x,pos.z);
                entity.prot = Mth.clamp((float)Math.asin(-pos.normalize().y),-3.14f,3.14f);
                entity.trans = Mth.clamp(entity.trans+0.12f,0.2f,1);
                entity.pos = (float) $$4.getPosition(0).subtract(blockPos.getCenter()).length();
                entity.actualpos =$$4.getPosition(0).subtract(blockPos.getCenter());
                if(level instanceof ServerLevel serverLevel && !strikePointerBlock.isPowered(state))
                {
                    System.out.println("yeah");
                    strikePointerBlock.setPowered(state,serverLevel,blockPos,true);
                }
            }
            else
            {
                entity.trans = Mth.clamp(entity.trans-0.05f,0.2f,1);
                if(level instanceof ServerLevel serverLevel && strikePointerBlock.isPowered(state))
                    strikePointerBlock.setPowered(state,serverLevel,blockPos,false);
            }

        }
        else
        {
            entity.trans = Mth.clamp(entity.trans-0.2f,0.2f,1);
            if(level instanceof ServerLevel serverLevel && strikePointerBlock.isPowered(state))
                strikePointerBlock.setPowered(state,serverLevel,blockPos,false);
        }
    }

}
