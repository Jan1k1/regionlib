package dev.regionlib;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MinecraftVersionTest {

    private static final Pattern PAPERLIB_1_0_8 = Pattern.compile(
            "(?i)\\(MC: (\\d)\\.(\\d+)\\.?(\\d+?)?(?: (Pre-Release|Release Candidate) )?(\\d)?\\)");

    @Test
    void twentySixIsNotVersionZero() {
        String bukkit = "git-Paper-70 (MC: 26.1.2)";
        assertFalse(PAPERLIB_1_0_8.matcher(bukkit).find());

        MinecraftVersion v = MinecraftVersion.parse(bukkit);
        assertEquals(26, v.major());
        assertEquals(1, v.minor());
        assertEquals(2, v.patch());
        assertTrue(v.isAtLeast(26, 1));
        assertTrue(v.isAtLeast(1, 21, 1));
        assertFalse(v.isAtLeast(26, 1, 3));
        assertFalse(v.isAtLeast(27, 0));
    }

    @Test
    void oneTwentyOne() {
        MinecraftVersion v = MinecraftVersion.parse("git-Paper-445 (MC: 1.21.1)");
        assertEquals(1, v.major());
        assertEquals(21, v.minor());
        assertEquals(1, v.patch());
        assertTrue(v.isAtLeast(1, 21));
        assertTrue(v.isAtLeast(1, 20, 6));
        assertFalse(v.isAtLeast(1, 21, 2));
        assertFalse(v.isAtLeast(26, 1));
        assertTrue(PAPERLIB_1_0_8.matcher("git-Paper-445 (MC: 1.21.1)").find());
    }

    @Test
    void spigotTwentySixString() {
        MinecraftVersion v = MinecraftVersion.parse(
                "4626-Spigot-566f972-b5ccd9c (MC: 26.1.2) (Implementing API version 26.1.2-R0.1-SNAPSHOT)");
        assertEquals(26, v.major());
        assertEquals(1, v.minor());
        assertEquals(2, v.patch());
    }

    @Test
    void preAndRc() {
        MinecraftVersion pre = MinecraftVersion.parse("(MC: 1.21.4 Pre-Release 3)");
        assertEquals(1, pre.major());
        assertEquals(21, pre.minor());
        assertEquals(4, pre.patch());
        assertEquals(3, pre.preRelease());
        assertEquals(-1, pre.releaseCandidate());

        MinecraftVersion rc = MinecraftVersion.parse("(MC: 1.21.11 Release Candidate 1)");
        assertEquals(11, rc.patch());
        assertEquals(-1, rc.preRelease());
        assertEquals(1, rc.releaseCandidate());
        assertFalse(rc.isAtLeast(1, 21, 11));
        assertTrue(rc.isAtLeast(1, 21, 10));

        MinecraftVersion yearPre = MinecraftVersion.parse("(MC: 26.1 Pre-Release 2)");
        assertEquals(26, yearPre.major());
        assertEquals(1, yearPre.minor());
        assertEquals(0, yearPre.patch());
        assertEquals(2, yearPre.preRelease());
        assertFalse(yearPre.isAtLeast(26, 1));
        assertTrue(yearPre.isAtLeast(26, 0));
        assertTrue(yearPre.isAtLeast(1, 21, 8));
    }
}
