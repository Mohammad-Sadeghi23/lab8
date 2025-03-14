package ca.bcit.comp2522.lab8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class CountryLab
{
    private final Path dataPath;

    public CountryLab(final Path dirPath,
                      final Path dataPath)
    {

        // create directory if not exist
        if(!Files.exists(dirPath))
        {
            try
            {
                Files.createDirectory(dirPath);
            }
            catch(IOException e)
            {
                throw new RuntimeException(e);
            }
        }

        // create file if not exist,
        // if exist, erase it
        try
        {
            Files.writeString(
                    dataPath,
                    "",
                    StandardOpenOption.CREATE
            );
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }

        this.dataPath = dataPath;
    }

    public void writelnToFile(String string)
    {
        try
        {
            Files.writeString(
                    dataPath,
                    string + "\n",
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
                    // Prevents overwriting existing content
            );
        }
        catch(IOException e)
        {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }

    public static void main(final String[] args)
    {
        List<String> countries;
        final Path   dirPath;
        final Path   dataPath;

        final Stream<String>   longNames;
        final Stream<String>   shortNames;
        final Stream<String>   startA;
        final Stream<String>   endingLand;
        final Stream<String>   containsUnited;
        final Stream<String>   sortedAlpha;
        final Stream<String>   sortedAlphaDesc;
        final Stream<String>   uniqueFirstLetter;
        final long             countryCount;
        final Optional<String> longestName;
        final Optional<String> shortestName;
        final Stream<String>   upperNames;
        final Stream<String>   moreThanOneWord;
        final Stream<String>   mapToChars;
        final boolean          startWithZ;
        final boolean          moreThanThreeChars;

        dirPath  = Paths.get("matches");
        dataPath = dirPath.resolve("data.txt");
        CountryLab countryLab = new CountryLab(dirPath, dataPath);

        countries = List.of(); // in case input file is unreadable

        try
        {
            final Path path;
            path = Paths.get("src",
                             "code",
                             "ca",
                             "bcit",
                             "comp2522",
                             "lab8",
                             "week8countries.txt");

            // Check if file exists before reading
            if(!Files.exists(path))
            {
                System.out.println(
                        "Error: File not found at " + path.toAbsolutePath());
                return;
            }

            countries = Files.readAllLines(path);
            System.out.println("Countries read successfully: " + countries.size());

        }
        catch(final IOException e)
        {
            System.err.println("Error reading file: " + e.getMessage());
        }

        // 1. Filter long country names
        longNames = countries.stream()
                             .filter(p -> p.length() > 10);
        countryLab.writelnToFile("Country names longer than 10 characters:");
        longNames.forEach(countryLab::writelnToFile);

        // 2. short names
        shortNames = countries.stream()
                              .filter(p -> p.length() < 5);
        shortNames.forEach(countryLab::writelnToFile);

        // 3. starting with A
        startA = countries.stream()
                          .filter(p -> p.substring(0, 1)
                                        .equalsIgnoreCase("a"));
        countryLab.writelnToFile("\nCountry names starting with 'A':");
        startA.forEach(countryLab::writelnToFile);

        // 4. Ending with land
        endingLand = countries.stream()
                              .filter(p -> p.endsWith("land"));
        endingLand.forEach(countryLab::writelnToFile);

        // 5. Ending with land
        containsUnited = countries.stream()
                                  .filter(p -> p.toLowerCase()
                                                .contains("united"));
        containsUnited.forEach(countryLab::writelnToFile);

        // 6. Sorted alpha
        sortedAlpha = countries.stream()
                               .sorted();
        sortedAlpha.forEach(countryLab::writelnToFile);

        // 7. Sorted alpha desc
        sortedAlphaDesc = countries.stream()
                                   .sorted(Collections.reverseOrder());
        sortedAlphaDesc.forEach(countryLab::writelnToFile);

        // 8. Unique first letter
        uniqueFirstLetter = countries.stream()
                                     .map(p -> p.substring(0, 1))
                                     .distinct();
        uniqueFirstLetter.forEach(countryLab::writelnToFile);

        // 9. Country count
        countryCount = countries.stream()
                                .count();
        countryLab.writelnToFile("\nThere are " + countryCount + " countries");

        // 10. Longest name
        longestName = countries.stream()
                               .max(Comparator.comparingInt(String::length));
        longestName.ifPresent(name ->
                                      countryLab.writelnToFile(
                                              "\nLongest country name: " + name)
        );

        // 11. Shortest name
        shortestName = countries.stream()
                                .min(Comparator.comparingInt(String::length));
        shortestName.ifPresent(name ->
                                       countryLab.writelnToFile(
                                               "\nShortest country name: " + name)
        );

        // 12. Names in uppercase
        upperNames = countries.stream()
                              .map(String::toUpperCase);
        countryLab.writelnToFile("\nCountry names in uppercase:");
        upperNames.forEach(countryLab::writelnToFile);

        // 13. Names with more than one word
        moreThanOneWord = countries.stream()
                                   .filter(p -> p.contains(" "));
        countryLab.writelnToFile("\nCountry names with more than one word:");
        moreThanOneWord.forEach(countryLab::writelnToFile);

        // 14. Map to number of characters
        mapToChars = countries.stream()
                              .map(p -> p + ": " + p.length() +
                                        " characters");
        countryLab.writelnToFile("\nNumber of characters in each country name:");
        mapToChars.forEach(countryLab::writelnToFile);

        // 15. Does it start with Z?
        startWithZ = countries.stream()
                              .anyMatch(s -> s.toLowerCase().startsWith("z"));
        countryLab.writelnToFile("\nDoes any country start with z? " +
                                 (startWithZ
                                  ? "Yes"
                                  : "No")
        );

        // 15. Do all names have more than 3 chars?
        moreThanThreeChars = countries.stream()
                                      .allMatch(s -> s.length() > 3);
        countryLab.writelnToFile("\nDo all countries have more than 3 characters? " +
                                 (moreThanThreeChars
                                  ? "Yes"
                                  : "No"));
    }
}
