package gcewing.sg.ic2.zpm;

import gcewing.sg.SGCraft;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;

import javax.annotation.Nullable;

public class ZpmInterfaceCart extends BlockContainer {
  public ZpmInterfaceCart(final Material material) {
    super(material);
  }

  @Nullable
  @Override
  public TileEntity createNewTileEntity(final World world, final int meta) {
    return new ZpmInterfaceCartTE();
  }

  @Override
  public boolean isFullBlock(IBlockState state) { //Render surrounding blocks which connect..
    return false;
  }

  @Override
  public EnumBlockRenderType getRenderType(IBlockState state) {
    return EnumBlockRenderType.MODEL;
  }

  @Deprecated
  @Override
  public IBlockState getStateForPlacement(final World world, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
    return this.getDefaultState().withProperty(BlockHorizontal.FACING, placer.getHorizontalFacing().getOpposite());
  }

  @Deprecated
  @Override
  public IBlockState getStateFromMeta(final int meta) {
    return this.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.getHorizontal(meta));
  }

  @Override
  public int getMetaFromState(final IBlockState state) {
    return state.getValue(BlockHorizontal.FACING).getHorizontalIndex();
  }

  @Override
  public boolean isOpaqueCube(IBlockState state) { //Render surrounding block that don't touch.
    return false; //
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, BlockHorizontal.FACING);
  }

  @Override
  protected boolean hasInvalidNeighbor(World worldIn, BlockPos pos) {
    return false;
  }

  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX,
                                  float hitY, float hitZ) {
    if (!world.isRemote) {
      super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
      FMLNetworkHandler.openGui(player, SGCraft.mod, 0, world, pos.getX(), pos.getY(), pos.getZ());
    }
    return true;
  }
}