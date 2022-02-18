import processing.core.PImage;

import java.util.List;

public abstract class Dude extends People{
    private int resourceLimit;

    public Dude(String id,
                Point position,
                List<PImage> images,
                int imageIndex,
                int animationPeriod,
                int actionPeriod,
                int resourceLimit)
    {
        super(id, position, images, imageIndex, animationPeriod, actionPeriod);
        this.resourceLimit = resourceLimit;
    }

    public int getResourceLimit()
    {
        return resourceLimit;
    }

    protected Point nextPosition(
            WorldModel world, Point destPos)
    {
        int horiz = Integer.signum(destPos.x - super.getPosition().x);
        Point newPos = new Point(super.getPosition().x + horiz, super.getPosition().y);

        if (horiz == 0 || world.isOccupied(newPos) && world.getOccupancyCell(newPos).getClass() != Stump.class) {
            int vert = Integer.signum(destPos.y - super.getPosition().y);
            newPos = new Point(super.getPosition().x, super.getPosition().y + vert);

            if (vert == 0 || world.isOccupied(newPos) &&  world.getOccupancyCell(newPos).getClass() != Stump.class) {
                newPos = super.getPosition();
            }
        }

        return newPos;
    }
}
