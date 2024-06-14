package net.atired.crystalseized.blocks.custom;

import net.atired.crystalseized.entities.custom.StarFallEntity;
import net.atired.crystalseized.particles.CSparticleRegistry;
import net.atired.crystalseized.util.LivingGasAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class OzoneBlock extends AirBlock {
    public OzoneBlock(Properties p_48756_) {
        super(p_48756_);
    }
    public RenderShape getRenderShape(BlockState p_48758_) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public void onPlace(BlockState p_60566_, Level p_60567_, BlockPos p_60568_, BlockState p_60569_, boolean p_60570_) {
        super.onPlace(p_60566_, p_60567_, p_60568_, p_60569_, p_60570_);
    }

    public VoxelShape getShape(BlockState p_48760_, BlockGetter p_48761_, BlockPos p_48762_, CollisionContext p_48763_) {
        return Shapes.empty();
    }

    @Override
    public void entityInside(BlockState p_60495_, Level p_60496_, BlockPos p_60497_, Entity p_60498_) {
        super.entityInside(p_60495_, p_60496_, p_60497_, p_60498_);
        if(p_60498_ instanceof LivingGasAccessor gasAccessor)
        {
            gasAccessor.setGasType(2);

        }
    }

    @Override
    public void animateTick(BlockState p_220827_, Level p_220828_, BlockPos blockPos, RandomSource p_220830_) {
        super.animateTick(p_220827_, p_220828_, blockPos, p_220830_);
        Vec3 pos = blockPos.getCenter();
        if(Math.random()<0.3)
            p_220828_.addParticle(CSparticleRegistry.OZONE_PARTICLES.get(),pos.x+Math.random()-0.5,pos.y+Math.random()-0.5,pos.z+Math.random()-0.5,0,Math.random()/5,0);
    }
}
