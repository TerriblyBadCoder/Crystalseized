package net.atired.crystalseized.effects;

import net.atired.crystalseized.util.LivingGasAccessor;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class AccelerantEffect extends MobEffect {
    protected AccelerantEffect(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
    }
    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if(pLivingEntity instanceof LivingGasAccessor accessor)
        {
            if(accessor.getGasType()!=2 && pLivingEntity.getDeltaMovement().length()<20)
            {

                double scale = pLivingEntity.getDeltaMovement().length();

                if(pLivingEntity.isFallFlying())
                {
                    pLivingEntity.setDeltaMovement(pLivingEntity.getDeltaMovement().multiply(1+(1/(Math.pow(scale,0.6)+1))/45,1+(1/(Math.pow(scale,0.6)+1))/50,1+(1/(Math.pow(scale,0.6)+1))/45));
                }
                else
                {
                    pLivingEntity.setDeltaMovement(pLivingEntity.getDeltaMovement().multiply(1+(1/(Math.pow(scale,0.6)+1))/9,1+(1/(Math.pow(scale,0.6)+1))/10,1+(1/(Math.pow(scale,0.6)+1))/9));
                }
                pLivingEntity.resetFallDistance();
            }
        }
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }



    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
