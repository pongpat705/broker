package com.krungsri.intellinx.message.broker.capture.netty;

import java.util.List;

import org.springframework.stereotype.Component;

import com.couchbase.client.deps.io.netty.channel.ChannelHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

@Component
@ChannelHandler.Sharable
public class CustomByteDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		 Object decoded = decode(ctx, in);
		    if (decoded != null) {
		      out.add(decoded);
		    }
	}
	
	protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
	    int readableBytes = in.readableBytes();
	    if (readableBytes < 4)
	    {
	      if (0 == readableBytes) {
	        return null;
	      }
	      return in.readRetainedSlice(readableBytes);
	    }
	    return in.readRetainedSlice(4);
	}

}
