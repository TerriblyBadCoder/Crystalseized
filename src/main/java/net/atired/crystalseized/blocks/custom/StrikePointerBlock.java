package net.atired.crystalseized.blocks.custom;

import net.atired.crystalseized.blocks.CSblockRegistry;
import net.atired.crystalseized.entities.CSblockEntityRegistry;
import net.atired.crystalseized.entities.custom.blockentity.StrikePointerBlockEntity;
import net.atired.crystalseized.networking.ModMessages;
import net.atired.crystalseized.networking.packets.RangeStarerS2Cpacket;
import net.minecraft.client.renderer.blockentity.EnchantTableRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.EnchantmentTableBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class StrikePointerBlock extends RodBlock implements EntityBlock {
    public static final BooleanProperty POWERED;
    public StrikePointerBlock(BlockBehaviour.Properties p_154339_) {
        super(p_154339_);
        this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.UP).setValue(POWERED,false));
    }
    public BlockState getStateForPlacement(BlockPlaceContext p_53087_) {
        Direction $$1 = p_53087_.getClickedFace();
        BlockState $$2 = p_53087_.getLevel().getBlockState(p_53087_.getClickedPos().relative($$1.getOpposite()));
        return $$2.is(this) && $$2.getValue(FACING) == $$1 ? (BlockState)this.defaultBlockState().setValue(FACING, $$1.getOpposite()) : (BlockState)this.defaultBlockState().setValue(FACING, $$1);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_153746_) {
        p_153746_.add(new Property[]{FACING,POWERED});

    }

    @Override
    public InteractionResult use(BlockState p_60503_, Level p_60504_, BlockPos p_60505_, Player p_60506_, InteractionHand p_60507_, BlockHitResult p_60508_) {
        BlockEntity $$3 = p_60504_.getBlockEntity(p_60505_);
        if($$3 instanceof StrikePointerBlockEntity strikePointerBlockEntity && p_60507_ == InteractionHand.MAIN_HAND)
        {
            if(!p_60506_.isCrouching())
            {
                strikePointerBlockEntity.range+=1;
            }
            else
            {
                strikePointerBlockEntity.range+=7;
            }
            strikePointerBlockEntity.range%=8;
            p_60506_.swing(p_60507_);
        }
        return super.use(p_60503_, p_60504_, p_60505_, p_60506_, p_60507_, p_60508_);
    }

    @javax.annotation.Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153182_, BlockState p_153183_, BlockEntityType<T> p_153184_) {
        return  createTickerHelper(p_153184_, CSblockEntityRegistry.STRIKE_POINTER.get(), StrikePointerBlockEntity::tick);
    }
    @javax.annotation.Nullable
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> p_152133_, BlockEntityType<E> p_152134_, BlockEntityTicker<? super E> p_152135_) {
        return p_152134_ == p_152133_ ? (BlockEntityTicker<A>) p_152135_ : null;
    }

    @Override
    public RenderShape getRenderShape(BlockState p_60550_) {
        return RenderShape.MODEL;

    }
    public void onRemove(BlockState p_153728_, Level p_153729_, BlockPos p_153730_, BlockState p_153731_, boolean p_153732_) {
        if (!p_153728_.is(p_153731_.getBlock())) {
            if ((Boolean)p_153728_.getValue(POWERED)) {
                this.updateNeighbours(p_153728_, p_153729_, p_153730_);
            }
            super.onRemove(p_153728_, p_153729_, p_153730_, p_153731_, p_153732_);
        }
    }
    public int getSignal(BlockState p_153723_, BlockGetter p_153724_, BlockPos p_153725_, Direction p_153726_) {
        return (Boolean)p_153723_.getValue(POWERED) ? 15 : 0;
    }

    public int getDirectSignal(BlockState p_153748_, BlockGetter p_153749_, BlockPos p_153750_, Direction p_153751_) {
        return (Boolean)p_153748_.getValue(POWERED) && p_153748_.getValue(FACING) == p_153751_ ? 15 : 0;
    }
    public boolean isPowered(BlockState state)
    {
        return state.getValue(POWERED);
    }
    public void setPowered(BlockState state, ServerLevel getter, BlockPos pos, boolean bool)
    {
        getter.setBlock(pos, (BlockState)state.setValue(POWERED, bool), 3);
        this.updateNeighbours(state, getter, pos);
    }
    private void updateNeighbours(BlockState p_153765_, Level p_153766_, BlockPos p_153767_) {
        p_153766_.updateNeighborsAt(p_153767_.relative(((Direction)p_153765_.getValue(FACING)).getOpposite()), this);
    }
    public void onPlace(BlockState p_153753_, Level p_153754_, BlockPos p_153755_, BlockState p_153756_, boolean p_153757_) {
        if (!p_153753_.is(p_153756_.getBlock())) {
            if ((Boolean)p_153753_.getValue(POWERED) && !p_153754_.getBlockTicks().hasScheduledTick(p_153755_, this)) {
                p_153754_.setBlock(p_153755_, (BlockState)p_153753_.setValue(POWERED, false), 18);
            }
        }
    }
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new StrikePointerBlockEntity(blockPos,blockState);
    }
    public boolean isSignalSource(BlockState p_153769_) {
        return true;
    }

    static {
        POWERED = BlockStateProperties.POWERED;
    }
}
