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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.DyeableLeatherItem;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
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


    @Shadow protected abstract void setPartVisibility(A p_117126_, EquipmentSlot p_117127_);

    @Shadow protected abstract Model getArmorModelHook(T entity, ItemStack itemStack, EquipmentSlot slot, A model);

    @Shadow protected abstract boolean usesInnerModel(EquipmentSlot p_117129_);

    @Shadow public abstract ResourceLocation getArmorResource(Entity entity, ItemStack stack, EquipmentSlot slot, @Nullable String type);

    @Shadow protected abstract void renderGlint(PoseStack p_289673_, MultiBufferSource p_289654_, int p_289649_, A p_289659_);

    @Shadow protected abstract void renderModel(PoseStack p_289664_, MultiBufferSource p_289689_, int p_289681_, ArmorItem p_289650_, Model p_289658_, boolean p_289668_, float p_289678_, float p_289674_, float p_289693_, ResourceLocation armorResource);

    @Shadow protected abstract void renderGlint(PoseStack p_289673_, MultiBufferSource p_289654_, int p_289649_, Model p_289659_);

    @Unique
    private Vec3 position = new Vec3(0,0,0);
    private static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation("textures/models/armor/chainmail_layer_2.png");
    public HumanoidArmorLayerMixin(RenderLayerParent<T, M> p_117346_) {
        super(p_117346_);
    }
    @Inject(method = "renderArmorPiece(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;ILnet/minecraft/client/model/HumanoidModel;)V", at = @At(value =  "HEAD"), cancellable = true)
    public void yeah(PoseStack p_117119_, MultiBufferSource p_117120_, T p_117121_, EquipmentSlot p_117122_, int p_117123_, A p_117124_, CallbackInfo ci)
    {
        Vec3 pos = p_117121_.getPosition(0);
        ItemStack itemstack = p_117121_.getItemBySlot(p_117122_);
        Item $$9 = itemstack.getItem();
        if ($$9 instanceof ArmorItem armoritem) {
            if (armoritem.getEquipmentSlot() == p_117122_) {

                if (armoritem instanceof PrismaticPantsItem) {
                    ((HumanoidModel)this.getParentModel()).copyPropertiesTo(p_117124_);
                    this.setPartVisibility(p_117124_, p_117122_);
                    Model model = this.getArmorModelHook(p_117121_, itemstack, p_117122_, p_117124_);
                    boolean flag = this.usesInnerModel(p_117122_);
                    float colourhue = (((float)pos.x +(float)pos.y+ Mth.sin(((float)pos.z + (float)pos.x) / 35) * 35) % 200) / 200;
                    Color color = Color.getHSBColor(colourhue, 0.35F, 1F);


                    this.renderModel(p_117119_, p_117120_, p_117123_, armoritem, model, flag, color.getRed()/255f, color.getGreen()/255f, color.getBlue()/255f, this.getArmorResource(p_117121_, itemstack, p_117122_, "overlay"));
                    this.renderModel(p_117119_, p_117120_, p_117123_, armoritem, model, flag, color.getRed()/255f, color.getGreen()/255f, color.getBlue()/255f, this.getArmorResource(p_117121_, itemstack, p_117122_, (String)null));

                    if (itemstack.hasFoil()) {
                        this.renderGlint(p_117119_, p_117120_, p_117123_, model);
                    }
                }

            }
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
