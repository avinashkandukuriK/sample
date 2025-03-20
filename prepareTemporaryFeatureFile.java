import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FeatureFileTempManager {

    private static final String ORIGINAL_FEATURE_FILE = "src/test/resources/features/DynamicCSV.feature";
    private static final String TEMP_FEATURE_FILE = "src/test/resources/features/TempDynamicCSV.feature";
    private static final String CSV_FILE_PATH = "src/test/resources/data.csv";

    public static void prepareTemporaryFeatureFile() {
        try {
            // Copy original feature file to a temporary one
            Files.copy(Paths.get(ORIGINAL_FEATURE_FILE), Paths.get(TEMP_FEATURE_FILE), StandardCopyOption.REPLACE_EXISTING);
            
            // Read CSV data
            List<Map<String, String>> newTestData = CSVDataLoader.readCSV(CSV_FILE_PATH);
            List<String> fileLines = Files.readAllLines(Paths.get(TEMP_FEATURE_FILE));
            int examplesIndex = findExamplesIndex(fileLines);

            if (examplesIndex == -1) {
                System.out.println("ERROR: Could not find Examples section.");
                return;
            }

            // Convert CSV data into Example table rows
            List<String> newRows = new ArrayList<>();
            for (Map<String, String> row : newTestData) {
                newRows.add("      | " + row.get("username") + " | " + row.get("password") + " | " + row.get("expectedResult") + " |");
            }

            // Insert new rows in the temporary file
            fileLines.addAll(examplesIndex + 1, newRows);
            Files.write(Paths.get(TEMP_FEATURE_FILE), fileLines);

            System.out.println("Temporary feature file created: " + TEMP_FEATURE_FILE);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteTemporaryFeatureFile() {
        try {
            Files.deleteIfExists(Paths.get(TEMP_FEATURE_FILE));
            System.out.println("Temporary feature file deleted.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Find the index where the Examples section begins
    private static int findExamplesIndex(List<String> fileLines) {
        for (int i = 0; i < fileLines.size(); i++) {
            if (fileLines.get(i).trim().startsWith("Examples:")) {
                return i + 1; // Return the index after Examples:
            }
        }
        return -1; // Not found
    }

    public static void main(String[] args) {
        prepareTemporaryFeatureFile();
    }
}
