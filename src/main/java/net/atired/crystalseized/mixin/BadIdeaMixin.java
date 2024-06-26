package net.atired.crystalseized.mixin;

import net.atired.crystalseized.entities.CSentityRegistry;
import net.atired.crystalseized.entities.custom.StarFallEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.AirItem;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.level.storage.WritableLevelData;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

@Mixin(ServerLevel.class)
public abstract class BadIdeaMixin extends Level{
    @Unique
    public int starFallTime = 0;
    @Shadow protected abstract BlockPos findLightningTargetAround(BlockPos p_143289_);

    @Shadow public abstract boolean addFreshEntity(Entity p_8837_);


    @Shadow public abstract DimensionDataStorage getDataStorage();

    @Shadow @Final private MinecraftServer server;

    @Shadow @Nonnull public abstract MinecraftServer getServer();

    @Shadow @Final private ServerLevelData serverLevelData;

    @Shadow public abstract List<ServerPlayer> players();



    protected BadIdeaMixin(WritableLevelData p_270739_, ResourceKey<Level> p_270683_, RegistryAccess p_270200_, Holder<DimensionType> p_270240_, Supplier<ProfilerFiller> p_270692_, boolean p_270904_, boolean p_270470_, long p_270248_, int p_270466_) {
        super(p_270739_, p_270683_, p_270200_, p_270240_, p_270692_, p_270904_, p_270470_, p_270248_, p_270466_);
    }
    @Inject(method = "tick", at = @At(value =  "HEAD"))
    public void starFallWeatherTick(BooleanSupplier p_8794_, CallbackInfo ci) {
        if (this.random.nextInt(4000) == 0 && starFallTime ==0) {
            this.starFallTime = this.random.nextInt(500,750);
        }
        if(this.starFallTime >0)
        {
            this.starFallTime -=1;
        }
    }
    @Inject(method = "tickChunk", at = @At(value =  "HEAD"))
    public void starFallTick(LevelChunk p_8715_, int p_8716_, CallbackInfo ci)
    {
        ChunkPos chunkpos = p_8715_.getPos();
        boolean flag = this.isRaining();
        int i = chunkpos.getMinBlockX();
        int j = chunkpos.getMinBlockZ();
        BlockPos blockpos1;
        int randumb = 50000;
        if(this.starFallTime >0)
        {
            randumb = 250;
        }
        if (this.random.nextInt(randumb) == 0) {
            blockpos1 = this.findLightningTargetAround(this.getBlockRandomPos(i, 0, j, 15));
            if(getBiome(blockpos1).getTagKeys().toList().contains(BiomeTags.IS_END)&&getBiome(blockpos1).unwrapKey().get()!= Biomes.THE_END)
            {
                StarFallEntity starFallEntity = new StarFallEntity(CSentityRegistry.STARFALL.get(),this,(float)Math.random());
                starFallEntity.moveTo(Vec3.atBottomCenterOf(blockpos1.atY(255)));
                this.addFreshEntity(starFallEntity);
            }
        }
    }

}
