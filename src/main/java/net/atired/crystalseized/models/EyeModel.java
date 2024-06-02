package net.atired.crystalseized.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.BookModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.EnchantTableRenderer;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector3f;

import java.util.function.Function;

public class EyeModel extends Model {
    private final ModelPart root;
    private final ModelPart eye;


    public EyeModel(ModelPart part) {
        super(RenderType::entityTranslucentEmissive);
        root = part;
        eye = part.getChild("eye");
    }


    public static LayerDefinition createBodyLayer() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.getRoot();
        $$1.addOrReplaceChild("eye", CubeListBuilder.create().texOffs(0,0).addBox(-2.5f,-2.5f,-2.5f,5,5,5), PartPose.ZERO);
        return LayerDefinition.create($$0, 64, 32);
    }
    public void renderToBuffer(PoseStack p_102298_, VertexConsumer p_102299_, int p_102300_, int p_102301_, float p_102302_, float p_102303_, float p_102304_, float p_102305_) {
        this.render(p_102298_, p_102299_, p_102300_, p_102301_, p_102302_, p_102303_, p_102304_, p_102305_);
    }

    public void render(PoseStack p_102317_, VertexConsumer p_102318_, int p_102319_, int p_102320_, float p_102321_, float p_102322_, float p_102323_, float p_102324_) {
        this.root.render(p_102317_, p_102318_, p_102319_, p_102320_, p_102321_, p_102322_, p_102323_, p_102324_);
    }
    public void rotate(float yRot, float age, float pRot)
    {
        this.eye.xRot=pRot;
        this.eye.yRot=yRot;
        this.root.y= (float)Math.sin(age*2f)/3f;
    }
}
