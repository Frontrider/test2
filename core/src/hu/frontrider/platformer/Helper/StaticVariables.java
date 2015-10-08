package hu.frontrider.platformer.Helper;

/**
 * Created by Frontrider on 2015.09.16..
 */
public abstract class StaticVariables
{

    public static short CATEGORY_PLAYER = 0x0001;  // 0000000000000001 in binary
    public static short CATEGORY_MONSTER = 0x0002; // 0000000000000010 in binary
    public static short CATEGORY_SCENERY = 0x0004; // 0000000000000100 in binary
    public static short CATEGORY_TRIGGER = 0x0008;

    public static short PLAYER_GROUP = 1;


    public static short MASK_PLAYER = (short)(/*CATEGORY_MONSTER |*/ CATEGORY_SCENERY | CATEGORY_TRIGGER); // or ~CATEGORY_PLAYER
    public static short MASK_MONSTER = (short)(/*CATEGORY_PLAYER |*/ CATEGORY_SCENERY |CATEGORY_TRIGGER); // or ~CATEGORY_MONSTER
    public static short MASK_SCENERY = -1;
    public static short MASK_FIST = (short) (CATEGORY_MONSTER |CATEGORY_SCENERY);
    public static short MASK_TRIGGER = (short) (CATEGORY_MONSTER | CATEGORY_TRIGGER/*|CATEGORY_PLAYER*/);
    public static short MASK_TRIGGER2 = (short) (CATEGORY_MONSTER | CATEGORY_TRIGGER | CATEGORY_SCENERY);
    public static short MASK_TRIGGER3 = (short) (CATEGORY_PLAYER);

    public static int TILE_SIZE = 128;
    public static int TILE_SCALE = 2;


    public static final String DELETE ="delete";
    public static final String ONE_WAY_PLATFORM ="one way platform";





}
