package cogrob;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.Sound;
import lejos.hardware.ev3.EV3;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;

public class Lella
{
  //Lellaj

  private static EV3ColorSensor colorSensor=new EV3ColorSensor(SensorPort.S2);
  private static SensorMode color=colorSensor.getAmbientMode();
  private static float[] colorSample=new float[2];
  private static EV3ColorSensor colorSensor2=new EV3ColorSensor(SensorPort.S1);
  private static SensorMode light=colorSensor2.getAmbientMode();
  public static void main(String[] args)
  {
    final double  THRESHOLD= 0.4;
    final EV3 ev3=(EV3)BrickFinder.getLocal();
    Keys keys=ev3.getKeys();
    Sound.beep();
    System.out.println("Welcome everybody!");
    System.out.println("Left = aggressive");
    while(true)
    {
      int buttons = keys.getButtons();
      switch(buttons)
      {
        case Keys.ID_RIGHT:
          while(!(keys.getButtons()==Keys.ID_ESCAPE))
          {
            color.fetchSample(colorSample,0);
            light.fetchSample(colorSample,1);
            System.out.println(colorSample[0]*100+"  "+colorSample[1]*100);
            Motor.D.setSpeed(colorSample[0]*600);
            Motor.A.setSpeed(colorSample[1]*600);
            Motor.A.forward();
            Motor.D.forward();
          }
          break;
        case Keys.ID_LEFT:
          while(!(keys.getButtons()==Keys.ID_ESCAPE))
          {
            color.fetchSample(colorSample,0);
            light.fetchSample(colorSample,1);
            System.out.println(colorSample[0]*100+"  "+colorSample[1]*100);
            Motor.A.setSpeed(colorSample[0]*600);
            Motor.D.setSpeed(colorSample[1]*600);
            Motor.A.forward();
            Motor.D.forward();
          }
          break;
        case Keys.ID_DOWN:
          Sound.beep();
          while(!(keys.getButtons()==Keys.ID_ESCAPE))
          {
            color.fetchSample(colorSample,0);
            light.fetchSample(colorSample,1);
            if((1-colorSample[1])<THRESHOLD)
              Motor.A.stop();
            else
            {
              Motor.A.setSpeed(1-colorSample[1]*600);
            }
            if((1-colorSample[0])<THRESHOLD)
              Motor.D.stop();
            else
            {
              Motor.D.setSpeed(1-colorSample[0]*600);
            }
          }
          break;
        case Keys.ID_UP:
          Sound.beep();
          while(!(keys.getButtons()==Keys.ID_ESCAPE))
          {
            color.fetchSample(colorSample,0);
            light.fetchSample(colorSample,1);
            if((1-colorSample[1])<THRESHOLD)
              Motor.D.stop();
            else
            {
              Motor.D.setSpeed(1-colorSample[1]*600);
            }
            if((1-colorSample[0])<THRESHOLD)
              Motor.A.stop();
            else
            {
              Motor.A.setSpeed(1-colorSample[0]*600);
            }
          }
          break;
          default:
            break;
      }

    }
  }
}
