# regionlib

Shade-only Java library for Paper plugin authors. `groupId` `dev.regionlib`, artifact `regionlib`. Shade and relocate; this is not a plugin jar.

`MinecraftVersion.parse(Bukkit.getVersion())` reads `(MC: 1.21.1)` and `(MC: 26.1.2)`, including pre/rc, and does not treat 26.x as version 0.
