package net.atired.crystalseized.blocks.custom;

import net.minecraft.client.particle.DustParticleBase;
import net.minecraft.client.particle.FallingDustParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class LunarSandBlock extends FallingBlock {
    public LunarSandBlock(Properties p_53205_) {
        super(p_53205_);
    }

    @Override
    protected int getDelayAfterPlace() {
        return 8;
    }

    @Override
    public void tick(BlockState p_221124_, ServerLevel p_221125_, BlockPos p_221126_, RandomSource p_221127_) {
        if (isFree(p_221125_.getBlockState(p_221126_.below())) && p_221126_.getY() >= p_221125_.getMinBuildHeight()) {
            FallingBlockEntity $$4 = FallingBlockEntity.fall(p_221125_, p_221126_, p_221124_);
            this.falling($$4);
        }
    }

    @Override
    protected void falling(FallingBlockEntity p_53206_) {
        super.falling(p_53206_);
    }


}
