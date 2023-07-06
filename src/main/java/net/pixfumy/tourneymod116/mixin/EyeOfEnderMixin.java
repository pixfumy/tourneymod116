package net.pixfumy.tourneymod116.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EyeOfEnderEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;
import net.pixfumy.tourneymod116.ILevelProperties;
import net.pixfumy.tourneymod116.RNGStreamGenerator;
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
        RNGStreamGenerator rngStreamGenerator = ((ILevelProperties)world.getServer().getOverworld().getLevelProperties()).getRNGStreamGenerator();
        dropsItem = new Random(rngStreamGenerator.getAndUpdateSeed("enderEyeSeed")).nextInt(5) > 0;
    }
}
