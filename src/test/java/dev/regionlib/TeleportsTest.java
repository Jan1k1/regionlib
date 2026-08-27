package dev.regionlib;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TeleportsTest {

    @Test
    void foliaNeverCallsSyncTeleport() {
        AtomicInteger async = new AtomicInteger();
        AtomicInteger sync = new AtomicInteger();
        CompletableFuture<Boolean> result = Teleports.teleportAsync(
                Platform.FOLIA,
                () -> {
                    async.incrementAndGet();
                    return CompletableFuture.completedFuture(true);
                },
                () -> {
                    sync.incrementAndGet();
                    return true;
                });
        assertTrue(result.join());
        assertEquals(1, async.get());
        assertEquals(0, sync.get());
    }

    @Test
    void paperUsesAsync() {
        AtomicInteger async = new AtomicInteger();
        AtomicInteger sync = new AtomicInteger();
        Teleports.teleportAsync(
                Platform.PAPER,
                () -> {
                    async.incrementAndGet();
                    return CompletableFuture.completedFuture(true);
                },
                () -> {
                    sync.incrementAndGet();
                    return true;
                });
        assertEquals(1, async.get());
        assertEquals(0, sync.get());
    }

    @Test
    void spigotUsesSync() {
        AtomicInteger async = new AtomicInteger();
        AtomicInteger sync = new AtomicInteger();
        CompletableFuture<Boolean> result = Teleports.teleportAsync(
                Platform.SPIGOT,
                () -> {
                    async.incrementAndGet();
                    return CompletableFuture.completedFuture(true);
                },
                () -> {
                    sync.incrementAndGet();
                    return false;
                });
        assertFalse(result.join());
        assertEquals(0, async.get());
        assertEquals(1, sync.get());
    }
}
