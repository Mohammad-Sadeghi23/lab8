package ca.bcit.comp2522.lab8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class CountryLab
{
    public static void main(final String[] args) {

        List<String> countries = List.of(); // Initialize empty list to prevent errors
        String dataMessage = "";

        // Define output file paths
        Path dirPath = Paths.get("src", "code", "ca", "bcit", "comp2522", "lab8", "matches");
        Path dataPath = dirPath.resolve("data.txt");

        try {
            Path path = Paths.get("src", "code", "ca", "bcit", "comp2522", "lab8", "week8countries.txt");

            // Check if file exists before reading
            if (!Files.exists(path)) {
                System.out.println("Error: File not found at " + path.toAbsolutePath());
                return;
            }

            countries = Files.readAllLines(path);
            System.out.println("Countries read successfully: " + countries.size());

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        // 1. Filter long country names
        List<String> longNames = countries.stream()
                                          .filter(p -> p.length() > 10)
                                          .toList();
        longNames.forEach(System.out::println);

        // 2. short names
        List<String> shortNames = countries.stream()
                                          .filter(p -> p.length() < 5)
                                          .toList();
        shortNames.forEach(System.out::println);

        // 3. starting with A
        List<String> startA = countries.stream()
                                          .filter(p -> p.substring(0,1).equalsIgnoreCase("a"))
                                          .toList();
        startA.forEach(System.out::println);

        // 4. Ending with land
        List<String> endingLand = countries.stream()
                                       .filter(p -> p.endsWith("land"))
                                       .toList();
        endingLand.forEach(System.out::println);

        // 5. Ending with land
        List<String> containsUnited = countries.stream()
                                           .filter(p -> p.toLowerCase().contains("united"))
                                           .toList();
        containsUnited.forEach(System.out::println);

        // 6. Sorted alpha
        List<String> sortedAlpha = countries.stream()
                                            .sorted()
                                            .toList();
        sortedAlpha.forEach(System.out::println);

        // 7. Sorted alpha desc
        List<String> sortedAlphaDesc = sortedAlpha.reversed();
        sortedAlphaDesc.forEach(System.out::println);

        // 8. Unique first letter
        List<String> uniqueFirstLetter = countries.stream()
                                                  .map(p -> p.substring(0,1))
                                                  .distinct()
                                                  .toList();
        uniqueFirstLetter.forEach(System.out::println);

        // 9. Country count
        long countryCount = countries.stream()
                                        .count();
        System.out.printf("There are %d countries\n", countryCount);

        // 10. Longest name
        Optional<String> longestName = countries.stream()
                                                .max(Comparator.comparingInt(String::length));
        longestName.ifPresent(System.out::println);

        // 11. Shortest name
        Optional<String> shortestName = countries.stream()
                                                .min(Comparator.comparingInt(String::length));
        shortestName.ifPresent(System.out::println);

        // 12. Names in uppercase
        List<String> upperNames = countries.stream()
                                           .map(String::toUpperCase)
                                           .toList();
        upperNames.forEach(System.out::println);

        // 13. Names with more than one word
        List<String> moreThanOneWord = countries.stream()
                                                .filter(p -> p.contains(" "))
                                                .toList();
        moreThanOneWord.forEach(System.out::println);

        // 14. Map to number of characters
        List<String> mapToChars = countries.stream()
                                                .map(p -> p + ": " + p.length() + " characters")
                                                .toList();
        mapToChars.forEach(System.out::println);

        // 15. Does it start with Z?
        boolean startWithZ = countries.stream()
                                      .anyMatch(s -> s.toLowerCase().startsWith("z"));
        System.out.println("Does any country start with z? " + startWithZ);

        // 15. Do all names have more than 3 chars?
        boolean moreThanThreeChars = countries.stream()
                .allMatch(s -> s.length() > 3);
        System.out.println("Do all countries have more than 3 characters? " + moreThanThreeChars);


        try {
            Files.createDirectories(dirPath); // Create directory if it doesn’t exist

            Files.writeString(
                    dataPath,
                    dataMessage,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND // Prevents overwriting existing content
                             );

            System.out.println("Data has been saved to " + dataPath.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }
}
