package net.sf.jinsim;

public enum PacketType {

	NONE(0),
	INSIM_INITIALIZE(1),
	VERSION(2),	
	TINY(3),
	SMALL(4),
	STATE(5),
	SINGLE_CHARACTER(6),
	STATE_FLAG(7),
	SET_CAR_CAMERA(8),
	CAMERA_POSITION(9),
	START_MULTIPLAYER(10),
	MESSAGE_OUT(11),
	HIDDEN_MESSAGE(12),
	MESSAGE(13),
	MESSAGE_TO_CONNECTION(14),
	SCREEN_MODE(15),
	VOTE_NOTIFICATION(16),
	RACE_START(17),
	NEW_CONNECTION(18),
	CONNECTION_LEFT(19),
	CONNECTION_PLAYER_RENAMED(20),
	NEW_PLAYER(21),
	PLAYER_PIT(22),
	PLAYER_LEAVE(23),
	LAP(24),
	SPLIT(25),
	PIT(26),
	PIT_FINISHED(27),
	PIT_LANE(28),
	CAMERA_CHANGED(29),
	PENALTY(30),
	TAKE_OVER_CAR(31),
	FLAG(32),
	PLAYER_FLAGS(33),
	FINISHED_RACE(34),
	RESULT_CONFIRMED(35),
	REORDER(36),
	NODE_LAP(37),
	MULIT_CAR_INFO(38),
	MESSAGE_EXTENDED(39),
	MESSAGE_TO_LOCAL(40),
	CAR_RESET(41),
	BUTTON_FUNCTION(42),
	AUTOCROSS_LAYOUT(43),
	AUTOCROSS_HIT(44),
	BUTTON(45),
	BUTTON_CLICKED(46),
	BUTTON_TYPED(47),
	OUT_GAUGE(1000),
	OUT_SIM(10001),
	RELAY_HOST_LIST(252),
	RELAY_HOST_LIST_INFO(253),
	RELAY_SELECT_HOST(254),
	RELAY_ERROR(255);
	
	private int id;
	
	PacketType(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public static PacketType getPacket(int id) {
		for (PacketType type : PacketType.values()) {
			if (type.id == id) {
				return type;
			}
		}
		throw new IllegalArgumentException("The specified id is not a packet type id: " + id);
	}
	
}