package hu.frontrider.platformer.Entity.Player;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import hu.frontrider.platformer.Entity.Trigger;

/**
 * Created by Frontrider on 2015.10.07..
 */
public class GroundSensor  implements Trigger
{
    private static final String LOG_TAG = "PlayerGroundsensor";
    Player player;
    

    public GroundSensor(Player player)
    {
        this.player =player;
    }

    @Override
    public void Trigger(Fixture f,WorldManifold manifold)
    {
        player.addOnGround(1);
    //    Gdx.app.log(LOG_TAG, "triggered");
    }

    @Override
    public void UnTrigger(Fixture f,WorldManifold manifold)
    {
        player.addOnGround(-1);
    //    Gdx.app.log(LOG_TAG, "un triggered");
    }
}
