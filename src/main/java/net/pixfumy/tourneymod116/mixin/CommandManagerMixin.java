package net.pixfumy.tourneymod116.mixin;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.GameModeCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.pixfumy.tourneymod116.command.RatesCommand;
import net.pixfumy.tourneymod116.command.UpdateRNGSeedCommand;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CommandManager.class)
public class CommandManagerMixin {
    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/command/GameModeCommand;register(Lcom/mojang/brigadier/CommandDispatcher;)V"))
    private void registerRatesCommand(CommandDispatcher<ServerCommandSource> dispatcher) {
        GameModeCommand.register(dispatcher);
        RatesCommand.register(dispatcher);
        UpdateRNGSeedCommand.register(dispatcher);
    }
}
