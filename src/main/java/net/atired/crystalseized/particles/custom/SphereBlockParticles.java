package net.atired.crystalseized.particles.custom;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.atired.crystalseized.particletypes.DirectedParticleOptions;
import net.atired.crystalseized.particletypes.DirectedSphereParticleOptions;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Vector3f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SphereBlockParticles extends BounceParticlesBase<DirectedSphereParticleOptions> {
    private final SpriteSet sprites;
    private final Vec3 dir;
    private final float scale;
    private static final Vector3f ROTATION_VECTOR = (new Vector3f(0.5F, 0.5F, 0.5F)).normalize();
    private static final Vector3f TRANSFORM_VECTOR = new Vector3f(-1.0F, -1.0F, 0.0F);
    protected SphereBlockParticles(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, DirectedSphereParticleOptions pOptions, SpriteSet pSprites) {
        super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, pOptions, pSprites);
        this.gravity = 0f;
        this.friction = 0.8f;
        this.xd = pXSpeed;
        this.yd = pYSpeed;
        this.zd = pZSpeed;
        this.sprites = pSprites;
        this.scale = pOptions.getScale()*2;
        this.quadSize = pOptions.getScale();
        this.lifetime = 50;
        this.rCol = 1f;
        this.bCol =1f;
        this.gCol = 1f;
        this.dir = new Vec3(pOptions.getColor().x,pOptions.getColor().y,pOptions.getColor().z);
        this.roll = (float) Math.random() * ((float) Math.PI * 2F);
        this.oRoll = this.roll;
        this.setSpriteFromAge(pSprites);
        this.alpha = 0.95F;
        this.setBoundingBox(this.getBoundingBox().inflate(7));
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.sprites);

    }
    @Override
    public void render(VertexConsumer pBuffer, Camera pRenderInfo, float pPartialTicks) {

        sphereThing(this.getPos(),pBuffer,this.scale, pRenderInfo, pPartialTicks);
        this.alpha = Mth.clamp(0.8F - Mth.clamp(((float)this.age + pPartialTicks) / (float)this.lifetime, 0.0F, 0.8F),0.0F,0.8F);
    }

    public void sphereThing(Vec3 pos, VertexConsumer pConsumer, float scale, Camera camera, float pTick)
    {
        System.out.println(this.scale);
        Vec3 camPos = camera.getPosition();
        float $$5 = (float)(Mth.lerp((double)pTick, this.xo, this.x) - camPos.x());
        float $$6 = (float)(Mth.lerp((double)pTick, this.yo, this.y) - camPos.y());
        float $$7 = (float)(Mth.lerp((double)pTick, this.zo, this.z) - camPos.z());
        float amount = 10;
        List<Vec3> vec3List= new ArrayList<>();
        List<Vec3> oldvec3List= new ArrayList<>();
        for(int j = 0; j<= amount /2; j++)
        {
            float angle = 1.57f-j/ amount *2*3.14f;
            for(int i = 0; i < amount; i++)
            {

                Vec3 offset2 = new Vec3(scale,0,0).zRot(angle).yRot((float) i / amount *2*3.14f);
                vec3List.add(offset2);
                if(!oldvec3List.isEmpty() && i > 0)
                {
                    this.makeCornerVertex(pConsumer, oldvec3List.get(i).toVector3f() , this.getU1(), this.getV1(), 15,new Vec3($$5,$$6,$$7));
                    this.makeCornerVertex(pConsumer, vec3List.get(i).toVector3f(), this.getU1(), this.getV0(), 15,new Vec3($$5,$$6,$$7));
                    this.makeCornerVertex(pConsumer, vec3List.get(i-1).toVector3f(), this.getU0(), this.getV0(), 15,new Vec3($$5,$$6,$$7));
                    this.makeCornerVertex(pConsumer, oldvec3List.get(i-1).toVector3f(), this.getU0(), this.getV1(), 15,new Vec3($$5,$$6,$$7));

                    this.makeCornerVertex(pConsumer, oldvec3List.get(i-1).toVector3f() , this.getU1(), this.getV1(), 15,new Vec3($$5,$$6,$$7));
                    this.makeCornerVertex(pConsumer, vec3List.get(i-1).toVector3f(), this.getU1(), this.getV0(), 15,new Vec3($$5,$$6,$$7));
                    this.makeCornerVertex(pConsumer, vec3List.get(i).toVector3f(), this.getU0(), this.getV0(), 15,new Vec3($$5,$$6,$$7));
                    this.makeCornerVertex(pConsumer, oldvec3List.get(i).toVector3f(), this.getU0(), this.getV1(), 15,new Vec3($$5,$$6,$$7));
                    if(i==amount-1)
                    {
                        this.makeCornerVertex(pConsumer, oldvec3List.get(0).toVector3f() , this.getU1(), this.getV1(), 15,new Vec3($$5,$$6,$$7));
                        this.makeCornerVertex(pConsumer, vec3List.get(0).toVector3f(), this.getU1(), this.getV0(), 15,new Vec3($$5,$$6,$$7));
                        this.makeCornerVertex(pConsumer, vec3List.get(i).toVector3f(), this.getU0(), this.getV0(), 15,new Vec3($$5,$$6,$$7));
                        this.makeCornerVertex(pConsumer, oldvec3List.get(i).toVector3f(), this.getU0(), this.getV1(), 15,new Vec3($$5,$$6,$$7));

                        this.makeCornerVertex(pConsumer, oldvec3List.get(i).toVector3f() , this.getU1(), this.getV1(), 15,new Vec3($$5,$$6,$$7));
                        this.makeCornerVertex(pConsumer, vec3List.get(i).toVector3f(), this.getU1(), this.getV0(), 15,new Vec3($$5,$$6,$$7));
                        this.makeCornerVertex(pConsumer, vec3List.get(0).toVector3f(), this.getU0(), this.getV0(), 15,new Vec3($$5,$$6,$$7));
                        this.makeCornerVertex(pConsumer, oldvec3List.get(0).toVector3f(), this.getU0(), this.getV1(), 15,new Vec3($$5,$$6,$$7));
                    }
                }

            }
            oldvec3List = vec3List;
            vec3List = new ArrayList<>();
        }
    }
    private void makeCornerVertex(VertexConsumer pConsumer, Vector3f pVertex, float pU, float pV, int pPackedLight, Vec3 offset) {
        Color color = getShatterColor(new Vec3(pVertex.x,pVertex.y,pVertex.z).add(this.getPos()));
        float dist = this.quadSize - (float)new Vec3(pVertex.x,pVertex.y,pVertex.z).add(this.getPos()).subtract(this.dir).length();
        pVertex.add(offset.toVector3f());
        pConsumer.vertex((double)pVertex.x(), (double)pVertex.y(), (double)pVertex.z()).uv(pU, pV).color(color.getRed()/255f, color.getGreen()/255f, color.getBlue()/255f, Mth.clamp(this.alpha*dist,0f,1f)).uv2(pPackedLight).endVertex();
    }
    public static Color getShatterColor(Vec3 pos)
    {

        float colourhue = (((float)pos.x +(float)pos.y+ Mth.sin(((float)pos.z + (float)pos.x) / 35) * 35) % 200) / 200;
        Color colour = Color.getHSBColor(colourhue, 0.4F, 1F);
        return colour;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<DirectedSphereParticleOptions>{
        private final SpriteSet sprites;
        public Provider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }
        public Particle createParticle(DirectedSphereParticleOptions particleType, ClientLevel level,
                                       double x, double y, double z,
                                       double dx, double dy, double dz) {
            return new SphereBlockParticles(level, x, y, z, dx, dy, dz, particleType, this.sprites);

        }
    }
    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }
}
