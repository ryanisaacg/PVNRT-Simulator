
public class PVNRT {
	
	final static double r = 8.3144598;
	
	public static double getTemperature(double p, double v, double n) {
		return p * v / (n * r);
	}
	
	public static double getVolume(double p, double n, double t) {
		return n * r * t / p;
	}
	
	public static double getPressure(double v, double n, double t) {
		return n * r * t / v;
	}
	
	public static double getMoles(double p, double v, double t) {
		return p * v / (t * r);
	}
}
