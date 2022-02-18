import processing.core.PImage;

import java.util.List;

public abstract class Plant extends ActivityEntity{
    private int health;

    public Plant(String id,
                 Point position,
                 List<PImage> images,
                 int imageIndex,
                 int actionPeriod,
                 int animationPeriod,
                 int health)
    {
        super(id, position, images, imageIndex, animationPeriod, actionPeriod);
        this.health = health;
    }

    public int getHealth()
    {
        return health;
    }

    public void setHealth(int h)
    {
        health = health + h;
    }

    protected abstract boolean transformPlant(WorldModel world, EventScheduler scheduler, ImageStore imageStore);
}
