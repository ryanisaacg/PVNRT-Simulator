
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
	
	public static double getWork(double pStart, double pFinal, double dv) {
		return (pStart + pFinal) / 2 * dv;
	}
	
	public static void update(PVNRTUI p) {
		boolean pLock = p.pressureLock.isSelected();
		boolean vLock = p.volumeLock.isSelected();
		boolean tLock = p.tempLock.isSelected();
		if(pLock && vLock && !tLock) {
			p.temperature = getVolume(p.pressure, p.volume, 1);
		} else if(!pLock && vLock && tLock) {
			p.pressure = getPressure(p.volume, 1, p.temperature);
		} else if(pLock && !vLock && tLock) {
			p.volume = getVolume(p.pressure, 1, p.temperature);
		}
	}
}
