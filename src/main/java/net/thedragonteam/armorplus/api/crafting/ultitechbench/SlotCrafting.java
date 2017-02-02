/*
 * Copyright (c) TheDragonTeam 2016-2017.
 */

package net.thedragonteam.armorplus.api.crafting.ultitechbench;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.ForgeHooks;
import net.thedragonteam.armorplus.registry.ModAchievements;

import javax.annotation.Nonnull;

import static net.thedragonteam.armorplus.registry.APItems.*;
import static net.thedragonteam.armorplus.util.EnchantmentUtils.getEnchantment;

/**
 * net.thedragonteam.armorplus.api.crafting.hightechbench
 * ArmorPlus created by sokratis12GR on 6/21/2016 3:55 PM.
 * - TheDragonTeam
 */
public class SlotCrafting extends Slot {
    /**
     * The craft matrix inventory linked to this result slot.
     */
    private final InventoryCrafting craftMatrix;
    /**
     * The player that is using the GUI where this slot resides.
     */
    private final EntityPlayer player;
    /**
     * The number of items that have been crafted so far. Gets passed to ItemStack.onCrafting before being reset.
     */
    private int amountCrafted;

    public SlotCrafting(EntityPlayer player, InventoryCrafting craftingInventory, IInventory inventoryIn, int slotIndex, int xPosition, int yPosition) {
        super(inventoryIn, slotIndex, xPosition, yPosition);
        this.player = player;
        this.craftMatrix = craftingInventory;
    }

    /**
     * Check if the stack is allowed to be placed in this slot, used for armor itemHandler as well as furnace fuel.
     */
    @Override
    public boolean isItemValid(ItemStack stack) {
        return false;
    }

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
     * stack.
     */
    @Override
    @Nonnull
    public ItemStack decrStackSize(int amount) {
        if (this.getHasStack()) {
            this.amountCrafted += Math.min(amount, this.getStack().getCount());
        }

        return super.decrStackSize(amount);
    }

    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood. Typically increases an
     * internal count then calls onCrafting(item).
     */
    @Override
    protected void onCrafting(ItemStack stack, int amount) {
        this.amountCrafted += amount;
        this.onCrafting(stack);
    }

    @Override
    protected void onSwapCraft(int p_190900_1_) {
        this.amountCrafted += p_190900_1_;
    }

    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood.
     */
    @Override
    protected void onCrafting(ItemStack stack) {

        if (this.amountCrafted > 0) {
            stack.onCrafting(this.player.world, this.player, this.amountCrafted);
        }

        this.amountCrafted = 0;

        if (stack.getItem() == guardianSword)
            stack.addEnchantment(getEnchantment("sharpness"), 1);
        else if (stack.getItem() == guardianBattleAxe)
            stack.addEnchantment(getEnchantment("sharpness"), 1);
        else if (stack.getItem() == guardianBow)
            stack.addEnchantment(getEnchantment("power"), 1);
        /*Guardian Armor Thorns*/
        else if (stack.getItem() == guardianHelmet || stack.getItem() == guardianChestplate || stack.getItem() == guardianLeggings || stack.getItem() == guardianBoots)
            stack.addEnchantment(getEnchantment("thorns"), 3);
        /*Guardian Armor Unbreaking 3*/
        else if (stack.getItem() == guardianHelmet || stack.getItem() == guardianChestplate || stack.getItem() == guardianLeggings || stack.getItem() == guardianBoots)
            stack.addEnchantment(getEnchantment("unbreaking"), 3);
        /* Guardian Boots Enchantments*/
        else if (stack.getItem() == guardianBoots)
            stack.addEnchantment(getEnchantment("depth_strider"), 3);
        /*Mending*/
        else if (stack.getItem() == theUltimateHelmet || stack.getItem() == theUltimateChestplate || stack.getItem() == theUltimateLeggings || stack.getItem() == theUltimateBoots)
            stack.addEnchantment(getEnchantment("mending"), 1);
        /*Full of Thorns! - Achievement Trigger*/
        else if (stack.getItem() == guardianHelmet || stack.getItem() == guardianChestplate || stack.getItem() == guardianLeggings || stack.getItem() == guardianBoots)
            this.player.addStat(ModAchievements.craftGuardianArmor, 1);
        /*Godlike! - Achievement Trigger*/
        else if (stack.getItem() == superStarHelmet || stack.getItem() == superStarChestplate || stack.getItem() == superStarLeggings || stack.getItem() == superStarBoots)
            this.player.addStat(ModAchievements.craftSuperStarArmor, 1);
        /*The Power of the Ender Dragon! - Achievement Trigger*/
        else if (stack.getItem() == enderDragonHelmet || stack.getItem() == enderDragonChestplate || stack.getItem() == enderDragonLeggings || stack.getItem() == enderDragonBoots)
            this.player.addStat(ModAchievements.craftEnderDragonArmor, 1);
        /*The Ultimate Power! - Achievement Trigger*/
        else if (stack.getItem() == theUltimateHelmet || stack.getItem() == theUltimateChestplate || stack.getItem() == theUltimateLeggings || stack.getItem() == theUltimateBoots)
            this.player.addStat(ModAchievements.craftTheUltimateArmor, 1);
    }

    @Override
    @Nonnull
    public ItemStack onTake(EntityPlayer player, @Nonnull ItemStack stack) {
        this.onCrafting(stack);
        ForgeHooks.setCraftingPlayer(player);
        NonNullList<ItemStack> nonnulllist = UltiTechBenchCraftingManager.getInstance().getRemainingItems(this.craftMatrix, player.world);
        ForgeHooks.setCraftingPlayer(null);

        for (int i = 0; i < nonnulllist.size(); ++i) {
            ItemStack itemstack = this.craftMatrix.getStackInSlot(i);
            ItemStack itemstack1 = nonnulllist.get(i);

            if (!itemstack.isEmpty()) {
                this.craftMatrix.decrStackSize(i, 1);
                itemstack = this.craftMatrix.getStackInSlot(i);
            }

            if (!itemstack1.isEmpty()) {
                if (itemstack.isEmpty()) {
                    this.craftMatrix.setInventorySlotContents(i, itemstack1);
                } else if (ItemStack.areItemsEqual(itemstack, itemstack1) && ItemStack.areItemStackTagsEqual(itemstack, itemstack1)) {
                    itemstack1.grow(itemstack.getCount());
                    this.craftMatrix.setInventorySlotContents(i, itemstack1);
                } else if (!this.player.inventory.addItemStackToInventory(itemstack1)) {
                    this.player.dropItem(itemstack1, false);
                }
            }
        }

        return stack;
    }
}
