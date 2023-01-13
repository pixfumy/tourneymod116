package net.pixfumy.tourneymod116.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EyeOfEnderEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;
import net.pixfumy.tourneymod116.RNGStream;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(EyeOfEnderEntity.class)
public class EyeOfEnderMixin {
    @Shadow private boolean dropsItem;

    @Inject(method = "moveTowards", at = @At(value = "TAIL"))
    private void setDropsItem(BlockPos pos, CallbackInfo ci) {
        World world = ((Entity)(Object)this).world;
        PersistentStateManager persistentStateManager = world.getServer().getOverworld().getPersistentStateManager();
        RNGStream enderEyeStream = persistentStateManager.getOrCreate(
                () -> {return new RNGStream("eye", world.getServer().getOverworld().getSeed() ^ 1908417367458294L);}, "eye");
        dropsItem = new Random(enderEyeStream.getAndUpdateSeed()).nextInt(5) > 0;
        enderEyeStream.markDirty();
    }
}
