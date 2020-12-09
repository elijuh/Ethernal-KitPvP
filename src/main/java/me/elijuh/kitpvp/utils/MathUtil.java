package me.elijuh.kitpvp.utils;

import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public class MathUtil {
    private static final Random random = new Random();

    public int getRandom(int low, int high) {
        return 1 + random.nextInt(high - low) + low;
    }

    public int getNeededExp(int level) {
        return 500 + (level * 75);
    }

    public double roundTo(double value, int decimals) {
        double divisor = Math.pow(10, decimals);
        return Math.round(value * divisor) / divisor;
    }
}
