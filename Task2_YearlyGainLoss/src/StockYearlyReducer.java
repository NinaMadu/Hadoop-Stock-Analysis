import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class StockYearlyReducer extends Reducer<Text, Text, Text, Text> {

    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        double maxGain = Double.NEGATIVE_INFINITY;
        double maxLoss = Double.POSITIVE_INFINITY;
        String maxGainTicker = "";
        String maxGainDate = "";
        String maxLossTicker = "";
        String maxLossDate = "";

        for (Text val : values) {
            String[] parts = val.toString().split(",");
            String ticker = parts[0];
            double gain = Double.parseDouble(parts[1]);
            String date = parts[2];

            if (gain > maxGain) {
                maxGain = gain;
                maxGainTicker = ticker;
                maxGainDate = date;
            }
            if (gain < maxLoss) {
                maxLoss = gain;
                maxLossTicker = ticker;
                maxLossDate = date;
            }
        }

        context.write(key, new Text(
            "Highest Gain: " + maxGainTicker + " " + maxGain + " on " + maxGainDate +
            " | Highest Loss: " + maxLossTicker + " " + maxLoss + " on " + maxLossDate
        ));
    }
}
