package itamio.memoryleakfix;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unofficial port of Memory Leak Fix by FX (LGPL-2.1-only).
 * See NOTICE for credit and licensing, and README for port status:
 * most of the original's leak fixes were absorbed by Mojang in
 * vanilla 1.19.4+ (the original's own notes say so for the entity
 * memories leak, MC-260605), and several mixin targets no longer
 * exist in 26.3. This port ships as a loadable mod documenting
 * that status; see README for the per-fix breakdown.
 */
public class MemoryLeakFixMod implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("memoryleakfix263");

    @Override
    public void onInitialize() {
        LOGGER.info("[MemoryLeakFix263] loaded - see README for vanilla-absorption status of original fixes");
    }
}
