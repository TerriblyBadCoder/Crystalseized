package net.atired.crystalseized.particles.custom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class SandParticles extends TextureSheetParticle {
    private final SpriteSet sprites;
    protected SandParticles(ClientLevel p_106610_, double p_106611_, double p_106612_, double p_106613_, float p_106614_, float p_106615_, float p_106616_, SpriteSet spriteSet) {
        super(p_106610_,p_106611_,p_106612_,p_106613_);
        this.gravity = 0.02F;
        this.friction = 0.98f;
        this.yd = Math.random()+0.4f;
        this.xd = Math.random()/5f;
        this.zd = Math.random()/5f;
        this.sprites = spriteSet;
        this.quadSize *= 2.5F;
        this.lifetime = 25;
        this.rCol = p_106614_;
        this.gCol = p_106615_;
        this.bCol = p_106616_;
        this.roll = (float) Math.random() * ((float) Math.PI * 2F);
        this.oRoll = this.roll;
        this.setSpriteFromAge(spriteSet);
        this.alpha = 0.6F;
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.sprites);
        this.oRoll = this.roll;
        this.roll += (5/(float)this.age)/5;
        this.alpha -= 0.02F;
        this.quadSize *= 1.02F;
    }



    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<BlockParticleOption> {
        private final SpriteSet sprite;

        public Provider(SpriteSet p_106634_) {
            this.sprite = p_106634_;
        }

        @Nullable
        public Particle createParticle(BlockParticleOption p_106636_, ClientLevel p_106637_, double p_106638_, double p_106639_, double p_106640_, double p_106641_, double p_106642_, double p_106643_) {
            BlockState $$8 = p_106636_.getState();
            if (!$$8.isAir() && $$8.getRenderShape() == RenderShape.INVISIBLE) {
                return null;
            } else {
                BlockPos $$9 = BlockPos.containing(p_106638_, p_106639_, p_106640_);
                int $$10 = Minecraft.getInstance().getBlockColors().getColor($$8, p_106637_, $$9);
                if ($$8.getBlock() instanceof FallingBlock) {
                    $$10 = ((FallingBlock)$$8.getBlock()).getDustColor($$8, p_106637_, $$9);
                }

                float $$11 = (float)($$10 >> 16 & 255) / 255.0F;
                float $$12 = (float)($$10 >> 8 & 255) / 255.0F;
                float $$13 = (float)($$10 & 255) / 255.0F;
                return new SandParticles(p_106637_, p_106638_, p_106639_, p_106640_, $$11, $$12, $$13, this.sprite);
            }
        }
    }
    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }
}
