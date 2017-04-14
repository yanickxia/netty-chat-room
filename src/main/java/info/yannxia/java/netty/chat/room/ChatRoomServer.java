package info.yannxia.java.netty.chat.room;

import info.yannxia.java.netty.chat.room.codec.BinProtocolDecoder;
import info.yannxia.java.netty.chat.room.handler.ChatHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.log4j.Log4j2;

import java.util.logging.Logger;

/**
 * Created by yann on 2017/4/14.
 */
@Log4j2
public class ChatRoomServer {

    public static int DEFAULT_PORT = 8080;

    public static String DEFAULT_HOST = "localhost";

    public static void main(String[] args) {
        log.info("starting server");
        long startTime = System.currentTimeMillis();

        int port = getPort(args);



        EventLoopGroup masterGroup = new NioEventLoopGroup(1);
        EventLoopGroup slaveGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap boot = new ServerBootstrap();
            boot.group(masterGroup, slaveGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pip = ch.pipeline();

                            pip.addLast(new LineBasedFrameDecoder(2048));
                            pip.addLast("decoder", new BinProtocolDecoder());
                            pip.addLast("handler", new ChatHandler());
//                            pip.addLast("encoder", new BinProtocolEncoder());
                            //pip.addLast("http-codec", new HttpServerCodec());
                            //pip.addLast("aggregator", new HttpObjectAggregator(65536));
                            //pip.addLast("http-chunked", new ChunkedWriteHandler());
                            //pip.addLast("handler", new WebSocketHandler(repo));
                        }
                    });

            ChannelFuture f = boot.bind(8080).sync();
            log.info("server is listening at {}:{}/{}", "localhost", port, "chat");
            log.info("time consumed:{}ms", System.currentTimeMillis() - startTime);

            f.channel().closeFuture().sync();


        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            log.info("stopping server");

            masterGroup.shutdownGracefully();
            slaveGroup.shutdownGracefully();

            log.info("server stopped");
        }
    }

    public static int getPort(String[] args) {
        if (args.length > 0) {
            int port;
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException ex) {
                log.info("invalid port, using default {}", DEFAULT_PORT);
                return DEFAULT_PORT;
            }
            return port;
        }
        log.info("using default port {}", DEFAULT_PORT);
        return DEFAULT_PORT;
    }
}
