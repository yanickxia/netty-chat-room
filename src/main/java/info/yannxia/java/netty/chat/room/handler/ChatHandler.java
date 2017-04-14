package info.yannxia.java.netty.chat.room.handler;

import info.yannxia.java.netty.chat.room.protocol.BinProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.log4j.Log4j2;

/**
 * Created by yann on 2017/4/14.
 */

@Log4j2
public class ChatHandler extends SimpleChannelInboundHandler<BinProtocol> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BinProtocol msg) throws Exception {
        log.info("Received : " + msg);
    }
}
