package net.atired.crystalseized.items;

import net.atired.crystalseized.Crystalseized;
import net.atired.crystalseized.items.custom.PrismaticPantsItem;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CSitemRegistry {
    public static final DeferredRegister<Item>
            ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Crystalseized.MODID);

    public static final RegistryObject<Item> PRISMATIC_LEGGINGS = ITEMS.register("prismatic_leggings",
            () -> new PrismaticPantsItem(CSarmorMat.PRISMATIC, ArmorItem.Type.LEGGINGS, new Item.Properties()));


    public static void register(IEventBus eventBus) { ITEMS.register(eventBus); }
}