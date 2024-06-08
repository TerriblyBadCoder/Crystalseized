package net.atired.crystalseized.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.atired.crystalseized.Crystalseized;
import net.atired.crystalseized.entities.custom.blockentity.StrikePointerBlockEntity;
import net.atired.crystalseized.models.EyeModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
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
public class StrikePointerRenderer implements BlockEntityRenderer<StrikePointerBlockEntity> {
    private static final ResourceLocation TRAIL_TEXTURE = new ResourceLocation(Crystalseized.MODID, "textures/particle/lines.png");
    public static final ResourceLocation EYE_LOCATION = new ResourceLocation(Crystalseized.MODID,"textures/block/strike_pointer_eye.png");
    private final EyeModel eyeModel;
    public StrikePointerRenderer(BlockEntityRendererProvider.Context p_173619_) {
        this.eyeModel = new EyeModel(EyeModel.createBodyLayer().bakeRoot());
    }
    @Override
    public void render(StrikePointerBlockEntity strikePointerBlockEntity, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1) {
        poseStack.pushPose();

        Vec3i vec3i = strikePointerBlockEntity.getBlockState().getValue(BlockStateProperties.FACING).getNormal();
        Vector3f vector3f = new Vector3f(vec3i.getX(),vec3i.getY(),vec3i.getZ());
        vector3f = vector3f.mul(0.5f).add(0.5f,0.5f,0.5f);
        poseStack.translate(vector3f.x,vector3f.y,vector3f.z);
        eyeModel.rotate(strikePointerBlockEntity.yrot, strikePointerBlockEntity.age/10f,strikePointerBlockEntity.prot);
        eyeModel.renderToBuffer(poseStack, multiBufferSource.getBuffer(RenderType.entityTranslucent(EYE_LOCATION)),210+strikePointerBlockEntity.range*5,i1,strikePointerBlockEntity.range/7f,1,1, strikePointerBlockEntity.trans);
        Vec3 offset = new Vec3(1,0,0).scale(Math.pow(strikePointerBlockEntity.pos,0.5)).yRot(strikePointerBlockEntity.yrot).add( 0,(float) Math.sin(strikePointerBlockEntity.age/10f)/3.14f,0);

        Vec3 pos = strikePointerBlockEntity.actualpos;
        if (strikePointerBlockEntity.trans > 0.3f) {
            PoseStack.Pose posestack$pose = poseStack.last();
            Matrix4f matrix4f = posestack$pose.pose();
            Matrix3f matrix3f = posestack$pose.normal();
            VertexConsumer vertexconsumer = multiBufferSource.getBuffer(RenderType.entityTranslucent(TRAIL_TEXTURE));
            makeVertexes(vertexconsumer, matrix4f, matrix3f, strikePointerBlockEntity, pos, offset);
        }
        poseStack.popPose();
    }
    public void makeVertexes(VertexConsumer vertexconsumer, Matrix4f matrix4f, Matrix3f matrix3f,StrikePointerBlockEntity strikePointerBlockEntity,Vec3 pos,Vec3 offs)
    {

        float yeah = strikePointerBlockEntity.pos/6f;
        Vec3 leftpos = pos.add(offs.scale(-1)).add(Math.random()/10f,Math.random()/10f,Math.random()/10f);
        Vec3 rightpos = pos.add(offs).add(Math.random()/10f,Math.random()/10f,Math.random()/10f);
        vertexconsumer.vertex(matrix4f,0,0,0).color(0.4f,1,0.6f,0).uv(0.5f, 1f-yeah).overlayCoords(NO_OVERLAY).uv2(250).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
        vertexconsumer.vertex(matrix4f, (float) leftpos.x, (float) leftpos.y, (float) leftpos.z).color(0.3f,0.8f,0.45f,strikePointerBlockEntity.trans/2-0.1f).uv(0, 1f).overlayCoords(NO_OVERLAY).uv2(250).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
        vertexconsumer.vertex(matrix4f, (float) rightpos.x, (float) rightpos.y, (float) rightpos.z).color(0.3f,0.8f,0.45f,strikePointerBlockEntity.trans/2-0.1f).uv(1f, 1f).overlayCoords(NO_OVERLAY).uv2(250).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
        vertexconsumer.vertex(matrix4f, (float) leftpos.x, (float) leftpos.y, (float) leftpos.z).color(0.3f,0.8f,0.45f,strikePointerBlockEntity.trans/2-0.1f).uv(0, 1f).overlayCoords(NO_OVERLAY).uv2(250).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
    }

}
