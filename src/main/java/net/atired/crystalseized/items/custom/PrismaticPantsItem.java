package net.atired.crystalseized.items.custom;

import net.atired.crystalseized.items.DyeablePrismItem;
import net.atired.crystalseized.particletypes.DirectedParticleOptions;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

public class PrismaticPantsItem extends ArmorItem{

    public PrismaticPantsItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);

    }



    @Override
    public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
        super.onInventoryTick(stack, level, player, slotIndex, selectedIndex);

    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level p_41405_, Entity entity, int p_41407_, boolean p_41408_) {
        super.inventoryTick(itemStack, p_41405_, entity, p_41407_, p_41408_);


        if(entity instanceof LivingEntity livingEntity)
        {
            if(livingEntity.getItemBySlot(EquipmentSlot.LEGS) == itemStack)
            {
                float cd = ((CompoundTag)itemStack.serializeNBT().get("tag")).getFloat("crystalseized:prismpants_cd");
                cd -= 1;
                CompoundTag compound = new CompoundTag();
                if(livingEntity.onGround()) {
                    cd = -1;
                    compound.putBoolean("crystalseized:prismpants_onground",true);
                    itemStack.addTagElement("crystalseized:prismpants_onground",compound.get("crystalseized:prismpants_onground"));
                    compound.putBoolean("crystalseized:prismpants_held",false);
                    itemStack.addTagElement("crystalseized:prismpants_held",compound.get("crystalseized:prismpants_held"));
                }
                compound.putFloat("crystalseized:prismpants_cd",Mth.clamp(cd,-1,7));
                itemStack.addTagElement("crystalseized:prismpants_cd",compound.get("crystalseized:prismpants_cd"));
                if(cd%3 == 0 && cd>=0 && livingEntity.getDeltaMovement().length() > 0.3f)
                {
                    livingEntity.level().addParticle(new DirectedParticleOptions(livingEntity.getDeltaMovement().normalize().toVector3f(), (float) (0.5f*Math.pow(livingEntity.getDeltaMovement().length()+0.7f,2)*Math.pow(cd+1,0.5))),livingEntity.getX(),livingEntity.getY(),livingEntity.getZ(),0,0,0);
                }
            }
            else
            {
                CompoundTag compound = new CompoundTag();
                compound.putFloat("crystalseized:prismpants_cd",-1);
                itemStack.addTagElement("crystalseized:prismpants_cd",compound.get("crystalseized:prismpants_cd"));
            }
        }
    }


}
