package hu.frontrider.platformer.Entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import hu.frontrider.platformer.Entity.Trigger;

/**
 * Created by Frontrider on 2015.10.07..
 */
public interface PickupObjects extends Trigger,Disposable,Drawable
{
}
