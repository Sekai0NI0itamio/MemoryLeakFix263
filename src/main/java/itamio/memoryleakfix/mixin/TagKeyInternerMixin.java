package itamio.memoryleakfix.mixin;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import net.minecraft.tags.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Port of FX's TagKey_internerMixin (MC-248621). TagKey's static
 * interner was a strong interner that retained every TagKey forever;
 * a weak interner lets unused tag keys be garbage collected.
 * In Mojang mappings the Yarn class_6862 target is net.minecraft.tags.TagKey.
 */
@Mixin(value = TagKey.class, priority = 1500)
public abstract class TagKeyInternerMixin {

    @Redirect(method = "<clinit>", at = @At(value = "INVOKE",
            target = "Lcom/google/common/collect/Interners;newStrongInterner()Lcom/google/common/collect/Interner;"))
    private static Interner memoryLeakFix$useWeakInterner() {
        return Interners.newWeakInterner();
    }
}
