package hu.frontrider.platformer.Classes;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import java.util.Iterator;

/**
 * Created by Frontrider on 2015.10.08..
 */
public class Disposer
{
    Array<Disposable> livingArray;

    public Disposer()
    {
        livingArray = new Array();
    }

    public void add(Array updatables)
    {
        this.livingArray.addAll(updatables);
    }

    public void add(Disposable disposable)
    {
        this.livingArray.add(disposable);
    }

    public void dispose()
    {
        Iterator<Disposable> itr = livingArray.iterator();

        while( itr.hasNext())
        {
            itr.next().dispose();
        }
    }
}
