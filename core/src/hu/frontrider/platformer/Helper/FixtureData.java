package hu.frontrider.platformer.Helper;

import com.badlogic.gdx.physics.box2d.Fixture;
import hu.frontrider.platformer.Entity.Trigger;

/**
 * Created by Frontrider on 2015.09.18..
 */
public abstract class FixtureData
{
    public static boolean UserDataEquals(Fixture f, String s)
    {
        return userDataNotNull(f) & f.getUserData() == s;
    }

 /*   public static boolean UserDataEquals(Fixture f,Object o) {
        try
        {
            return f.getUserData().getClass().isInstance();
        }
        catch(Exception e)
        {}
        return false;
    }*/

    public static boolean oneFixureIs(Fixture a,Fixture b,String s)
    {
       // Gdx.app.log("one fixture is ", String.valueOf(UserDataEquals(a,s) | UserDataEquals(b,s)));
        return UserDataEquals(a, s)  | UserDataEquals(b, s) ;

    }
    public static boolean oneFixureIs(Fixture a,Fixture b,Class s)
    {
        // Gdx.app.log("one fixture is ", String.valueOf(UserDataEquals(a,s) | UserDataEquals(b,s)));
        return UserDataEquals(a, s)  | UserDataEquals(b, s) ;

    }

    public static boolean FixturesAre(Fixture fixtureA, Fixture fixtureB, String s1, String s2)
    {
        return UserDataEquals(fixtureA, s1) & UserDataEquals(fixtureB, s2) | UserDataEquals(fixtureA, s2) & UserDataEquals(fixtureB, s1);
    }

    public static boolean userDataNotNull(Fixture fixture,Fixture fixture2)
    {
        return fixture.getUserData() != null & fixture.getUserData() != null ;
    }

    public static boolean userDataNotNull(Fixture fixture)
    {
        return fixture.getUserData() != null;
    }

    public static boolean oneFixureIs(Fixture a,Fixture b,Object s)
    {
        // Gdx.app.log("one fixture is ", String.valueOf(UserDataEquals(a,s) | UserDataEquals(b,s)));
        return UserDataEquals(a, s) & b.getUserData() != null | UserDataEquals(b, s) & a.getUserData() != null;

    }

    public static boolean UserDataEquals(Fixture f, Object s) {
        try
        {
            return f.getUserData().getClass().isInstance(s.getClass());
        }
        catch(Exception e)
        {}
        return false;
    }

    public static boolean FixturesAre(Fixture fixtureA, Fixture fixtureB, Object s1, Object s2)
    {
        return UserDataEquals(fixtureA, s1) & UserDataEquals(fixtureB, s2) | UserDataEquals(fixtureA, s2) & UserDataEquals(fixtureB, s1);
    }

    public static boolean FixturesAre(Fixture fixtureA, Fixture fixtureB, Class s1, Class s2)
    {
        return UserDataEquals(fixtureA, s1) & UserDataEquals(fixtureB, s2) | UserDataEquals(fixtureA, s2) & UserDataEquals(fixtureB, s1);
    }

    public static boolean OneFixtureIsNot(Fixture fixtureA,Fixture fixtureB,Class c)
    {
        return !oneFixureIs(fixtureA,fixtureB,c);
    }

    public static boolean IsSensor(Fixture fixture)
    {return fixture.isSensor();}

    public static boolean oneFixureIsTrigger(Fixture fixtureA, Fixture fixtureB)
    {
        return IsTrigger(fixtureA)|IsTrigger(fixtureB);
    }

    public static boolean IsTrigger(Fixture fixtureA)
    {
        return fixtureA.getUserData() instanceof Trigger;
    }
}
