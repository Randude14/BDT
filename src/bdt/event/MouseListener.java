package bdt.event;


public interface MouseListener
{

public abstract void mouseReleased(Mouse mouse, int x, int y);

public abstract void mousePressed(Mouse mouse, int x, int y);

public abstract void mouseDragged(Mouse mouse, int x, int y);

public abstract void mouseWheel(int clicks, int x, int y);

public abstract void mouseMoved(int x, int y);

public abstract void mouseEntered(int x, int y);

public abstract void mouseExited();

}
