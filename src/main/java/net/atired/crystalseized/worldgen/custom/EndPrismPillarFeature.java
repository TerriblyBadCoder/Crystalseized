package net.atired.crystalseized.worldgen.custom;

import com.mojang.serialization.Codec;
import net.atired.crystalseized.blocks.CSblockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class EndPrismPillarFeature extends Feature<NoneFeatureConfiguration> {
    public EndPrismPillarFeature(Codec<NoneFeatureConfiguration> p_65190_) {
        super(p_65190_);
    }

    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> p_159446_) {
        BlockPos pos = p_159446_.origin();
        WorldGenLevel $$2 = p_159446_.level();
        RandomSource randomSource = p_159446_.random();

        if ($$2.isEmptyBlock(pos) && !$$2.isEmptyBlock(pos.above())) {
        for(int i = 0; i < 60; i ++)
        {
            int counter = 0;
            if ($$2.isEmptyBlock(pos) && !$$2.isEmptyBlock(pos.above())) {
            BlockPos.MutableBlockPos $$4 = pos.mutable();
            BlockPos.MutableBlockPos $$5 = pos.mutable();
            boolean $$6 = true;
            boolean $$7 = true;
            boolean $$8 = true;
            boolean $$9 = true;

            while($$2.isEmptyBlock($$4) && counter<(15-i/8)) {
                if ($$2.isOutsideBuildHeight($$4)) {
                    break;
                }

                counter+=1;
                $$2.setBlock($$4, CSblockRegistry.END_CRYSTAL.get().defaultBlockState(), 3);
                $$6 = $$6 && this.placeHangOff($$2, randomSource, $$5.setWithOffset($$4, Direction.NORTH),i);
                $$7 = $$7 && this.placeHangOff($$2, randomSource, $$5.setWithOffset($$4, Direction.SOUTH),i);
                $$8 = $$8 && this.placeHangOff($$2, randomSource, $$5.setWithOffset($$4, Direction.WEST),i);
                $$9 = $$9 && this.placeHangOff($$2, randomSource, $$5.setWithOffset($$4, Direction.EAST),i);
                $$4.move(Direction.DOWN);
                }
            }

            pos = pos.offset(randomSource.nextInt(3)-1,randomSource.nextInt(3)-1,randomSource.nextInt(3)-1);
        }
            return true;
        } else {
            return false;
        }
    }
    private boolean placeHangOff(LevelAccessor levelAccessor, RandomSource randomSource, BlockPos blockPos, int count) {
        if (randomSource.nextInt(Math.max(2,7-count/8)) > 0 && !levelAccessor.isEmptyBlock(blockPos.above())) {
            levelAccessor.setBlock(blockPos, CSblockRegistry.END_CRYSTAL.get().defaultBlockState(), 3);
            return true;
        } else {
            return false;
        }
    }
}