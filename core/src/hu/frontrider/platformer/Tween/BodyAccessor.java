package hu.frontrider.platformer.Tween;

import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by Frontrider on 2015.09.15..
 */
public class BodyAccessor implements TweenAccessor<Body>
{
    public static final int ANGLE = 1;

    @Override
    public int getValues(Body body, int i, float[] floats)
    {
        switch (i)
        {
            case ANGLE:

                floats[0] = body.getAngle();

                return 1;
            default:
                assert false;
                return -1;

        }
    }

    @Override
    public void setValues(Body body, int i, float[] floats)
    {

        switch (i)
        {
            case ANGLE:
                      body.setTransform(body.getPosition(),floats[0]);
                break;

            default:
                assert false;

        }

    }
}
