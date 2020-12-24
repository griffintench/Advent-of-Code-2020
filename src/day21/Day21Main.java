package day21;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Day21Main {
    public static void run() throws FileNotFoundException {
        final File inputFile = new File("src/day21/input.txt");
        final Scanner scan = new Scanner(inputFile);
        
        final List<Food> foods = new ArrayList<>();
        while (scan.hasNextLine()) {
            final String line = scan.nextLine();
            final Food food = new Food(line);
            foods.add(food);
        }
        
        scan.close();
        
        System.out.println(part2(foods));
    }
    
    private static int part1(final List<Food> foods) {
        final Set<String> setOfAllAllergens = new HashSet<>();
        for (final Food food : foods) {
            setOfAllAllergens.addAll(food.getAllergens());
        }
        
        final Deque<String> allAllergens = new LinkedList<>(setOfAllAllergens);
        while (!allAllergens.isEmpty()) {
            final String allergen = allAllergens.pop();
            final List<Food> foodsWithAllergen = new ArrayList<>();
            for (final Food food : foods) {
                if (food.getAllergens().contains(allergen)) {
                    foodsWithAllergen.add(food);
                }
            }
            final String commonIngredient = Food.getCommonIngredient(foodsWithAllergen);
            if (commonIngredient != null) {
                for (final Food food : foods) {
                    food.remove(commonIngredient, allergen);
                }
            } else {
                allAllergens.addLast(allergen);
            }
        }
        
        int count = 0;
        for (final Food food : foods) {
            count += food.getIngredients().size();
        }
        
        return count;
    }
    
    private static String part2(final List<Food> foods) {
        final Set<String> setOfAllAllergens = new HashSet<>();
        for (final Food food : foods) {
            setOfAllAllergens.addAll(food.getAllergens());
        }
        
        final Map<String, String> ingredientsToAllergens = new HashMap<>();
        final Deque<String> allAllergens = new LinkedList<>(setOfAllAllergens);
        while (!allAllergens.isEmpty()) {
            final String allergen = allAllergens.pop();
            final List<Food> foodsWithAllergen = new ArrayList<>();
            for (final Food food : foods) {
                if (food.getAllergens().contains(allergen)) {
                    foodsWithAllergen.add(food);
                }
            }
            final String commonIngredient = Food.getCommonIngredient(foodsWithAllergen);
            if (commonIngredient != null) {
                ingredientsToAllergens.put(commonIngredient, allergen);
                for (final Food food : foods) {
                    food.remove(commonIngredient, allergen);
                }
            } else {
                allAllergens.addLast(allergen);
            }
        }
        
        final List<String> orderedIngredients = new ArrayList<>(ingredientsToAllergens.keySet());
        orderedIngredients.sort((final String ing1, final String ing2) -> {
            if (ing1.equals(ing2)) {
                return 0;
            }
            return ingredientsToAllergens.get(ing1).compareTo(ingredientsToAllergens.get(ing2));
        });
        
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < orderedIngredients.size(); ++i) {
            sb.append(orderedIngredients.get(i));
            if (i < orderedIngredients.size() - 1) {
                sb.append(',');
            }
        }
        return sb.toString();
    }
}
