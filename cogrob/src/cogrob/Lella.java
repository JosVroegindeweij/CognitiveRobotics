package cogrob;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.utility.Delay;

public class Lella
{
  private static EV3ColorSensor colorSensor=new EV3ColorSensor(SensorPort.S1);
  private static SensorMode color=colorSensor.getRGBMode();
  private static float[] colorSample = new float[color.sampleSize()];
  
  public static void main(String[] args)
  {
    final EV3 ev3 = (EV3) BrickFinder.getLocal();
    Keys keys = ev3.getKeys();
    
    while (!(keys.getButtons() == Keys.ID_ESCAPE)){
    System.out.println(lightValue());
    Delay.msDelay(500);
    }
  }
  
  public static double lightValue()
  {
    color.fetchSample(colorSample,0);
    return (double)((colorSample[0]+colorSample[1]+colorSample[2])*100);
  }
}
