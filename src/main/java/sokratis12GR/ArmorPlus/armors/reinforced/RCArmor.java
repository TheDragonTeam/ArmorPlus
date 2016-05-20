package sokratis12GR.ArmorPlus.armors.reinforced;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import sokratis12GR.ArmorPlus.ArmorPlus;
import sokratis12GR.ArmorPlus.registry.ModItems;
import sokratis12GR.ArmorPlus.resources.ConfigHandler;

public class RCArmor {

    public static Item helmet;
    public static Item chestplate;
    public static Item legs;
    public static Item boots;
    public Object instance;

    public void load(FMLInitializationEvent event) {
        if (event.getSide() == Side.CLIENT) {
            ModelLoader.setCustomModelResourceLocation(helmet, 0,
                    new ModelResourceLocation("armorplus:RCHelmet", "inventory"));
            ModelLoader.setCustomModelResourceLocation(chestplate, 0,
                    new ModelResourceLocation("armorplus:RCChestplate", "inventory"));
            ModelLoader.setCustomModelResourceLocation(legs, 0,
                    new ModelResourceLocation("armorplus:RCLeggings", "inventory"));
            ModelLoader.setCustomModelResourceLocation(boots, 0,
                    new ModelResourceLocation("armorplus:RCBoots", "inventory"));
        }
        if (ConfigHandler.enableReinforcedArmorsRecipes) {
            GameRegistry.addRecipe(new ItemStack(helmet, 1), new Object[]
                    {"XXX", "RRR", "RIR", Character.valueOf('R'), new ItemStack(ModItems.REINFORCING_MATERIAL, 1), Character.valueOf('I'),
                            new ItemStack(Items.chainmail_helmet, 1),});
            GameRegistry.addRecipe(new ItemStack(helmet, 1), new Object[]
                    {"RRR", "RIR", "XXX", Character.valueOf('R'), new ItemStack(ModItems.REINFORCING_MATERIAL, 1), Character.valueOf('I'),
                            new ItemStack(Items.chainmail_helmet, 1),});
            GameRegistry.addRecipe(new ItemStack(chestplate, 1), new Object[]
                    {"RIR", "RRR", "RRR", Character.valueOf('R'), new ItemStack(ModItems.REINFORCING_MATERIAL, 1), Character.valueOf('I'),
                            new ItemStack(Items.chainmail_chestplate, 1),});
            GameRegistry.addRecipe(new ItemStack(legs, 1), new Object[]
                    {"RRR", "RIR", "RXR", Character.valueOf('R'), new ItemStack(ModItems.REINFORCING_MATERIAL, 1), Character.valueOf('I'),
                            new ItemStack(Items.chainmail_leggings, 1),});
            GameRegistry.addRecipe(new ItemStack(boots, 1), new Object[]
                    {"XXX", "RIR", "RXR", Character.valueOf('R'), new ItemStack(ModItems.REINFORCING_MATERIAL, 1), Character.valueOf('I'),
                            new ItemStack(Items.chainmail_boots, 1),});
            GameRegistry.addRecipe(new ItemStack(boots, 1), new Object[]
                    {"RIR", "RXR", "XXX", Character.valueOf('R'), new ItemStack(ModItems.REINFORCING_MATERIAL, 1), Character.valueOf('I'),
                            new ItemStack(Items.chainmail_boots, 1),});
            helmet.setCreativeTab(ArmorPlus.tabArmorPlus);
            chestplate.setCreativeTab(ArmorPlus.tabArmorPlus);
            legs.setCreativeTab(ArmorPlus.tabArmorPlus);
            boots.setCreativeTab(ArmorPlus.tabArmorPlus);
        }

    }

    public void preInit(FMLPreInitializationEvent event) {
        if (event.getSide() == Side.CLIENT) {
            ModelLoader.setCustomModelResourceLocation(helmet, 0,
                    new ModelResourceLocation("armorplus:RCHelmet", "inventory"));
            ModelLoader.setCustomModelResourceLocation(chestplate, 0,
                    new ModelResourceLocation("armorplus:RCChestplate", "inventory"));
            ModelLoader.setCustomModelResourceLocation(legs, 0,
                    new ModelResourceLocation("armorplus:RCLeggings", "inventory"));
            ModelLoader.setCustomModelResourceLocation(boots, 0,
                    new ModelResourceLocation("armorplus:RCBoots", "inventory"));
        }
    }

    public void registerRenderers() {
    }

    static {

        ItemArmor.ArmorMaterial enuma = EnumHelper.addArmorMaterial("RCARMOR", ArmorPlus.MODID + ":" + "RCArmor", 18, new int[]
                {2, 5, 6, 3}, 30, SoundEvents.item_armor_equip_chain);

        int armorPreffix = 0;
        helmet = (new ItemArmor(enuma, armorPreffix, EntityEquipmentSlot.HEAD) {
            public void onArmorTick(World world, EntityPlayer entity, ItemStack itemStack) {

            }
            public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
                return repair.getItem() == ModItems.CHAINMAIL;
            }
        }).setUnlocalizedName("RCHelmet");
        helmet.setMaxStackSize(1);
        chestplate = (new ItemArmor(enuma, armorPreffix, EntityEquipmentSlot.CHEST) {
            public void onArmorTick(World world, EntityPlayer entity, ItemStack itemStack)
            {
            }
            public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
                return repair.getItem() == ModItems.CHAINMAIL;
            }
        }).setUnlocalizedName("RCChestplate");
        chestplate.setMaxStackSize(1);
        legs = (new ItemArmor(enuma, armorPreffix, EntityEquipmentSlot.LEGS) {
            public void onArmorTick(World world, EntityPlayer entity, ItemStack itemStack)
            {

            }
            public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
                return repair.getItem() == ModItems.CHAINMAIL;
            }
        }).setUnlocalizedName("RCLeggings");
        legs.setMaxStackSize(1);
        boots = (new ItemArmor(enuma, armorPreffix, EntityEquipmentSlot.FEET) {
            public void onArmorTick(World world, EntityPlayer entity, ItemStack itemStack) {


            }
            public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
                return repair.getItem() == ModItems.CHAINMAIL;
            }
        }).setUnlocalizedName("RCBoots");
        boots.setMaxStackSize(1);
        GameRegistry.registerItem(helmet, "RCHelmet");
        GameRegistry.registerItem(chestplate, "RCChestplate");
        GameRegistry.registerItem(legs, "RCLeggings");
        GameRegistry.registerItem(boots, "RCBoots");

    }
}
