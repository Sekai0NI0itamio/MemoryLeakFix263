package itamio.memoryleakfix.mixin;

import com.mojang.blaze3d.platform.TextureUtil;
import org.lwjgl.system.MemoryUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

/**
 * Port of FX's TextureUtil_freeBufferMixin (MC-226729). If reading a
 * texture resource fails part-way, the off-heap ByteBuffer allocated
 * by TextureUtil.readResource was leaked; free it on failure.
 * Rewritten with plain Mixin @Redirect (no MixinExtras on Forge).
 * require = 0: TextureUtil internals vary, never crash loading for this.
 */
@Mixin(TextureUtil.class)
public abstract class TextureUtilFreeBufferMixin {

    @Redirect(method = "readResource(Ljava/io/InputStream;)Ljava/nio/ByteBuffer;",
            at = @At(value = "INVOKE",
                    target = "Ljava/nio/channels/FileChannel;read(Ljava/nio/ByteBuffer;)I"),
            require = 0)
    private int memoryLeakFix$readFileChannel(FileChannel channel, ByteBuffer byteBuffer) {
        try {
            return channel.read(byteBuffer);
        } catch (Exception e) {
            MemoryUtil.memFree(byteBuffer);
            throw e;
        }
    }

    @Redirect(method = "readResource(Ljava/io/InputStream;)Ljava/nio/ByteBuffer;",
            at = @At(value = "INVOKE",
                    target = "Ljava/nio/channels/ReadableByteChannel;read(Ljava/nio/ByteBuffer;)I"),
            require = 0)
    private int memoryLeakFix$readReadableChannel(ReadableByteChannel channel, ByteBuffer byteBuffer) {
        try {
            return channel.read(byteBuffer);
        } catch (Exception e) {
            MemoryUtil.memFree(byteBuffer);
            throw e;
        }
    }
}
