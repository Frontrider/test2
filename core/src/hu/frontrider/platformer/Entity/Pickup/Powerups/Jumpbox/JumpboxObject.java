package hu.frontrider.platformer.Entity.Pickup.Powerups.Jumpbox;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import hu.frontrider.platformer.Entity.Powerup;
import hu.frontrider.platformer.Entity.Player.Player;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

/**
 * Created by Frontrider on 2015.10.07..
 */
public class JumpboxObject implements Powerup
{
    int jumpcount =0;
    int time;
    Player player;
    Sprite sprite;

    public JumpboxObject(Player player)
    {
       this.player = player;
       time = 10;
       sprite = new Box2DSprite(new Texture("textures/jumpbox.png"));
    }

    @Override
    public void act(float delta)
    {
         time -= delta;
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

    @Override
    public boolean remove()
    {
        if (time <0)
        return true;

        return false;
    }


    @Override
    public void dispose()
    {

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
}
