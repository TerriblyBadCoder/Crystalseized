package net.atired.crystalseized.particles.custom;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.DustParticleOptionsBase;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.awt.*;
import java.util.function.Consumer;

@OnlyIn(Dist.CLIENT)
public class BounceParticlesBase<T extends DustParticleOptionsBase> extends TextureSheetParticle {
    private final SpriteSet sprites;
    private static final Vector3f ROTATION_VECTOR = (new Vector3f(0.5F, 0.5F, 0.5F)).normalize();
    private static final Vector3f TRANSFORM_VECTOR = new Vector3f(-1.0F, -1.0F, 0.0F);
    private final Vec3 direction;


    protected BounceParticlesBase(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, T pOptions, SpriteSet pSprites) {
        super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
        this.friction = 0.96F;
        this.speedUpWhenYMotionIsBlocked = true;
        this.sprites = pSprites;
        this.xd = pXSpeed;
        this.yd = pYSpeed;
        this.zd = pZSpeed;
        this.direction = new Vec3(pOptions.getColor().x,pOptions.getColor().y,pOptions.getColor().z).normalize();
        Color color = getShatterColor(this.getPos());
        this.rCol = color.getRed()/256F;
        this.gCol = color.getGreen()/256F;
        this.bCol = color.getBlue()/256F;
        this.quadSize = 0.6F * pOptions.getScale();
        this.alpha = 0.8F;
        this.lifetime = 15;
        this.setSpriteFromAge(pSprites);
    }
    public void render(VertexConsumer pBuffer, Camera pRenderInfo, float pPartialTicks) {
            float pitch = (float)Math.asin(-this.direction.y);
            float yaw = (float)Math.atan2(this.direction.x,this.direction.z);

            this.alpha = Mth.clamp(0.8F - Mth.clamp(((float)this.age + pPartialTicks) / (float)this.lifetime, 0.0F, 0.8F),0.0F,0.8F);
        this.renderRotatedParticle(pBuffer, pRenderInfo, pPartialTicks, (p_253347_) -> {
            p_253347_.mul((new Quaternionf()).rotationYXZ(yaw,pitch,0));
        });
        this.renderRotatedParticle(pBuffer, pRenderInfo, pPartialTicks, (quaternionf) -> {
            quaternionf.mul((new Quaternionf()).rotationYXZ(yaw+3.14F,-pitch,0));
        });
    }

    public static Color getShatterColor(Vec3 pos)
    {

        float colourhue = (((float)pos.x +(float)pos.y+ Mth.sin(((float)pos.z + (float)pos.x) / 35) * 35) % 200) / 200;
        Color colour = Color.getHSBColor(colourhue, 0.4F, 1F);
        return colour;
    }
    private void renderRotatedParticle(VertexConsumer pConsumer, Camera pCamera, float p_233991_, Consumer<Quaternionf> pQuaternion) {
        Vec3 $$4 = pCamera.getPosition();
        float $$5 = (float)(Mth.lerp((double)p_233991_, this.xo, this.x) - $$4.x());
        float $$6 = (float)(Mth.lerp((double)p_233991_, this.yo, this.y) - $$4.y());
        float $$7 = (float)(Mth.lerp((double)p_233991_, this.zo, this.z) - $$4.z());
        Quaternionf $$8 = (new Quaternionf()).setAngleAxis(0.0F, ROTATION_VECTOR.x(), ROTATION_VECTOR.y(), ROTATION_VECTOR.z());
        pQuaternion.accept($$8);
        $$8.transform(TRANSFORM_VECTOR);
        Vector3f[] $$9 = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
        float $$10 = this.getQuadSize(p_233991_);

        int $$13;
        for($$13 = 0; $$13 < 4; ++$$13) {
            Vector3f $$12 = $$9[$$13];
            $$12.rotate($$8);
            $$12.mul($$10);
            $$12.add($$5, $$6, $$7);
        }

        $$13 = this.getLightColor(p_233991_);
        this.makeCornerVertex(pConsumer, $$9[0], this.getU1(), this.getV1(), $$13);
        this.makeCornerVertex(pConsumer, $$9[1], this.getU1(), this.getV0(), $$13);
        this.makeCornerVertex(pConsumer, $$9[2], this.getU0(), this.getV0(), $$13);
        this.makeCornerVertex(pConsumer, $$9[3], this.getU0(), this.getV1(), $$13);
    }
    private void makeCornerVertex(VertexConsumer pConsumer, Vector3f pVertex, float pU, float pV, int pPackedLight) {
        pConsumer.vertex((double)pVertex.x(), (double)pVertex.y(), (double)pVertex.z()).uv(pU, pV).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(pPackedLight).endVertex();
    }


    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public float getQuadSize(float pScaleFactor) {
        return this.quadSize;
    }

    public void tick() {
        super.tick();
        Color color = getShatterColor(this.getPos());
        this.rCol = color.getRed()/256F;
        this.gCol = color.getGreen()/256F;
        this.bCol = color.getBlue()/256F;

        this.setSpriteFromAge(this.sprites);
    }
}