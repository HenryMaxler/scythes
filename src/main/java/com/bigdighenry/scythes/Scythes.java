package com.bigdighenry.scythes;

import com.bigdighenry.scythes.init.ModItems;
import com.bigdighenry.scythes.items.SickleItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("scythes")
public class Scythes {

    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "scythes";

    public Scythes() {
        ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent.LeftClickBlock event) {

        if (event.getPlayer().isCreative()) {
            ItemStack heldItem = event.getPlayer().getHeldItem(event.getHand());
            if (!heldItem.isEmpty() && heldItem.getItem() instanceof SickleItem) {
                heldItem.getItem().onBlockDestroyed(heldItem, event.getWorld(), event.getWorld().getBlockState(event.getPos()), event.getPos(), event.getPlayer());
            }
        }

    }

}
