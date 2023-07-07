package net.pixfumy.tourneymod116.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.pixfumy.tourneymod116.ILevelProperties;
import net.pixfumy.tourneymod116.RNGStreamGenerator;
import net.pixfumy.tourneymod116.SeedfinderUtil;

import java.io.IOException;
import java.util.Random;

public class UpdateRNGSeedCommand {
    public static String[] seeds = {"blazeRodSeed", "blazeSpawnSeed", "enderEyeSeed", "flintSeed", "piglinSeed"};
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> literalArgumentBuilder = (LiteralArgumentBuilder) CommandManager
                .literal("resetRNGSeed")
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2));
        for (String seed : seeds) {
            literalArgumentBuilder.then(CommandManager.literal(seed)
                    .executes(context -> execute(context, seed))
            );
        }
        dispatcher.register(literalArgumentBuilder);
    }

    private static int execute(CommandContext<ServerCommandSource> context, String rngSeed) throws CommandSyntaxException {
        ServerWorld overWorld = context.getSource().getWorld().getServer().getOverworld();
        RNGStreamGenerator rngStreamGenerator = ((ILevelProperties)(overWorld).getLevelProperties()).getRNGStreamGenerator();
        if (!rngStreamGenerator.keySet().contains(rngSeed)) {
            return 1;
        }
        rngStreamGenerator.setSeed(rngSeed, (new Random()).nextLong());
        context.getSource().getPlayer().sendMessage(new LiteralText("RNGSeed " + rngSeed + " updated."), true);
        return 0;
    }
}
