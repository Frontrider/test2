package hu.frontrider.platformer.Tween;

import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Frontrider on 2015.09.07..
 */


public class Spriteaccessor implements TweenAccessor<Sprite>
{
    public static final int ALPHA = 0;


    @Override
    public int getValues(Sprite sprite, int tweenType, float[] floats)
    {
        switch (tweenType)
        {
            case ALPHA :
                floats[0] = sprite.getColor().a;
                return 1;
            default:
                assert false;
                return -1;

        }


    }

    @Override
    public void setValues(Sprite sprite, int tweenType, float[] floats)
    {
        switch (tweenType)
        {
            case ALPHA :
                sprite.setColor(sprite.getColor().r,sprite.getColor().g,sprite.getColor().b,floats[0]);

            default:
                assert false;





        }

    }
}
