package net.pixfumy.tourneymod116.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.util.Identifier;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;
import net.pixfumy.tourneymod116.ILevelProperties;
import net.pixfumy.tourneymod116.RNGStreamGenerator;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlazeEntity.class)
public class BlazeDropsMixin extends HostileEntity {
    protected BlazeDropsMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void dropLoot(DamageSource source, boolean causedByPlayer) {
        RNGStreamGenerator rngStreamGenerator = ((ILevelProperties)this.world.getServer().getOverworld().getLevelProperties()).getRNGStreamGenerator();
        Identifier identifier = this.getLootTable();
        LootTable lootTable = this.world.getServer().getLootManager().getTable(identifier);
        LootContext.Builder builder = this.getLootContextBuilder(causedByPlayer, source).random(rngStreamGenerator.getAndUpdateSeed("blazeRodSeed"));
        lootTable.generateLoot(builder.build(LootContextTypes.ENTITY), this::dropStack);
    }
}