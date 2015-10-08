package hu.frontrider.platformer.Classes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import hu.frontrider.platformer.Entity.Drawable;
import hu.frontrider.platformer.Entity.GuiHook;

import java.util.Iterator;

/**
 * Created by Frontrider on 2015.10.08..
 */
public class GUIHandler
{
    Array<GuiHook> gui;
    int x;
    int y;

    public GUIHandler()
    {
        gui = new Array();
    }

    public void add(Array drawables)
    {
        this.gui.addAll(drawables);
    }

    public void add(GuiHook drawable)
    {
        this.gui.add(drawable);
    }

    public void Draw(SpriteBatch batch)
    {
        Iterator<GuiHook> itr = gui.iterator();

        while( itr.hasNext())
        {
            itr.next().Draw(batch,x,y);
        }
    }


}
