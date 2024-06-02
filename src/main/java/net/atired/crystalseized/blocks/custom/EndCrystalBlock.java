package net.atired.crystalseized.blocks.custom;

import net.atired.crystalseized.blocks.CSblockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.carver.CanyonCarverConfiguration;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;

public class EndCrystalBlock extends AbstractGlassBlock
        implements BeaconBeamBlock{

    public EndCrystalBlock(Properties p_48729_) {
        super(p_48729_);
    }

    @Override
    public @Nullable float[] getBeaconColorMultiplier(BlockState state, LevelReader level, BlockPos pos, BlockPos beaconPos) {
        Color color = getEndCrystalColor(pos);
        float[] array;
        array = new float[]{color.getRed()/255f,color.getGreen()/255f,color.getBlue()/255f};
        return array;
    }
    public static Color getEndCrystalColor( BlockPos pos)
    {

        float colourhue = (((float)pos.getX() +(float)pos.getY()+ Mth.sin(((float)pos.getZ() + (float)pos.getX()) / 35) * 35) % 200) / 200;
        Color colour = Color.getHSBColor(colourhue, 0.5F, 1F);
        return colour;
    }

    @Override
    public DyeColor getColor() {
        return DyeColor.BLUE;
    }
}

