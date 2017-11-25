package org.brutality;

import java.net.InetSocketAddress;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.Executors;

import org.brutality.api.WebServer;
import org.brutality.clip.ObjectDef;
import org.brutality.clip.Region;
import org.brutality.data.ServerData;
import org.brutality.database.ChatLogHandler;
import org.brutality.database.KillLogHandler;
import org.brutality.database.LoginLogHandler;
import org.brutality.event.CycleEventHandler;
import org.brutality.model.content.clan.ClanManager;
import org.brutality.model.minigames.FightPits;
import org.brutality.model.multiplayer_session.MultiplayerSessionListener;
import org.brutality.model.npcs.NPCCacheDefinition;
import org.brutality.model.npcs.NPCHandler;
import org.brutality.model.npcs.boss.abstract_bosses.BossData;
import org.brutality.model.players.PlayerHandler;
import org.brutality.net.PipelineFactory;
import org.brutality.util.RemoveItems;
import org.brutality.util.SimpleTimer;
import org.brutality.util.date.Calendar;
import org.brutality.util.json.EquipmentRequirementLoader;
import org.brutality.util.json.ItemDefinitionLoader;
import org.brutality.util.json.NpcDefinitionLoader;
import org.brutality.util.json.NpcDropCacheLoader;
import org.brutality.util.json.NpcDropTableLoader;
import org.brutality.util.log.Logger;
import org.brutality.world.ItemHandler;
import org.brutality.world.PlayerManager;
import org.brutality.world.ShopHandler;
import org.brutality.world.StillGraphicsManager;
import org.brutality.world.objects.GlobalObjects;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.util.HashedWheelTimer;

import com.motivoters.motivote.service.MotivoteRS;

/**
 * The main class needed to start the server.
 * 
 * @author Sanity
 * @author Graham
 * @author Blake
 * @author Ryan Lmctruck30 Revised by Shawn Notes by Shawn
 */
public class Server { // go to your commands

	/**
	 * Represents our calendar with a given delay using the TimeUnit class
	 */
	private static Calendar calendar = new org.brutality.util.date.Calendar(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"));
	// public static MainLoader vote = new MainLoader("", "", "", "");
	private static MultiplayerSessionListener multiplayerSessionListener = new MultiplayerSessionListener();

	static GlobalObjects globalObjects = new GlobalObjects();

	public static long START_UP_TIME;
	public static int uptime;
	public static long recordLogin = 0;
	
	private static final MotivoteRS motivote = new MotivoteRS("noxious", "302a1f144a5c4baf2afa1d316bf8207a");

	/**
	 * Timers
	 **/
	@SuppressWarnings("unused")
	private static SimpleTimer engineTimer, debugTimer;

	/**
	 * ClanChat Added by Valiant
	 */
	public static ClanManager clanManager = new ClanManager();

	/**
	 * Calls to manage the players on the server.
	 */
	public static PlayerManager playerManager = null;
	private static StillGraphicsManager stillGraphicsManager = null;

	/**
	 * Sleep mode of the server.
	 */
	public static boolean sleeping;
	/**
	 * The test thingy
	 */
	public static boolean canGiveReward;

	public static long lastReward = 0;
	/**
	 * Calls the rate in which an event cycles.
	 */
	public static final int cycleRate;

	/**
	 * Server updating.
	 */
	public static boolean UpdateServer = false;

	/**
	 * Calls in which the server was last saved.
	 */
	public static long lastMassSave = System.currentTimeMillis();

	/**
	 * Calls the usage of CycledEvents.
	 */
	private static long sleepTime;

	/**
	 * Used for debugging the server.
	 */
	@SuppressWarnings("unused")
	private static DecimalFormat debugPercentFormat;

	/**
	 * Forced shutdowns.
	 */
	public static boolean shutdownServer = false;
	public static boolean shutdownClientHandler;

	public static boolean canLoadObjects = false;

	/**
	 * Used to identify the server port.
	 */
	public static int serverlistenerPort = 43595;

	/**
	 * Contains data which is saved between sessions.
	 */
	public static ServerData serverData = new ServerData();

	/**
	 * Handles the logging of all logins.
	 */
	public static LoginLogHandler loginLogHandler = new LoginLogHandler();

	/**
	 * Handles the logging of all chat messages.
	 */
	public static ChatLogHandler chatLogHandler = new ChatLogHandler();

	/**
	 * Handles the logging of all PvP kills.
	 */
	public static KillLogHandler killLogHandler = new KillLogHandler();

	/**
	 * Calls the usage of player items.
	 */
	public static ItemHandler itemHandler = new ItemHandler();

	/**
	 * Handles logged in players.
	 */
	public static PlayerHandler playerHandler = new PlayerHandler();

	/**
	 * Handles global NPCs.
	 */
	public static NPCHandler npcHandler = new NPCHandler();

	/**
	 * Handles global shops.
	 */
	public static ShopHandler shopHandler = new ShopHandler();

	public static final GameEngine ENGINE = new GameEngine();

	/**
	 * Handles the fightpits minigame.
	 */
	public static FightPits fightPits = new FightPits();

	public static long currentServerTime;

	static {
		if (!Config.SERVER_DEBUG) {
			serverlistenerPort = 43595;
		} else {
			serverlistenerPort = 43595;
		}
		cycleRate = 600;
		shutdownServer = false;
		engineTimer = new SimpleTimer();
		debugTimer = new SimpleTimer();
		sleepTime = 0;
		debugPercentFormat = new DecimalFormat("0.0#%");
	}

	public static BossData task;

	public static void main(java.lang.String args[]) {
		try {
			long startTime = System.currentTimeMillis();

			System.setOut(new Logger(System.out));
			System.setErr(new Logger(System.err));

			//new Motivote<Reward>(new RewardHandler(), "http://vote.OS-Brutality.com/", "e620cfed").start();
			//RemoveItems.main(args);
			ObjectDef.loadConfig();
			Region.load();
			CycleEventHandler.getSingleton();
			new ItemDefinitionLoader().load();
			new NpcDefinitionLoader().load();
			new EquipmentRequirementLoader().load();
			NPCCacheDefinition.unpackConfig();
			globalObjects.loadGlobalObjectFile();
			bind();
			playerManager = PlayerManager.getSingleton();
			playerManager.setupRegionPlayers();
			stillGraphicsManager = new StillGraphicsManager();
			Connection.initialize();
			//task = new BossData();
			NPCHandler.loadDefs();
			new NpcDropTableLoader().load();
			new NpcDropCacheLoader().load();
			ENGINE.start();
			//WebServer.initialize();
			long endTime = System.currentTimeMillis();
			long elapsed = endTime - startTime;
			START_UP_TIME = System.currentTimeMillis();

			System.out.println("ServerName has successfully started up in " + elapsed + " milliseconds.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the sleep mode timer and puts the server into sleep mode.
	 */
	public static long getSleepTimer() {
		return sleepTime;
	}

	/**
	 * Gets the Graphics manager.
	 */
	public static StillGraphicsManager getStillGraphicsManager() {
		return stillGraphicsManager;
	}

	/**
	 * Gets the Player manager.
	 */
	public static PlayerManager getPlayerManager() {
		return playerManager;
	}

	public static MultiplayerSessionListener getMultiplayerSessionListener() {
		return multiplayerSessionListener;
	}

	/**
	 * Java connection. Ports.
	 */

	private static void bind() {
		ServerBootstrap serverBootstrap = new ServerBootstrap(
				new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
		serverBootstrap.setPipelineFactory(new PipelineFactory(new HashedWheelTimer()));
		serverBootstrap.bind(new InetSocketAddress(serverlistenerPort));
	}

	public static Calendar getCalendar() {
		return calendar;
	}

	public static ServerData getServerData() {
		return serverData;
	}

	public static GlobalObjects getGlobalObjects() {
		return globalObjects;
	}

	public static MotivoteRS getMotivote() {
		return motivote;
	}

	/*
	 * public static LoginLogHandler getLoginLogHandler() { return
	 * loginLogHandler; } public static ChatLogHandler getChatLogHandler() {
	 * return chatLogHandler; }
	 * 
	 * public static KillLogHandler getKillLogHandler() { return killLogHandler;
	 * }
	 */

}
