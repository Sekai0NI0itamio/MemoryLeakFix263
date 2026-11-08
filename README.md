# Memory Leak Fix — Forge Port for Minecraft 1.20.1

![Mod loader: Forge](https://img.shields.io/badge/modloader-Forge-orange?style=for-the-badge)
![Minecraft: 1.20.1](https://img.shields.io/badge/Minecraft-1.20.1-green?style=for-the-badge)
![GitHub license](https://img.shields.io/github/license/Sekai0NI0itamio/MemoryLeakFix263?style=for-the-badge)

**Fixes multiple memory leaks in Minecraft, both client-side and server-side.**

The original Memory Leak Fix (38.8M downloads, FX) bundled a set of
small fixes for known vanilla memory leaks. This is an unofficial
**Forge port for Minecraft 1.20.1**, built and verified in CI via
GitHub Actions, including a headless runtime test.

### What it does (ported fixes)

- **Biome temperature cache** — shares one static `ThreadLocal`
  temperature cache instead of one per `Biome` instance (the
  original's non-static `ThreadLocal` created a cache per biome).
- **Texture resource buffer (MC-226729)** — frees the off-heap
  `ByteBuffer` allocated by `TextureUtil.readResource` when reading
  a texture fails part-way.
- **Crosshair target clear** — clears `Minecraft.crosshairPickEntity`
  and `hitResult` at the start of each client tick so stale targets
  do not pin entities between frames.

### Per-fix status (original → 1.20.1 Forge)

| Original fix | 1.20.1 status |
|---|---|
| Biome temperature ThreadLocal | Ported (`BiomeThreadLocalMixin`) |
| TagKey interner leak (MC-248621) | Not ported — `TagKey` was rewritten in 1.20.1 (its static interner shape changed), so the original's `<clinit>` redirector has no target; the fix is not applicable to 1.20.1 |
| TextureUtil free buffer (MC-226729) | Ported (`TextureUtilFreeBufferMixin`, `require = 0` so texture-internals drift can never break loading) |
| Target entity clear | Ported; injects at head of `runTick(boolean)` since `updateScreenAndTick` takes a `Screen` in 1.20.1 (`MinecraftTargetClearMixin`) |
| Entity memories leak (MC-260605) | Not ported — vanilla fixed it in 1.19.4 (per the original's own notes); the Brain/Entity/LivingEntity mixins would be no-ops |
| Drowned navigation leak (MC-202246) | Not ported — original targeted 1.16.3–1.16.5 only; its `Drowned` navigation fields no longer exist in 1.20.1 |
| Huge screenshot leak | Not ported — it needs MixinExtras local capture, which is not bundled in this Forge port; the method target also changed shape in 1.20.1 |

### Port changes vs. the original

- Forge `@Mod` entrypoint, `mods.toml`, and a plain-Mixin setup
  (MixinExtras was not bundled; the ported mixins were rewritten
  with plain `@Redirect`/`@Inject` and compile against official
  Mojang mappings for 1.20.1).
- Mixin config: `memoryleakfix.mixins.json` (minVersion 0.8,
  JAVA_17, refmap), `pack.mcmeta` pack_format 15.

### Installation

1. Install Forge 47.4.20 for Minecraft 1.20.1
2. Drop `memoryleakfix-1.20.1-forge-*.jar` into your `mods` folder

### Original mod & credit

This is an unofficial Forge port by **Itamio** of **Memory Leak
Fix** by **FX (PR0CESS)**.

- Original mod: https://modrinth.com/mod/memoryleakfix
- Original source: https://github.com/fxmorin/memoryLeakFix
- Leak explanations: https://github.com/fxmorin/memoryLeakFix/wiki/Current-Fixes
- License: LGPL-2.1-only — see `LICENSE`

All credit for the original concept, design, and logic goes to FX.
See `NOTICE`.
