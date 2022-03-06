package utils.TensorVision;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Ball {
    @JsonProperty("label")
    public String color;
    @JsonProperty("box")
    public Box box;
    @JsonProperty("confidence")
    public double confidence;
    // public double d = 5;
}
