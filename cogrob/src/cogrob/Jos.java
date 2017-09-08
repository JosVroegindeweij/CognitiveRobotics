package cogrob;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.SampleProvider;

public class Jos {
	public Jos(Port port) {
		super();
	}

	public static void main(String[] args) {
		int program = startRobot();
		System.out.println("Robot is starting...");

		if (program == Button.ID_LEFT) // This is used to select different algorithms you want to run
			braitenbergFirst();
	}

	public static int startRobot() {
		System.out.println("Press a button to start!");
		int buttonNr = Button.waitForAnyPress();
		Sound.setVolume(25);
		Sound.twoBeeps();
		return buttonNr;
	}

	private static void braitenbergFirst() {
		// Initializing first color sensor
		Port colorSensorPort1 = LocalEV3.get().getPort("S1");
		SensorModes colorSensor1 = new EV3ColorSensor(colorSensorPort1);
		SampleProvider colorValue1 = colorSensor1.getMode("Red");
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
		
		while (true) {		// We want to loop infinitely
			double extraSpeed1 = 100 * colorSample2[0]; 
//			Extra speed of left side, based on the lightvalue of the right side
			double extraSpeed2 = 100 * colorSample1[0]; 
//			Extra speed of right side, based on the lightvalue of the left side

			Motor.A.setSpeed((int) (100 + extraSpeed1));	// Motor A is on the left side
			Motor.B.setSpeed((int) (100 + extraSpeed2));	// Motor B is on the right side

			Motor.A.forward();
			Motor.B.forward();
		}

	}
}