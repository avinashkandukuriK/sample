import java.io.FileReader;
import java.util.*;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class CSVDataLoader {

    public static List<Map<String, String>> readCSV(String filePath) {
        List<Map<String, String>> dataList = new ArrayList<>();

        try (CSVReader reader = new CSVReaderBuilder(new FileReader(filePath)).withSkipLines(1).build()) {
            List<String[]> rows = reader.readAll();

            // Assuming first row (header) contains column names
            String[] headers = {"username", "password", "expectedResult"};

            for (String[] row : rows) {
                Map<String, String> data = new HashMap<>();
                for (int i = 0; i < headers.length; i++) {
                    data.put(headers[i], row[i]); // Map column name to value
                }
                dataList.add(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    public static void main(String[] args) {
        List<Map<String, String>> csvData = readCSV("src/test/resources/data.csv");
        csvData.forEach(System.out::println); // Print for debugging
    }
}
