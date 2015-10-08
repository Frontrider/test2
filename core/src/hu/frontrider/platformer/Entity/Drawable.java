package hu.frontrider.platformer.Entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Frontrider on 2015.10.08..
 */
public interface Drawable extends Disposable
{
    void Draw(SpriteBatch batch,World world);
}
