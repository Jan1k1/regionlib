package dev.regionlib;

import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Minecraft version from a Bukkit {@code getVersion()} string such as {@code git-Paper-70 (MC: 26.1.2)}.
 * Components are the numbers as printed: 1.21.1 is major 1 / minor 21 / patch 1; 26.1.2 is major 26 / minor 1 / patch 2.
 */
public final class MinecraftVersion {

    private static final Pattern MC = Pattern.compile(
            "(?i)\\(MC: (\\d+)\\.(\\d+)(?:\\.(\\d+))?(?: (Pre-Release|Release Candidate) (\\d+))?\\)");

    private final int major;
    private final int minor;
    private final int patch;
    private final int preRelease;
    private final int releaseCandidate;

    private MinecraftVersion(int major, int minor, int patch, int preRelease, int releaseCandidate) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.preRelease = preRelease;
        this.releaseCandidate = releaseCandidate;
    }

    public static MinecraftVersion parse(String bukkitVersion) {
        if (bukkitVersion == null) {
            throw new IllegalArgumentException("version is null");
        }
        Matcher matcher = MC.matcher(bukkitVersion);
        if (!matcher.find()) {
            throw new IllegalArgumentException("no (MC: ...) version in: " + bukkitVersion);
        }
        int major = Integer.parseInt(matcher.group(1), 10);
        int minor = Integer.parseInt(matcher.group(2), 10);
        int patch = matcher.group(3) != null ? Integer.parseInt(matcher.group(3), 10) : 0;
        int preRelease = -1;
        int releaseCandidate = -1;
        if (matcher.group(4) != null) {
            int n = Integer.parseInt(matcher.group(5), 10);
            if (matcher.group(4).toLowerCase(Locale.ENGLISH).contains("pre")) {
                preRelease = n;
            } else {
                releaseCandidate = n;
            }
        }
        return new MinecraftVersion(major, minor, patch, preRelease, releaseCandidate);
    }

    public int major() {
        return major;
    }

    public int minor() {
        return minor;
    }

    public int patch() {
        return patch;
    }

    /** {@code -1} if this is not a pre-release. */
    public int preRelease() {
        return preRelease;
    }

    /** {@code -1} if this is not a release candidate. */
    public int releaseCandidate() {
        return releaseCandidate;
    }

    public boolean isAtLeast(int major, int minor) {
        return isAtLeast(major, minor, 0);
    }

    public boolean isAtLeast(int major, int minor, int patch) {
        if (this.major != major) {
            return this.major > major;
        }
        if (this.minor != minor) {
            return this.minor > minor;
        }
        return this.patch >= patch;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MinecraftVersion other)) {
            return false;
        }
        return major == other.major
                && minor == other.minor
                && patch == other.patch
                && preRelease == other.preRelease
                && releaseCandidate == other.releaseCandidate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(major, minor, patch, preRelease, releaseCandidate);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(major).append('.').append(minor).append('.').append(patch);
        if (preRelease >= 0) {
            sb.append(" Pre-Release ").append(preRelease);
        } else if (releaseCandidate >= 0) {
            sb.append(" Release Candidate ").append(releaseCandidate);
        }
        return sb.toString();
    }
}
