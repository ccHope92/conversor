import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class CurrencyConverter {
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/f5e04e695bcddea36ac0aaa7/latest/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Crear un formateador con dos decimales
        DecimalFormat formato = new DecimalFormat("#.##");

        while (true) {
            // Monedas populares
            Map<String, String> popularCurrencies = new LinkedHashMap<>();
            popularCurrencies.put("USD", "Dólar estadounidense");
            popularCurrencies.put("EUR", "Euro");
            popularCurrencies.put("GBP", "Libra esterlina");
            popularCurrencies.put("JPY", "Yen japonés");
            popularCurrencies.put("CAD", "Dólar canadiense");
            popularCurrencies.put("AUD", "Dólar australiano");
            popularCurrencies.put("CNY", "Yuan chino");
            popularCurrencies.put("INR", "Rupia india");
            popularCurrencies.put("MXN", "Peso mexicano");
            popularCurrencies.put("BRL", "Real brasileño");
            popularCurrencies.put("COP", "Peso colombiano");

            System.out.println("Monedas más populares:");
            popularCurrencies.forEach((code, name) -> System.out.printf("%s - %s\n", code, name));

            // Solicitar moneda de origen
            System.out.print("\nIngrese el código de la moneda de origen (por ejemplo, USD): ");
            String baseCurrency = scanner.nextLine().toUpperCase();

            // Solicitar moneda de destino
            System.out.print("Ingrese el código de la moneda de destino (por ejemplo, EUR): ");
            String targetCurrency = scanner.nextLine().toUpperCase();

            // Solicitar monto a convertir
            System.out.print("Ingrese el monto a convertir: ");
            double amount = scanner.nextDouble();
            scanner.nextLine(); // Limpiar el buffer

            // Realizar la conversión
            try {
                double convertedAmount = convertCurrency(baseCurrency, targetCurrency, amount);

                // Obtener el símbolo de la moneda
                String baseSymbol = getCurrencySymbol(baseCurrency);
                String targetSymbol = getCurrencySymbol(targetCurrency);

                // Obtener la tasa de cambio y la fecha actual
                double exchangeRate = getExchangeRate(baseCurrency, targetCurrency);
                String currentDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());

                // Mostrar la tasa de cambio y la cantidad convertida con dos decimales
                System.out.printf("Tasa de cambio del %s: %s%.4f\n", currentDate, baseSymbol, exchangeRate);
                System.out.printf("Tu tasa de cambio es: %s%.2f = %s%.2f\n", baseSymbol, amount, targetSymbol, convertedAmount);
            } catch (Exception e) {
                System.out.println("Error al convertir moneda: " + e.getMessage());
            }

            // Preguntar si el usuario quiere realizar otra conversión
            System.out.print("\n¿Quieres realizar otra conversión? (sí/no): ");
            String answer = scanner.nextLine().toLowerCase();
            if (answer.equals("no")) {
                break;  // Salir del bucle y terminar el programa
            }
        }

        scanner.close();
        System.out.println("Gracias por usar el conversor de monedas. ¡Hasta luego!");
    }

    private static double convertCurrency(String baseCurrency, String targetCurrency, double amount) throws Exception {
        // Construir la URL de la API
        String apiUrl = API_URL + baseCurrency;

        // Realizar la solicitud HTTP
        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
        connection.setRequestMethod("GET");

        // Verificar el código de respuesta HTTP
        if (connection.getResponseCode() != 200) {
            throw new Exception("Error al obtener los datos de la API.");
        }

        // Leer la respuesta
        InputStreamReader reader = new InputStreamReader(connection.getInputStream());
        JsonObject jsonResponse = JsonParser.parseReader(reader).getAsJsonObject();
        reader.close();

        // Verificar si la moneda de destino está disponible
        if (!jsonResponse.getAsJsonObject("conversion_rates").has(targetCurrency)) {
            throw new Exception("La moneda de destino no está disponible.");
        }

        // Obtener la tasa de cambio y calcular el monto convertido
        double exchangeRate = jsonResponse.getAsJsonObject("conversion_rates").get(targetCurrency).getAsDouble();
        return amount * exchangeRate;
    }

    private static double getExchangeRate(String baseCurrency, String targetCurrency) throws Exception {
        // Construir la URL de la API para obtener la tasa de cambio
        String apiUrl = API_URL + baseCurrency;

        // Realizar la solicitud HTTP
        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
        connection.setRequestMethod("GET");

        // Verificar el código de respuesta HTTP
        if (connection.getResponseCode() != 200) {
            throw new Exception("Error al obtener los datos de la API.");
        }

        // Leer la respuesta
        InputStreamReader reader = new InputStreamReader(connection.getInputStream());
        JsonObject jsonResponse = JsonParser.parseReader(reader).getAsJsonObject();
        reader.close();

        // Verificar si la moneda de destino está disponible
        if (!jsonResponse.getAsJsonObject("conversion_rates").has(targetCurrency)) {
            throw new Exception("La moneda de destino no está disponible.");
        }

        // Obtener la tasa de cambio
        return jsonResponse.getAsJsonObject("conversion_rates").get(targetCurrency).getAsDouble();
    }

    private static String getCurrencySymbol(String currencyCode) {
        switch (currencyCode) {
            case "USD": return "$";
            case "EUR": return "€";
            case "GBP": return "£";
            case "JPY": return "¥";
            case "CAD": return "CA$";
            case "AUD": return "A$";
            case "CNY": return "¥";
            case "INR": return "₹";
            case "MXN": return "$";
            case "BRL": return "R$";
            case "COP": return "$";  // Agregado para el Peso colombiano
            default: return "";
        }
    }
}
