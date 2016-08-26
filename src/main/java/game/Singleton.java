package game;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by kaique on 23/08/16.
 */
public class Singleton {

    // Battle Variables
    public static final int BATTLE_MODE_ELEMENTS = 707;
    public static final int BATTLE_MODE_MAGIC    = 404;
    public static final int BATTLE_MODE_EQUIP    = 303;
    public static final int BATTLE_MODE_ACTION   = 101;

    public static final int BATTLE_OPPONENT_PLAYER = 7777;
    public static final int BATTLE_OPPONENT_AI     = 8888;

    public static final int BATTLE_NONE = 42;

    public static int BATTLE_OPPONENT;
    public static int BATTLE_MODE;

    // Game Character Variables
    public static GameChar GAME_CHAR;
    public static LatLng CASTLE_LOCATION;

    // Screen Variables
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;

    public static int GAME_WIDTH  = 667;
    public static int GAME_HEIGHT = 1000;

    public static float RATIO;
}
