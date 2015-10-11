package hu.frontrider.platformer.Controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import hu.frontrider.platformer.Entity.Player.Player;
import hu.frontrider.platformer.Helper.StaticVariables;
import hu.frontrider.platformer.Interfaces.Controllable;
import hu.frontrider.platformer.Screens.LevelMenu;

/**
 * Created by Frontrider on 2015.09.10..
 */
public class InputController implements InputProcessor
{
    private static final String LOG_TAG = "Input Processor";
    private Controllable controllable;
    private Player player;

    Vector2 center,target;
    boolean touchenabled,touching;
    int correction;
    private OrthographicCamera camera;
    Vector3 tmp,tmp2;


    public InputController(Controllable controllable,Player player)
    {
        this.controllable = controllable;
        this.player = player;
        touchenabled =false;
    }

    public void enableTouch(OrthographicCamera camera)
    {
        touchenabled = true;
        touching = false;
        correction = StaticVariables.TILE_SIZE/2;
        this.camera = camera;
        center = new Vector2();
        tmp = new Vector3();
        target = new Vector2();
        tmp2 = new Vector3();
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
        if(!touchenabled)
        {
            return false;
        }
        touching = true;
        center.x = camera.unproject(tmp.set(screenX,screenY,0)).x;
        center.y = camera.unproject(tmp.set(screenX, screenY, 0)).y;
        return  true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        if(!touchenabled)
        {
            return false;
        }
        touching = false;
        controllable.Stop();
        controllable.FinishJump();

        return  true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {

        if(!touchenabled)
        {
            return false;
        }
        if(touching)
        {
            target.x = camera.unproject(tmp2.set(screenX,screenY,0)).x;
            target.y = camera.unproject(tmp2.set(screenX, screenY, 0)).y;

            if(target.x > center.x+correction)
            {
                    controllable.Left();
                    controllable.StopRight();
            }
            if(target.x < center.x+correction)
            {
                   controllable.Right();
                   controllable.StopLeft();
            }

            if(target.y > center.y+correction)
            {
                  controllable.JumpControl();
            }
            if(target.y < center.y+correction)
            {
                 controllable.FinishJump();
            }

        }


        return  true;
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
