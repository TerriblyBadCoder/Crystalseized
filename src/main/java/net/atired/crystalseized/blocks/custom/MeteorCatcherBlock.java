package net.atired.crystalseized.blocks.custom;

import net.atired.crystalseized.blocks.CSblockRegistry;
import net.atired.crystalseized.entities.custom.StarFallEntity;
import net.atired.crystalseized.networking.ModMessages;
import net.atired.crystalseized.networking.packets.DirectedParticlesS2Cpacket;
import net.atired.crystalseized.particles.CSparticleRegistry;
import net.atired.crystalseized.particletypes.DirectedSphereParticleOptions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.sampling.UniformSampling;

import java.util.ArrayList;
import java.util.List;

public class MeteorCatcherBlock extends EndCrystalBlock{
    public MeteorCatcherBlock(Properties p_48729_) {
        super(p_48729_);
    }

    @Override
    public void onPlace(BlockState p_60566_, Level p_60567_, BlockPos p_60568_, BlockState p_60569_, boolean p_60570_) {
        super.onPlace(p_60566_, p_60567_, p_60568_, p_60569_, p_60570_);
        p_60567_.scheduleTick(p_60568_, CSblockRegistry.METEOR_CATCHER.get(),1);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos p_222947_, RandomSource p_222948_) {
        super.tick(state, level, p_222947_, p_222948_);
        List<StarFallEntity> starFallEntityList = level.getEntitiesOfClass(StarFallEntity.class,new AABB(p_222947_).inflate(6));
        for( StarFallEntity a : starFallEntityList)
        {

            Vec3 viewvec = a.getPosition(1).subtract(p_222947_.getCenter());
            Vec3 pos = a.getPosition(1);
            System.out.println("eh?Ha! heh " + viewvec.length());
            if(viewvec.length()<6)
            {
                for(ServerPlayer b : level.players())
                {
                    ModMessages.sendToPlayer(new DirectedParticlesS2Cpacket(ParticleTypes.FLASH,true,pos.x,pos.y,pos.z,viewvec.normalize().toVector3f(),2),b);
                }
                level.sendParticles(new DirectedSphereParticleOptions(pos.toVector3f(), (float)viewvec.length()/2),p_222947_.getCenter().x,p_222947_.getCenter().y,p_222947_.getCenter().z,1,0,0,0,0);
                a.discard();
            }
        }

        level.scheduleTick(p_222947_, CSblockRegistry.METEOR_CATCHER.get(),1);
    }

    @Override
    public void animateTick(BlockState p_220827_, Level p_220828_, BlockPos p_220829_, RandomSource p_220830_) {
        super.animateTick(p_220827_, p_220828_, p_220829_, p_220830_);
        Vec3 pos = p_220829_.getCenter();
        p_220828_.addParticle(CSparticleRegistry.SHATTER_PARTICLES.get(), pos.x+Math.random()-0.5,pos.y+0.5,pos.z+Math.random()-0.5,0,0.5,0);
    }

}
