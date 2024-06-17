package net.atired.crystalseized.particles.custom;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class BurningParticles extends TextureSheetParticle {
    private final SpriteSet sprites;
    protected BurningParticles(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet spriteSet, double pXSpeed, double pYSpeed, double pZSpeed) {
        super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
        this.gravity = 0F;
        this.friction = 0.85F;
        this.xd = pXSpeed;
        this.yd = pYSpeed;
        this.zd = pZSpeed;
        this.sprites = spriteSet;
        this.quadSize = 2.6F;
        this.lifetime = 9;

        this.rCol =1f;
        this.gCol = 1f;
        this.bCol =1f;
        this.setSpriteFromAge(spriteSet);
        this.alpha = 0.9F;
    }

    @Override
    public void render(VertexConsumer p_107678_, Camera p_107679_, float p_107680_) {
        Vec3 $$3 = p_107679_.getPosition();
        float $$4 = (float)(Mth.lerp((double)p_107680_, this.xo, this.x) - $$3.x());
        float $$5 = (float)(Mth.lerp((double)p_107680_, this.yo, this.y) - $$3.y());
        float $$6 = (float)(Mth.lerp((double)p_107680_, this.zo, this.z) - $$3.z());
        Quaternionf $$8;
        if (this.roll == 0.0F) {
            $$8 = p_107679_.rotation();
        } else {
            $$8 = new Quaternionf(p_107679_.rotation());
            $$8.rotateZ(Mth.lerp(p_107680_, this.oRoll, this.roll));
        }

        Vector3f[] $$9 = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
        float $$10 = this.getQuadSize(p_107680_);

        for(int $$11 = 0; $$11 < 4; ++$$11) {
            Vector3f $$12 = $$9[$$11];
            $$12.rotate($$8);
            $$12.mul($$10);
            $$12.add($$4, $$5, $$6);
            $$12.add((float) (Math.random()/10-0.05f), (float) (Math.random()/10-0.05f), (float) (Math.random()/10-0.05f));
        }

        float $$13 = this.getU0();
        float $$14 = this.getU1();
        float $$15 = this.getV0();
        float $$16 = this.getV1();
        int $$17 = 255;
        p_107678_.vertex((double)$$9[0].x(), (double)$$9[0].y(), (double)$$9[0].z()).uv($$14, $$16).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2($$17).endVertex();
        p_107678_.vertex((double)$$9[1].x(), (double)$$9[1].y(), (double)$$9[1].z()).uv($$14, $$15).color(this.rCol, this.gCol, this.bCol, 0).uv2($$17).endVertex();
        p_107678_.vertex((double)$$9[2].x(), (double)$$9[2].y(), (double)$$9[2].z()).uv($$13, $$15).color(this.rCol, this.gCol, this.bCol, 0).uv2($$17).endVertex();
        p_107678_.vertex((double)$$9[3].x(), (double)$$9[3].y(), (double)$$9[3].z()).uv($$13, $$16).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2($$17).endVertex();

    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.sprites);
        this.oRoll = this.roll;
        this.roll = (float) (Math.sin(this.age)/5f);
        this.quadSize /= 1.09f;
        this.alpha-=0.09f;
        this.bCol-=0.04f;
        this.gCol-=0.04f;
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
            return new BurningParticles(level, x, y, z, this.sprites, dx, dy, dz);
        }
    }
    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }
}
