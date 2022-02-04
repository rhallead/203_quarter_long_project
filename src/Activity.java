public class Activity implements Action{
    private AnimationEntity entity;
    private WorldModel world;
    private ImageStore imageStore;

    public Activity(
            AnimationEntity entity,
            WorldModel world,
            ImageStore imageStore)
    {
        this.entity = entity;
        this.imageStore = imageStore;
        this.world = world;
    }

    public void executeAction(EventScheduler scheduler) {
        entity.executeActivity(world, imageStore, scheduler);
    }
}
