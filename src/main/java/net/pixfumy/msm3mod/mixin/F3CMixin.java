package net.pixfumy.msm3mod.mixin;

import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Locale;

@Mixin(Keyboard.class)
public class F3CMixin {
    @Shadow private MinecraftClient client;
    @Redirect(method = "processF3", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Keyboard;setClipboard(Ljava/lang/String;)V"))
    private void reduceF3CInfo(Keyboard instance, String string) {
        instance.setClipboard(String.format(Locale.ROOT,
                "%d %d %d",
                (int) this.client.player.getX(), (int) this.client.player.getY(), (int) this.client.player.getZ()));
    }
}
