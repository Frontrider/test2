package hu.frontrider.platformer.Controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import hu.frontrider.platformer.Interfaces.Controllable;
import hu.frontrider.platformer.Screens.LevelMenu;

/**
 * Created by Frontrider on 2015.09.10..
 */
public class InputController implements InputProcessor
{
    private Controllable controllable;

    public InputController(Controllable controllable)
    {
        this.controllable = controllable;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.A)
        {
           controllable.Left();
            //Gdx.app.log(LOG_TAG,"backward");
            return true;
        }
        if(keycode == Input.Keys.D)
        {
            controllable.Right();
            // Gdx.app.log(LOG_TAG,"forward");
            return true;
        }
        if(keycode == Input.Keys.SPACE)
        {
            controllable.JumpControl();
            return true;
        }
        if(keycode == Input.Keys.ESCAPE)
        {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new LevelMenu());
            return  true;
        }
        if(keycode == Input.Keys.UP)
        {
            controllable.Control1(false);
        }

        if(keycode == Input.Keys.DOWN)
        {
            controllable.Control2(false);
        }
        return true;
    }
    @Override
    public boolean keyUp(int keycode) {
        switch(keycode) {
            case Input.Keys.A:
                controllable.StopRight();
                break;
            case Input.Keys.D:
                controllable.StopLeft();
            case Input.Keys.SPACE:
                controllable.FinishJump();
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
