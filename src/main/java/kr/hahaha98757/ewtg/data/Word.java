package kr.hahaha98757.ewtg.data;

import org.jetbrains.annotations.NotNull;

public record Word(@NotNull String english, @NotNull String mean, boolean englishFirst) {
    @Override
    public String toString() {
        return englishFirst ? english + " " + mean : mean + " " + english;
    }
}