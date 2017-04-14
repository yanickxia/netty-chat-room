package info.yannxia.java.netty.chat.room.codec;

import info.yannxia.java.netty.chat.room.protocol.BinProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.log4j.Log4j2;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by yann on 2017/4/14.
 */
@Log4j2
public class BinProtocolDecoder extends MessageToMessageDecoder<ByteBuf> {

    private final Charset charset;

    /**
     * Creates a new instance with the current system character set.
     */
    public BinProtocolDecoder() {
        this(Charset.defaultCharset());
    }

    /**
     * Creates a new instance with the specified character set.
     */
    public BinProtocolDecoder(Charset charset) {
        if (charset == null) {
            throw new NullPointerException("charset");
        }
        this.charset = charset;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        out.add(BinProtocol.builder().msg(msg.toString(charset)).build());
    }
}
