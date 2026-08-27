package dev.regionlib;

import java.util.function.Predicate;

/** Runtime from class presence: Folia, then Paper, otherwise Spigot/Bukkit. Not {@code Bukkit.getVersion()}. */
public enum Platform {
    FOLIA,
    PAPER,
    SPIGOT;

    private static final Platform CURRENT = detect(Platform::loaded);

    public static Platform current() {
        return CURRENT;
    }

    static Platform detect(Predicate<String> loaded) {
        if (loaded.test("io.papermc.paper.threadedregions.RegionizedServer")) {
            return FOLIA;
        }
        if (loaded.test("io.papermc.paper.configuration.Configuration")
                || loaded.test("com.destroystokyo.paper.PaperConfig")) {
            return PAPER;
        }
        return SPIGOT;
    }

    private static boolean loaded(String name) {
        try {
            Class.forName(name);
            return true;
        } catch (ClassNotFoundException ignored) {
            return false;
        }
    }
}
