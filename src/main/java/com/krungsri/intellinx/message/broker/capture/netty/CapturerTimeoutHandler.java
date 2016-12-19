package com.krungsri.intellinx.message.broker.capture.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.ReadTimeoutException;

@Component
public class CapturerTimeoutHandler extends ChannelDuplexHandler{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if (cause instanceof ReadTimeoutException) {
			logger.info("channel is timeout");
        } else {
            super.exceptionCaught(ctx, cause);
        }
	}

}
