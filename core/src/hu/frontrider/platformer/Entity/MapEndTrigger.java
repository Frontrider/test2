package hu.frontrider.platformer.Entity;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import hu.frontrider.platformer.Interfaces.Trigger;
import hu.frontrider.platformer.Screens.GameScreen;

/**
 * Created by Frontrider on 2015.10.11..
 */
public class MapEndTrigger implements Trigger
{
    String mapname;
    public MapEndTrigger(String mapname)
    {
         this.mapname = mapname;
    }

    @Override
    public void Trigger(Fixture f1, Fixture f2, WorldManifold manifold)
    {
        ((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreen(mapname));
    }

    @Override
    public void UnTrigger(Fixture f1, Fixture f2, WorldManifold manifold)
    {

    }

    @Override
    public void preSolve(Fixture fixtureB, Fixture fixtureA, Manifold oldManifold)
    {

    }

    @Override
    public void postSolve(Fixture fixtureB, Fixture fixtureA, ContactImpulse impulse)
    {

    }
}
