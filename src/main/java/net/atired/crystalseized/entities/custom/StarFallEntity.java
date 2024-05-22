package net.atired.crystalseized.entities.custom;

import net.atired.crystalseized.networking.ModMessages;
import net.atired.crystalseized.networking.packets.DirectedParticlesS2Cpacket;
import net.atired.crystalseized.networking.packets.ParticlesS2Cpacket;
import net.atired.crystalseized.particles.CSparticleRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.*;
import net.minecraftforge.event.ForgeEventFactory;
import org.joml.Vector3f;

import java.util.List;

public class StarFallEntity extends Entity {
    private Vec3[] trailPositions = new Vec3[64];
    private int trailPointer = -1;
    private int tilldiscarded = 5;
    private boolean discarded = false;
    private Vec3 lastPos;
    public StarFallEntity(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
        setViewScale(5);
        this.noCulling = true;
        this.setDeltaMovement(Math.random()*0.5,-2,Math.random()*0.5);
        this.lastPos = new Vec3(0,0,0);
    }
    public Vec3 getTrailPosition(int pointer, float partialTick) {
        if (this.isRemoved()) {
            partialTick = 1.0F;
        }
        int i = this.trailPointer - pointer & 63;
        int j = this.trailPointer - pointer - 1 & 63;
        Vec3 d0 = this.trailPositions[j];
        Vec3 d1 = this.trailPositions[i].subtract(d0);
        return d0.add(d1.scale(partialTick));
    }
    public boolean hasTrail() {
        return trailPointer != -1;
    }
    protected boolean canHitEntity(Entity p_37250_) {
        return false;
    }
    protected void onHit(HitResult hitResult) {
        HitResult.Type hitresult$type = hitResult.getType();
        if (hitresult$type == HitResult.Type.BLOCK) {

            BlockHitResult blockhitresult = (BlockHitResult)hitResult;
            BlockPos blockpos = blockhitresult.getBlockPos();
            Vec3 blockHitVec = blockhitresult.getLocation();
            if(this.level() instanceof ServerLevel serverLevel)
            {
                List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class,new AABB(blockpos).inflate(2)).stream().toList();
                for(LivingEntity a : list)
                {
                    a.addDeltaMovement(new Vec3(0,2,0));
                    a.hurt(this.level().damageSources().lava(),5);

                }
                for(int j = 0; j < serverLevel.players().size(); ++j) {
                    ServerPlayer player = serverLevel.players().get(j);

                        ModMessages.sendToPlayer(new ParticlesS2Cpacket(CSparticleRegistry.METEOR_PARTICLES.get(), false,blockHitVec.x,blockHitVec.y,blockHitVec.z, new Vector3f(0,0.5f,0), 1.5f),player);
                }
            }

            this.discard();
        }

    }

    @Override
    public void tick() {
        super.tick();

        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this,this::canHitEntity);
        if (hitresult.getType() != HitResult.Type.MISS ) {
            this.onHit(hitresult);
        }
        this.setDeltaMovement(this.getDeltaMovement().scale(1.01f).add(0,-0.01,0));
        Vec3 vec3 = this.getDeltaMovement();
        double d2 = this.getX() + vec3.x;
        double d0 = this.getY() + vec3.y;
        double d1 = this.getZ() + vec3.z;
        this.setPos(d2, d0, d1);
        this.lastPos = this.position();
        Vec3 trailAt = this.position().add(0, this.getBbHeight()/2, 0);
        if (trailPointer == -1) {
            Vec3 backAt = trailAt;
            for (int i = 0; i < trailPositions.length; i++) {
                trailPositions[i] = backAt;
            }
        }
        if (++this.trailPointer == this.trailPositions.length) {
            this.trailPointer = 0;
        }
        this.trailPositions[this.trailPointer] = trailAt;

    }





    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {

    }
}
