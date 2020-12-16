package me.elijuh.kitpvp.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

import java.text.NumberFormat;
import java.util.Locale;

@UtilityClass
public class ChatUtil {

    public String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public String capitalize(String s) {
        String[] words = s.split(" ");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            result.append(word.substring(0, 1).toUpperCase()).append(word.substring(1).toLowerCase()).append(" ");
        }
        return result.toString().trim();
    }

    public String formatSeconds(long seconds) {
        long days = 0, hours = 0, minutes = 0;

        while (seconds >= 60) {
            seconds -= 60;
            minutes++;
        }

        while (minutes >= 60) {
            minutes -= 60;
            hours++;
        }

        while (hours >= 24) {
            hours -= 24;
            days++;
        }

        String format = pluralize(days, "day", ", ")
                + pluralize(hours, "hour", ", ")
                + pluralize(minutes, "minute", ", ")
                + pluralize(seconds, "second", ", ");
        return format.isEmpty() ? "" : format.substring(0, format.length() - 2);
    }

    private String pluralize(long amount, String name, String... extra) {
        StringBuilder format = new StringBuilder();
        if (amount == 1) {
            format.append(amount).append(" ").append(name);
        } else {
            format.append(amount > 0 ? amount + " " + name + "s" : "");
        }
        if (!format.toString().isEmpty()) {
            for (String s : extra) {
                format.append(s);
            }
        }
        return format.toString();
    }

    public static String getLevelColor(int level) {
        if (level < 5) return "§7";
        if (level < 10) return "§f";
        if (level < 20) return "§e";
        if (level < 30) return "§a";
        if (level < 40) return "§d";
        if (level < 50) return "§6";
        return "§4";
    }

    public String formatMoney(double d) {
        NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(0);

        if (d < 1000L) {
            return format.format(d);
        }
        if (d < 1000000L) {
            return format.format(d / 1000L) + "k";
        }
        if (d < 1000000000L) {
            return format.format(d / 1000000L) + "M";
        }
        if (d < 1000000000000L) {
            return format.format(d / 1000000000L) + "B";
        }
        if (d < 1000000000000000L) {
            return format.format(d / 1000000000000L) + "T";
        }
        if (d < 1000000000000000000L) {
            return format.format(d / 1000000000000000L) + "Q";
        }

        return String.valueOf((long) d);
    }
}
