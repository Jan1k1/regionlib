package dev.regionlib;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.concurrent.CompletableFuture;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public final class Teleports {

    private Teleports() {
    }

    public static CompletableFuture<Boolean> teleportAsync(Entity entity, Location location) {
        return teleportAsync(
                Platform.current(),
                () -> entity.teleportAsync(location),
                () -> entity.teleport(location));
    }

    static CompletableFuture<Boolean> teleportAsync(
            Platform platform,
            Supplier<CompletableFuture<Boolean>> asyncTeleport,
            BooleanSupplier syncTeleport) {
        if (platform == Platform.SPIGOT) {
            return CompletableFuture.completedFuture(syncTeleport.getAsBoolean());
        }
        return asyncTeleport.get();
    }
}
