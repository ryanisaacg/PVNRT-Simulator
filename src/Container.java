
public class Container 
{
	//Universal gas constant
	final static double r = 8.3144598;
	//Container has a constant 1 mol
	final static double n = 1;
	
	private enum State {
		ISOBARIC, //Constant pressure
		ISOCHORIC, //Constant volume
		ISOTHERMAL //constant temperature
	}
	
	private double pressure, volume, temperature;
	private State state;
	
	public Container()
	{
		temperature = 273;
		state = State.ISOTHERMAL;
		setVolume(1);
	}
	
	public void setIsobaric()
	{
		state = State.ISOBARIC;
	}
	
	public void setIsochoric()
	{
		state = State.ISOCHORIC;
	}
	
	public void setIsothermal()
	{
		state = State.ISOTHERMAL;
	}
	
	public double getPressure()
	{
		return pressure;
	}
	
	public double getVolume()
	{
		return volume;
	}
	
	public double getTemperature()
	{
		return temperature;
	}
	
	public void setPressure(double pressure)
	{
		this.pressure = pressure;
		switch(state) {
		case ISOBARIC:
			throw new RuntimeException("The container is isobaric; you can't change its pressure.");
		case ISOCHORIC:
			temperature =  pressure * volume / (n * r);
			break;
		case ISOTHERMAL:
			volume = n * r * temperature / pressure;
			break;
		}
	}
	
	public void setVolume(double volume) 
	{
		this.volume = volume;
		switch(state) {
		case ISOBARIC:
			temperature = n * r * temperature / pressure;
			break;
		case ISOCHORIC:
			throw new RuntimeException("The container is isochoric; you can't change its volume.");
		case ISOTHERMAL:
			pressure = n * r * temperature / volume;
			break;
		}
		System.out.println(pressure);
	}
	
	public void setTemperature(double temperature) 
	{
		this.temperature = temperature;
		switch(state) {
		case ISOBARIC:
			volume = n * r * temperature / pressure;
		case ISOCHORIC:
			pressure = n * r * temperature / volume;
			break;
		case ISOTHERMAL:
			throw new RuntimeException("The container is isothermal; you can't change its temperature.");
		}
	}
}
