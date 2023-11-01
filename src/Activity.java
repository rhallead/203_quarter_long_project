public class Activity implements Action {
    private WorldModel world;
    private ImageStore imageStore;
    private ActivityEntity entity;

    public Activity(ActivityEntity entity, WorldModel world, ImageStore imageStore) {
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
    }

    public void executeAction(EventScheduler scheduler) {
        entity.executeActivity(scheduler, world, imageStore);
    }
}
