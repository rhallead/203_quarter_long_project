public class Activity extends Action{
    private ActivityEntity entity;
    private WorldModel world;
    private ImageStore imageStore;

    public Activity(
            ActivityEntity entity,
            WorldModel world,
            ImageStore imageStore)
    {
        this.entity = entity;
        this.imageStore = imageStore;
        this.world = world;
    }

    protected void executeAction(EventScheduler scheduler) {
        entity.executeActivity(world, imageStore, scheduler);
    }
}
