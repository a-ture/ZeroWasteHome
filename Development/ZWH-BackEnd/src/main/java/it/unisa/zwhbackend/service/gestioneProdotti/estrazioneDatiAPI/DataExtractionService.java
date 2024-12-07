package it.unisa.zwhbackend.service.gestioneProdotti.estrazioneDatiAPI;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class DataExtractionService {

    public JSONObject getProductDetails(String barcode) {
        String url = "https://world.openfoodfacts.org/api/v0/product/" + barcode + ".json";
        RestTemplate restTemplate = new RestTemplate();
        JSONObject response = new JSONObject(restTemplate.getForObject(url, String.class));

        if (response.getInt("status") != 1) {
            throw new RuntimeException("Prodotto non trovato. Controlla il codice a barre.");
        }

        JSONObject product = response.getJSONObject("product");

        // Nutrienti: logica per calcolare e mostrare i dati corretti (solo valori per 100g)
        JSONObject nutriments = product.optJSONObject("nutriments");
        String energyKcal = getFallbackValue(nutriments, "energy-kcal_100g", "energy-kcal_value", "energy-kcal", "?");
        String energyKj = getFallbackValue(nutriments, "energy-kj_100g", "energy-kj_value", "energy-kj", "?");
        String fat = getFallbackValue(nutriments, "fat_100g", "fat_value", "fat", "?");
        String saturatedFat = getFallbackValue(nutriments, "saturated-fat_100g", "saturated-fat_value", "saturated-fat", "?");
        String carbohydrates = getFallbackValue(nutriments, "carbohydrates_100g", "carbohydrates_value", "carbohydrates", "?");
        String sugars = getFallbackValue(nutriments, "sugars_100g", "sugars_value", "sugars", "?");
        String fiber = getFallbackValue(nutriments, "fiber_100g", "fiber_value", "fiber", "?");
        String proteins = getFallbackValue(nutriments, "proteins_100g", "proteins_value", "proteins", "?");
        String salt = getFallbackValue(nutriments, "salt_100g", "salt_value", "salt", "?");

        String nutritionText = String.format(
                "Valori nutrizionali per 100 grammi:\nEnergia: %s kj (%s kcal)\nGrassi: %s g\nAcidi grassi saturi: %s g\n" +
                        "Carboidrati: %s g\nZuccheri: %s g\nFibra alimentare: %s g\nProteine: %s g\nSale: %s g",
                energyKj, energyKcal, fat, saturatedFat, carbohydrates, sugars, fiber, proteins, salt
        );

        // Popolare il nome del prodotto con fallback e quantità
        String productName = product.optString("product_name", product.optString("generic_name_it", "Prodotto sconosciuto"));
        String quantity = product.optString("quantity", product.optString("quantity_per_unit", null));
        if (quantity != null) {
            productName += " (" + quantity + ")";
        }

        // Note
        String genericName = product.optString("generic_name_it", "");
        String ingredients = product.optString("ingredients_text_it", "");
        String notes = !genericName.isEmpty() || !ingredients.isEmpty()
                ? genericName + (genericName.isEmpty() ? "" : ".\n") + (ingredients.isEmpty() ? "" : "Ingredienti: " + ingredients)
                : "";

        // Gluten-Free, Vegan e Vegetarian
        List<String> labels = splitStringToList(product.optString("labels"));
        List<String> labelsTags = splitStringToList(product.optString("labels_tags"));
        List<String> labelsHierarchy = splitStringToList(product.optString("labels_hierarchy"));
        List<String> ingredientsAnalysisTags = splitStringToList(product.optString("ingredients_analysis_tags"));
        List<String> allergens = splitStringToList(product.optString("allergens_tags"));
        List<String> allergensFromIngredients = splitStringToList(product.optString("allergensFromIngredients"));

        String glutenFree = calculateGlutenFree(labels, labelsTags, labelsHierarchy, allergens, allergensFromIngredients);
        String vegan = calculateVegan(labels, labelsTags, labelsHierarchy, ingredientsAnalysisTags);
        String vegetarian = calculateVegetarian(labels, labelsTags, labelsHierarchy, ingredientsAnalysisTags);

        // Risultati
        JSONObject result = new JSONObject();
        result.put("nutrition", nutritionText);
        result.put("productName", productName);
        result.put("notes", notes);
        result.put("glutenFree", glutenFree);
        result.put("vegan", vegan);
        result.put("vegetarian", vegetarian);

        return result;
    }

    private String getFallbackValue(JSONObject object, String... keys) {
        for (String key : keys) {
            if (object != null && object.has(key)) {
                return object.optString(key, "?");
            }
        }
        return "?";
    }

    private List<String> splitStringToList(String value) {
        return value.isEmpty() ? List.of() : Arrays.asList(value.split(","));
    }

    private String calculateGlutenFree(List<String> labels, List<String> labelsTags, List<String> labelsHierarchy,
                                       List<String> allergens, List<String> allergensFromIngredients) {
        boolean labelsContainNoGluten = containsText(labels, "no-gluten") || containsText(labelsTags, "no-gluten") || containsText(labelsHierarchy, "no-gluten");
        boolean allergensContainGluten = containsText(allergens, "gluten") || containsText(allergensFromIngredients, "gluten");

        if (labelsContainNoGluten && !allergensContainGluten) {
            return "SI";
        } else if (!labelsContainNoGluten && !allergensContainGluten) {
            return "NULL";
        } else {
            return "NO";
        }
    }

    private String calculateVegan(List<String> labels, List<String> labelsTags, List<String> labelsHierarchy, List<String> ingredientsAnalysisTags) {
        boolean nonVegan = containsText(labels, "non-vegan") || containsText(labelsTags, "non-vegan")
                || containsText(labelsHierarchy, "non-vegan") || containsText(ingredientsAnalysisTags, "non-vegan");

        if (nonVegan) {
            return "NO";
        } else {
            boolean vegan = containsText(labels, "vegan") || containsText(labelsTags, "vegan")
                    || containsText(labelsHierarchy, "vegan") || containsText(ingredientsAnalysisTags, "vegan")
                    && !containsText(ingredientsAnalysisTags, "vegan-status-unknown");
            return vegan ? "SI" : "NULL";
        }
    }

    private String calculateVegetarian(List<String> labels, List<String> labelsTags, List<String> labelsHierarchy, List<String> ingredientsAnalysisTags) {
        boolean nonVegetarian = containsText(labels, "non-vegetarian") || containsText(labelsTags, "non-vegetarian")
                || containsText(labelsHierarchy, "non-vegetarian") || containsText(ingredientsAnalysisTags, "non-vegetarian");

        if (nonVegetarian) {
            return "NO";
        } else {
            boolean vegetarian = containsText(labels, "vegetarian") || containsText(labelsTags, "vegetarian")
                    || containsText(labelsHierarchy, "vegetarian") || containsText(ingredientsAnalysisTags, "vegetarian")
                    && !containsText(ingredientsAnalysisTags, "vegetarian-status-unknown");
            return vegetarian ? "SI" : "NULL";
        }
    }

    private boolean containsText(List<String> list, String text) {
        return list.stream().anyMatch(item -> item.contains(text));
    }
}
