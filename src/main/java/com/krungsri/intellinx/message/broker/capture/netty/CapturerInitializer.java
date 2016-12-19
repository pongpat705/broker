package com.krungsri.intellinx.message.broker.capture.netty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.Timer;

@Component
public class CapturerInitializer extends ChannelInitializer<SocketChannel>{

	  @Autowired
	  private CustomByteDecoder byteDecoder;
	  
	  @Autowired
	  private CapturerHandler handler;
	  
	  @Autowired
	  private CapturerTimeoutHandler timoutHandler;
	  
	@Override
	protected void initChannel(SocketChannel ch) {
	    ChannelPipeline p = ch.pipeline();
	    p.addLast(new ChannelHandler[] { this.byteDecoder });
	    p.addLast(new ChannelHandler[] { this.handler });
//	    p.addLast("readTimeoutHandler", new ReadTimeoutHandler(2));
//	    p.addLast("capturerTimeoutHandler", this.timoutHandler);
	}

}
