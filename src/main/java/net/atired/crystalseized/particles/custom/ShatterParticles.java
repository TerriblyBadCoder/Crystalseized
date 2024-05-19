package net.atired.crystalseized.particles.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;

public class ShatterParticles extends TextureSheetParticle {
    private final SpriteSet sprites;
    protected ShatterParticles(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet spriteSet, double pXSpeed, double pYSpeed, double pZSpeed) {
        super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
        this.gravity = 0F;
        this.friction = 0.2F;
        this.xd = pXSpeed;
        this.yd = pYSpeed;
        this.zd = pZSpeed;
        this.sprites = spriteSet;
        this.quadSize *= 2.5F;
        this.lifetime = 12;
        Color color = getShatterColor(this.getPos());
        this.rCol = color.getRed()/256F;
        this.gCol = color.getGreen()/256F;
        this.bCol = color.getBlue()/256F;
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
        Color color = getShatterColor(this.getPos());
        this.rCol = color.getRed()/256F;
        this.gCol = color.getGreen()/256F;
        this.bCol = color.getBlue()/256F;
    }
    public static Color getShatterColor(Vec3 pos)
    {

        float colourhue = (((float)pos.x +(float)pos.y+ Mth.sin(((float)pos.z + (float)pos.x) / 35) * 35) % 200) / 200;
        Color colour = Color.getHSBColor(colourhue, 0.5F, 1F);
        return colour;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;
        public Provider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }
        public Particle createParticle(SimpleParticleType particleType, ClientLevel level,
                                       double x, double y, double z,
                                       double dx, double dy, double dz) {
            return new ShatterParticles(level, x, y, z, this.sprites, dx, dy, dz);
        }
    }
    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }
}
