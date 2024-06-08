package net.atired.crystalseized.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.atired.crystalseized.Crystalseized;
import net.atired.crystalseized.entities.custom.StarFallEntity;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.LightningBoltRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY;


@OnlyIn(Dist.CLIENT)
public class StarFallRenderer extends EntityRenderer<StarFallEntity> {
    @Override
    public boolean shouldRender(StarFallEntity p_114491_, Frustum p_114492_, double p_114493_, double p_114494_, double p_114495_) {
        return true;
    }

    private static final ResourceLocation TRAIL_TEXTURE = new ResourceLocation(Crystalseized.MODID, "textures/particle/star.png");
    private final float scale;
    private static final float MIN_CAMERA_DISTANCE_SQUARED = 90.25F;
    private final ItemRenderer itemRenderer;
    private int time;
    public StarFallRenderer(EntityRendererProvider.Context pContext, float pScale) {
        super(pContext);
        System.out.println("eh?? 2");
        this.itemRenderer = pContext.getItemRenderer();
        this.scale = pScale;
    }
    public StarFallRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.itemRenderer = pContext.getItemRenderer();
        this.scale = 1f;
    }


    public void render(StarFallEntity pEntity, float yaw, float ticks, PoseStack pose, MultiBufferSource bufferSource, int packedLight) {
            if (pEntity.hasTrail()) {
                double x = Mth.lerp(ticks, pEntity.xOld, pEntity.getX());
                double y = Mth.lerp(ticks, pEntity.yOld, pEntity.getY());
                double z = Mth.lerp(ticks, pEntity.zOld, pEntity.getZ());
                pose.pushPose();
                pose.translate(-x, -y, -z);
                renderTrail(pEntity, ticks, pose, bufferSource, 1F,1F,0.9F, 0.3F, 250);
                pose.popPose();
            }
        super.render(pEntity, yaw, ticks, pose, bufferSource, packedLight);
    }


    private void renderTrail(StarFallEntity entityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, float trailR, float trailG, float trailB, float trailA, int packedLightIn) {

        int sampleSize = 10;
        float trailHeight = 0.8F;
        float trailZRot = entityIn.tickCount;
        float sideoffset = (float) Math.sin(entityIn.tickCount)/10f;
        float oldoffset = 0;
        if(entityIn.getRandumb()<0.03)
        {
            trailHeight+=0.2f*(entityIn.getRandumb()+0.05f)*70;
            trailR = 0.7f;
            trailB = 0.8f;
        }
        Vec3 drawFrom = entityIn.getTrailPosition(0, partialTicks);
        VertexConsumer vertexconsumer = bufferIn.getBuffer(RenderType.entityTranslucent(TRAIL_TEXTURE));

        float yRot = -Minecraft.getInstance().getCameraEntity().getViewYRot(0)%360f/180f*3.14f;



        for(int samples = 0; samples < 10; samples++) {
            float randumb = (float)(Math.random()-0.5f)/4;
            Vec3 topAngleVec = new Vec3(0, trailHeight, 0).zRot(0.5f*3.14f+randumb).yRot(yRot);
            Vec3 bottomAngleVec = new Vec3(0, -trailHeight, 0).zRot(0.5f*3.14f+randumb).yRot(yRot);
            Vec3 sample = entityIn.getTrailPosition(samples + 2, partialTicks);
            float u1 = samples / (float) sampleSize;
            float u2 = u1 + 1 / (float) sampleSize;
            float dist = 25/Minecraft.getInstance().getCameraEntity().distanceTo(entityIn);
            Vec3 draw1 = drawFrom;
            Vec3 draw2 = sample;

            PoseStack.Pose posestack$pose = poseStack.last();
            Matrix4f matrix4f = posestack$pose.pose();
            Matrix3f matrix3f = posestack$pose.normal();

            vertexconsumer.vertex(matrix4f, (float) draw1.x + (float) bottomAngleVec.x+ (float) topAngleVec.scale(oldoffset).x, (float) draw1.y + (float) bottomAngleVec.y+ (float) topAngleVec.scale(oldoffset).y, (float) draw1.z + (float) bottomAngleVec.z+ (float) topAngleVec.scale(oldoffset).z).color(trailR, trailG, trailB, Mth.clamp(trailA*dist,0f,1f)).uv(u1, 1F).overlayCoords(NO_OVERLAY).uv2(packedLightIn).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
            vertexconsumer.vertex(matrix4f, (float) draw2.x + (float) bottomAngleVec.x+ (float) topAngleVec.scale(sideoffset).x, (float) draw2.y + (float) bottomAngleVec.y+ (float) topAngleVec.scale(sideoffset).y, (float) draw2.z + (float) bottomAngleVec.z+ (float) topAngleVec.scale(sideoffset).z).color(trailR, trailG, trailB,  Mth.clamp(trailA*dist,0f,1f)).uv(u2, 1F).overlayCoords(NO_OVERLAY).uv2(packedLightIn).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
            vertexconsumer.vertex(matrix4f, (float) draw2.x + (float) topAngleVec.x+ (float) topAngleVec.scale(sideoffset).x, (float) draw2.y + (float) topAngleVec.y+ (float) topAngleVec.scale(sideoffset).y, (float) draw2.z + (float) topAngleVec.z+ (float) topAngleVec.scale(sideoffset).z).color(trailR, trailG, trailB,  Mth.clamp(trailA*dist,0f,1f)).uv(u2, 0).overlayCoords(NO_OVERLAY).uv2(packedLightIn).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
            vertexconsumer.vertex(matrix4f, (float) draw1.x + (float) topAngleVec.x + (float) topAngleVec.scale(oldoffset).x, (float) draw1.y + (float) topAngleVec.y+ (float) topAngleVec.scale(oldoffset).y, (float) draw1.z + (float) topAngleVec.z+ (float) topAngleVec.scale(oldoffset).z).color(trailR, trailG, trailB,  Mth.clamp(trailA*dist,0f,1f)).uv(u1, 0).overlayCoords(NO_OVERLAY).uv2(packedLightIn).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
            drawFrom = sample;
            if(entityIn.getRandumb()>=0.03)
            {
                sideoffset = 0;
                trailA+=0.07f;
                trailB-=0.9f;
                trailG-=0.07f;
            }
            else
            {
                oldoffset = sideoffset;
                sideoffset = (float) Math.sin(samples*30+entityIn.tickCount)/10f;
                trailA+=0.07f;
                trailB+=0.01f;
                trailR-=0.04f;
                trailG-=0.09f;
            }

        }
    }

    @Override
    public ResourceLocation getTextureLocation(StarFallEntity starFallEntity) {
        return TRAIL_TEXTURE;
    }
}
