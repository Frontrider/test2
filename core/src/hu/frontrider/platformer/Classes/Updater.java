package hu.frontrider.platformer.Classes;

import com.badlogic.gdx.utils.Array;
import hu.frontrider.platformer.Entity.Updatable;

import java.util.Iterator;

/**
 * Created by Frontrider on 2015.10.08..
 */
public class Updater
{
    Array<Updatable> livingArray;

    public Updater()
    {
        livingArray = new Array();
    }

    public void add(Array updatables)
    {
        this.livingArray.addAll(updatables);
    }

    public void add(Updatable updatable)
    {
        this.livingArray.add(updatable);
    }

    public void Update(float delta)
    {
        Iterator<Updatable> itr = livingArray.iterator();

        while( itr.hasNext())
        {
            itr.next().Update(delta);
        }
    }
}
