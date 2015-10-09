package hu.frontrider.platformer.Interfaces;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.WorldManifold;

/**
 * Created by Frontrider on 2015.10.07..
 */
public interface Trigger
{
    void Trigger(Fixture f1,Fixture f2,WorldManifold manifold);
    void UnTrigger(Fixture f1,Fixture f2,WorldManifold manifold);
    void preSolve (Fixture f1,Fixture f2,WorldManifold manifold);
    void postSolve(Fixture f1,Fixture f2,WorldManifold manifold);
}
