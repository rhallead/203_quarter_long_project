import processing.core.PImage;

import java.util.List;

public abstract class AnimationEntity extends Entity{
    private int animationPeriod;
    private int imageIndex;

    public AnimationEntity(String id,
                           Point position,
                           List<PImage> images,
                           int imageIndex,
                           int animationPeriod)
    {
        super(id, position, images);
        this.imageIndex = imageIndex;
        this.animationPeriod = animationPeriod;
    }

    public int getAnimationPeriod()
    {
        return animationPeriod;
    }

    protected abstract void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore);

    public void nextImage()
    {
        imageIndex = (imageIndex + 1) % super.getImages().size();
    }

    public PImage getCurrentImage()
    {
        return super.getImages().get(imageIndex);
    }
}
