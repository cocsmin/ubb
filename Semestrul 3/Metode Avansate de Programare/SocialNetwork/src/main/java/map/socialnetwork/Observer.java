package map.socialnetwork;

import map.socialnetwork.events.Event;

public interface Observer<E extends Event> {
    void update(E event);
}
