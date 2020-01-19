package pl.pw.pamiw.biblio.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("notifications")
@Data
@NoArgsConstructor
public class Notification {
    @Id
    private long notificationId;

    private String sessionId;

    private String userName;

    private String pubName;

    private boolean seen;
}
