package map.socialnetwork.domain;

import java.time.LocalDateTime;

public class Cerere extends Entity<Long>{

    private Long id1;
    private Long id2;
    private LocalDateTime date;
    private String status;

    public Cerere(Long id1, Long id2, LocalDateTime date, String status) {
        this.id1 = id1;
        this.id2 = id2;
        this.date = date;
        this.status = status;
    }

    public Long getId1() {
        return id1;
    }
    public Long getId2() {
        return id2;
    }

    public LocalDateTime getDate() {
        return date;
    }
    public String getStatus() {
        return status;
    }

}

