package hu.frontrider.platformer.Entity.Enemy;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import hu.frontrider.platformer.Entity.Player.Player;
import hu.frontrider.platformer.Helper.FixtureData;

import java.util.Iterator;

/**
 * Created by Frontrider on 2015.09.20..
 */
public class EnemyHandler
{
    private static final String LOG_TAG = "Enemy Handler";
    Enemy tmp;
    Array<Enemy> enemies;
    Player player;
    raycastcallback raycastcallback;

    public EnemyHandler(Array<Enemy> enemies,Player player)
    {
        this.enemies = enemies;
        this.player = player;
        this.raycastcallback = new raycastcallback();

    }

    public void update(Player player,World world)
    {
        Iterator<Enemy> itr = enemies.iterator();

      //  Gdx.app.log(LOG_TAG, String.valueOf(enemies.size));
        while(itr.hasNext())
        {
            // Gdx.app.log(LOG_TAG, " enemy updated");
            tmp = itr.next();

            world.rayCast(raycastcallback, tmp.getPosition(), player.getPosition());

           if(!tmp.destroyed){
            //  Gdx.app.log(LOG_TAG, " distance: " + tmp.getPosition().dst(player.getPosition()));
            if (tmp.getPosition().dst(player.getPosition()) < 10f)
            {
                tmp.setPatrolPoint(player.getPosition());
              //  tmp.trigger(player);

                // Gdx.app.log(LOG_TAG, "player set");
            }
            else
            {
                tmp.setPatrolPoint(tmp.getPosition());
                //Gdx.app.log(LOG_TAG, "player unset");
               // tmp.triggerend(player);
               }}}}

    private class raycastcallback implements RayCastCallback
    {
        @Override
        public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction)
        {
            if(FixtureData.userDataNotNull(fixture))
                if(fixture.isSensor() ==false)
                {
                    if (5 > tmp.getPosition().dst(point))
                    {
                        if (fixture.getUserData() == player)
                        {
                            if (tmp.AttackTimer > 3)
                            {
                                player.Damage(tmp.damage);
                                tmp.AttackTimer = 0;
                            }
                            return 0;
                        }
                        //Gdx.app.log(LOG_TAG,"raycasting");
                    }
                }
                else
                return -1;

            return 0;
        }
    }

    public Array<Enemy> getEnemies(){return enemies;}
}
