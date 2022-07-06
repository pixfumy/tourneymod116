package net.pixfumy.msm3mod.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import net.pixfumy.msm3mod.MSM3Mod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IronGolemEntity.class)
public class IronGolemDropsMixin extends GolemEntity {
    protected IronGolemDropsMixin(EntityType<? extends GolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void dropLoot(DamageSource source, boolean causedByPlayer) {
        ((Entity)(Object)this).dropStack(new ItemStack(Items.IRON_INGOT, 4));
    }
}
