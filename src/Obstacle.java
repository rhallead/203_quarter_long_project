import processing.core.PImage;

import java.util.*;

public final class Obstacle extends AnimationEntity {
    public Obstacle(String id, Point position, List<PImage> images, int imageIndex, int animationPeriod) {
        super(id, position, images, imageIndex, animationPeriod);
    }

}