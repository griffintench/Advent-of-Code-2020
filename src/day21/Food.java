package day21;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Food {
    
    private final Set<String> ingredients;
    
    private final Set<String> allergens;
    
    public Food(final String listItem) {
        ingredients = new HashSet<>();
        allergens = new HashSet<>();
        
        final String[] splitItem = listItem.split("( \\()|\\)");
        final String[] splitIngredients = splitItem[0].split(" ");
        for (int i = 0; i < splitIngredients.length; ++i) {
            ingredients.add(splitIngredients[i]);
        }
        final String[] splitAllergens = splitItem[1].substring(9).split(", ");
        for (int i = 0; i < splitAllergens.length; ++i) {
            allergens.add(splitAllergens[i]);
        }
    }
    
    public void remove(final String ingredient, final String allergen) {
        ingredients.remove(ingredient);
        allergens.remove(allergen);
    }
    
    public Set<String> getIngredients() {
        return ingredients;
    }
    
    public Set<String> getAllergens() {
        return allergens;
    }
    
    public static String getCommonIngredient(final List<Food> foods) {
        final List<String> commonIngredients = new ArrayList<>(foods.get(0).ingredients);
        for (int i = 1; i < foods.size(); ++i) {
            final int j = i; // lambda scope issues
            commonIngredients.removeIf((final String ingredient) -> !foods.get(j).ingredients.contains(ingredient));
        }
        if (commonIngredients.size() != 1) {
            return null;
        }
        return commonIngredients.get(0);
    }

}
