package com.androidthermostat.server.utils;

import ioio.lib.api.AnalogInput;
import ioio.lib.api.DigitalOutput;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.BaseIOIOLooper;
import ioio.lib.util.android.IOIOActivity;
import android.os.Bundle;

public class IOIOHelper extends BaseIOIOLooper {

	AnalogInput tempIn;
	private DigitalOutput fan;
	private DigitalOutput heat;
	private DigitalOutput cool;
	

	private static IOIOHelper current;
	public static IOIOHelper getCurrent() { return current; }
	public static void setCurrent(IOIOHelper value) { current = value; }
	
	
	public void toggleFan(boolean on) { 
		try { fan.write(on); } catch (Exception e) {  Utils.debugText = "IOIOHelper.toggleFan - " + e.toString();  }
	}
	
	public void toggleHeat(boolean on)
	{
		try { heat.write(on); } catch (Exception e) {  Utils.debugText = "IOIOHelper.toggleHeat - " + e.toString();  }
	}
	
	public void toggleCool(boolean on)
	{
		try { cool.write(on); } catch (Exception e) { Utils.debugText = "IOIOHelper.toggleCool - " + e.toString(); }
	}
	
	public double getTemperature()
	{
		int debugNum = -99;
		try{
			double totalVolts = 0;
			int samples = tempIn.available();
			debugNum = samples;
			for (int i=0;i<samples;i++)
			{
				totalVolts+=tempIn.getVoltageBuffered();
			}
			double averageVolts = totalVolts / (double)samples;
			
			double mv = averageVolts * 1000.0;
			double c = (mv - 500.0)/10.0;
			double f = Math.round( (c * 9.0 / 5.0 + 32.0) * 10.0 ) / 10.0;
			return f;
		} catch (Exception ex) { 
				//Utils.debugText = "IOIO.getTemperature - " + ex.toString() + String.valueOf(debugNum);
		}
		return -99;
	}
	
		
		@Override
		protected void setup() throws ConnectionLostException {
			Utils.debugText = "IOIO.setup";
			try {
				//DigitalOutput led_ = ioio_.openDigitalOutput(0, true);
				
				fan = ioio_.openDigitalOutput(10, false);
				heat = ioio_.openDigitalOutput(12, false);
				cool = ioio_.openDigitalOutput(7, false);
				
				tempIn = ioio_.openAnalogInput(46);
				//this.
				tempIn.setBuffer(5000);
			} catch (Exception e) {
				Utils.debugText = "IOIOHelper.setup - " + e.toString();
			}
			
		}
	
		@Override
		public void loop() throws ConnectionLostException {
			
		}
	

	

}