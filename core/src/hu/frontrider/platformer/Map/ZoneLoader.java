package hu.frontrider.platformer.Map;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import hu.frontrider.platformer.Entity.Enviroment.Death;
import hu.frontrider.platformer.Entity.Enviroment.Zone;
import hu.frontrider.platformer.Helper.StaticVariables;

import java.util.Iterator;

/**
 * Created by Frontrider on 2015.10.09..
 */
public abstract class ZoneLoader
{
    private static final String LOG_TAG = "Zone loader";

    public static Array<Body> getPos(TiledMap map,World world)
    {
        MapObjects layer = map.getLayers().get("Zones").getObjects();
        //   Gdx.app.log(LOG_TAG, "reteg toltve");
        Array<Body> list = new Array();

        Iterator itr = layer.iterator();

        while (itr.hasNext())
        {
            RectangleMapObject tmp = (RectangleMapObject) itr.next();
            Vector2 tmpv = new Vector2();
            (tmp.getRectangle().getCenter(tmpv)).scl(1 / StaticVariables.TILE_SIZE);

            list.add(Zone.NewZone(tmp.getRectangle().getWidth()/StaticVariables.TILE_SIZE,tmp.getRectangle().getHeight()/StaticVariables.TILE_SIZE,tmpv,new Death(),world));

        }
        return list;
    }


}
