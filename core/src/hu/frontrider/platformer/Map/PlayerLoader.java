package hu.frontrider.platformer.Map;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import hu.frontrider.platformer.Helper.StaticVariables;

/**
 * Created by Frontrider on 2015.09.18..
 */
public abstract class PlayerLoader
{

    public static Vector2 getPos(TiledMap map)
    {
        MapObjects layer = map.getLayers().get("Player").getObjects();

        RectangleMapObject obj = (RectangleMapObject) layer.get("PlayerSpawner");
        return new Vector2((obj.getRectangle().x / StaticVariables.TILE_SIZE), (obj.getRectangle().y / StaticVariables.TILE_SIZE)+2f);
    }
}
