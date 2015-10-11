package hu.frontrider.platformer.Entity.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import hu.frontrider.platformer.Interfaces.Damagable;
import hu.frontrider.platformer.Interfaces.Trigger;

/**
 * Created by Frontrider on 2015.10.09..
 */
public class EnemySensor implements Trigger
{
    Player player;

    public EnemySensor(Player player)
    {
        this.player =player;
    }

    @Override
    public void Trigger(Fixture f1, Fixture f2, WorldManifold manifold)
    {

    }

    @Override
    public void UnTrigger(Fixture f1, Fixture f2, WorldManifold manifold)
    {

    }

    @Override
    public void preSolve(Fixture f1, Fixture f2, Manifold oldManifold)
    {

    }

    @Override
    public void postSolve(Fixture fixtureB, Fixture fixtureA, ContactImpulse impulse)
    {

    }
}
