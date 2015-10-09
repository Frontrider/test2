package hu.frontrider.platformer.Entity.Enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.Disposable;
import hu.frontrider.platformer.Entity.Living;
import hu.frontrider.platformer.Entity.Player.Player;
import hu.frontrider.platformer.Helper.StaticVariables;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

/**
 * Created by Frontrider on 2015.09.18..
 */
public class Enemy implements Living
{    public int id;


    protected String LOG_TAG;

    protected int SPEED = 300;
    protected int TORQUE = 1000;
    protected int damage = 10;

    protected Body body;
    protected Body wheel;
    protected boolean FacingRight;
    protected RevoluteJoint motor;
    protected World world;
   // protected raycastcallback raycastcallback;
    protected Vector2 Startpoint,patrolPoint,p1,p2,collosion,velocity;

    private int health;
    public float AttackTimer;

    Box2DSprite sprite;
    private Player player;
    public boolean destroyed = false;


    public Enemy(World world,Vector2 pos,int id,Player player) {


        this.patrolPoint = pos;
        createbody(world, pos);
        createdata(pos,id,player);
    }

    public Enemy(World world,Vector2 pos,Vector2 patrolPoint,int id,Player player) {

        this.patrolPoint = patrolPoint;
        createbody(world, pos);
        createdata(pos,id,player);
    }

   private void createdata(Vector2 pos, int id,Player player)
    {
        this.player = player;
      //  raycastcallback = new raycastcallback();
        velocity = new Vector2(0,0);
        health = 20;
        AttackTimer =0;
        Startpoint = pos;
        this.id = id;
        LOG_TAG = "enemy"+String.valueOf(id);
        p1 = new Vector2();
        p2 = new Vector2();
        collosion = new Vector2();
    }

    private void createbody(World world,Vector2 pos) {
        sprite = new Box2DSprite(new Texture("textures/sprite.png"));
        this.world = world;
        BodyDef bodydef = new BodyDef();
        bodydef.position.set(pos);
        //bodydef.gravityScale =0;
        bodydef.type = BodyDef.BodyType.DynamicBody;
        bodydef.allowSleep = true;

        FixtureDef fixture = new FixtureDef();

        CircleShape circleShape = new CircleShape();
        PolygonShape polygonShape = new PolygonShape();
        body = world.createBody(bodydef);
        circleShape.setRadius(1f);
        fixture.shape = circleShape;
        fixture.friction = 1.0F;
        fixture.restitution = 0.0F;
        fixture.density = 5.0F;
        fixture.filter.categoryBits = StaticVariables.CATEGORY_MONSTER;
        fixture.filter.maskBits = StaticVariables.MASK_MONSTER;
        fixture.isSensor = false;
        fixture.filter.groupIndex = StaticVariables.PLAYER_GROUP;
        wheel = world.createBody(bodydef);
        wheel.createFixture(fixture).setUserData(this);

        polygonShape.setAsBox(1,1);
        fixture.shape = polygonShape;
        fixture.filter.categoryBits = StaticVariables.CATEGORY_TRIGGER;
        fixture.filter.maskBits = StaticVariables.MASK_TRIGGER;
        fixture.isSensor = true;
        fixture.density =0;
        body.createFixture(fixture).setUserData(sprite);

        fixture.filter.groupIndex = 0;
        fixture.isSensor = false;
        fixture.filter.categoryBits = StaticVariables.CATEGORY_MONSTER;
        fixture.filter.maskBits = StaticVariables.MASK_MONSTER;
        fixture.filter.groupIndex = 0;

        bodydef.fixedRotation = true;
        bodydef.position.set(pos.add(0, 0f));


        polygonShape.setAsBox(1f,0.5f,new Vector2(0f,0.25f),0);
        fixture.shape =  polygonShape;

        body.createFixture(fixture);
        fixture.isSensor = true;
        polygonShape.setAsBox(1,1);
        fixture.shape = polygonShape;
        fixture.isSensor = true;

        RevoluteJointDef motorJointDef = new RevoluteJointDef();
        motorJointDef.motorSpeed = SPEED;
        motorJointDef.enableMotor = true;
        motorJointDef.maxMotorTorque = TORQUE;
        motorJointDef.initialize(body, wheel, wheel.getPosition());
        motor = (RevoluteJoint)world.createJoint(motorJointDef);

        circleShape.dispose();
        polygonShape.dispose();

       // Gdx.app.log(LOG_TAG,"ellenseg elkeszult");

    }

    @Override
    public void Stop() {
        velocity.x = 0.0F;

            body.setLinearVelocity(0.0F, 0.0F);

        //this.motor.enableLimit(true);
        this.wheel.setFixedRotation(true);
        //Gdx.app.log(LOG_TAG,"Stopped");
    }

    @Override
    public void Control1(boolean Override)
    {

    }

    @Override
    public void Control2(Boolean Override)
    {

    }

    @Override
    public void Control1Up(boolean Override)
    {

    }

    @Override
    public void Control2Up(Boolean Override)
    {

    }

    @Override
    public void Update(float delta)
    {
        if (body.getPosition().x > patrolPoint.x)
        {
            if (getPosition().dst(patrolPoint) > 3)
            {
                if (FacingRight)
                {
                    setPatrolPoint(getPosition());
                }
                Move((byte) 2,false);
            }
            else setPatrolPoint(getPosition());
            // Gdx.app.log(LOG_TAG," forward");
        }
        else
        {
            if (getPosition().dst(patrolPoint) > 3)
            {
                if (!FacingRight)
                {
                    setPatrolPoint(getPosition());
                }
                Move((byte) 1,false);
            }
            else
                setPatrolPoint(getPosition());
            //Gdx.app.log(LOG_TAG, " backward");
        }


        if (patrolPoint.equals(getPosition()))
        {
            this.Stop();
            //Gdx.app.log(LOG_TAG, " stop");
        }

        AttackTimer += delta;


      //  world.rayCast(raycastcallback, this.body.getPosition(), player.getPosition());
    }

    public void setPatrolPoint(Vector2 vector2)
    {
        patrolPoint.set(vector2);
    }

    public Vector2 getPosition()
    {
        return body.getPosition();
    }

    @Override
    public void Draw(SpriteBatch batch, World world) {
        sprite.draw(batch,world);
    }

    @Override
    public void dispose()
    {
        sprite.getTexture().dispose();
    }

    @Override
    public void Left()
    {

    }

    @Override
    public void Right()
    {

    }

    @Override
    public void StopLeft()
    {

    }

    @Override
    public void StopRight()
    {

    }

    @Override
    public void Move(Byte direction, boolean Override)
    {
        this.wheel.setFixedRotation(false);
        switch(direction)
        {
            case 2:
                this.motor.setMotorSpeed(SPEED);
                this.FacingRight = false;
                break;
            case 1:
                this.motor.setMotorSpeed(-SPEED);
                this.FacingRight = true;
        }
    }

    @Override
    public void Jump(boolean Override)
    {

    }

    @Override
    public void JumpControl()
    {

    }

    @Override
    public void FinishJump()
    {

    }

    @Override
    public void Damage(int amount)
    {
        health -= damage;
        if(health <0)
        {

            wheel.setUserData(StaticVariables.DELETE);
            body.setUserData(StaticVariables.DELETE);

            sprite.getTexture().dispose();
            Gdx.app.log(LOG_TAG, "Destroyed");
            destroyed = true;
        }

    }
}



