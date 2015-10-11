package hu.frontrider.platformer.Classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import hu.frontrider.platformer.Interfaces.Updatable;

import java.util.Iterator;

/**
 * Created by Frontrider on 2015.10.08..
 */
public class Updater extends Thread implements Runnable
{
    Array<Updatable> updatable;
    Updatable tmp;

    public Updater()
    {
        updatable = new Array();

    }

    public void add(Array updatables)
    {
        this.updatable.addAll(updatables);
    }

    public void add(Updatable updatable)
    {
        this.updatable.add(updatable);
    }

    public void Update(float delta)
    {
        Iterator<Updatable> itr = updatable.iterator();

        while( itr.hasNext())
        {
            tmp = itr.next();
            if(tmp.Remove())
            {itr.remove();}
            else
            {tmp.Update(delta);}
        }

    }
    public Array Get()
    {return updatable;}

    @Override
    public void run()
    {
        Iterator<Updatable> itr = updatable.iterator();

        while( itr.hasNext())
        {
            tmp = itr.next();
            if(tmp.Remove())
            {itr.remove();}
            else
            {tmp.Update(Gdx.graphics.getDeltaTime());}
        }
    }
}

