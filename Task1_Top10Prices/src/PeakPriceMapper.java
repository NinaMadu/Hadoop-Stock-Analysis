import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/*
 MAPPER: Stock Peak Price
 Input  : One line from a stock file
 Ticker : Read from the FILENAME (not from data)
 Output : (TICKER, HighPrice)
*/

public class PeakPriceMapper
        extends Mapper<LongWritable, Text, Text, DoubleWritable> {

    private Text tickerKey = new Text();
    private DoubleWritable highValue = new DoubleWritable();


    // map() function
    @Override
    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {

        String line = value.toString().trim();

        // Skip header line and empty lines
        if (line.startsWith("Date") || line.isEmpty()) {
            return;
        }

        // CSV format:
        // Date,     Open,   High,   Low,    Close,  Volume,    OpenInt
        String[] parts = line.split(",");

        if (parts.length < 3) {
            return;
        }

        // Get ticker from filename
        // FileSplit tells us which file Hadoop is currently reading
        FileSplit fileSplit = (FileSplit) context.getInputSplit();
        String filename = fileSplit.getPath().getName();
        String ticker = filename.split("\\.")[0].toUpperCase();

        try {
            // High price
            double high = Double.parseDouble(parts[2].trim());

            if (high > 0) {
                tickerKey.set(ticker);
                highValue.set(high);

                // Emit: (AAPL, 0.42902)
                context.write(tickerKey, highValue);
            }

        } catch (NumberFormatException e) {
            // Skip rows where High is not a valid number
        }
    }
}