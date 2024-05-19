package net.atired.crystalseized.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.atired.crystalseized.Crystalseized;
import net.atired.crystalseized.items.DyeablePrismItem;
import net.atired.crystalseized.items.custom.PrismaticPantsItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.DyeableLeatherItem;

import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(HumanoidArmorLayer.class)
public abstract class HumanoidArmorLayerMixin<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M> {


    @Unique
    private Vec3 position = new Vec3(0,0,0);
    private static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation("textures/models/armor/chainmail_layer_2.png");
    public HumanoidArmorLayerMixin(RenderLayerParent<T, M> p_117346_) {
        super(p_117346_);
    }
    @Inject(method = "renderArmorPiece(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;ILnet/minecraft/client/model/HumanoidModel;)V", at = @At(value =  "HEAD"))
    public void yeah(PoseStack p_117119_, MultiBufferSource p_117120_, T p_117121_, EquipmentSlot p_117122_, int p_117123_, A p_117124_, CallbackInfo ci)
    {
        position = p_117121_.getPosition(0);
    }

    @Inject(method = "renderModel(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/item/ArmorItem;Lnet/minecraft/client/model/Model;ZFFFLnet/minecraft/resources/ResourceLocation;)V", at = @At(value = "HEAD"), cancellable = true, remap = false)
    public void renderPrismArmor(PoseStack poseStack, MultiBufferSource p_289689_, int p_289681_, ArmorItem armorItem, Model model, boolean p_289668_, float r, float g, float b, ResourceLocation armorResource, CallbackInfo ci)
    {
        if(armorItem instanceof PrismaticPantsItem)
        {

            ci.cancel();

            VertexConsumer vertexconsumerOverlay = p_289689_.getBuffer(RenderType.armorCutoutNoCull(RESOURCE_LOCATION));
            model.renderToBuffer(poseStack, vertexconsumerOverlay, p_289681_, OverlayTexture.NO_OVERLAY, r, g, b, 1.0F);
            VertexConsumer vertexconsumer = p_289689_.getBuffer(RenderType.armorCutoutNoCull(armorResource));
            Color color = getEndCrystalColor(position);
            Vector3f vector3f = poseStack.last().normal().positiveY(new Vector3f(0,0,0));
            model.renderToBuffer(poseStack, vertexconsumerOverlay, p_289681_, OverlayTexture.NO_OVERLAY, (float) color.getRed() /255, (float) color.getGreen() /255, (float) color.getBlue() /255, 0.6F);
        }
    }
    @Unique
    private static Color getEndCrystalColor(Vec3 pos)
    {

        float colourhue = (((float)pos.x +(float)pos.y+ Mth.sin(((float)pos.z + (float)pos.x) / 35) * 35) % 200) / 200;
        Color colour = Color.getHSBColor(colourhue, 0.35F, 1F);
        return colour;
    }
}
