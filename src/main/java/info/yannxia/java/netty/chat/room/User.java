package info.yannxia.java.netty.chat.room;

import lombok.Builder;
import lombok.Data;

/**
 * Created by yann on 2017/4/14.
 */
@Data
@Builder
public class User {
    private Long id;
    private String name;
}
