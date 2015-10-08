package hu.frontrider.platformer;

import com.badlogic.gdx.*;
import hu.frontrider.platformer.Screens.GameScreen;

public class TiledPlatformer extends Game
{

    public static final String Name = "MY game";

	@Override
	public void create () {
        Gdx.app.log(Name, "inicializálás kezdete");

        setScreen(new GameScreen());
        Gdx.app.log(Name,"inicializálás vége");
	}

    @Override
    public void resize(int width, int height)
    {
        super.resize(width,height);
        Gdx.app.log(Name,"átméretez");
    }

    @Override
	public void render () {
        super.render();


	}

    @Override
    public void pause()
    {
        super.pause();
        Gdx.app.log(Name,"szüneteltet");
    }

    @Override
    public void resume()
    {
        Gdx.app.log(Name,"újraindít");
        super.resume();
    }

    @Override
    public void dispose()
    {
        Gdx.app.log(Name,"lezár");
        super.dispose();
    }


}
