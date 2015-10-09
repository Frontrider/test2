package hu.frontrider.platformer.Entity.Enviroment;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import hu.frontrider.platformer.Interfaces.Damagable;
import hu.frontrider.platformer.Interfaces.Trigger;

/**
 * Created by Frontrider on 2015.10.09..
 */
public class Death implements Trigger
{
    @Override
    public void Trigger(Fixture f1, Fixture f2, WorldManifold manifold)
    {
        try
        {
            ((Damagable)f1.getUserData()).Damage(9999999);
        }
        catch (Exception e)
        {}

        try
        {
            ((Damagable)f2.getUserData()).Damage(9999999);
        }
        catch (Exception e)
        {}
    }

    @Override
    public void UnTrigger(Fixture f1, Fixture f2, WorldManifold manifold)
    {

    }

    @Override
    public void preSolve(Fixture f1, Fixture f2, WorldManifold manifold)
    {

    }

    @Override
    public void postSolve(Fixture f1, Fixture f2, WorldManifold manifold)
    {

    }
}
