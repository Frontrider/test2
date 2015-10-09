package hu.frontrider.platformer.Controller;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import hu.frontrider.platformer.Entity.Trigger;
import hu.frontrider.platformer.Helper.FixtureData;

/**
 * Created by Frontrider on 2015.09.19..
 */
public class MyContactListener implements ContactListener
{
    private static final String LOG_TAG = "Contact listener";

    @Override
    public void beginContact(Contact contact)
    {

        if (FixtureData.oneFixureIsTrigger(contact.getFixtureA(), contact.getFixtureB()))    {
             //Gdx.app.log(LOG_TAG, "one is trigger");
                if (FixtureData.IsTrigger(contact.getFixtureA()))
                {
                    ((Trigger)contact.getFixtureA().getUserData()).Trigger(contact.getFixtureB(),contact.getFixtureA(),contact.getWorldManifold());
                //    Gdx.app.log(LOG_TAG, "A is trigger");
                }
                if (FixtureData.IsTrigger(contact.getFixtureB()))
                {
                 ((Trigger) contact.getFixtureB().getUserData()).Trigger(contact.getFixtureA(),contact.getFixtureB(),contact.getWorldManifold());
                  //  Gdx.app.log(LOG_TAG, "B is trigger");
                }
            }

        }
    @Override
    public void endContact(Contact contact)
    {
     if (FixtureData.oneFixureIsTrigger(contact.getFixtureA(), contact.getFixtureB()))    {
               // Gdx.app.log(LOG_TAG, "one is trigger");
                if (FixtureData.IsTrigger(contact.getFixtureA()))
                {
                    ((Trigger)contact.getFixtureA().getUserData()).UnTrigger(contact.getFixtureB(),contact.getFixtureA(),contact.getWorldManifold());
                  //  Gdx.app.log(LOG_TAG, "A is trigger");
                }
                if (FixtureData.IsTrigger(contact.getFixtureB()))
                {
                    ((Trigger) contact.getFixtureB().getUserData()).UnTrigger(contact.getFixtureA(),contact.getFixtureB(),contact.getWorldManifold());
                    //Gdx.app.log(LOG_TAG, "B is trigger");
                }
            }
        }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold)
    {
        if (FixtureData.oneFixureIsTrigger(contact.getFixtureA(), contact.getFixtureB()))    {
            //Gdx.app.log(LOG_TAG, "one is trigger");
            if (FixtureData.IsTrigger(contact.getFixtureA()))
            {
                ((Trigger)contact.getFixtureA().getUserData()).preSolve(contact.getFixtureB(), contact.getFixtureA(), contact.getWorldManifold());
                //    Gdx.app.log(LOG_TAG, "A is trigger");
            }
            if (FixtureData.IsTrigger(contact.getFixtureB()))
            {
                ((Trigger) contact.getFixtureB().getUserData()).preSolve(contact.getFixtureA(), contact.getFixtureB(), contact.getWorldManifold());
                //  Gdx.app.log(LOG_TAG, "B is trigger");
            }
        }


    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse)
    {
        if (FixtureData.oneFixureIsTrigger(contact.getFixtureA(), contact.getFixtureB()))    {
            //Gdx.app.log(LOG_TAG, "one is trigger");
            if (FixtureData.IsTrigger(contact.getFixtureA()))
            {
                ((Trigger)contact.getFixtureA().getUserData()).postSolve(contact.getFixtureB(),contact.getFixtureA(),contact.getWorldManifold());
                //    Gdx.app.log(LOG_TAG, "A is trigger");
            }
            if (FixtureData.IsTrigger(contact.getFixtureB()))
            {
                ((Trigger) contact.getFixtureB().getUserData()).postSolve(contact.getFixtureA(),contact.getFixtureB(),contact.getWorldManifold());
                //  Gdx.app.log(LOG_TAG, "B is trigger");
            }
        }

    }
}
