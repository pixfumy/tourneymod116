package net.pixfumy.tourneymod116.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.world.MobSpawnerLogic;
import net.minecraft.world.World;
import net.pixfumy.tourneymod116.ILevelProperties;
import net.pixfumy.tourneymod116.RNGStreamGenerator;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.Random;

@Mixin(MobSpawnerLogic.class)
public abstract class BlazeSpawnsMixin {
    @Shadow public abstract World getWorld();

    @Shadow @Nullable protected abstract Identifier getEntityId();

    @Redirect(method = "update", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextDouble()D"),
            slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/ListTag;size()I"), to = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;doesNotCollide(Lnet/minecraft/util/math/Box;)Z")))
    private double modifySpawnXandZ(Random instance) {
        if (this.getEntityId().toString().equals("minecraft:blaze")) {
            RNGStreamGenerator rngStreamGenerator = ((ILevelProperties) this.getWorld().getServer().getOverworld().getLevelProperties()).getRNGStreamGenerator();
            return new Random(rngStreamGenerator.getAndUpdateSeed("blazeSpawnSeed")).nextDouble();
        }
        return instance.nextDouble();
    }

    @Redirect(method = "update", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I"))
    private int modifySpawnY(Random instance, int bound) {
        if (this.getEntityId().toString().equals("minecraft:blaze")) {
            RNGStreamGenerator rngStreamGenerator = ((ILevelProperties) this.getWorld().getServer().getOverworld().getLevelProperties()).getRNGStreamGenerator();
            return new Random(rngStreamGenerator.getAndUpdateSeed("blazeSpawnSeed")).nextInt(bound);
        }
        return instance.nextInt(bound);
    }

    @Redirect(method = "updateSpawns", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I"))
    private int setCooldownTimer(Random instance, int bound) {
        if (this.getEntityId().toString().equals("minecraft:blaze")) {
            RNGStreamGenerator rngStreamGenerator = ((ILevelProperties) this.getWorld().getServer().getOverworld().getLevelProperties()).getRNGStreamGenerator();
            return new Random(rngStreamGenerator.getAndUpdateSeed("blazeSpawnSeed")).nextInt(bound);
        }
        return instance.nextInt(bound);
    }
}
