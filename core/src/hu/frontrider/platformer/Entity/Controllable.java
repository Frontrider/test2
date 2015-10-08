package hu.frontrider.platformer.Entity;

/**
 * Created by Frontrider on 2015.10.07..
 */
public interface Controllable
{
    void Left();
    void Right();
    void Move(Byte direction,boolean Override);
    void Jump(boolean Override);
    void JumpControl();
    void Stop();
}
