package hu.frontrider.platformer.Controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import hu.frontrider.platformer.Entity.Enemy.Enemy;
import hu.frontrider.platformer.Entity.Player.Player;
import hu.frontrider.platformer.Screens.LevelMenu;

import java.util.Iterator;

/**
 * Created by Frontrider on 2015.09.10..
 */
public class InputController implements InputProcessor
{
    private Player player;

    public InputController(Player player)
    {
        this.player = player;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.A)
        {
           player.Left();
            //Gdx.app.log(LOG_TAG,"backward");
            return true;
        }
        if(keycode == Input.Keys.D)
        {
            player.Right();
            // Gdx.app.log(LOG_TAG,"forward");
            return true;
        }
        if(keycode == Input.Keys.SPACE)
        {
            player.JumpControl();
            return true;
        }
        if(keycode == Input.Keys.ESCAPE)
        {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new LevelMenu());
            return  true;
        }
        if(keycode == Input.Keys.UP)
        {
        }

        if(keycode == Input.Keys.DOWN)
        {
            if(Gdx.input.isKeyPressed(Input.Keys.W))
            {


            }


        }
        return true;
    }
    @Override
    public boolean keyUp(int keycode) {
        switch(keycode) {
            case Input.Keys.A:
                player.Backward = false;
                break;
            case Input.Keys.D:
                player.Forward = false;
            case Input.Keys.SPACE:
                player.Finishjump();
                break;
            case  Input.Keys.UP:
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public boolean keyTyped(char character)
    {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        return  true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        return false;
    }

    @Override
    public boolean scrolled(int amount)
    {
        return false;
    }
}
