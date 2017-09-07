package cogrob;
import java.util.Random;

public class Braitenberg {

	public static void main(String[] args) {
		
		// To generate fake sensor reading
		Random rand = new Random();
		
		// sensor readings, use Ambient Mode for get 1 scalar from each sensor for the light intensity
		float[] sensors = new float[2]; 
		// speeds for each of the motors
		float[] motorSpeeds = new float[2];
		
		
		// The behavior of Braitenberg robot is reactive
		// For any timestep: sense the environment, compute the speed of motors as a function of the sense alone and set it

		long startTimer = System.currentTimeMillis();
		long lastMeasure = System.currentTimeMillis();
		int measurementFreqency = 100;
		
		
		do {
			
			// measure with the sensors every |measurementFreqency| ms
			if(System.currentTimeMillis() - lastMeasure > measurementFreqency){
				// sense
				sensors[0] = rand.nextFloat();
				sensors[1] = rand.nextFloat(); 
				// compute speed of the motor - here select the different behaviors 
				motorSpeeds = fear(sensors);
				System.out.println("Light Intensity: Right " + sensors[0] + " Left " + sensors[1]);
				System.out.println("Motor speed: Right " + motorSpeeds[0] + " Left " + motorSpeeds[1]);
				// set the motor speed
				
				
				lastMeasure = System.currentTimeMillis() ;
			}
			// Drive forward
			
			
		} while(lastMeasure - startTimer < 2000); // Do this for 30 sec
		
		
		
		
		
	}
	
	public static float[] fear(float[] sensors){
		float[] motorSpeeds = new float[2];
		// unilateral connection for fear
		motorSpeeds[0] = sensors[0] * 300;
		motorSpeeds[1] = sensors[1] * 300;
		return motorSpeeds;
	}
	
	public static float[] aggression(float[] sensors){
		float[] motorSpeeds = {0,1, 0,1};
		return motorSpeeds;
	}
	
	public static float[] love(float[] sensors){
		float[] motorSpeeds = {0,1, 0,1};
		return motorSpeeds;
	}
	
	public static float[] exploration(float[] sensors){
		float[] motorSpeeds = {0,1, 0,1};
		return motorSpeeds;
	}
	
	
	

}



/*
 * final EV3 ev3 = (EV3) BrickFinder.getLocal();
		TextLCD lcd = ev3.getTextLCD();
		Keys keys = ev3.getKeys();

		// Color sensor
		EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S3);
		SensorMode color = colorSensor.getRGBMode();
		float[] colorSample = new float[color.sampleSize()];
		lcd.drawInt(colorSample.length, 0, 2);
		int key;
		long startTime = System.currentTimeMillis();
		long duration;

		do {
			duration = System.currentTimeMillis() - startTime;
			color.fetchSample(colorSample, 0);
			lcd.drawString("" + colorSample[0], 0, 3);
			lcd.drawString("" + colorSample[1], 0, 4);
			lcd.drawString("" + colorSample[2], 0, 5);
			
			lcd.drawString("" + isReflecting(colorSample), 0, 6);
			
//			key = keys.waitForAnyPress();
		} while (duration < 60000);
 * 
 * */
