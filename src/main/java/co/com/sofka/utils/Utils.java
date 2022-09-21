package co.com.sofka.utils;

import java.util.Random;

public class Utils {

    private static final Random randon = new Random();

    private Utils() {
    }

    public static String generateDegreeCelsius(){
        return String.valueOf(randon.nextInt(200  + 100) - 100);
    }

    public static String convertCelsiusToFahrenheit(String temperatureInCelsius){
        int temperatureCelsiusNumber = Integer.parseInt(temperatureInCelsius);
        return String.valueOf((temperatureCelsiusNumber *  9 / 5) + 32);
    }

    public static String generateISBNCode(){
        String firstAndLastSubCode = randon.nextInt(9 - 0 + 1) + 0 + "-";
        String secondSubCode = randon.nextInt(99 - 10 + 1) + 10 + "-";
        String thirdSubCode = randon.nextInt(999999 - 100000 + 1) + 100000 + "-";
        String isISBNcode = firstAndLastSubCode + secondSubCode + thirdSubCode + firstAndLastSubCode;
        isISBNcode = isISBNcode.substring(0,13);
        return isISBNcode;
    }
}

