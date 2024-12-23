package map.socialnetwork.events;

import map.socialnetwork.domain.User;

public class UserEvent extends Event {
    private EventType type;
    private User data, olddata;

    public UserEvent(EventType type, User data) {
        this.type = type;
        this.data = data;
    }

    public UserEvent(EventType type, User data, User olddata) {
        this.type = type;
        this.data = data;
        this.olddata = olddata;
    }

    public EventType getType() {
        return type;
    }

    public User getData() {
        return data;
    }

    public User getOlddata() {
        return olddata;
    }
}

