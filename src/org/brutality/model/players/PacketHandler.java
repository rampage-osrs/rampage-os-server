package org.brutality.model.players;

import org.brutality.Config;
import org.brutality.model.players.packets.AttackPlayer;
import org.brutality.model.players.packets.Bank10;
import org.brutality.model.players.packets.Bank5;
import org.brutality.model.players.packets.BankAll;
import org.brutality.model.players.packets.BankAllButOne;
import org.brutality.model.players.packets.BankModifiableX;
import org.brutality.model.players.packets.BankX1;
import org.brutality.model.players.packets.BankX2;
import org.brutality.model.players.packets.ChallengePlayer;
import org.brutality.model.players.packets.ChangeAppearance;
import org.brutality.model.players.packets.ChangeRegions;
import org.brutality.model.players.packets.Chat;
import org.brutality.model.players.packets.ClickItem;
import org.brutality.model.players.packets.ClickNPC;
import org.brutality.model.players.packets.ClickObject;
import org.brutality.model.players.packets.ClickingButtons;
import org.brutality.model.players.packets.ClickingInGame;
import org.brutality.model.players.packets.ClickingStuff;
import org.brutality.model.players.packets.Commands;
import org.brutality.model.players.packets.Dialogue;
import org.brutality.model.players.packets.DropItem;
import org.brutality.model.players.packets.FollowPlayer;
import org.brutality.model.players.packets.IdleLogout;
import org.brutality.model.players.packets.InputField;
import org.brutality.model.players.packets.ItemClick2;
import org.brutality.model.players.packets.ItemClick2OnGroundItem;
import org.brutality.model.players.packets.ItemClick3;
import org.brutality.model.players.packets.ItemOnGroundItem;
import org.brutality.model.players.packets.ItemOnItem;
import org.brutality.model.players.packets.ItemOnNpc;
import org.brutality.model.players.packets.ItemOnObject;
import org.brutality.model.players.packets.ItemOnPlayer;
import org.brutality.model.players.packets.MagicOnFloorItems;
import org.brutality.model.players.packets.MagicOnItems;
import org.brutality.model.players.packets.Moderate;
import org.brutality.model.players.packets.MoveItems;
import org.brutality.model.players.packets.PickupItem;
import org.brutality.model.players.packets.PrivateMessaging;
import org.brutality.model.players.packets.RemoveItem;
import org.brutality.model.players.packets.Report;
import org.brutality.model.players.packets.SelectItemOnInterface;
import org.brutality.model.players.packets.SilentPacket;
import org.brutality.model.players.packets.Trade;
import org.brutality.model.players.packets.Walking;
import org.brutality.model.players.packets.WearItem;
import org.brutality.model.players.packets.action.InterfaceAction;
import org.brutality.model.players.packets.action.JoinChat;
import org.brutality.model.players.packets.action.ReceiveString;
import org.brutality.net.Packet;

public class PacketHandler {

	private static PacketType packetId[] = new PacketType[256];
	
	public static Player c;
	
	static {
		SilentPacket u = new SilentPacket();
		packetId[3] = u;
		packetId[202] = u;
		packetId[77] = u;
		packetId[86] = u;
		packetId[78] = u;
		packetId[36] = u;
		packetId[226] = u;
		packetId[246] = u;
		packetId[148] = u;
		packetId[183] = u;
		packetId[230] = u;
		packetId[136] = u;
		packetId[189] = u;
		packetId[152] = u;
		packetId[200] = u;
		packetId[85] = u;
		packetId[165] = u;
		packetId[238] = u;
		packetId[150] = u;
		packetId[74] = u;
		packetId[234] = u;
		packetId[34] = u;
		packetId[68] = u;
		packetId[79] = u;
		packetId[140] = u;
		packetId[228] = u;
		//packetId[18] = u;
		packetId[223] = u;
		packetId[8] = new Moderate();
		packetId[142] = new InputField();
		packetId[253] = new ItemClick2OnGroundItem();
		packetId[218] = new Report();
		packetId[40] = new Dialogue();
		ClickObject co = new ClickObject();
		packetId[132] = co;
		packetId[252] = co;
		packetId[70] = co;
		packetId[57] = new ItemOnNpc();
		ClickNPC cn = new ClickNPC();
		packetId[72] = cn;
		packetId[131] = cn;
		packetId[155] = cn;
		packetId[17] = cn;
		packetId[21] = cn;
		packetId[18] = cn;
		packetId[124] = new SelectItemOnInterface();
		packetId[16] = new ItemClick2();
		packetId[75] = new ItemClick3();
		packetId[122] = new ClickItem(c);
		packetId[241] = new ClickingInGame();
		packetId[4] = new Chat();
		packetId[236] = new PickupItem();
		packetId[87] = new DropItem();
		packetId[185] = new ClickingButtons();
		packetId[130] = new ClickingStuff();
		packetId[103] = new Commands();
		packetId[214] = new MoveItems();
		packetId[237] = new MagicOnItems();
		packetId[181] = new MagicOnFloorItems();
		packetId[202] = new IdleLogout();
		AttackPlayer ap = new AttackPlayer();
		packetId[73] = ap;
		packetId[249] = ap;
		packetId[128] = new ChallengePlayer();
		packetId[39] = new Trade();
		packetId[139] = new FollowPlayer();
		packetId[41] = new WearItem();
		packetId[145] = new RemoveItem();
		packetId[117] = new Bank5();
		packetId[43] = new Bank10();
		packetId[129] = new BankAll();
		packetId[140] = new BankAllButOne();
		packetId[141] = new BankModifiableX();
		packetId[101] = new ChangeAppearance();
		PrivateMessaging pm = new PrivateMessaging();
		packetId[188] = pm;
		packetId[126] = pm;
		packetId[215] = pm;
		packetId[74] = pm;
		packetId[95] = pm;
		packetId[133] = pm;
		packetId[135] = new BankX1();
		packetId[208] = new BankX2();
		Walking w = new Walking();
		packetId[98] = w;
		packetId[164] = w;
		packetId[248] = w;
		packetId[53] = new ItemOnItem();
		packetId[192] = new ItemOnObject();
		packetId[25] = new ItemOnGroundItem();
		ChangeRegions cr = new ChangeRegions();
		packetId[60] = new JoinChat();
		packetId[127] = new ReceiveString();
		packetId[213] = new InterfaceAction();
		packetId[14] = new ItemOnPlayer();
		packetId[121] = cr;
		packetId[210] = cr;
	}

	public static void processPacket(Player c, Packet packet) {
        PacketType p = packetId[packet.getOpcode()];
        if(p != null && packet.getOpcode() > 0 && packet.getOpcode() < 257) {
            if (Config.sendServerPackets && c.getRights().isDeveloper() && Config.SERVER_DEBUG) {
                c.sendMessage("PacketType: " + packet.getOpcode() + ". PacketSize: " + packet.getLength() + ".");
            }
            try {
                p.processPacket(c, packet);
            } catch(Exception e) {
                e.printStackTrace();
            }
        } else {
            c.disconnected = true;
            System.out.println(c.playerName + " is sending invalid PacketType: " + packet.getOpcode() + ". PacketSize: " + packet.getLength());
        }
    }
	}