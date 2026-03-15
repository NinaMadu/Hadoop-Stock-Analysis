import java.io.*;
import java.util.*;

/*
 POST PROCESSOR
 Reads MapReduce output file and prints Top 10 stocks by peak price
 Run AFTER hadoop job finishes
*/
public class Top10 {

    public static void main(String[] args) throws Exception {

        String inputFile = args.length > 0 ? args[0] : "part-r-00000";

        List<double[]> results = new ArrayList<>();
        List<String>   tickers = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        String line;

        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;

            // Each line: TICKER \t PeakPrice
            String[] parts = line.split("\t");
            if (parts.length == 2) {
                try {
                    tickers.add(parts[0]);
                    results.add(new double[]{Double.parseDouble(parts[1])});
                } catch (NumberFormatException e) {
                    // skip bad line
                }
            }
        }
        br.close();

        // Combine into pairs and sort by price descending
        List<String[]> combined = new ArrayList<>();
        for (int i = 0; i < tickers.size(); i++) {
            combined.add(new String[]{tickers.get(i),
                         String.valueOf(results.get(i)[0])});
        }

        combined.sort((a, b) ->
            Double.compare(Double.parseDouble(b[1]),
                           Double.parseDouble(a[1]))
        );

        // Print Top 10
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║   TOP 10 STOCKS BY ALL-TIME PEAK PRICE  ║");
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.printf("║ %-4s  %-10s  %-22s ║%n",
                          "Rank", "Ticker", "All-Time Peak Price");
        System.out.println("╠══════════════════════════════════════════╣");

        for (int i = 0; i < 10 && i < combined.size(); i++) {
            String ticker = combined.get(i)[0];
            double price  = Double.parseDouble(combined.get(i)[1]);
            System.out.printf("║  %-4d  %-10s  $%-21.4f║%n",
                              i + 1, ticker, price);
        }

        System.out.println("╠══════════════════════════════════════════╣");
        System.out.printf("║  Total stocks processed: %-16d║%n",
                          combined.size());
        System.out.println("╚══════════════════════════════════════════╝");
    }
}