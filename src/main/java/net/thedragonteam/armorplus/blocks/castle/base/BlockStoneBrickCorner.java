/*
 * Copyright (c) TheDragonTeam 2016-2017.
 */

package net.thedragonteam.armorplus.blocks.castle.base;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.thedragonteam.armorplus.ArmorPlus;
import net.thedragonteam.armorplus.blocks.castle.StoneBricks;

import static net.thedragonteam.armorplus.util.Utils.setName;

// TODO: fix CollisionBox to mach Shape (not any time soon)
public class BlockStoneBrickCorner extends BlockStairs {

    private StoneBricks stoneBricks;

    public BlockStoneBrickCorner(StoneBricks stoneBricks, IBlockState modelState) {
        super(modelState);
        setUnlocalizedName(setName(stoneBricks.getName() + "_stone_brick_corner"));
        setRegistryName(stoneBricks.getName() + "_stone_brick_corner");
        setHardness(10.0F);
        setResistance(5.0F);
        setHarvestLevel("pickaxe", 0);
        setLightOpacity(255);
        this.stoneBricks = stoneBricks;
        GameRegistry.register(this);
        GameRegistry.register(new ItemBlock(this), getRegistryName());
        setCreativeTab(ArmorPlus.tabArmorplusBlocks);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    /**
     * Get the MapColor for this Block and the given BlockState
     */
    public MapColor getMapColor(IBlockState state) {
        return stoneBricks.getMapColor();
    }
}
