package hu.frontrider.platformer.Map;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import hu.frontrider.platformer.Entity.Enemy.Enemy;
import hu.frontrider.platformer.Entity.Player.Player;
import hu.frontrider.platformer.Helper.StaticVariables;

import java.util.Iterator;

/**
 * Created by Frontrider on 2015.09.19..
 */
public abstract class EnemyLoader
{
    private static final String LOG_TAG = "Enemy loader";
    public static Array<Enemy> getPos(TiledMap map,World world,Player player)
    {
        int index = 0;
        MapObjects layer = map.getLayers().get("Enemy").getObjects();
     //   Gdx.app.log(LOG_TAG, "reteg toltve");
        Array<Enemy> list = new Array();

        Iterator itr = layer.iterator();

        while (itr.hasNext())
        {
             RectangleMapObject tmp = (RectangleMapObject) itr.next();


             Vector2 tmpv = new Vector2(tmp.getRectangle().x / StaticVariables.TILE_SIZE, tmp.getRectangle().y / StaticVariables.TILE_SIZE+1f);
            if(tmp.getRectangle().getWidth() < StaticVariables.TILE_SIZE)
            {

                Enemy enemy = new Enemy(world,tmpv,index,player);
                list.add(enemy);
              //  Gdx.app.log(LOG_TAG, " enemy set");
            }
            else
            {
                Enemy enemy = new Enemy(world,tmpv,new Vector2(tmpv.x+tmp.getRectangle().getWidth(), tmpv.y),index,player);
                list.add(enemy);
               // Gdx.app.log(LOG_TAG, " enemy set");
            }


             index++;
            // Gdx.app.log(LOG_TAG, "ellenfelek toltese "+index);
        }

        return list;
    }


}
