package bdt.event;

import java.util.Map;
import java.util.HashMap;

public enum Mouse
{

LEFT_CLICK(1),
RIGHT_CLICK(3),
WHEEL_CLICK(2);

private Mouse(int ID)
{
this.ID = ID;
}

public static Mouse getMouseById(int ID)
{
return map.get(ID);
}

public final int ID;

private static final Map<Integer, Mouse> map;

static
{
map = new HashMap<Integer, Mouse>();

  for(Mouse mouse : Mouse.values())
  {
  map.put(mouse.ID, mouse);
  }
    
}

}
