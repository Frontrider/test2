package hu.frontrider.platformer.Interfaces;

/**
 * Created by Frontrider on 2015.10.07..
 */
public interface Powerup extends GuiHook
{
     void act(float delta);
     boolean ask(String string);
     boolean remove();
}
