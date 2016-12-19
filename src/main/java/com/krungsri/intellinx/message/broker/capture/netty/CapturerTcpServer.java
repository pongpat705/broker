package com.krungsri.intellinx.message.broker.capture.netty;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

@Component
public class CapturerTcpServer implements GenericFutureListener<Future<Void>> {
	
	  private Logger logger = LoggerFactory.getLogger(getClass());
	  
	  @Value("${capturer.netty.port}")
	  private int port;
	  
	  @Autowired
	  private CapturerInitializer initializer;
	  
	  private EventLoopGroup bossGroup;
	  private EventLoopGroup workerGroup;
	  private Channel channel;
	
	
	 @PreDestroy
	  public void preDestroy() throws InterruptedException {
	    this.logger.info("Shutting down Gateway Http Server (Netty) channel");
	    if (null != this.channel) {
	      this.channel.close().sync();
	    }
	  }
	  
	  @PostConstruct
	  public void createChannel() throws InterruptedException {
	    this.logger.info("Starting Gateway Http Server (Netty)");
	    
	    this.bossGroup = new NioEventLoopGroup(10);
	    this.workerGroup = new NioEventLoopGroup(80);
	    
	    ServerBootstrap b = new ServerBootstrap();
	    
	    ((ServerBootstrap)((ServerBootstrap)b.group(this.bossGroup, this.workerGroup).channel(NioServerSocketChannel.class)).handler(new LoggingHandler(LogLevel.INFO)))
	      .childHandler(this.initializer)
	      .childOption(ChannelOption.AUTO_READ, Boolean.valueOf(true));
	    
	    
	    this.channel = b.bind(this.port).sync().channel();
	    this.channel.closeFuture().addListener(this);
	  }
	  
	@Override
	public void operationComplete(Future<Void> arg0) throws Exception {
		this.logger.info("Shutting down workgroup of Gateway Http Server (Netty) gracefully");
	    if (null != this.bossGroup) {
	      this.bossGroup.shutdownGracefully();
	    }
	    if (null != this.workerGroup) {
	      this.workerGroup.shutdownGracefully();
	    }
	}

}
