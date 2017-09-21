package cogrob;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.NXTLightSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.utility.Delay;
public class Lella
{

  private static EV3ColorSensor colorSensor=new EV3ColorSensor(SensorPort.S2);
  private static SensorMode color=colorSensor.getAmbientMode();
  private static float[] colorSample = new float[2];
  private static NXTLightSensor colorSensor2 = new NXTLightSensor(SensorPort.S1);  
  private static SensorMode light = colorSensor2.getAmbientMode();
  public static void main(String[] args)
  {
    final EV3 ev3 = (EV3) BrickFinder.getLocal();
    Keys keys = ev3.getKeys();
    
    while (!(keys.getButtons() == Keys.ID_ESCAPE)){
      color.fetchSample(colorSample,0);
      light.fetchSample(colorSample,1);
      System.out.println(colorSample[0]*100+"  "+ colorSample[1]*100);
      Delay.msDelay(500);
    }
  }
}
