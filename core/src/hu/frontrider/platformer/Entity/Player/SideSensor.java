package hu.frontrider.platformer.Entity.Player;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import hu.frontrider.platformer.Entity.Trigger;

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
    public void Trigger(Fixture f, WorldManifold manifold)
    {
        if(f.isSensor() ==false)
        {
            player.addWallCount(1);
        }
    }

    @Override
    public void UnTrigger(Fixture f, WorldManifold manifold)
    {
        if(f.isSensor() ==false)
        {
            player.addWallCount(-1);
            player.wallrunEnd();
        }

    }
}
