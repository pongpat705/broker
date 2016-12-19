package com.krungsri.intellinx.message.broker.capture.netty;

import org.springframework.stereotype.Component;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

@Component
public class CapturerInitializer extends ChannelInitializer<SocketChannel>{

	@Override
	protected void initChannel(SocketChannel ch) {
	    ChannelPipeline p = ch.pipeline();
	    p.addLast(new ChannelHandler[] { new CustomByteDecoder() });
	    p.addLast(new ChannelHandler[] { new CapturerHandler() });
	}

}
