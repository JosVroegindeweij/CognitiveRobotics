package cogrob;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.NXTLightSensor;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.SampleProvider;

public class Jos {
	public Jos(Port port) {
		super();
	}

	public static void main(String[] args) {
		int program = startRobot();
		System.out.println("Robot is starting...");

		System.out.println("Left for Fear");
		System.out.println("Right for Aggression");
		System.out.println("Down for Love");
		System.out.println("Up for Exploration");
		if (program == Button.ID_LEFT) // Fear
			braitenbergFirst(0);
		else if (program == Button.ID_RIGHT)// Aggression
			braitenbergFirst(1);
		else if (program == Button.ID_DOWN)// Love
			braitenbergFirst(2);
		else if (program == Button.ID_UP)// Exploration
			braitenbergFirst(3);
	}

	public static int startRobot() {
		System.out.println("Press a button to start!");
		int buttonNr = Button.waitForAnyPress();
		Sound.setVolume(25);
		Sound.twoBeeps();
		return buttonNr;
	}

	private static void braitenbergFirst(int behavior) {
		// Initializing first color sensor
		NXTLightSensor sensor1 = new NXTLightSensor(SensorPort.S1);
		SampleProvider colorValue1 = sensor1.getRedMode();
		float[] colorSample1 = new float[colorValue1.sampleSize()];
		colorValue1.fetchSample(colorSample1, 0);
		// Values are saved in colorSample1

		// Initializing second color sensor
		Port colorSensorPort2 = LocalEV3.get().getPort("S2");
		SensorModes colorSensor2 = new EV3ColorSensor(colorSensorPort2);
		SampleProvider colorValue2 = colorSensor2.getMode("Red");
		float[] colorSample2 = new float[colorValue2.sampleSize()];
		colorValue2.fetchSample(colorSample2, 0);
		// Values are saved in colorSample2

		while (true) { // We want to loop infinitely
			// double extraSpeed1 = 100 * colorSample2[0];
			//// Extra speed of left side, based on the lightvalue of the right
			// side
			// double extraSpeed2 = 100 * colorSample1[0];
			// Extra speed of right side, based on the lightvalue of the left
			// side
			int[] speeds = new int[2];
			float[] lightValues = {colorSample1[0],(colorSample2[0]/100)};
			switch (behavior) {
			case 0:
				speeds = fear(lightValues); // Initiate fear
															// behavior, the
															// function returns
															// the motor speeds
				break;
			case 1:
				speeds = aggression(lightValues); // Initiate
																	// aggression
																	// behavior,
																	// the
																	// function
																	// returns
																	// the motor
																	// speeds
				break;
			case 2:
				speeds = love(lightValues); // Initiate love
															// behavior, the
															// function returns
															// the motor speeds
				break;
			case 3:
				speeds = exploration(lightValues); // Initiate
																	// exploration
																	// behavior,
																	// the
																	// function
																	// returns
																	// the motor
																	// speeds
				break;
			default:
			}
			Motor.A.setSpeed(speeds[0]); // Motor A is on the left side
			Motor.D.setSpeed(speeds[1]); // Motor D is on the right side

			Motor.A.forward();
			Motor.D.forward();
		}

	}

	public static int[] fear(float[] lightValues) {
		int speedLeft = (int) lightValues[0] * 100 + 100;
		int speedRight = (int) lightValues[1] * 100 + 100;
		int[] speeds = { speedLeft, speedRight };
		return speeds;

	}

	public static int[] aggression(float[] lightValues){
		int speedLeft = (int) lightValues[1] * 100 + 100;
		int speedRight = (int) lightValues[0] * 100 + 100;
		int [] speeds = {speedLeft, speedRight};
		return speeds;
	}
	
	public static int[] love(float[] lightValues){
		int speedLeft = (int) lightValues[1] * 100 + 100;
		int speedRight = (int) lightValues[0] * 100 + 100;
		if (lightValues[1]>0.8 || lightValues[0]>0.8){
			speedLeft = 0; speedRight = 0;
		}
		int [] speeds = {speedLeft, speedRight};
		return speeds;
	}
	
	public static int[] exploration(float[] lightValues){
		int speedLeft = (int) lightValues[0] * 100 + 100;
		int speedRight = (int) lightValues[1] * 100 + 100;
		if (lightValues[1]>0.8 || lightValues[0]>0.8){
			speedLeft = 0; speedRight = 0;
		}
		int [] speeds = {speedLeft, speedRight};
		return speeds;
	}
}