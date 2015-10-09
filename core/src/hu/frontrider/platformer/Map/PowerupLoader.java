package hu.frontrider.platformer.Map;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import hu.frontrider.platformer.Entity.Pickup.Powerups.Jumpbox.Jumpbox;
import hu.frontrider.platformer.Entity.PickupObjects;
import hu.frontrider.platformer.Helper.StaticVariables;

import java.util.Iterator;

/**
 * Created by Frontrider on 2015.10.07..
 */
public abstract class PowerupLoader
{
    private static final String LOG_TAG = "EnergyCell loader";

    public static Array<PickupObjects> getPos(TiledMap map,World world,int TILESIZE)
    {
        int index = 0;
        MapObjects layer = map.getLayers().get("Powerups").getObjects();
        //   Gdx.app.log(LOG_TAG, "reteg toltve");
        Array<PickupObjects> list = new Array();

        Iterator itr = layer.iterator();

        while (itr.hasNext())
        {

            RectangleMapObject tmp = (RectangleMapObject) itr.next();
            if(tmp.getName().equals("Jumpbox"))
            {
                Vector2 tmpv = new Vector2(tmp.getRectangle().x / StaticVariables.TILE_SIZE+1, tmp.getRectangle().y / StaticVariables.TILE_SIZE + 1f);
                Jumpbox cell = new Jumpbox(world, tmpv, index);
                list.add( cell);
            }
            index++;
        }

        return list;
    }



}
