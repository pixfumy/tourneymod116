package net.pixfumy.tourneymod116.mixin;

import net.minecraft.client.resource.SplashTextResourceSupplier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SplashTextResourceSupplier.class)
public class SplashTextMixin {
    @Inject(method = "get", at = @At("HEAD"), cancellable = true)
    private void getSplash(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue("Subscribe to Cscuile!");
    }
}
