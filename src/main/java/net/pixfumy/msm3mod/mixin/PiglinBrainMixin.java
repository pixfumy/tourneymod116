package net.pixfumy.msm3mod.mixin;

import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentStateManager;
import net.pixfumy.msm3mod.RNGStream;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(PiglinBrain.class)
public class PiglinBrainMixin {
    /**
     * @author Pixfumy
     * @reason Standardize Piglin Barters whilst standing on the shoulders of Duncan runs
     */
    @Overwrite
    private static List<ItemStack> getBarteredItem(@NotNull PiglinEntity piglin) {
        PersistentStateManager persistentStateManager = piglin.world.getServer().getOverworld().getPersistentStateManager();
        RNGStream piglinStream = persistentStateManager.getOrCreate(
                () -> {return new RNGStream("piglin", piglin.world.getServer().getOverworld().getSeed() ^ 47195191252716912L);}, "piglin");
        LootTable lootTable = piglin.world.getServer().getLootManager().getTable(LootTables.PIGLIN_BARTERING_GAMEPLAY);
        List<ItemStack> list = lootTable.generateLoot((new LootContext.Builder((ServerWorld) piglin.world)).parameter(LootContextParameters.THIS_ENTITY, piglin).random(piglinStream.getAndUpdateSeed()).build(LootContextTypes.BARTER));
        piglinStream.markDirty();
        return list;
    }
}
