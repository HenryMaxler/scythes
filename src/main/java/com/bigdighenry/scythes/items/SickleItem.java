//modified from BluePower under GPL around December 2020

package com.bigdighenry.scythes.items;

import com.google.common.collect.Sets;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Set;

public class SickleItem extends ToolItem {

    private Item customCraftingMaterial;
    private ITag.INamedTag customCraftingTag;

    private static final Set toolBlocks = Sets.newHashSet(ItemTags.LEAVES, Blocks.WHEAT, Blocks.POTATOES, Blocks.CARROTS,
            Blocks.NETHER_WART, Blocks.RED_MUSHROOM, Blocks.BROWN_MUSHROOM, Blocks.SUGAR_CANE, Blocks.TALL_GRASS, Blocks.VINE, Blocks.LILY_PAD,
            BlockTags.SMALL_FLOWERS);

    public SickleItem(IItemTier itemTier, Item repairItem) {
        super(2,-1.4F, itemTier, toolBlocks, new Properties().group(ItemGroup.TOOLS));
        this.customCraftingMaterial = repairItem;
    }
    public SickleItem(IItemTier itemTier, ITag.INamedTag repairItem) {
        super(2,-1.4F, itemTier, toolBlocks, new Properties().group(ItemGroup.TOOLS));
        this.customCraftingTag = repairItem;
    }
    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if ((state.getMaterial() == Material.LEAVES) || (state.getMaterial() == Material.PLANTS) || toolBlocks.contains(state)) {
            return this.efficiency;
        }
        return 1.0F;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity entityLiving) {

        boolean used = false;

        if (!(entityLiving instanceof PlayerEntity)) return false;
        PlayerEntity player = (PlayerEntity) entityLiving;
        Block destroyedBlock = state.getBlock();

        //break leaf, 3d harvests leaves
        if (destroyedBlock.getTags().contains(new ResourceLocation("minecraft:leaves")) || destroyedBlock instanceof LeavesBlock) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    for (int k = -1; k <= 1; k++) {
                        BlockState blockToCheck = world.getBlockState(pos.add(i,j,k));
                        if (blockToCheck.getBlock().getTags().contains(new ResourceLocation("minecraft:leaves")) || blockToCheck.getBlock() instanceof LeavesBlock) {
                            if (blockToCheck.canHarvestBlock(world, pos.add(i,j,k), player)) {
                                world.destroyBlock(pos.add(i,j,k), true);
                            }
                            used = true;
                        }
                    }
                }
            }
            if (used) {
                stack.damageItem(1, player, (playerEntity) ->
                        playerEntity.sendBreakAnimation(EquipmentSlotType.MAINHAND));
            }
            return used;
        }

        //else break lilypad, 2d harvest lilypads
        if ((destroyedBlock instanceof LilyPadBlock)) {
            for (int i = -2; i <= 2; i++) {
                for (int j = -2; j <= 2; j++) {
                    Block blockToCheck = world.getBlockState(pos.add(i,0,j)).getBlock();
                    if (blockToCheck instanceof LilyPadBlock) {
                        if (blockToCheck.canHarvestBlock(world.getBlockState(pos.add(i,0,j)), world, pos.add(i,0,j), player)) {
                            world.destroyBlock(pos.add(i,0,j), true);
                        }
                        used = true;
                    }
                }
            }
        }

        //else break sugarcane, 2d harvest sugarcane
        if ((destroyedBlock instanceof SugarCaneBlock)) {
            for (int i = -2; i <= 2; i++) {
                for (int j = -2; j <= 2; j++) {
                    Block blockToCheck = world.getBlockState(pos.add(i,0,j)).getBlock();
                    if (blockToCheck instanceof SugarCaneBlock) {
                        if (blockToCheck.canHarvestBlock(world.getBlockState(pos.add(i,0,j)), world, pos.add(i,0,j), player)) {
                            world.destroyBlock(pos.add(i,0,j), true);
                        }
                        used = true;
                    }
                }
            }
        }


        //else break anything, harvest BushBlock and not lilypads!
        if (!(destroyedBlock instanceof LilyPadBlock)) {
            for (int i = -2; i <= 2; i++) {
                for (int j = -2; j <= 2; j++) {
                    Block blockToCheck = world.getBlockState(pos.add(i,0,j)).getBlock();
                    if (blockToCheck instanceof BushBlock && !(blockToCheck instanceof LilyPadBlock)) {
                        if (blockToCheck.canHarvestBlock(world.getBlockState(pos.add(i,0,j)), world,  pos.add(i,0,j), player)) {
                            world.destroyBlock(pos.add(i,0,j), true);
                        }
                        used = true;
                    }
                }
            }
        }

        if (used) {
            stack.damageItem(1, player, (playerEntity) ->
                    playerEntity.sendBreakAnimation(EquipmentSlotType.MAINHAND));
        }
        return used;
    }

    @Override
    public boolean getIsRepairable(ItemStack is1, ItemStack is2) {
        if (customCraftingMaterial != null){
            return ((is1.getItem() == this || is2.getItem() == this) && (is1.getItem() == this.customCraftingMaterial || is2.getItem() == this.customCraftingMaterial));
        }
        else{
            return ((is1.getItem() == this || is2.getItem() == this) && (this.customCraftingTag.contains(is1.getItem()) || this.customCraftingTag.contains(is2.getItem())));
        }
    }
}