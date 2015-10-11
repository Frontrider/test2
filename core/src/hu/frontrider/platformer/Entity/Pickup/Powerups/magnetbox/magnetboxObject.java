package hu.frontrider.platformer.Entity.Pickup.Powerups.magnetbox;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import hu.frontrider.platformer.Entity.Player.Player;
import hu.frontrider.platformer.Interfaces.Powerup;

/**
 * Created by Frontrider on 2015.10.11..
 */
public class magnetboxObject implements Powerup
{
    byte control;
    Player player;

    public magnetboxObject(Player userData)
    {
        this.player = userData;
        control =0;
    }

    @Override
    public void act(float delta)
    {

    }

    @Override
    public boolean ask(String string)
    {
        try
        {
            control = Byte.valueOf(string);
        }
        catch (Exception e)
        {}
         if(control ==1)
         {
             player.Move((byte)1,true);
             control = 0;
             return true;
         }
        if(control ==2)
        {
            player.Move((byte)2,true);
            control = 0;
            return true;
        }
        control = 0;
        return false;

    }

    @Override
    public boolean remove()
    {
        return false;
    }

    @Override
    public void Draw(SpriteBatch batch, int x, int y)
    {

    }

    @Override
    public int getPriority()
    {
        return 0;
    }

    @Override
    public void dispose()
    {

    }
}
