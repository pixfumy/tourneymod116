package net.pixfumy.msm3mod.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(MobEntity.class)
public class TempleMobSpawnMixin {
    @Inject(method = "canMobSpawn", at = @At("HEAD"), cancellable = true)
    private static void checkIfInTemple(EntityType<? extends MobEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> cir) {
        BlockPos blockPos = pos.down();
        Block block = world.getWorld().getBlockState(blockPos).getBlock();
        long timeOfDay = world.getWorld().getTimeOfDay();
        if (timeOfDay % 24000 < 13000 && (block == Blocks.SANDSTONE || block == Blocks.TERRACOTTA)) {
            cir.setReturnValue(false);
        }
    }
}
