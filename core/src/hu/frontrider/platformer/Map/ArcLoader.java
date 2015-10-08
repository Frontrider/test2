package hu.frontrider.platformer.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import hu.frontrider.platformer.Helper.BodyEditorLoader;
import hu.frontrider.platformer.Helper.StaticVariables;

import java.util.Iterator;


/**
 * Created by Frontrider on 2015.09.18..
 */
public abstract class ArcLoader
{

      private static int TILESIZE = StaticVariables.TILE_SIZE;


      public static void LoadArcs (TiledMap map, World world)
      {

          MapObjects layer = map.getLayers().get("SpecialGround").getObjects();

          BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("maps/bodies/arc"));
          BodyDef bodyDef = new BodyDef();
          FixtureDef fixtureDef = new FixtureDef();

          Iterator itr = layer.iterator();

          while(itr.hasNext()){

              RectangleMapObject obj =(RectangleMapObject) itr.next();

                              bodyDef.position.set(obj.getRectangle().x/TILESIZE,obj.getRectangle().y/TILESIZE);
              if(obj.getName() != null)
              {
                  bodyDef.angle = MathUtils.degreesToRadians*Float.parseFloat(obj.getName());
              }
                              Body body = world.createBody(bodyDef);
                              fixtureDef.density =0;
                              fixtureDef.friction = 0.5f;
                              fixtureDef.filter.categoryBits = StaticVariables.CATEGORY_SCENERY;
                              fixtureDef.filter.maskBits = StaticVariables.MASK_SCENERY;
                              loader.attachFixture(body, "arc", fixtureDef, 2);
                    }

                   }

}

