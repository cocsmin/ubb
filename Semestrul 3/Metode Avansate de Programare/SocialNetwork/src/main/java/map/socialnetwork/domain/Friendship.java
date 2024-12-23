package map.socialnetwork.domain;

import java.time.LocalDateTime;
import static java.lang.Math.max;
import static java.lang.Math.min;
public class Friendship extends Entity<Long> {

    LocalDateTime friendsFrom;
    Long idUser1;
    Long idUser2;

    public Friendship(Long idUser1, Long idUser2, LocalDateTime friendsFrom) {
        this.idUser1 = min(idUser1, idUser2);
        this.idUser2 = max(idUser1, idUser2);
        this.friendsFrom = friendsFrom;
    }

    public Long getIdUser1() {
        return idUser1;
    }

    public Long getIdUser2() {
        return idUser2;
    }

    public LocalDateTime getFriendsFrom() {
        return friendsFrom;
    }

}
