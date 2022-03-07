import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import utils.TensorVision.Ball;

class SampleApp {

    private static Ball[] balls;
    private static String detectionsEntry;
    private static String teamColor = "red";

    public static void main(String[] args) throws IOException {
        Path fileName = Path.of("example1.json");
        detectionsEntry = Files.readString(fileName);

        balls = parseTargets(detectionsEntry);

        if (hasTargets(balls, teamColor)) {
            System.out.println("Found target!");
        } else {
            System.out.println("Not targets found!");
        }
    }

    public static Ball[] parseTargets(String payload) throws JsonParseException, JsonMappingException, IOException {
        // balls.clear(); // refresh balls
        String detections = payload;

        final ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(detections, Ball[].class);
    }

    // Do we have a target? If not, move on ...
    public static boolean hasTargets(Ball[] ballList, String teamColor) {
        boolean foundTarget = false;
        for (int i = 0; i < ballList.length; i++) {
            System.out.println(ballList[i].color);
            if (ballList[i].color.equals(teamColor)) {
                foundTarget = true;
            }
        }
        return foundTarget;
    }

    // Get the array index of the best target.
    public static int getBestTarget(Ball[] ballList, String teamColor) {
        int bestTarget = 0;
        return bestTarget;
    }

    // Used for setting strength of the controller rumble. The closer, the stronger
    public static int getBestTargetDistance() {
        int targetDistance = 0;
        return targetDistance;
    }

    // Get the Yaw of the best target to turn the robot
    public static double getBestTargetYaw() {
        int targetYaw = 0;
        return targetYaw;
    }

}
