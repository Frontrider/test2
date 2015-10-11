package hu.frontrider.platformer.Entity.Player;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.*;
import com.badlogic.gdx.utils.Array;
import hu.frontrider.platformer.Classes.Updater;
import hu.frontrider.platformer.Entity.Particle.Explosion;
import hu.frontrider.platformer.Interfaces.Damagable;
import hu.frontrider.platformer.Interfaces.Living;
import hu.frontrider.platformer.Interfaces.Powerup;
import hu.frontrider.platformer.Interfaces.Trigger;
import hu.frontrider.platformer.Screens.GameOverScreen;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

import java.util.Iterator;

public class Player implements Living,Trigger
{
    private static final String LOG_TAG = "Player";

    private static final float JUMPTIME = 0.265F/1.8f;
    private int Speed;
    private static int SPEED = 30;
    private static int JUMPFORCE = 3000;
    private static float AIR_CONTROL = 0.25f;
    private int TOP_SPEED = 30;
    public static int TOPSPEED =40;
    private static int TORQUE = 500;
    public Box2DSprite sprite;
    public boolean Forward = false;
    public boolean Backward = false;
    private Vector2 velocity;
    private Body body;
    private Body foot;
    private RevoluteJoint motor;
    private int numFootContacts = 0;
    public int lwallruncount = 0, rwallruncount = 0;
    private float jumptime = 0.0F;
    private boolean FacingRight = true;
    private int shield;
    public Array<Object> triggering;
    private Vector2 DefGravity,Gravity,Accel,Vel,anglePointLeft,anglepointRight;
    private Array Powerups;
    private Powerup tmpp;
    boolean jumping;
    boolean dash = false;
    private Updater updater;
    private World world;
    float strenght=0;

    @Override
    public String toString()
    {return LOG_TAG;}
    public Player(World world, Vector2 pos) {
        shield = 100;
        triggering= new Array();
        sprite = new Box2DSprite(new Texture("textures/sprite.png"));
        velocity = new Vector2(0.0F, 0.0F);
        DefGravity = world.getGravity();
        Gravity = DefGravity;
        Vel = new Vector2();
        Powerups = new Array();
        jumping = false;
        anglepointRight = new Vector2(0,0);
        anglePointLeft = new Vector2(0,0);

        this.world = world;

        BodyDef bodydef = new BodyDef();
        bodydef.type = BodyType.DynamicBody;
        bodydef.position.set(pos);
        bodydef.gravityScale = 0;
        bodydef.fixedRotation = true;
        bodydef.bullet = true;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(.9F, .9F);
        FixtureDef fixture = new FixtureDef();
        FixtureDef fixture2 = new FixtureDef();
        fixture2.shape = shape;
        fixture2.friction = 0.0F;
        fixture2.restitution = 0.0F;
        fixture2.density = 0.0F;
        fixture2.isSensor = true;
        body = world.createBody(bodydef);

        fixture.shape = shape;
        body.createFixture(fixture2).setUserData(this.sprite);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(.9F);
        fixture.shape = circleShape;
        fixture.friction = 2.0F;
        fixture.restitution = 0.0F;
        fixture.density = 5.0F;
        fixture.isSensor = false;
        bodydef.position.set(this.body.getPosition().x, this.body.getPosition().y);
        bodydef.fixedRotation = false;

        foot = world.createBody(bodydef);
        foot.createFixture(fixture).setUserData(this);

        shape.setAsBox(.7F, .25F, new Vector2(0.0F, -1.0F), 0.0F);
        fixture.shape = shape;
        fixture.isSensor = true;

        body.createFixture(fixture).setUserData(new GroundSensor(this));

        shape.setAsBox(0.2F, 0.3F, new Vector2(-1.0F, 0.0F), 0.0F);
        fixture.shape = shape;

        shape.setAsBox(.3f, 0.3F, new Vector2(-0.9f, 0), 0);
        fixture.shape = shape;
        body.createFixture(fixture).setUserData(new SideSensor(this, false));

        shape.setAsBox(.3f, 0.3F, new Vector2(0.9f, 0), 0);
        fixture.shape = shape;
        body.createFixture(fixture).setUserData(new SideSensor(this, true));

        EdgeShape edgeshape = new EdgeShape();

        edgeshape.set(-0.85f, 0f, -0.85f, -2);

        fixture.shape=edgeshape;

        body.createFixture(fixture).setUserData(new AngleSensor(this.anglePointLeft, false));

        edgeshape.set(0.85f, 0f, 0.85f, -2);//*/
        fixture.shape=edgeshape;

        body.createFixture(fixture).setUserData(new AngleSensor(this.anglepointRight, true));


        RevoluteJointDef motorJointDef = new RevoluteJointDef();
        Speed = 800;
        motorJointDef.motorSpeed = (float) Speed;
        motorJointDef.enableMotor = true;
        motorJointDef.maxMotorTorque = (float)TORQUE;
        motorJointDef.initialize(body,foot, body.getPosition());
        motor = (RevoluteJoint)world.createJoint(motorJointDef);
        motor.setLimits(0,0);
        numFootContacts = 0;

        DistanceJointDef distanceJointDef = new DistanceJointDef();
        distanceJointDef.length =0;
        distanceJointDef.initialize(body,foot,pos,pos);

        shape.dispose();
        circleShape.dispose();
        edgeshape.dispose();
    }

    public void setUpdater(Updater updater){this.updater = updater;}

    @Override
    public void Update(float delta) {

        //*
        Accel = Gravity;
        Vel = Vel.add(Accel.scl(delta));
        body.applyForceToCenter(Vel,true);
        foot.applyForceToCenter(Vel,true);
        //*/

        if(Forward) {
            this.MoveControl((byte) 2);
        }

        if(Backward) {
            this.MoveControl((byte) 1);
        }

        if(!Forward & !Backward & OnGround()) {
            this.Stop();
        }

        if(!OnGround()) {
           motor.enableLimit(true);
        }
        else
        {
        }

        if(OnGround()|OnWall())
        {
            jumping = false;
            Iterator<Powerup> itr = Powerups.iterator();
            while (itr.hasNext())
            {
                itr.next().ask("ground");
            }
        }

        if(jumptime > 0.0F) {

            body.applyForceToCenter(0,JUMPFORCE*delta,true);
            jumptime -= delta;
        } else {
            velocity.y = 0.0F;
            body.setLinearVelocity(body.getLinearVelocity().x, body.getLinearVelocity().y - .5f);
        }

       // Gdx.app.log(LOG_TAG,"Shield: "+shield);
        if(shield >100)
        shield = 100;

        if(body.getLinearVelocity().x > (float)TOP_SPEED & !dash) {
            body.setLinearVelocity(body.getLinearVelocity().x = ((float) TOP_SPEED), body.getLinearVelocity().y);
        }

        if(shield<0)
        {
            shield=0;
        }

        rotate();

        Iterator<Powerup> itr = Powerups.iterator();
        while (itr.hasNext())
        {
            tmpp = itr.next();

            if(!tmpp.remove())
            tmpp.act(delta);
            else
            {itr.remove();}
        }

        if(dash &shield>10)
        {
            if(TOPSPEED == TOP_SPEED)
           TOP_SPEED *=10;
            if(Speed == SPEED)
           Speed*=10;

            shield-=2;
        }

        else
        {
            TOP_SPEED = TOPSPEED;
            Speed = SPEED;
        }

        Vel = Vel.add(Accel.scl(delta));
        body.applyForceToCenter(Vel,true);
        foot.applyForceToCenter(Vel,true);
    }

    @Override
    public boolean Remove()
    {
        return false;
    }

    private boolean OnWall()
    {
        return lwallruncount >0 | rwallruncount >0;
    }

    private boolean OnBoothWalls()
    {
        return lwallruncount >0 & rwallruncount >0;
    }

    private boolean RightWall()
    {return rwallruncount >0;}


    private boolean LeftWall()
    {return lwallruncount >0;}

    public Vector2 getPosition() {
        return body.getPosition();
    }

    @Override
    public void Draw(SpriteBatch batch, World world) {
        sprite.draw(batch, world);
    }

    @Override
    public void JumpControl()
    {
        Iterator<Powerup> itr = Powerups.iterator();
        while(itr.hasNext())
        {
            if(itr.next().ask("jump"))
               return;
        }
        Jump(false);
    }

    @Override
    public void Left()
    {
        Backward = true;
    }

    @Override
    public void Right()
    {
        Forward = true;
    }

    @Override
    public void StopLeft()
    {
        Backward =false;
    }

    @Override
    public void StopRight()
    {
       Forward=false;
    }

    @Override
    public void Jump(boolean Override) {

        if((OnGround() | OnWall() |Override) & !jumping)
        {
            jumptime = JUMPTIME;
            if(body.getLinearVelocity().y <0)
                body.setLinearVelocity(body.getLinearVelocity().x,0);

            body.applyForceToCenter(0f, JUMPFORCE * 3, true);
            jumping = true;
            // Gdx.app.log(LOG_TAG,"jump"+OnGround());
        }
       //*
            if(OnWall())
         {
             if(lwallruncount <=0 & rwallruncount >0)
             {body.applyForceToCenter(-JUMPFORCE/2,0,true);}
             else
             {{body.applyForceToCenter(JUMPFORCE/2,0,true);}}
         }
         //*/
    }

    @Override
    public void FinishJump() {
        this.jumptime = -1.0F;
        this.body.setLinearVelocity(this.body.getLinearVelocity().x, this.body.getLinearVelocity().y * 0.7F);
       // Gdx.app.log(LOG_TAG, "finish jump" + OnGround());
    }

    @Override
    public void Move(Byte direction, boolean Override) {
        motor.enableLimit(false);



        switch(direction)
        {
            case 1:
                if (OnGround())
                {
                    this.motor.setMotorSpeed(Speed);
                }
                else
                {
                    if(Override | !OnWall())
                    {
                        body.applyForceToCenter(-(Speed / AIR_CONTROL), 0f, true);
                        foot.applyForceToCenter(-(Speed / AIR_CONTROL), 0f, true);
                    }
                }

                this.FacingRight = false;
                break;
            case 2:
                if (OnGround())
                {
                    this.motor.setMotorSpeed(-Speed);
                }
                else
                {
                    if(Override | !OnWall())
                    {
                        body.applyForceToCenter((Speed / AIR_CONTROL), 0f, true);
                        foot.applyForceToCenter((Speed / AIR_CONTROL), 0f, true);
                    }
                }

                this.FacingRight = true;
        }
    }

    @Override
    public void MoveControl(Byte direction)
    {
        Iterator<Powerup> itr = Powerups.iterator();
        while (itr.hasNext())
            {
                if (itr.next().ask(direction.toString()))
                    return;
            }
       Move(direction,false);

    }

    @Override
    public void Stop()
    {

        if(OnGround())
        {
            body.setLinearVelocity(body.getLinearVelocity().x/2, body.getLinearVelocity().y);
            foot.setLinearVelocity(foot.getLinearVelocity().x/2, body.getLinearVelocity().y);
        }
        this.motor.enableLimit(true);
        Forward = false;
        Backward = false;
        //Gdx.app.log(LOG_TAG,"Stopped");
    }

    @Override
    public void Control1(boolean Override)
    {
       updater.add(Explosion.Blow(world, body.getPosition(), 1500));
    }

    @Override
    public void Control2(Boolean Override)
    {
        dash = true;
    }

    @Override
    public void Control1Up(boolean Override)
    {

    }

    @Override
    public void Control2Up(Boolean Override)
    {
        dash = false;
    }

    public boolean OnGround()
    {
        return this.numFootContacts > 0 /*| normal.len() ==0 & body.getLinearVelocity().x >TOP_SPEED/2*/;
    }

    @Override
    public void dispose()
    {
       this.sprite.getTexture().dispose();

    }

    public void wallrunEnd()
    {
        
        if((!OnWall() & !OnGround()) &!OnBoothWalls()){
            body.setLinearVelocity(this.body.getLinearVelocity().x, body.getLinearVelocity().y / 2.2f);

            if(body.getLinearVelocity().y <6 & body.getLinearVelocity().y > 0 &FacingRight)
                body.setLinearVelocity(body.getLinearVelocity().x+1f,8);
            else
              if(body.getLinearVelocity().y < 6 & body.getLinearVelocity().y > 0 & !FacingRight)
                  body.setLinearVelocity(body.getLinearVelocity().x-1f,8);
            //Gdx.app.log(LOG_TAG,"wallrun ended");
        }
    }

    public int getShield()
    {return shield;}

    public void addOnGround(int i)
    {
        numFootContacts+=i;
    }

    public void addWallCount(int i,String s)
    {

        if(s.matches("left"))
        lwallruncount +=i;
        if(s.matches("right"))
        rwallruncount +=i;
    }

    public void chargeshield(int value)
    {
        shield+= value;
    }

    public void addPowerup(Powerup powerup)
    {
        Iterator<Powerup> itr = Powerups.iterator();
        while (itr.hasNext())
        {
            if(itr.next().equals(powerup));
            itr.remove();
        }
        Powerups.add(powerup);
    }

    private void rotate()
    {
        if(anglepointRight.len() ==0 | anglepointRight.len() ==0)
        {sprite.setRotation(0);}
        else
        sprite.setRotation(anglePointLeft.angle(anglepointRight));
        sprite.setRotation(anglePointLeft.angle(anglepointRight));

    }

    @Override
    public void Damage(int amount)
    {
        shield-= amount;
        if(shield <0)
        {
            ((Game)Gdx.app.getApplicationListener()).setScreen(new GameOverScreen());
        }

    }

    @Override
    public void Trigger(Fixture f1, Fixture f2, WorldManifold manifold)
    {
        strenght = Math.abs(f1.getBody().getLinearVelocity().x+f2.getBody().getLinearVelocity().x)+ Math.abs(f1.getBody().getLinearVelocity().y+f2.getBody().getLinearVelocity().y);
        //Gdx.app.log(LOG_TAG, String.valueOf(strenght)+" "+TOPSPEED);
        if(strenght>TOPSPEED/2)
        {
            try
            {
                if (f1.getUserData().equals(this))
                {
                    ((Damagable)f2.getUserData()).Damage(Math.round(strenght*2));
                    Gdx.app.log(LOG_TAG, String.valueOf(strenght*2)+" "+f2.getUserData().toString());
                }
                else
                {
                    ((Damagable)f1.getUserData()).Damage(Math.round(strenght*2));
                    Gdx.app.log(LOG_TAG, String.valueOf(strenght*2)+" "+f1.getUserData().toString());
                }
            }catch (Exception e)
            {
                Gdx.app.log(LOG_TAG,e.getMessage());
            }
        }
    }

    @Override
    public void UnTrigger(Fixture f1, Fixture f2, WorldManifold manifold)
    {

    }

    @Override
    public void preSolve(Fixture fixtureB, Fixture fixtureA, Manifold oldManifold)
    {

    }

    @Override
    public void postSolve(Fixture fixtureB, Fixture fixtureA, ContactImpulse impulse)
    {

    }
}
