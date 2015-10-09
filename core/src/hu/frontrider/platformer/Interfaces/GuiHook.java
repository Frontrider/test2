package hu.frontrider.platformer.Interfaces;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Frontrider on 2015.10.08..
 */
public interface GuiHook extends Disposable
{
    void Draw(SpriteBatch batch,int x,int y);
    int getPriority();
    int Priority=0;
}
