package info.yannxia.java.netty.chat.room.protocol;

import lombok.Builder;
import lombok.Data;

/**
 * Created by yann on 2017/4/14.
 */
@Data
@Builder
public class BinProtocol {
    private String msg;
}
