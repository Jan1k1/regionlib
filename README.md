# regionlib

Shade-only Java library for Paper plugin authors. `groupId` `dev.regionlib`, artifact `regionlib`. Shade and relocate; this is not a plugin jar.

This tree is **version parse only**. `MinecraftVersion.parse(Bukkit.getVersion())` reads `(MC: 1.21.1)` and `(MC: 26.1.2)` (including pre/rc). It does not treat 26.x as version 0 the way PaperLib 1.0.8 does. Folia/Paper detect, teleport, and schedulers are not in this PR.
