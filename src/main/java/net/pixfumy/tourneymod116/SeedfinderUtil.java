package net.pixfumy.tourneymod116;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.GravelBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameter;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.pixfumy.tourneymod116.mixin.access.LivingEntityAccess;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class SeedfinderUtil {
    public static void tellPlayerCurrentRates(ServerPlayerEntity player, ServerWorld world) {
        ServerWorld overWorld = world.getServer().getOverworld();
        RNGStreamGenerator originalRNGStreamGenerator = ((ILevelProperties)overWorld.getLevelProperties()).getRNGStreamGenerator();
        RNGStreamGenerator dummyRNGStreamGenerator = new RNGStreamGenerator(overWorld.getSeed());

        for (Map.Entry<String, Long> mapEntry : originalRNGStreamGenerator.entrySet()) {
            dummyRNGStreamGenerator.setSeed(mapEntry.getKey(), mapEntry.getValue());
        }

        AtomicInteger blazeRods = new AtomicInteger();
        int blazeRolls = 0;
        while (blazeRods.get() < 7) {
            BlazeEntity blazeEntity = new BlazeEntity(EntityType.BLAZE, overWorld); // holy spaghetti (mojang's fault for abstracting this too much)
            Identifier identifier = blazeEntity.getLootTable();
            LootTable lootTable = world.getServer().getLootManager().getTable(identifier);
            LootContext.Builder builder = ((LivingEntityAccess)blazeEntity).invokeGetLootContextBuilder(true, DamageSource.GENERIC)
                    .parameter(LootContextParameters.LAST_DAMAGE_PLAYER, player)
                    .random(dummyRNGStreamGenerator.getAndUpdateSeed("blazeRodSeed"));
            lootTable.generateLoot(builder.build(LootContextTypes.ENTITY), itemStack -> blazeRods.addAndGet(itemStack.getCount()));
            blazeRolls++;
        }

        boolean flint = false;
        int flintRolls = 0;
        while (!flint) {
            BlockState gravelDefaultState = Blocks.GRAVEL.getDefaultState();
            LootContext.Builder builder = new LootContext.Builder(overWorld).random(dummyRNGStreamGenerator.getAndUpdateSeed("flintSeed"))
                    .parameter(LootContextParameters.POSITION, player.getBlockPos())
                    .parameter(LootContextParameters.TOOL, new ItemStack(Items.NETHERITE_SHOVEL));
            List<ItemStack> droppedItems = gravelDefaultState.getDroppedStacks(builder);
            if (droppedItems.stream().anyMatch(itemStack -> itemStack.getItem() == Items.FLINT)) {
                flint = true;
            }
            flintRolls++;
        }

        boolean eyeBreak = false;
        int eyeThrows = 0;
        while (!eyeBreak) {
            eyeBreak = new Random(dummyRNGStreamGenerator.getAndUpdateSeed("enderEyeSeed")).nextInt(5) == 0;
            eyeThrows++;
        }

        AtomicInteger pearls = new AtomicInteger(), string = new AtomicInteger(), obsidian = new AtomicInteger();
        int pearlRolls = 0, stringRolls = 0, obsidianRolls = 0;
        while (pearls.get() < 12 || string.get() < 60 || obsidian.get() < 10) {
            if (pearls.get() < 12) {
                pearlRolls++;
            }
            if (obsidian.get() < 10) {
                obsidianRolls++;
            }
            if (string.get() < 60) {
                stringRolls++;
            }
            PiglinEntity piglin = new PiglinEntity(EntityType.PIGLIN, overWorld);
            LootTable lootTable = piglin.world.getServer().getLootManager().getTable(LootTables.PIGLIN_BARTERING_GAMEPLAY);
            lootTable.generateLoot((new LootContext.Builder((ServerWorld) piglin.world))
                .parameter(LootContextParameters.THIS_ENTITY, piglin)
                .random(dummyRNGStreamGenerator.getAndUpdateSeed("piglinSeed"))
                .build(LootContextTypes.BARTER),
                itemStack -> {
                    if (itemStack.getItem() == Items.ENDER_PEARL) {
                        pearls.addAndGet(itemStack.getCount());
                    } else if (itemStack.getItem() == Items.OBSIDIAN) {
                        obsidian.addAndGet(itemStack.getCount());
                    } else if (itemStack.getItem() == Items.STRING) {
                        string.addAndGet(itemStack.getCount());
                    }
                });
        }

        player.sendMessage(new LiteralText(""), false);
        player.sendMessage(new LiteralText(String.format("Current rates on this world: Blazes: %d/%d, First flint will be after mining %d gravel, First ender eye will break after %d throws,", blazeRods.get(), blazeRolls, flintRolls, eyeThrows)), false);
        player.sendMessage(new LiteralText(String.format("You'll get 12 pearls in %d trades, 10 obby in %d trades, and 60 string in %d trades.", pearlRolls, obsidianRolls, stringRolls)), false);
    }
}
