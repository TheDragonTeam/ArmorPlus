/*
 * Copyright (c) TheDragonTeam 2016-2017.
 */

package net.thedragonteam.armorplus.blocks.castle.base

import net.minecraft.block.BlockWall
import net.minecraft.block.material.MapColor
import net.minecraft.block.state.IBlockState
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemStack
import net.minecraft.util.NonNullList
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import net.thedragonteam.armorplus.ArmorPlus
import net.thedragonteam.armorplus.iface.IModelHelper
import net.thedragonteam.armorplus.util.Utils.setName

class BlockStonebrickWall(private val stoneBrick: BlockStoneBrick) : BlockWall(stoneBrick), IModelHelper {

    init {
        this.setRegistryName(stoneBrick.name + "_wall")
        this.unlocalizedName = setName(stoneBrick.name + "_wall")
        this.setResistance(10f)
        this.setHardness(5f)
        this.setCreativeTab(ArmorPlus.tabArmorplusBlocks)
        GameRegistry.register(this)
        GameRegistry.register(ItemBlock(this), registryName)
    }

    @Suppress("OverridingDeprecatedMember")
    override fun getMapColor(state: IBlockState?): MapColor {
        return this.stoneBrick.color
    }

    @SideOnly(Side.CLIENT)
    override fun getSubBlocks(itemIn: Item, tab: CreativeTabs?, list: NonNullList<ItemStack>) {
        list.add(ItemStack(itemIn, 1))
    }

    override fun initModel() {
        this.initModel(this, registryName, 0)
    }
}