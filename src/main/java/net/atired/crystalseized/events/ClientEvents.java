package net.atired.crystalseized.events;

import com.mojang.blaze3d.vertex.PoseStack;
import net.atired.crystalseized.Crystalseized;
import net.atired.crystalseized.effects.CSeffectRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BottleItem;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.Random;
import java.util.function.Consumer;

@OnlyIn(Dist.CLIENT)
public class ClientEvents {
    private static final Vector3f ROTATION_VECTOR = (new Vector3f(0.5F, 0.5F, 0.5F)).normalize();
    private final ResourceLocation PINK_LOC = new ResourceLocation(Crystalseized.MODID,"textures/particle/pink.png");
    private Random random;
    @SubscribeEvent
    public void idk(RenderPlayerEvent.Post event)
    {
        PlayerModel<AbstractClientPlayer> model = event.getRenderer().getModel();
        if(event.getEntity().hasEffect(CSeffectRegistry.ACCELERANT.get()))
        {
            PoseStack stack = event.getPoseStack();

            stack.pushPose();
            if(event.getEntity() instanceof AbstractClientPlayer abstractClientPlayer)
            {


                float pitch;
                if(abstractClientPlayer.isVisuallySwimming() || abstractClientPlayer.isFallFlying())
                {
                    pitch = -(float)Math.asin(abstractClientPlayer.getViewVector(event.getPartialTick()).y) + 3.14f/2;
                } else {
                    pitch = 0;
                }
                float size = abstractClientPlayer.getBbHeight();
                Vec3 pos = abstractClientPlayer.getDeltaMovementLerped(event.getPartialTick()).scale(-1.2);
                stack.translate(pos.x*0.8,pos.y*0.8+size/2*1.6,pos.z*0.8);
                stack.scale(1.2f,1.2f,1.2f);
                Consumer<Quaternionf> quaternionfConsumer = (p_253347_) -> {
                    p_253347_.mul((new Quaternionf()).rotationYXZ(-abstractClientPlayer.yBodyRot/180*3.14f,-3.14f+pitch,0));
                };
                Quaternionf $$8 = (new Quaternionf()).setAngleAxis(0.0F, ROTATION_VECTOR.x(), ROTATION_VECTOR.y(), ROTATION_VECTOR.z());
                quaternionfConsumer.accept($$8);
                stack.rotateAround($$8,0,0,0);
                model.renderToBuffer(stack,event.getMultiBufferSource().getBuffer(RenderType.entityTranslucentEmissive(PINK_LOC)),255,1,1,1,1, Mth.clamp(0.6f*(float)pos.length(),0,1));
                stack.popPose();
                stack.pushPose();
                stack.translate(pos.x*1.2,pos.y*1.2+size/2*1.6,pos.z*1.2);
                stack.rotateAround($$8,0,0,0);
                model.renderToBuffer(stack,event.getMultiBufferSource().getBuffer(RenderType.entityTranslucentEmissive(PINK_LOC)),255,1,0.2f,1,1, Mth.clamp(0.45f*(float)pos.length()-0.05f,0,1));
                stack.popPose();

                stack.pushPose();
                stack.translate(pos.x*1.5,pos.y*1.5+size/2*1.4,pos.z*1.5);
                stack.scale(0.8f,0.8f,0.8f);
                stack.rotateAround($$8,0,0,0);
                model.renderToBuffer(stack,event.getMultiBufferSource().getBuffer(RenderType.entityTranslucentEmissive(PINK_LOC)),255,1,0.5f,1,0.5f, Mth.clamp(0.3f*(float)pos.length()-0.1f,0,1));
                stack.popPose();
            }

        }
    }


}
