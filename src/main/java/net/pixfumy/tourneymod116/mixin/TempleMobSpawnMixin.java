package net.pixfumy.tourneymod116.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.StructureFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Iterator;
import java.util.Random;

@Mixin(MobEntity.class)
public class TempleMobSpawnMixin {
    @Inject(method = "canMobSpawn", at = @At("HEAD"), cancellable = true)
    private static void checkIfInTemple(EntityType<? extends MobEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> cir) {
        if (world instanceof ServerWorld) {
            Iterator structuresContainingThis = ((ServerWorld) world).getStructureAccessor().getStructuresWithChildren(ChunkSectionPos.from(pos), StructureFeature.DESERT_PYRAMID).iterator();
            if (structuresContainingThis.hasNext()) {
                cir.setReturnValue(false);
            }
        }
    }
}
