package hu.frontrider.platformer.Entity.Enviroment;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import hu.frontrider.platformer.Interfaces.Trigger;

/**
 * Created by Frontrider on 2015.10.09..
 */
public abstract class Zone
{
    public static Body NewZone(float Height,float Width,Vector2 Pos,Trigger effect,World world)
    {
        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.StaticBody;
        bd.position.set(Pos);
        Body body = world.createBody(bd);
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Width,Height);

        fixtureDef.shape = shape;
        fixtureDef.density = 0;
        fixtureDef.friction = 0.5f;
        body.createFixture(fixtureDef).setUserData(effect);
        return body;
    }


}
