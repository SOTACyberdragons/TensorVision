import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import utils.TensorVision.Ball;
import utils.TensorVision.Box;

class SampleApp {

    public static double xMin, yMin, xMax, yMax;
    public static List<Ball> balls;
    private static String detectionsEntry;

    public static void main(String[] args) throws IOException {
        Path fileName = Path.of("example1.json");
        detectionsEntry = Files.readString(fileName);

        final ObjectMapper objectMapper = new ObjectMapper();
        Ball[] balls = objectMapper.readValue(detectionsEntry, Ball[].class);

        System.out.println(balls[1].color);
        // System.out.println(getClosestBall(false));
    }

    private static void readBallData() {
        // balls.clear(); // refresh balls
        String output = detectionsEntry;

        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = new JsonFactory();

        try {
            JsonParser parser = factory.createParser(output);
            balls = mapper.readTree(parser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Ball getClosestBall(boolean red) {
        String color;

        if (red) {
            color = new String("red");
        } else {
            color = new String("blue");
        }

        readBallData(); // get the list of all balls in our field of view

        int closestBallIndex = 0;

        // delete balls from list that aren't of our color
        for (int i = 0; i < balls.size(); i++) {
            if (balls.get(i).color != color) {
                balls.remove(i);
                i--; // we have deleted an element, so our index must shrink by 1
            }
        }

        // get index of ball with largest box
        for (int i = 0; i < balls.size(); i++) {
            Box currentBox = balls.get(i).box;

            Box largestBox = balls.get(closestBallIndex).box;

            if (currentBox.xMax - currentBox.xMin > largestBox.xMax - largestBox.xMin) {
                closestBallIndex = i;
            }
        }

        return balls.get(closestBallIndex);
    }

}
