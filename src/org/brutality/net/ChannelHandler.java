package org.brutality.net;

import org.brutality.Config;
import org.brutality.Server;
import org.brutality.model.players.Player;
import org.brutality.model.players.PlayerSave;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class ChannelHandler extends SimpleChannelHandler {

	private Session session = null;

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
	}

	/*public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		if (e.getMessage() instanceof Player) {
			session.setClient((Player) e.getMessage());
		} else if (e.getMessage() instanceof Packet) {
			Player client = session.getClient();
			if (client != null) {
				if (client.getPacketsReceived().get() < Config.MAX_INCOMONG_PACKETS_PER_CYCLE) {
					client.queueMessage((Packet) e.getMessage());
				} else {
					client.getOutStream().createFrame(109);
				}
			}
		}
	}*/
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		if (e.getMessage() instanceof Player) {
			session.setClient((Player) e.getMessage());
		} else if (e.getMessage() instanceof Packet) {
			Player client = session.getClient();
			if (client != null) {
				Server.ENGINE.execute((Packet) e.getMessage(), client);
			}
		}
	}

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
		if (session == null)
			session = new Session(ctx.getChannel());
	}

	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		if (session != null) {
			Player client = session.getClient();
			if (client != null) {
				// ConnectionList.removeConnection(((InetSocketAddress)ctx.getChannel().getRemoteAddress()).getAddress().getHostAddress().toString());
				client.disconnected = true;
				PlayerSave.saveGame(client);

			}
			session = null;
		}
	}

}
