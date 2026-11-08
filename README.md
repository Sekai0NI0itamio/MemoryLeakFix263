# Memory Leak Fix — Modern Port for Minecraft 26.3 (Fabric)

![Mod loader: Fabric](https://img.shields.io/badge/modloader-Fabric-1976d2?style=for-the-badge)
![Minecraft: 26.3](https://img.shields.io/badge/Minecraft-26.3-green?style=for-the-badge)
![GitHub license](https://img.shields.io/github/license/Sekai0NI0itamio/MemoryLeakFix263?style=for-the-badge)

**Fixes multiple memory leaks in Minecraft, both client-side and server-side.**

The original Memory Leak Fix (38.8M downloads, FX) bundled a set of
small fixes for known vanilla memory leaks. This is a **modern port
for Minecraft 26.3 (Fabric)**.

> **Honest port status:** most of the original's fixes were absorbed
> by Mojang in vanilla itself — the original's own notes say the
> entity memories leak (MC-260605) was fixed by Mojang in 1.19.4,
> and several other mixin targets (e.g. the old `TagKey` interner,
> the non-static biome temperature `ThreadLocal`) no longer exist
> in 26.3's rewritten systems. Rather than ship dead mixins that
> crash on load, this port ships as a loadable mod that documents
> that status (see below), verified by GitHub Actions including a
> headless runtime test.

### Per-fix status (original → 26.3)

| Original fix | 26.3 status |
|---|---|
| Entity memories leak (MC-260605) | Already fixed by Mojang in 1.19.4 |
| TagKey interner leak (MC-248621) | Target class changed in 26.3, not ported |
| Biome temperature ThreadLocal | Target rewritten, not ported |
| Drowned navigation / screenshot / texture buffer leaks | Targets changed, not ported |

If a leak resurfaces in 26.3, it can be re-ported against the new
target with the diagnostic workflow used for the other ports.

### Modern port notes (26.3)

- Java 25 · Fabric Loader 0.20.1+ · Fabric API 0.165.0+26.3
- Built and verified in CI via GitHub Actions, including a headless
  runtime test that launches the client, joins a world, and quits cleanly

### Installation

1. Install Fabric Loader for Minecraft 26.3
2. Install Fabric API
3. Drop `memoryleakfix263-*.jar` into your `mods` folder

### Original mod & credit

This is an unofficial modern port by **Itamio** of **Memory Leak
Fix** by **FX (PR0CESS)** — the original (38.8M downloads on
Modrinth, last updated for 1.20.4) has not been updated to 26.3.

- Original mod: https://modrinth.com/mod/memoryleakfix
- Original source: https://github.com/fxmorin/memoryLeakFix
- Leak explanations: https://github.com/fxmorin/memoryLeakFix/wiki/Current-Fixes
- License: LGPL-2.1-only — see `LICENSE`

All credit for the original concept, design, and logic goes to FX.
This port exists to bring it to Minecraft 26.3. See `NOTICE`.
