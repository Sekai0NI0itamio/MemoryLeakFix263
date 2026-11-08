package itamio.memoryleakfix.mixin;

import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Supplier;

/**
 * Port of FX's Biome_threadLocalMixin (biome temperature cache leak).
 * The original's non-static ThreadLocal temperature cache in Biome
 * created one cache per Biome instance instead of sharing one per
 * thread; sharing a single static ThreadLocal fixes that.
 * Rewritten with plain Mixin @Redirect (no MixinExtras on Forge).
 */
@Mixin(Biome.class)
public abstract class BiomeThreadLocalMixin {

    private static ThreadLocal memoryLeakFix$sharedTempCache;

    @Redirect(method = "<init>", at = @At(value = "INVOKE",
            target = "Ljava/lang/ThreadLocal;withInitial(Ljava/util/function/Supplier;)Ljava/lang/ThreadLocal;"))
    private ThreadLocal memoryLeakFix$useSharedThreadLocal(Supplier<?> supplier) {
        if (memoryLeakFix$sharedTempCache == null) {
            memoryLeakFix$sharedTempCache = ThreadLocal.withInitial(supplier);
        }
        return memoryLeakFix$sharedTempCache;
    }
}
