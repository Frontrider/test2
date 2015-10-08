package hu.frontrider.platformer.Entity;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.WorldManifold;

/**
 * Created by Frontrider on 2015.10.07..
 */
public interface Trigger
{
    void Trigger(Fixture f,WorldManifold manifold);
    void UnTrigger(Fixture f,WorldManifold manifold);
}
