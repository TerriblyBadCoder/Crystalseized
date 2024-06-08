package net.atired.crystalseized.entities.custom;

import net.atired.crystalseized.blocks.CSblockRegistry;
import net.atired.crystalseized.items.CSitemRegistry;
import net.atired.crystalseized.networking.ModMessages;
import net.atired.crystalseized.networking.packets.DirectedParticlesS2Cpacket;
import net.atired.crystalseized.networking.packets.ParticlesS2Cpacket;
import net.atired.crystalseized.particles.CSparticleRegistry;
import net.minecraft.BlockUtil;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.blockentity.EnchantTableRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.ParticleUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.EnchantmentTableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.*;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.ForgeEventFactory;
import org.joml.Vector3f;

import java.util.List;

public class StarFallEntity extends Entity {
    private Vec3[] trailPositions = new Vec3[64];
    protected static final EntityDataAccessor<Float> DATA_SCALE;
    private int trailPointer = -1;
    private Vec3 lastPos;
    public StarFallEntity(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
        setViewScale(5);
        this.noCulling = true;
        this.setDeltaMovement(Math.random()*0.5,-2,Math.random()*0.5);
        this.lastPos = new Vec3(0,0,0);
        this.entityData.set(DATA_SCALE,0.55F);
    }
    public StarFallEntity(EntityType<?> p_19870_, Level p_19871_, float randum)
    {
        super(p_19870_,p_19871_);
        setViewScale(5);
        this.noCulling = true;
        this.setDeltaMovement(Math.random()*0.5,-2,Math.random()*0.5);
        this.lastPos = new Vec3(0,0,0);
        System.out.println(randum);
        this.entityData.set(DATA_SCALE,randum);
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

    public float getRandumb()
    {
        return this.entityData.get(DATA_SCALE);
    }

    protected boolean canHitEntity(Entity p_37250_) {
        return p_37250_.canBeHitByProjectile();
    }
    protected void onHit(HitResult hitResult) {
        HitResult.Type hitresult$type = hitResult.getType();
        Vec3 movement = this.getDeltaMovement();
        if(hitresult$type == HitResult.Type.ENTITY)
        {
            if(this.level() instanceof ServerLevel serverLevel)
            {
                Vec3 blockHitVec = hitResult.getLocation();
                spawnRandomItem(blockHitVec,movement);
                List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class,new AABB(new BlockPos((int)blockHitVec.x,(int)blockHitVec.y,(int)blockHitVec.z)).inflate(2)).stream().toList();
                for(LivingEntity a : list) {
                    a.addDeltaMovement(new Vec3(0, 2, 0));
                    a.hurt(this.level().damageSources().lava(), 5);
                }
                for(int j = 0; j < serverLevel.players().size(); ++j) {
                    ServerPlayer player = serverLevel.players().get(j);
                    ModMessages.sendToPlayer(new ParticlesS2Cpacket(CSparticleRegistry.METEOR_PARTICLES.get(), false,blockHitVec.x,blockHitVec.y,blockHitVec.z, new Vector3f(0,0.5f,0), 1.5f),player);
                }
            }
            this.discard();
        }
        if (hitresult$type == HitResult.Type.BLOCK) {

            BlockHitResult blockhitresult = (BlockHitResult)hitResult;
            BlockPos blockpos = blockhitresult.getBlockPos();
            Vec3 blockHitVec = blockhitresult.getLocation();
            spawnRandomItem(blockHitVec,movement);
            if(this.level() instanceof ServerLevel serverLevel)
            {
                List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class,new AABB(blockpos).inflate(2)).stream().toList();
                for(LivingEntity a : list) {
                    Vec3 pos = a.position().subtract(blockHitVec).multiply(1,0,1);
                    pos = pos.normalize().scale(1/pos.length());
                    a.addDeltaMovement(new Vec3(pos.x, 1, pos.z));
                    a.hurt(this.level().damageSources().lava(), 5);

                }
                for(int j = 0; j < serverLevel.players().size(); ++j) {
                    ServerPlayer player = serverLevel.players().get(j);
                    ModMessages.sendToPlayer(new ParticlesS2Cpacket(CSparticleRegistry.SCORCH_PARTICLES.get(), false,blockHitVec.x,blockHitVec.y+0.05f,blockHitVec.z, new Vector3f(0,0,0), 1.5f),player);
                    ModMessages.sendToPlayer(new ParticlesS2Cpacket(CSparticleRegistry.METEOR_PARTICLES.get(), false,blockHitVec.x,blockHitVec.y,blockHitVec.z, new Vector3f(0,0.5f,0), 1.5f),player);
                }
            }
            this.discard();
        }
    }
    private void spawnRandomItem(Vec3 pos, Vec3 motion)
    {
        double random = getRandumb();
        if(random<=0.1d)
        {
            motion = motion.multiply(2,-0.2 ,2);
            ItemEntity itemEntity = new ItemEntity(this.level(),pos.x,pos.y,pos.z,new ItemStack(Items.END_STONE,2),motion.x,motion.y,motion.z);
            itemEntity.lifespan = 60;
            itemEntity.setPickUpDelay(2);
            if(random<0.03d)
            {
                AABB ab=this.getBoundingBox().inflate(6);
                int xm = Mth.floor(ab.minX);
                int ym = Mth.floor(ab.minY);
                int zm = Mth.floor(ab.minZ);
                int xa = Mth.floor(ab.maxX);
                int ya = Mth.floor(ab.maxY);
                int za = Mth.floor(ab.maxZ);
                for(int x = xm; x<xa;x+=1)
                {
                    for(int y = ym; y<ya;y+=1)
                    {
                        for(int z = zm; z<za;z+=1)
                        {
                            BlockPos blockPos = new BlockPos(x,y,z);
                            if(!this.level().isClientSide() &&blockPos.getCenter().subtract(pos).length()<=4+Math.random() && this.level().getBlockState(blockPos).getBlock() == Blocks.END_STONE)
                            {
                              if(blockPos.getCenter().subtract(pos).length()<=2+Math.random()*2 && this.level().getBlockState(blockPos.above()).getBlock() instanceof AirBlock&& !(this.level().getBlockState(blockPos.below()).getBlock() instanceof AirBlock))
                              {
                                  this.level().setBlock(blockPos,CSblockRegistry.LUNAR_SAND.get().defaultBlockState(), 3);

                              }
                              else
                              {
                                  this.level().setBlock(blockPos,CSblockRegistry.LUNAR_BLOCK.get().defaultBlockState(), 3);
                              }
                            }
                            if(this.level() instanceof ServerLevel servo && this.level().getBlockState(blockPos.above()).getBlock() instanceof AirBlock && !(this.level().getBlockState(blockPos).getBlock() instanceof AirBlock) && blockPos.getCenter().subtract(pos).length()<=4+Math.random()*2)
                            {
                                servo.sendParticles(new BlockParticleOption(CSparticleRegistry.SAND_PARTICLES.get(),servo.getBlockState(blockPos)),blockPos.getX(),blockPos.getY()+0.5,blockPos.getZ(),0,1,0,1,0);
                            }
                        }
                    }
                }
                itemEntity.setItem(new ItemStack(CSitemRegistry.LUNAR_SHARD.get()));
            }
            this.level().addFreshEntity(itemEntity);
        }

    }
    @Override
    public void tick() {
        super.tick();
        this.checkInsideBlocks();
        BlockState state = this.level().getBlockState(this.blockPosition());
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

        this.entityData.define(DATA_SCALE,1F);

    }

    static {
        DATA_SCALE = SynchedEntityData.defineId(StarFallEntity.class, EntityDataSerializers.FLOAT);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        this.entityData.set(DATA_SCALE,compoundTag.getFloat("StarfallRandum"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        compoundTag.putFloat("StarfallRandum",this.entityData.get(DATA_SCALE));
    }
}
