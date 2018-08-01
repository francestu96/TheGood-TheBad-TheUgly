package goodbadugly.common;

public class Constants {
	// codes
	public final static String RENEGADE = "/////////RENEGADE/////////";
	public final static int ACCEPTED = -1;
	public final static int USEDNICK = -2;
	public final static int FULLSRV = -3;
	public final static int YOURTURN = -4;
	public final static int NOTYOURTURN = -13;
	public final static int LOSTTURN = -5;
	public final static int NO_ANSWER = -6;
	public final static int DISCONNECTED = -7;
	public final static int DUELYOU = -13;
	public final static int DUELOTH = -14;
	public final static int DUELFINISHED = -15;
	public final static int WIN = -16;
	public final static int LOSE = -17;
	public final static int TIE = -22;
	public final static int SCISSOR = -18;
	public final static int ROCK = -19;
	public final static int PAPER = -20;
	public final static int DUELCLICK = -21;

	// server constants
	public final static int SERVER_TIMEOUT = 5000;
	public final static int PORT_NO = 10025;
	public final static int CORE_POOL_SIZE = 4;
	public final static int MAX_POOL_SIZE = 10;
	public final static int TIME_ALIVE = 1;
	public final static int TURN_TIMEOUT = 15000;
	public final static int GAME_TIMEOUT = 1000000;

	public static final boolean BLACK = false;
	public static final boolean WHITE = true;

	// client constants
	public static final int NICK_ALREADY_USED = -8;
	public static final int SERVER_UNKNOWN = -9;
	public static final int SUCCESSFULLY_CONNECT = -10;
	public static final int CHANGE_TO_GAME_FRAME = -11;
	public static final int PAINT = -12;
	public final static int PAIR = -23;
	public final static int GAMEOVER = -24;
	public static final String SERVER_ADDR = "localhost";
	public static final int NPLAYERMAX = 4;

	// frame constants
	public final static int WIDTH = 1229;
	public final static int HEIGHT = 745;
	public final static int STATE_PANEL_HEIGHT = 30;
	public final static int BAR_HEIGHT = 20;
}
