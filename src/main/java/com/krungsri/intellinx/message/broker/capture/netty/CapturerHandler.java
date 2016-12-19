package com.krungsri.intellinx.message.broker.capture.netty;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@Component
public class CapturerHandler extends SimpleChannelInboundHandler<ByteBuf> {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
		int readableBytes = msg.readableBytes();
	    byte[] buffer = new byte[readableBytes];
	    msg.readBytes(buffer);
	    String hex = new String(Hex.encodeHex(buffer));
	    this.logger.info("0x" + hex.toUpperCase());
	}

}
