package hu.frontrider.platformer.Interfaces;

/**
 * Created by Frontrider on 2015.10.07..
 */
public interface Controllable
{
    void Left();
    void Right();
    void StopLeft();
    void StopRight();
    void Move(Byte direction,boolean Override);
    void MoveControl(Byte direction);
    void Jump(boolean Override);
    void JumpControl();
    void FinishJump();
    void Stop();
    void Control1(boolean Override);
    void Control2(Boolean Override);

    void Control1Up(boolean Override);
    void Control2Up(Boolean Override);
}
