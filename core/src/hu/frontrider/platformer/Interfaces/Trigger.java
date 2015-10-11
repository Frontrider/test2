package hu.frontrider.platformer.Interfaces;

import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.WorldManifold;

/**
 * Created by Frontrider on 2015.10.07..
 */
public interface Trigger
{
    void Trigger(Fixture f1,Fixture f2,WorldManifold manifold);
    void UnTrigger(Fixture f1,Fixture f2,WorldManifold manifold);



    void preSolve(Fixture fixtureB, Fixture fixtureA, Manifold oldManifold);

    void postSolve(Fixture fixtureB, Fixture fixtureA, ContactImpulse impulse);
}
