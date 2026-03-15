import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/*
 REDUCER: Final Maximum
 Input  : Shuffled Input (TICKER:{Price1, Price2, ...})
 Ticker : Read from the FILENAME (not from data)
 Output : (TICKER, HighPrice)
 */

public class PeakPriceReducer
        extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {

    private DoubleWritable result = new DoubleWritable();

    // reduce() function
    @Override
    public void reduce(Text key, Iterable<DoubleWritable> values, Context context)
            throws IOException, InterruptedException {

        double max = Double.NEGATIVE_INFINITY;

        // Find the global maximum across all values received
        for (DoubleWritable val : values) {
            if (val.get() > max) {
                max = val.get();
            }
        }

        result.set(max);

        // Final output: (AAPL, 182.80)
        context.write(key, result);
    }
}