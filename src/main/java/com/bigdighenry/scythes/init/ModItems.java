package com.bigdighenry.scythes.init;

import com.bigdighenry.scythes.Scythes;
import com.bigdighenry.scythes.items.SickleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Scythes.MOD_ID);
    public static final RegistryObject<SickleItem> WOOD_SCYTHE = ITEMS.register("wood_scythe",
            () -> new SickleItem(ItemTier.WOOD, ItemTags.PLANKS));
    public static final RegistryObject<SickleItem> IRON_SCYTHE = ITEMS.register("iron_scythe",
            () -> new SickleItem(ItemTier.IRON, Items.IRON_INGOT));
    public static final RegistryObject<SickleItem> GOLD_SCYTHE = ITEMS.register("gold_scythe",
            () -> new SickleItem(ItemTier.GOLD, Items.GOLD_INGOT));
    public static final RegistryObject<SickleItem> DIAMOND_SCYTHE = ITEMS.register("diamond_scythe",
            () -> new SickleItem(ItemTier.DIAMOND, Items.DIAMOND));
    public static final RegistryObject<SickleItem> NETHERITE_SCYTHE = ITEMS.register("netherite_scythe",
            () -> new SickleItem(ItemTier.NETHERITE, Items.NETHERITE_INGOT));
}
