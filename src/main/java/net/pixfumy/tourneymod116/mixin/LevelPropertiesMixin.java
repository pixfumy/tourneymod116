package net.pixfumy.tourneymod116.mixin;

import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.Lifecycle;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.registry.RegistryTracker;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.level.LevelInfo;
import net.minecraft.world.level.LevelProperties;
import net.minecraft.world.level.storage.SaveVersionInfo;
import net.pixfumy.tourneymod116.ILevelProperties;
import net.pixfumy.tourneymod116.RNGStreamGenerator;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(LevelProperties.class)
public abstract class LevelPropertiesMixin implements ILevelProperties {
    @Shadow public abstract GeneratorOptions getGeneratorOptions();

    private RNGStreamGenerator rngStreamGenerator;

    @Inject(method = "method_29029", at = @At("RETURN"))
    private static void readRNGSeedsFromNBT(Dynamic<Tag> dynamic, DataFixer dataFixer, int i, @Nullable CompoundTag compoundTag, LevelInfo levelInfo, SaveVersionInfo saveVersionInfo, GeneratorOptions generatorOptions, Lifecycle lifecycle, CallbackInfoReturnable<LevelProperties> ci) {
        LevelProperties levelProperties = ci.getReturnValue();
        long worldSeed = generatorOptions.getSeed();
        RNGStreamGenerator rngStreamGenerator = new RNGStreamGenerator(worldSeed);
        ((ILevelProperties)levelProperties).setRNGStreamGenerator(rngStreamGenerator);
        for (Map.Entry<String, Long> mapEntry: rngStreamGenerator.entrySet()) {
            String key = mapEntry.getKey();
            Long value = mapEntry.getValue();
            rngStreamGenerator.setSeed(key, dynamic.get(key).asLong(value));
        }
    }

    @Inject(method = "updateProperties", at = @At("TAIL"))
    private void writeRNGSeedsToNBT(RegistryTracker registryTracker, CompoundTag compoundTag, CompoundTag compoundTag2, CallbackInfo ci) {
        if (rngStreamGenerator == null) {
            rngStreamGenerator = new RNGStreamGenerator(this.getGeneratorOptions().getSeed());
        }
        for (Map.Entry<String, Long> mapEntry : rngStreamGenerator.entrySet()) {
            String key = mapEntry.getKey();
            Long value = mapEntry.getValue();
            compoundTag.putLong(key, value);
        }
    }

    @Override
    public RNGStreamGenerator getRNGStreamGenerator() {
        return this.rngStreamGenerator;
    }

    @Override
    public void setRNGStreamGenerator(RNGStreamGenerator rngStreamGenerator) {
        this.rngStreamGenerator = rngStreamGenerator;
    }
}
