package com.sofodev.armorplus.registry.items.extras;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.world.World;

import static com.sofodev.armorplus.utils.Utils.convertToSeconds;

public class BuffInstance {

    private final IBuff buff;
    private final boolean instant;
    private final Potion potion;

    public BuffInstance(IBuff buff, int amplifier) {
        this(buff, amplifier, 20, true);
    }

    public BuffInstance(IBuff buff, int amplifier, int duration) {
        this(buff, amplifier, duration, false);
    }

    /**
     * Creates a new Buff Instance, with the specified amplifier (starting with 0, which will be set to 1) and duration (in seconds)...
     */
    public BuffInstance(IBuff buff, int amplifier, int duration, boolean instant) {
        this.buff = buff;
        this.instant = instant;
        if (buff.isEffect() && buff.getEffect() != null) {
            this.potion = new Potion(new EffectInstance(buff.getEffect(), convertToSeconds(duration), amplifier));
        } else {
            this.potion = Potions.EMPTY;
        }
    }

    /**
     * This constructor is used so that special abilities can be used directly, without the need of them to be potion abilities.
     */
    public BuffInstance(IBuff buff) {
        this.buff = buff;
        this.instant = true;
        this.potion = Potions.EMPTY;
    }

    /**
     * Uses the {@link ArmorItem#onArmorTick(ItemStack, World, PlayerEntity)} function to trigger buffs
     * <p>
     * Applies Buff's effects.
     * <p>
     * If the buff is an effect it will be either applied instantly (even if you already have the effect))
     */
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        buff.onArmorTick(stack, world, player);
        if (buff.isEffect()) {
            for (EffectInstance pot : this.getPotion().getEffects()) {
                if (!instant) {
                    if (!player.getActivePotionEffects().contains(pot)) {
                        player.addPotionEffect(pot);
                    }
                } else {
                    player.addPotionEffect(pot);
                }
            }
        }
    }

    public void hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        buff.hitEntity(stack, target, attacker);
        if (buff.isEffect()) {
            this.getPotion().getEffects().forEach(target::addPotionEffect);
        }
    }

    /**
     * Returns a potion effect, might be {@link Potions#EMPTY} if the buff provided is not an effect.
     */
    public Potion getPotion() {
        return potion;
    }

    public IBuff getBuff() {
        return buff;
    }
}
