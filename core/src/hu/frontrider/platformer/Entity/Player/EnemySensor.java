package hu.frontrider.platformer.Entity.Player;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import hu.frontrider.platformer.Interfaces.Damagable;
import hu.frontrider.platformer.Interfaces.Trigger;

/**
 * Created by Frontrider on 2015.10.09..
 */
public class EnemySensor implements Trigger
{   float strenght=0;
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
    public void preSolve(Fixture f1, Fixture f2, WorldManifold manifold)
    {
        strenght = Math.abs(f1.getBody().getLinearVelocity().x+f2.getBody().getLinearVelocity().x)+ Math.abs(f1.getBody().getLinearVelocity().y+f2.getBody().getLinearVelocity().y);
        if(strenght>player.TOPSPEED)
        {
            try
            {
                if (f1.getUserData().equals(player))
                {
                    ((Damagable)f2.getUserData()).Damage(Math.round(strenght*2));
                }
                else
                {
                    ((Damagable)f1.getUserData()).Damage(Math.round(strenght*2));
                }
            }catch (Exception e)
            {}
        }
    }

    @Override
    public void postSolve(Fixture f1, Fixture f2, WorldManifold manifold)
    {

    }
}
