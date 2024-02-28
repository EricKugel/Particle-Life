import java.io.*;
import java.util.HashMap;
import java.awt.*;

public class Colors {
    public static final HashMap<String, Color> COLOR_DEFINITIONS = new HashMap<String, Color>();
    public String[] colors;

    public HashMap<String, HashMap<String, Double>> map = new HashMap<String, HashMap<String, Double>>();

    public Colors(String colorGrid) {
        try {
            File colorDefinitionsFile = new File("color_definitions.txt");
            BufferedReader reader = new BufferedReader(new FileReader(colorDefinitionsFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String colorString = line.split("=")[0];
                String[] rgbValues = line.split("=")[1].split(",");
                Color color = new Color(Integer.parseInt(rgbValues[0]), Integer.parseInt(rgbValues[1]), Integer.parseInt(rgbValues[2]));
                COLOR_DEFINITIONS.put(colorString, color);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            File file = new File(colorGrid);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            colors = reader.readLine().split(",", -1);
            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                HashMap<String, Double> rowMap = new HashMap<String, Double>();
                map.put(colors[lineNumber], rowMap);
                String[] values = line.split(",", -1);
                for (int i = 1; i < values.length; i++) {
                    String valueString = values[i];
                    double value = valueString == "" ? 0 : Double.parseDouble(valueString);
                    rowMap.put(colors[i], value);
                }
                lineNumber += 1;
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double get(String color1, String color2) {
        return map.get(color1).get(color2);
    }
}
