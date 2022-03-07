import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import utils.TensorVision.Target;

class SampleApp {

    private static Target[] targets;
    private static String detectionsEntry;
    private static String teamColor = "red";

    public static void main(String[] args) throws IOException {
        Path fileName = Path.of("example1.json");
        detectionsEntry = Files.readString(fileName);

        targets = parseTargets(detectionsEntry);

        if (hasTargets(targets, teamColor)) {
            System.out.println("Found target!");
        } else {
            System.out.println("Not targets found!");
        }
    }

    public static Target[] parseTargets(String payload) throws JsonParseException, JsonMappingException, IOException {
        // targets.clear(); // refresh targets
        String detections = payload;

        final ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(detections, Target[].class);
    }

    // Do we have a target? If not, move on ...
    public static boolean hasTargets(Target[] targetList, String teamColor) {
        boolean foundTarget = false;
        for (int i = 0; i < targetList.length; i++) {
            System.out.println(targetList[i].color);
            if (targetList[i].color.equals(teamColor)) {
                foundTarget = true;
            }
        }
        return foundTarget;
    }

    // Get the array index of the best target.
    public static int getBestTarget(Target[] targetList, String teamColor) {
        int bestTarget = 0;
        return bestTarget;
    }

    // Used for setting strength of the controller rumble. The closer, the stronger
    public static int getTargetDistance() {
        int targetDistance = 0;
        return targetDistance;
    }

    // Get the Yaw of the best target to turn the robot
    public static double getTargetYaw() {
        int targetYaw = 0;
        return targetYaw;
    }

}
