package net.pixfumy.msm3mod.mixin;

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
import net.pixfumy.msm3mod.RNGStream;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlazeEntity.class)
public class BlazeDropsMixin extends HostileEntity {
    protected BlazeDropsMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void dropLoot(DamageSource source, boolean causedByPlayer) {
        PersistentStateManager persistentStateManager = this.getServer().getOverworld().getPersistentStateManager();
        RNGStream blazeStream = persistentStateManager.getOrCreate(
                () -> {return new RNGStream("blaze", this.getServer().getOverworld().getSeed() ^ 7812357104191267L);}, "blaze");
        Identifier identifier = this.getLootTable();
        LootTable lootTable = this.world.getServer().getLootManager().getTable(identifier);
        LootContext.Builder builder = this.getLootContextBuilder(causedByPlayer, source).random(blazeStream.getAndUpdateSeed());
        blazeStream.markDirty();
        lootTable.generateLoot(builder.build(LootContextTypes.ENTITY), this::dropStack);
    }
}