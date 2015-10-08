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
import hu.frontrider.platformer.Entity.Living;
import hu.frontrider.platformer.Entity.Powerup;
import hu.frontrider.platformer.Screens.GameOverScreen;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

import java.util.Iterator;

public class Player implements Living
{
    private static final String LOG_TAG = "Player";

    private static final float JUMPTIME = 0.265F/1.8f;
    private int SPEED = 800;
    private static int JUMPFORCE = 1300;
    private static byte AIR_CONTROL = 4;
    private int TOP_SPEED = 30;
    private static int TORQUE = 800;
    public Box2DSprite sprite;
    public boolean Forward = false;
    public boolean Backward = false;
    private Vector2 velocity;
    private Body body;
    private Body foot;
    private RevoluteJoint motor;
    private int numFootContacts = 0;
    public int wallruncount = 0;
    private float jumptime = 0.0F;
    private boolean FacingRight = true;
    private int shield;
    public Array<Object> triggering;
    private float Dashtime;
    private Vector2 DefGravity,Gravity,Accel,Vel,anglePointLeft,anglepointRight;
    private Array Powerups;
    private Powerup tmpp;
    boolean jumping;

    public Player(World world, Vector2 pos) {

        shield = 50;
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

        BodyDef bodydef = new BodyDef();
        bodydef.type = BodyType.DynamicBody;
        bodydef.position.set(pos);
        bodydef.gravityScale = 0;
        bodydef.fixedRotation = true;
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
        shape.setAsBox(0.9f, 0.2f, new Vector2(0, 1), 0);


        shape.setAsBox(.7F, .25F, new Vector2(0.0F, -1.0F), 0.0F);
        fixture.shape = shape;
        fixture.isSensor = true;

        body.createFixture(fixture).setUserData(new GroundSensor(this));

        shape.setAsBox(0.2F, 0.3F, new Vector2(-1.0F, 0.0F), 0.0F);
        fixture.shape = shape;

        shape.setAsBox(1.2f, 0.3F, new Vector2(0, 0), 0);
        fixture.shape = shape;
        body.createFixture(fixture).setUserData(new SideSensor(this, true));
        ;

    //*
        EdgeShape edgeshape = new EdgeShape();

        edgeshape.set(-0.85f, 0f, -0.85f, -2);

        fixture.shape=edgeshape;
        fixture.isSensor = true;

        body.createFixture(fixture).setUserData(new AngleSensor(this.anglePointLeft,false));

        edgeshape.set(0.85f, 0f, 0.85f, -2);//*/
        fixture.shape=edgeshape;

        body.createFixture(fixture).setUserData(new AngleSensor(this.anglepointRight,true));

        RevoluteJointDef motorJointDef = new RevoluteJointDef();
        motorJointDef.motorSpeed = (float)SPEED;
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

    @Override
    public void Update(float delta) {

        //*
        Accel = Gravity;
        Vel = Vel.add(Accel.scl(delta));
        body.applyForceToCenter(Vel,true);
        foot.applyForceToCenter(Vel,true);
        //*/

        if(Forward) {
            this.Move((byte)2,false);
        }

        if(Backward) {
            this.Move((byte) 1,false);
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
            body.applyForceToCenter(0,JUMPFORCE,true);
            jumptime -= delta;
        } else {
            velocity.y = 0.0F;
            body.setLinearVelocity(body.getLinearVelocity().x, body.getLinearVelocity().y - .5f);
        }

       // Gdx.app.log(LOG_TAG,"Shield: "+shield);
        if(shield <100)
        shield+=40*delta;
        else
        shield = 100;

        if(Dashtime>0)
          {
             if(FacingRight)
             {
                 body.applyForceToCenter(JUMPFORCE,0,true);
             }
              else
             {body.applyForceToCenter(JUMPFORCE,0,true);}
             Dashtime-=delta;
          }

        if(body.getLinearVelocity().x > (float)TOP_SPEED) {
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

    }

    private boolean OnWall()
    {
        return wallruncount >0;
    }

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
        Forward = false;
    }

    @Override
    public void Right()
    {
        Backward = false;
        Forward = true;
    }

    public void Jump(boolean Override) {

        if((OnGround() | wallruncount >0 |Override) & !jumping)
        {
            jumptime = JUMPTIME;
            if(body.getLinearVelocity().y <0)
                body.setLinearVelocity(body.getLinearVelocity().x,0);

            body.applyForceToCenter(0f, JUMPFORCE * 3, true);
            jumping = true;
            // Gdx.app.log(LOG_TAG,"jump"+OnGround());
        }
        //*
            if(wallruncount>0)
         {
             if(FacingRight)
             {body.applyForceToCenter(-JUMPFORCE*3,0,true);}
             else
             {{body.applyForceToCenter(JUMPFORCE*3,0,true);}}
         }
         //*/
    }

    public void Finishjump() {
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
                    this.motor.setMotorSpeed(SPEED);
                }
                else
                {
                    body.applyForceToCenter((float)-(SPEED / AIR_CONTROL),0f,true);
                    foot.applyForceToCenter((float)-(SPEED / AIR_CONTROL),0f,true);
                }

                this.FacingRight = false;
                break;
            case 2:
                if (OnGround())
                {
                    this.motor.setMotorSpeed(-SPEED);
                }
                else
                {
                    body.applyForceToCenter((float)(SPEED / AIR_CONTROL), 0f, true);
                    foot.applyForceToCenter((float)(SPEED / AIR_CONTROL),0f,true);
                }

                this.FacingRight = true;
        }
    }

    @Override
    public void Stop()
    {

        if(OnGround())
        {
            velocity.x = 0.0F;
            body.setLinearVelocity(body.getLinearVelocity().x/2, body.getLinearVelocity().y);
            foot.setLinearVelocity(foot.getLinearVelocity().x/2, body.getLinearVelocity().y);
        }
        this.motor.enableLimit(true);
        //Gdx.app.log(LOG_TAG,"Stopped");
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
        
        if(wallruncount ==0 & !OnGround()) {
            velocity.y = 0.0F;
            body.setLinearVelocity(this.body.getLinearVelocity().x, body.getLinearVelocity().y / 2.2f);
            if(body.getLinearVelocity().y <6  &FacingRight)
                body.setLinearVelocity(body.getLinearVelocity().x+1f,8);
            else
              if(body.getLinearVelocity().y < 6 & !FacingRight)
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

    public void addWallCount(int i)
    {
        wallruncount +=i;
    }

    public void damage (int amount)
    {

        shield-= amount;
        if(shield <0)
        {
            ((Game)Gdx.app.getApplicationListener()).setScreen(new GameOverScreen());
        }

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


}
