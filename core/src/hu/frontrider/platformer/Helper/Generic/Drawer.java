package hu.frontrider.platformer.Helper.Generic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import hu.frontrider.platformer.Entity.Drawable;
import hu.frontrider.platformer.Entity.Updatable;

import java.util.Iterator;

/**
 * Created by Frontrider on 2015.10.08..
 */
public class Drawer
{
    Array<Drawable> livingArray;

    public Drawer()
    {
        livingArray = new Array();
    }

    public void add(Array drawables)
    {
        this.livingArray.addAll(drawables);
    }

    public void add(Drawable drawable)
    {
        this.livingArray.add(drawable);
    }

    public void Draw(SpriteBatch batch, World world)
    {
        Iterator<Drawable> itr = livingArray.iterator();

        while( itr.hasNext())
        {
            itr.next().Draw(batch,world);
        }
    }


}
