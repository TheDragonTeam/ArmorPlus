/*
 * Copyright (c) TheDragonTeam 2016-2017.
 */

package net.thedragonteam.armorplus.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.thedragonteam.armorplus.ModConfig;
import net.thedragonteam.armorplus.items.weapons.effects.Negative;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static net.minecraft.inventory.EntityEquipmentSlot.*;
import static net.thedragonteam.armorplus.util.PotionUtils.PotionType.BAD;
import static net.thedragonteam.armorplus.util.PotionUtils.PotionType.GOOD;
import static net.thedragonteam.armorplus.util.PotionUtils.addPotion;
import static net.thedragonteam.armorplus.util.PotionUtils.getPotion;
import static net.thedragonteam.armorplus.util.Utils.convertToSeconds;

/**
 * @author Sokratis Fotkatzikis - TheDragonTeam
 **/
public final class ArmorPlusItemUtils {

    //=========================================================
    //||                                                     ||
    //||                        ITEMS                        ||
    //||                                                     ||
    //=========================================================


    /**
     * @param repair The ItemStack that we will check if it can be repaired
     * @param expert The ItemStack that will represent the item that will be used for repairing the repair ItemStack
     * @return Returns true if the repair ItemStack can be repaired depending on the `game difficulty` set in armorplus' configuration file.
     */
    public static boolean isItemRepairable(ItemStack repair, ItemStack expert) {
        return ModConfig.getRD().isItemRepairable(repair, expert);
    }

    //=========================================================
    //||                                                     ||
    //||               ARMORS & EQUIPMENT GEAR               ||
    //||                                                     ||
    //=========================================================

    /**
     * @param player The player entity that we will check if the full armor set is equipped.
     * @param head   The head armor piece item.
     * @param chest  The chest armor piece item.
     * @param legs   The legs armor piece item.
     * @param feet   The feet armor piece item.
     * @return Returns a validated result of the check if the player has the full armor set equipped.
     */
    public static boolean isFullSet(EntityPlayer player, Item head, Item chest, Item legs, Item feet) {
        ItemStack headStack = getHead(player), chestStack = getChest(player), legsStack = getLegs(player), feetStack = getFeet(player);
        return !isArmorEmpty(headStack, chestStack, legsStack, feetStack) && areEqual(headStack, head) && areEqual(chestStack, chest) && areEqual(legsStack, legs) && areEqual(feetStack, feet);
    }

    /**
     * @param a The ItemStack that we will get the Item from.
     * @param b The Item that we will compare with the Item from ItemStack `a`
     * @return Returns true if the items are the same, false if they are not.
     */
    public static boolean areEqual(ItemStack a, Item b) {
        return a.getItem() == b;
    }

    /**
     * @param helmet     The helmet ItemStack
     * @param chestplate The chestplate ItemStack
     * @param leggings   The leggings ItemStack
     * @param boots      The boots ItemStack
     * @return Returns true if any of the equipped armor pieces are missing (Only needed for checking if a full armor set has valid ItemStacks)
     */
    public static boolean isArmorEmpty(ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots) {
        return (helmet.isEmpty() || chestplate.isEmpty() || leggings.isEmpty() || boots.isEmpty());
    }

    /**
     * @param player The player that we'll be getting the equipment data from
     * @return Returns the ItemStack that is located in the head (Armor) slot
     */
    public static ItemStack getHead(EntityPlayer player) {
        return player.getItemStackFromSlot(HEAD);
    }

    /**
     * @param player The player that we'll be getting the equipment data from
     * @return Returns the ItemStack that is located in the chest (Armor) slot
     */
    public static ItemStack getChest(EntityPlayer player) {
        return player.getItemStackFromSlot(CHEST);
    }

    /**
     * @param player The player that we'll be getting the equipment data from
     * @return Returns the ItemStack that is located in the legs (Armor) slot
     */
    public static ItemStack getLegs(EntityPlayer player) {
        return player.getItemStackFromSlot(LEGS);
    }

    /**
     * @param player The player that we'll be getting the equipment data from
     * @return Returns the ItemStack that is located in the feet (Armor) slot
     */
    public static ItemStack getFeet(EntityPlayer player) {
        return player.getItemStackFromSlot(FEET);
    }


    /**
     * @param player               The player that we will apply the effects on (The entity with the equipped armor pieces)
     * @param applyEffectNames     The list of the effects that will get applied
     * @param applyEffectDurations A list of the applied effects' durations
     * @param applyEffectLevels    A list of the applied effects' levels
     * @param removableEffects     A list of the effects that will be getting removed as soon as they are applied to the entity (From other sources)
     */
    public static void applyEffects(EntityPlayer player, List<String> applyEffectNames, List<Integer> applyEffectDurations, List<Integer> applyEffectLevels, List<String> removableEffects) {
        List<Potion> potions = applyEffectNames.stream().map(PotionUtils::getPotion).collect(toList());
        IntStream.range(0, potions.size()).forEach(potionID -> {
            Potion potionEffect = potions.get(potionID);
            if ((player.getActivePotionEffect(potionEffect) == null || potionEffect == MobEffects.NIGHT_VISION)) {
                addPotion(player, potionEffect, convertToSeconds(applyEffectDurations.get(potionID)), applyEffectLevels.get(potionID), GOOD);
            }
        });

        List<Potion> removablePotions = removableEffects.stream().map(PotionUtils::getPotion).collect(toList());
        removablePotions.stream().filter(
            potionEffect -> player.getActivePotionEffect(potionEffect) != null
        ).forEach(
            player::removeActivePotionEffect
        );
    }

    //=========================================================
    //||                                                     ||
    //||                   WEAPONS & TOOLS                   ||
    //||                                                     ||
    //=========================================================

    /**
     * @param target The target entity that we will apply the negative or "de-buff" effects on
     * @param effect The negative effects that we will apply ({@link Negative#getEffects()},{@link Negative#getEffectDurations()},{@link Negative#getEffectLevels()})
     */
    public static void applyNegativeEffect(EntityLivingBase target, Negative effect) {
        if (effect.isEnabled()) {
            IntStream.range(0, effect.getEffects().length).forEach(
                potionID -> addPotion(target, getPotion(effect.getEffects()[potionID]), convertToSeconds(effect.getEffectDurations()[potionID]), effect.getEffectLevels()[potionID], BAD)
            );
        }
    }

}
