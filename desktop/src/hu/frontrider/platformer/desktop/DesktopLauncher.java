package hu.frontrider.platformer.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import hu.frontrider.platformer.TiledPlatformer;


/**
 * Created by Frontrider on 2015.09.17..
 */
public class DesktopLauncher
{
    public static void main (String[] arg)
    {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = TiledPlatformer.Name;
        config.vSyncEnabled = true;
        config.width = 640;
        config.height = 480;
        config.resizable = false;
        new LwjglApplication(new TiledPlatformer(), config);
    }
}
