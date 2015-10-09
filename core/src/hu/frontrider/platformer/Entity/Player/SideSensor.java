package hu.frontrider.platformer.Entity.Player;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import hu.frontrider.platformer.Interfaces.Trigger;

/**
 * Created by Frontrider on 2015.10.07..
 */
public class SideSensor implements Trigger
{

    private String LOG_TAG;
    Player player;
    Boolean RightSide;

    public SideSensor(Player player,boolean RightSide)
    {
        this.player = player;
        this.RightSide = RightSide;
        if(RightSide)
        this.LOG_TAG = "SideSensorRight";
        else
        this.LOG_TAG="SideSensorLeft";

    }

    @Override
    public void Trigger(Fixture f1,Fixture f2,WorldManifold manifold)
    {
        if(f1.isSensor() ==false)
        {
            if(RightSide)
            player.addWallCount(1,"right");
            else
            {player.addWallCount(1,"left");}
        }
    }

    @Override
    public void UnTrigger(Fixture f1,Fixture f2,WorldManifold manifold)
    {
        if(f1.isSensor() == false)
        {
            if(RightSide)
            player.addWallCount(-1,"right");
            else
            {player.addWallCount(-1,"left");}
            player.wallrunEnd();
        }

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
