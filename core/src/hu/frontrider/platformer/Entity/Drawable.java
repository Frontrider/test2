package hu.frontrider.platformer.Entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Frontrider on 2015.10.08..
 */
public interface Drawable
{
    void Draw(SpriteBatch batch,World world);
}
