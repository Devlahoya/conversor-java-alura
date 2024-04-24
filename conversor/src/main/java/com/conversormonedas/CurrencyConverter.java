package com.conversormonedas;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;

public class CurrencyConverter {

    public static void main(String[] args) throws IOException {
        Dotenv dotenv = Dotenv.load();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Type currency to convert from");
        String convertFrom = scanner.nextLine();
        System.out.println("Type currency to convert to");
        String convertTo = scanner.nextLine();
        System.out.println("Type quantity to convert");
        BigDecimal quantity = scanner.nextBigDecimal();

        String urlString = "https://v6.exchangerate-api.com/v6/"+dotenv.get("API_CURRENCYEX")+"/latest/" + convertFrom.toUpperCase();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(urlString)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String stringResponse = response.body().string();
        JSONObject jsonObject = new JSONObject(stringResponse);
        JSONObject ratesObject = jsonObject.getJSONObject("conversion_rates");
        BigDecimal rate = ratesObject.getBigDecimal(convertTo.toUpperCase());

        BigDecimal result = rate.multiply(quantity);
        System.out.println("El equivalente de: "+quantity+ " "+convertFrom.toUpperCase()+ " son: " +result+ " "+convertTo.toUpperCase() );

    }
}