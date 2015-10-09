package hu.frontrider.platformer.Controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import hu.frontrider.platformer.Entity.Player.Player;
import hu.frontrider.platformer.Interfaces.Controllable;
import hu.frontrider.platformer.Screens.LevelMenu;

/**
 * Created by Frontrider on 2015.09.10..
 */
public class InputController implements InputProcessor
{
    private Controllable controllable;
    private Player player;

    public InputController(Controllable controllable,Player player)
    {
        this.controllable = controllable;
        this.player = player;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.A)
        {
           controllable.Left();
            //Gdx.app.log(LOG_TAG,"backward");
        }
        if(keycode == Input.Keys.D)
        {
            controllable.Right();
            // Gdx.app.log(LOG_TAG,"forward");
        }
        if(keycode == Input.Keys.SPACE)
        {
            controllable.JumpControl();
        }
        if(keycode == Input.Keys.ESCAPE)
        {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new LevelMenu());
        }
        if(keycode == Input.Keys.UP)
        {
            controllable.Control1(false);
        }

        if(keycode == Input.Keys.DOWN)
        {
            controllable.Control2(false);
        }
        if(keycode == Input.Keys.P)
        {
            player.chargeshield(100);
        }

        return true;
    }
    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.A)
        {
            controllable.StopLeft();
            //Gdx.app.log(LOG_TAG,"backward");
        }
        if(keycode == Input.Keys.D)
        {
            controllable.StopRight();
            // Gdx.app.log(LOG_TAG,"forward");
        }
        if(keycode == Input.Keys.SPACE)
        {
            controllable.FinishJump();
        }
        if(keycode == Input.Keys.ESCAPE)
        {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new LevelMenu());
        }
        if(keycode == Input.Keys.UP)
        {
            controllable.Control1Up(false);
        }

        if(keycode == Input.Keys.DOWN)
        {
            controllable.Control2Up(false);
        }
        if(keycode == Input.Keys.P)
        {
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
