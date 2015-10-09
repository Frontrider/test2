package hu.frontrider.platformer.Entity.Player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import hu.frontrider.platformer.Entity.Trigger;

/**
 * Created by Frontrider on 2015.10.07..
 */
public class AngleSensor implements Trigger
{
    Vector2 point;
    boolean IsRight;
    public AngleSensor(Vector2 point,boolean IsRight)
    {
        this.point = point;
        this.IsRight = IsRight;
    }

    @Override
    public void Trigger(Fixture f1,Fixture f2,WorldManifold manifold)
    {
          point = manifold.getPoints()[0];
    }

    @Override
    public void UnTrigger(Fixture f1,Fixture f2,WorldManifold manifold)
    {
          point.x =0;
          point.y =0;
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
