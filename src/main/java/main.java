import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import utils.TensorVision.Box;
import utils.TensorVision.Target;

class SampleApp {

    private static final double kImageHeight = 480;
    private static final double kImageWidth = 640;
    private static final double kFOVWidth = 60;
    private static final double kPixelsPerDegreeWidth = kImageWidth / kFOVWidth;

    private static Target[] m_targets;
    private static String m_detectionsEntry;
    private static String teamColor = "red";

    public static void main(String[] args) throws IOException {
        Path fileName = Path.of("example3.json");
        m_detectionsEntry = Files.readString(fileName);

        m_targets = parseTargets(m_detectionsEntry);

        if (hasTargets(m_targets, teamColor)) {
            System.out.println("Rumble strength: " + getRumbleStrength(m_targets, teamColor));
            System.out.println("Target Yaw: " + getTargetYaw(m_targets, teamColor));
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
            if (targetList[i].color.equals(teamColor)) {
                foundTarget = true;
            }
        }
        return foundTarget;
    }

    // Get the array index of the best target, the target with the close y value
    public static int getBestTarget(Target[] targetList, String teamColor) {
        int bestTarget = 0;
        Box largestBox = new Box();

        for (int i = 0; i < targetList.length; i++) {
            Box currentBox = targetList[i].box;

            // System.out.println("Checking ball ..." + targetList[i].color + " " +
            // currentBox.yMax + " " + largestBox.yMax);

            if (targetList[i].color.equals(teamColor)) {
                if (currentBox.yMax > largestBox.yMax) {
                    largestBox = targetList[i].box;
                    bestTarget = i;
                }
            }
        }
        return bestTarget;
    }

    // Used for finding the depth of the closet target
    public static double getTargetDistance(Target[] targetList, String teamColor) {
        double targetDistance = 0;
        targetDistance = targetList[getBestTarget(targetList, teamColor)].box.yMax;
        return targetDistance;
    }

    // Return rumbles strength for the controller, the closer the stronger the
    // effect
    public static double getRumbleStrength(Target[] targetList, String teamColor) {
        double rumbleStrength = 0;
        rumbleStrength = getTargetDistance(targetList, teamColor);
        rumbleStrength = rumbleStrength / kImageHeight;
        return rumbleStrength;
    }

    // Get the Yaw of the best target in order to turn the robot
    public static double getTargetYaw(Target[] targetList, String teamColor) {
        int myTarget = 0;
        double myTargetCentroid = 0;
        double myTargetPosition = 0;
        double myTargetYaw = 0;
 
        myTarget = getBestTarget(targetList, teamColor);
        myTargetCentroid = ((targetList[myTarget].box.xMax - targetList[myTarget].box.xMin) / 2)
                + targetList[myTarget].box.xMin;

        // Ensure that yaw is negative if the target is left of the center
        myTargetPosition = ((kImageWidth / 2) - myTargetCentroid) * -1;
        myTargetYaw = myTargetPosition / kPixelsPerDegreeWidth;

        return myTargetYaw;
    }

}
