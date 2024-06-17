package net.atired.crystalseized.blocks.custom;

import net.atired.crystalseized.blocks.CSblockRegistry;
import net.atired.crystalseized.entities.custom.StarFallEntity;
import net.atired.crystalseized.mixin.LivingEntityMixin;
import net.atired.crystalseized.particles.CSparticleRegistry;
import net.atired.crystalseized.util.LivingGasAccessor;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.WaterFluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class HydrogenBlock extends AirBlock {

    public HydrogenBlock(Properties p_48756_) {
        super(p_48756_);
    }
    public RenderShape getRenderShape(BlockState p_48758_) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public BlockState updateShape(BlockState p_60541_, Direction p_60542_, BlockState p_60543_, LevelAccessor p_60544_, BlockPos pos, BlockPos posother) {
        if(p_60544_.getBlockState(posother).getBlock() instanceof BaseFireBlock)
        {

            p_60544_.scheduleTick(pos, CSblockRegistry.HYDROGEN_BLOCK.get(), 1);
        }
        return super.updateShape(p_60541_, p_60542_, p_60543_, p_60544_, pos, posother);
    }



    @Override
    public void tick(BlockState p_222945_, ServerLevel p_222946_, BlockPos pos, RandomSource p_222948_) {
        super.tick(p_222945_, p_222946_, pos, p_222948_);
        Direction[] var4 = Direction.values();
        Vec3 poscent = pos.getCenter();
        int var5 = var4.length;
        for(int var6 = 0; var6 < var5; ++var6) {
           BlockState state = p_222946_.getBlockState(pos.relative(var4[var6]));
           if(state.getBlock() instanceof HydrogenBlock)
           {
               p_222946_.scheduleTick(pos.relative(var4[var6]), CSblockRegistry.HYDROGEN_BLOCK.get(), (int) (Math.random()*3+1));
           }
        }
        p_222946_.sendParticles(CSparticleRegistry.BURNING_PARTICLES.get(),poscent.x,poscent.y,poscent.z,1,0,0,0,0);
        p_222946_.destroyBlock(pos,true);
        p_222946_.explode(null,p_222946_.damageSources().explosion(null,null), new ExplosionDamageCalculator(),poscent.x,poscent.y,poscent.z,1,true, Level.ExplosionInteraction.TNT);
    }

    @Override
    public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion) {
        super.onBlockExploded(state, level, pos, explosion);
        Direction[] var4 = Direction.values();
        Vec3 poscent = pos.getCenter();
        int var5 = var4.length;
        for(int var6 = 0; var6 < var5; ++var6) {
            BlockState status = level.getBlockState(pos.relative(var4[var6]));
            if(status.getBlock() instanceof HydrogenBlock)
            {
                level.scheduleTick(pos.relative(var4[var6]), CSblockRegistry.HYDROGEN_BLOCK.get(), (int) (Math.random()*3+1));
            }
        }
        if(level instanceof ServerLevel serverLevel)
        {
            serverLevel.sendParticles(CSparticleRegistry.BURNING_PARTICLES.get(),poscent.x,poscent.y,poscent.z,1,0,0,0,0);
            level.destroyBlock(pos,true);

        }

    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 10;
    }


    public VoxelShape getShape(BlockState p_48760_, BlockGetter p_48761_, BlockPos p_48762_, CollisionContext p_48763_) {
        return Shapes.empty();
    }

    @Override
    public void entityInside(BlockState p_60495_, Level p_60496_, BlockPos p_60497_, Entity p_60498_) {
        super.entityInside(p_60495_, p_60496_, p_60497_, p_60498_);

        if(!(p_60498_ instanceof StarFallEntity))
        {
            if(p_60498_.getRemainingFireTicks()>0)
            {
                p_60496_.scheduleTick(p_60497_,CSblockRegistry.HYDROGEN_BLOCK.get(), 1);
            }
            if(p_60498_ instanceof LivingGasAccessor)
            {
                ((LivingGasAccessor) p_60498_).setGasType(1);
            }
        }


    }

    @Override
    public void animateTick(BlockState p_220827_, Level p_220828_, BlockPos blockPos, RandomSource p_220830_) {
        super.animateTick(p_220827_, p_220828_, blockPos, p_220830_);
        Vec3 pos = blockPos.getCenter();
        if(Math.random()<0.3)
            p_220828_.addParticle(CSparticleRegistry.TOXIN_PARTICLES.get(),pos.x+Math.random()-0.5,pos.y+Math.random()-0.5,pos.z+Math.random()-0.5,0,Math.random()/7,0);
    }
}
