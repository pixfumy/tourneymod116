package net.pixfumy.tourneymod116.mixin.access;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.loot.context.LootContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntity.class)
public interface LivingEntityAccess {
    @Invoker
    LootContext.Builder invokeGetLootContextBuilder(boolean causedByPlayer, DamageSource source);

}
