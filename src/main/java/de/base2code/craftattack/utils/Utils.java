package de.base2code.craftattack.utils;

public class Utils {
    public static String stripColorCodes(String input) {
        return input.replaceAll("§.", "").replaceAll("&.", "");
    }
}
