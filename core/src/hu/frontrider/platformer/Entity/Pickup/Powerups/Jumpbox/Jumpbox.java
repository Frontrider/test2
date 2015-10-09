package hu.frontrider.platformer.Entity.Pickup.Powerups.Jumpbox;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import hu.frontrider.platformer.Entity.PickupObjects;
import hu.frontrider.platformer.Entity.Player.Player;
import hu.frontrider.platformer.Helper.StaticVariables;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

/**
 * Created by Frontrider on 2015.10.07..
 */
public class Jumpbox implements PickupObjects
{
    private Body body;
    Box2DSprite sprite;
    private String LOG_TAG;
    int index;

    public Jumpbox(World world,Vector2 pos,int index)
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(pos);
        bodyDef.allowSleep = true;
        bodyDef.type = BodyDef.BodyType.StaticBody;
        sprite = new Box2DSprite(new Texture("textures/jumpbox.png"));
        LOG_TAG = "JUMPBOX"+index;
        this.index = index;

        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1f, 1f,new Vector2(0f,0f),0);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;

        body.createFixture(fixtureDef).setUserData(this);
        body.createFixture(fixtureDef).setUserData(sprite);
    }

    @Override
    public void Draw(SpriteBatch batch, World world) {
        sprite.draw(batch,world);
    }

    @Override
    public void Trigger(Fixture f1,Fixture f2,WorldManifold manifold)
    {
        try
        {
            ((Player)f1.getUserData()).addPowerup(new JumpboxObject((Player)f1.getUserData()));
            body.setUserData(StaticVariables.DELETE);
            sprite.getTexture().dispose();
        }
        catch (Exception e)
        {}
    }

    @Override
    public void UnTrigger(Fixture f1,Fixture f2,WorldManifold manifold)
    {

    }

    @Override
    public void preSolve(Fixture f1, Fixture f2, WorldManifold manifold)
    {

    }

    @Override
    public void postSolve(Fixture f1, Fixture f2, WorldManifold manifold)
    {

    }

    @Override
    public void dispose()
    {
        sprite.getTexture().dispose();
    }
}
