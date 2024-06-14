package net.atired.crystalseized.worldgen.custom;

import com.mojang.serialization.Codec;
import net.atired.crystalseized.blocks.CSblockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;

public class HydrogenPatchFeature extends Feature<NoneFeatureConfiguration> {
    public HydrogenPatchFeature(Codec<NoneFeatureConfiguration> p_65701_) {
        super(p_65701_);
    }

    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> p_159717_) {
        WorldGenLevel $$1 = p_159717_.level();
        RandomSource $$2 = p_159717_.random();
        $$2.setSeed($$1.getSeed());
        BlockPos $$3 = p_159717_.origin();
        float $$4 = (float)$$2.nextInt(3) + 4.0F;
        PerlinNoise perlinNoise = PerlinNoise.create($$2,1,1,0.05,0.05);
        for(int $$5 = 2; $$4 > 0.5F; --$$5) {
            for(int $$6 = Mth.floor(-$$4); $$6 <= Mth.ceil($$4); ++$$6) {
                for(int $$7 = Mth.floor(-$$4); $$7 <= Mth.ceil($$4); ++$$7) {
                    if ((float)($$6 * $$6 + $$7 * $$7) <= ($$4 + 1.0F) * ($$4 + 1.0F)) {
                        if($$1.getBlockState($$3).getTags().toList().contains(BlockTags.REPLACEABLE))
                        {
                            if(perlinNoise.getValue(($$6+$$3.getX())/100f,($$5+$$3.getY())/100f,($$7+$$3.getZ())/100f)>0.02f)
                                this.setBlock($$1, $$3.offset($$6, $$5, $$7), CSblockRegistry.HYDROGEN_BLOCK.get().defaultBlockState());
                            else
                                this.setBlock($$1, $$3.offset($$6, $$5, $$7), CSblockRegistry.OZONE_BLOCK.get().defaultBlockState());
                        }

                    }
                }
            }
            int i =0;
            if($$5!=0)
            {
                i = Math.abs($$5)/($$5);
            }

            $$4 += ((float)$$2.nextInt(2) + 0.5F)*i;
        }

        return true;
    }
}
