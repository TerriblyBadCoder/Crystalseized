package net.atired.crystalseized.mixin;

import net.atired.crystalseized.util.LivingGasAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements LivingGasAccessor {
    private int gastype;
    private int oldgastype;

    public LivingEntityMixin(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }
    @Inject(method = {"Lnet/minecraft/world/entity/LivingEntity;tick()V"}, remap = true, at = @At(value = "TAIL"))
    public void livingTickMixin(CallbackInfo ci)
    {
        int yeah = getGasType();

        if(yeah == 1)
        {
            setDeltaMovement(getDeltaMovement().scale(0.95));
            resetFallDistance();
            addDeltaMovement(new Vec3(0,-0.02,0));
        }
        if(yeah == 2 && getDeltaMovement().length()<20)
        {
            double scale = getDeltaMovement().length();
            setDeltaMovement(getDeltaMovement().multiply(1+(1/(Math.pow(scale,0.6)+1))/9,1+(1/(Math.pow(scale,0.6)+1))/10,1+(1/(Math.pow(scale,0.6)+1))/9));
            resetFallDistance();
        }
        setGasType(0);
    }
    public void setGasType(int numb)
    {
        this.gastype = numb;

    }
    public int getGasType()
    {
        return this.gastype;
    }
}
