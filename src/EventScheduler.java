import java.util.*;

/**
 * Keeps track of events that have been scheduled.
 */
public final class EventScheduler
{
    private PriorityQueue<Event> eventQueue;
    private Map<Entity, List<Event>> pendingEvents;
    private double timeScale;

    public EventScheduler(double timeScale) {
        this.eventQueue = new PriorityQueue<>(new EventComparator());
        this.pendingEvents = new HashMap<>();
        this.timeScale = timeScale;
    }

    public Map<Entity, List<Event>> getPendingEvents() { return pendingEvents;}

    public PriorityQueue<Event> getEventQueue() { return eventQueue; }
    public void updateOnTime(long time) {
        while (!eventQueue.isEmpty()
                && eventQueue.peek().getTime() < time) {
            Event next = eventQueue.poll();

            next.removePendingEvent(this);

            next.getAction().executeAction(this);
        }
    }

    public void unscheduleAllEvents(Entity entity)
    {
        List<Event> pending = pendingEvents.remove(entity);

        if (pending != null) {
            for (Event event : pending) {
                eventQueue.remove(event);
            }
        }
    }

    public void scheduleEvent(Entity entity,
                              Action action,
                              long afterPeriod)
    {
        long time = System.currentTimeMillis() + (long)(afterPeriod
                * timeScale);
        Event event = new Event(action, time, entity);

        eventQueue.add(event);

        // update list of pending events for the given entity
        List<Event> pending = pendingEvents.getOrDefault(entity,
                new LinkedList<>());
        pending.add(event);
        pendingEvents.put(entity, pending);
    }
}