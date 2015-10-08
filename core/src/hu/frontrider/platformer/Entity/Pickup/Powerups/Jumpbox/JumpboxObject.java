package hu.frontrider.platformer.Entity.Pickup.Powerups.Jumpbox;

import hu.frontrider.platformer.Entity.Powerup;
import hu.frontrider.platformer.Entity.Player.Player;

/**
 * Created by Frontrider on 2015.10.07..
 */
public class JumpboxObject implements Powerup
{
    int jumpcount =0;
    Player player;

    public JumpboxObject(Player player)
    {
       this.player = player;
    }

    @Override
    public void act()
    {

    }

    @Override
    public boolean ask(String string)
    {
        if(string.matches("jump") )
        {   jumpcount=+1;
            if(jumpcount <2)
            player.Jump(true);

            return true;
        }
        if(string.matches("ground"))
        {
            jumpcount=0;
        }
        return false;
    }
}
