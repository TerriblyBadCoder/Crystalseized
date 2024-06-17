package net.atired.crystalseized.items;

import net.atired.crystalseized.Crystalseized;
import net.atired.crystalseized.items.custom.HydrogenBottleItem;
import net.atired.crystalseized.items.custom.OzoneBottleItem;
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
    public static final RegistryObject<Item> OZONE_BOTTLE = ITEMS.register("ozone_bottle",
            () -> new OzoneBottleItem(new Item.Properties().stacksTo(16).food(CSfoodRegistry.OZONE_BOTTLE)));
    public static final RegistryObject<Item> HYDROGEN_BOTTLE = ITEMS.register("hydrogen_bottle",
            () -> new HydrogenBottleItem(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> LUNAR_SHARD = ITEMS.register("lunar_shard",
            () -> new Item(new Item.Properties()));


    public static void register(IEventBus eventBus) { ITEMS.register(eventBus); }
}
