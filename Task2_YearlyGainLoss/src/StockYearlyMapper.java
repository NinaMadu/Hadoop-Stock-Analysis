import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class StockYearlyMapper extends Mapper<Object, Text, Text, Text> {
    private Text yearKey = new Text();

    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        if (line.startsWith("Date")) return; // skip header

        String[] parts = line.split(",");
        if (parts.length < 5) return;

        String date = parts[0];
        String year = date.split("-")[0];
        double open = Double.parseDouble(parts[1]);
        double close = Double.parseDouble(parts[4]);
        double gain = close - open;

        String filename = ((org.apache.hadoop.mapreduce.lib.input.FileSplit) context.getInputSplit()).getPath().getName();
        String ticker = filename.replace(".txt", "");

        yearKey.set(year);
        context.write(yearKey, new Text(ticker + "," + gain + "," + date));
    }
}
