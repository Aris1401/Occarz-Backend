package com.occarz.end.dto.statistiques;

import lombok.Data;

import java.util.List;
import java.util.Random;

@Data
public class VenteMarqueData {
    String name;
    List<Double> data;
    String color = "#fff";

    public static String getColor() {
        Random random = new Random();

        // Generate random RGB values
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);

        // Convert RGB to hexadecimal string format
        String hexColor = String.format("#%02x%02x%02x", r, g, b);

        return hexColor;
    }
}
