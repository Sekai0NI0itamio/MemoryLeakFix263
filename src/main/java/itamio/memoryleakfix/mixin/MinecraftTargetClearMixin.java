package itamio.memoryleakfix.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Port of FX's Minecraft_targetClearMixin (target entity leak).
 * The original cleared Minecraft.crosshairPickEntity / hitResult at
 * the start of the tick (before runTick inside updateScreenAndTick).
 * In 1.20.1 updateScreenAndTick takes a Screen argument, so this
 * injects at the head of runTick(boolean) instead — same effect:
 * stale crosshair targets do not pin entities between frames.
 */
@Mixin(Minecraft.class)
public abstract class MinecraftTargetClearMixin {

    @Shadow
    @Nullable
    public Entity crosshairPickEntity;

    @Shadow
    @Nullable
    public HitResult hitResult;

    @Inject(method = "runTick(Z)V", at = @At("HEAD"))
    private void memoryLeakFix$resetTarget(boolean tick, CallbackInfo ci) {
        this.crosshairPickEntity = null;
        this.hitResult = null;
    }
}
