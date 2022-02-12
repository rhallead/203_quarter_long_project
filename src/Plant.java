import processing.core.PImage;

import java.util.List;

public abstract class Plant extends ActivityEntity{
    private int health;
    private int healthLimit;

    public Plant(String id,
                 Point position,
                 List<PImage> images,
                 int imageIndex,
                 int actionPeriod,
                 int animationPeriod,
                 int health,
                 int healthLimit)
    {
        super(id, position, images, imageIndex, animationPeriod, actionPeriod);
        this.health = health;
        this.healthLimit = healthLimit;
    }

    public int getHealth()
    {
        return health;
    }

    public int getHealthLimit()
    {
        return healthLimit;
    }

    public void setHealth(int h)
    {
        health = health + h;
    }
}
