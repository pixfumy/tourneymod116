package net.pixfumy.tourneymod116.mixin;

import net.minecraft.entity.Entity;
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
import net.minecraft.world.World;
import net.pixfumy.tourneymod116.ILevelProperties;
import net.pixfumy.tourneymod116.RNGStreamGenerator;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.List;

@Mixin(PiglinBrain.class)
public class PiglinBrainMixin {
    /**
     * @author Pixfumy
     * @reason Standardize Piglin Barters whilst standing on the shoulders of Duncan runs
     */
    @Overwrite
    private static List<ItemStack> getBarteredItem(@NotNull PiglinEntity piglin) {
        RNGStreamGenerator rngStreamGenerator = ((ILevelProperties)piglin.world.getServer().getOverworld().getLevelProperties()).getRNGStreamGenerator();
        LootTable lootTable = piglin.world.getServer().getLootManager().getTable(LootTables.PIGLIN_BARTERING_GAMEPLAY);
        List<ItemStack> list = lootTable.generateLoot((new LootContext.Builder((ServerWorld) piglin.world)).parameter(LootContextParameters.THIS_ENTITY, piglin).random(rngStreamGenerator.getAndUpdateSeed("piglinSeed")).build(LootContextTypes.BARTER));
        return list;
    }
}
