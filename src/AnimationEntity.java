import processing.core.PImage;

import java.util.List;

public abstract class AnimationEntity extends Entity {
    private int animationPeriod;

    public AnimationEntity(String id, Point position, List<PImage> images, int imageIndex, int animationPeriod) {
        super(id, position, images, imageIndex);
        this.animationPeriod = animationPeriod;
    }
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, Factory.createAnimationAction(this, 0), getAnimationPeriod());
    }
    public int getAnimationPeriod() {
        return animationPeriod;
    }
    public void nextImage() { setImageIndex((getImageIndex() + 1) % getImages().size()); }
}