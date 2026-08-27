package dev.regionlib;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlatformTest {

    @Test
    void folia() {
        Set<String> classes = Set.of(
                "io.papermc.paper.threadedregions.RegionizedServer",
                "io.papermc.paper.configuration.Configuration");
        assertEquals(Platform.FOLIA, Platform.detect(classes::contains));
    }

    @Test
    void paper() {
        Set<String> classes = Set.of(
                "io.papermc.paper.configuration.Configuration",
                "io.papermc.paper.threadedregions.scheduler.RegionScheduler");
        assertEquals(Platform.PAPER, Platform.detect(classes::contains));
        assertEquals(Platform.PAPER, Platform.detect("com.destroystokyo.paper.PaperConfig"::equals));
    }

    @Test
    void spigot() {
        assertEquals(Platform.SPIGOT, Platform.detect(name -> false));
    }
}
