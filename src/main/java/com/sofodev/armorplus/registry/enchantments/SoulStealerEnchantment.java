package com.sofodev.armorplus.registry.enchantments;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;

import static net.minecraft.enchantment.EnchantmentType.WEAPON;
import static net.minecraft.inventory.EquipmentSlotType.MAINHAND;

public class SoulStealerEnchantment extends APEnchantment {


    public SoulStealerEnchantment() {
        super(Rarity.VERY_RARE, WEAPON, new EquipmentSlotType[]{MAINHAND},
                1, 1, 10, 60, false, true
        );
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return WEAPON.canEnchantItem(stack.getItem()) || stack.getItem() instanceof AxeItem;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return this.canApply(stack);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return true;
    }
}