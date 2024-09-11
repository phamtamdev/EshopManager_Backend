package com.tambao.EshopManeger_Backend.Utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class SlugUtils {
    public static String removeDiacriticalMarks(String str) {
        String normalized = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{M}+");
        return pattern.matcher(normalized).replaceAll("")
                .replace('đ', 'd')
                .replace('Đ', 'D');
    }

    public static String toSlug(String str) {
        return removeDiacriticalMarks(str).toLowerCase()
                .replaceAll("[^a-z0-9]", "-")
                .replaceAll("-{2,}", "-")
                .replaceAll("^-|-$", "");
    }
}
