package hu.frontrider.platformer.Controller;

import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Fixture;
import hu.frontrider.platformer.Helper.FixtureData;
import hu.frontrider.platformer.Helper.StaticVariables;

/**
 * Created by Frontrider on 2015.09.27..
 */
public class MyContactFilter implements ContactFilter
{
    @Override
    public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB)
    {

        if(FixtureData.oneFixureIs(fixtureA, fixtureB, StaticVariables.ONE_WAY_PLATFORM))
        {
            if(FixtureData.UserDataEquals(fixtureA,StaticVariables.ONE_WAY_PLATFORM))
            {
                if (fixtureB.getBody().getLinearVelocity().y >0)
                {
                   return false;
                }
            }
            else
            {
                if (fixtureA.getBody().getLinearVelocity().y <0)
                {
                    return false;
                }
            }
        }

        return true;
    }
}
