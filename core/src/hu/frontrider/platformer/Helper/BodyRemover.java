package hu.frontrider.platformer.Helper;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

/**
 * Created by Frontrider on 2015.09.21..
 */
public class BodyRemover
{
    Body tmp;

    public void removebodies(Array<Body> bodies,World world)
    {
        Iterator<Body> body = bodies.iterator();
        while (body.hasNext())
        {
            boolean delete = false;
            tmp = body.next();
            Iterator<Fixture> itrf = tmp.getFixtureList().iterator();
            while (itrf.hasNext())
            {
                if(FixtureData.UserDataEquals(itrf.next(), StaticVariables.DELETE))
                {
                    itrf.remove();
                    break;
                }
            }
            if(tmp.getUserData() == StaticVariables.DELETE)
            {
                world.destroyBody(tmp);
            }
        }
    }

}
