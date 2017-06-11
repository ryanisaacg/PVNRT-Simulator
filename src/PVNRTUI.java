import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;

public class PVNRTUI
{
	Container container;
	JLabel tempLabel, volumeLabel, pressureLabel;
	JSlider tempSlider, volumeSlider, pressureSlider;
	public JRadioButton tempLock, volumeLock, pressureLock;
	private DecimalFormat format;
	
	public static void main(String[] args)
	{
		new PVNRTUI();
	}
	
	private void refreshLabelValues()
	{
		tempLabel.setText(format.format(container.getTemperature()) + "K");
		volumeLabel.setText(format.format(container.getVolume()) + "m^3");
		pressureLabel.setText(format.format(container.getPressure()) + "kPa");
	}
	
	private void refreshSliderValues()
	{
		tempSlider.setValue((int)container.getTemperature());
		volumeSlider.setValue((int)container.getVolume());
		pressureSlider.setValue((int)container.getPressure());
	}
	
	public PVNRTUI()
	{
		container = new Container();
		tempLabel = new JLabel();
		volumeLabel = new JLabel();
		pressureLabel = new JLabel();
		tempSlider = new JSlider(1, 500, 1);
		volumeSlider = new JSlider(1, 1000, 1);
		pressureSlider = new JSlider(1, 1000, 1);
		ButtonGroup bg = new ButtonGroup();
		bg.add(tempLock = new JRadioButton("Isothermic"));
		bg.add(volumeLock = new JRadioButton("Isochoric"));
		bg.add(pressureLock = new JRadioButton("Isobaric"));
		tempLock.setSelected(true);
		tempSlider.setEnabled(false);
		container.setIsothermal();
		
		format = new DecimalFormat("0.###E0");
		refreshLabelValues();
		refreshSliderValues();
		
		JFrame frame = new JFrame("PV = nRT");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.getContentPane().add(new JPanel(){
			private static final long serialVersionUID = 1L;
			
			//Constructor
			{
				JPanel p = this;
				this.add(tempLabel);
				this.add(tempSlider);
				this.add(tempLock);
				this.add(volumeLabel);
				this.add(volumeSlider);
				this.add(volumeLock);
				this.add(pressureLabel);
				this.add(pressureSlider);
				this.add(pressureLock);

				tempSlider.addChangeListener(e -> {
					container.setTemperature(tempSlider.getValue());
					refreshLabelValues();
					refreshSliderValues();
				});
				volumeSlider.addChangeListener(e -> {
					container.setVolume(volumeSlider.getValue());
					refreshLabelValues();
					refreshSliderValues();
				});
				pressureSlider.addChangeListener(e -> {
					container.setPressure(pressureSlider.getValue());
					refreshLabelValues();
					refreshSliderValues();
				});
				
				Timer t = new Timer();
				t.scheduleAtFixedRate(new TimerTask(){
					public void run()
					{
						p.repaint();
					}
				}, 0, 1000/60);
				
				this.setPreferredSize(new Dimension(440, 400));
			}
			
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				
				//Thermometer
				g.setColor(Color.black);
				g.fillOval(320, 330, 15, 15);
				g.fillRoundRect(324, 254, 8, 81, 4, 4);
				g.setColor(new Color(100, 100, 100));
				g.fillRect(310, 333, 12, 3);
				g.setColor(Color.red);
				g.fillOval(321, 331, 13, 13);
				g.fillRoundRect(325, (int)(330 - container.getTemperature() *75/500), 6, (int)(5+container.getTemperature()*75/500), 3, 3);
				
				//Pressure Gauge
				g.setColor(new Color(100, 100, 100));
				g.drawLine(73, 334, 109, 334);
				g.drawLine(71, 333, 109, 333);
				g.drawLine(70, 332, 109, 332);
				g.drawLine(69, 331, 109, 331);
				g.drawLine(68, 330, 93, 330);
				g.drawLine(67, 329, 94, 329);
				g.drawLine(66, 328, 95, 328);
				g.drawLine(65, 327, 96, 327);
				g.drawLine(64, 326, 97, 326);
				g.drawLine(63, 325, 98, 325);
				g.setColor(Color.black);
				g.drawOval(60, 295, 40, 40);
				g.drawLine(80, 325, 80+(int)(22*(Math.cos(Math.toRadians(160 - container.getPressure() *140/1000)))),
						325-(int)(22*(Math.sin(Math.toRadians(160 - container.getPressure()*140/1000)))));

				//Container
				g.drawLine(109, 149, 109, 350);
				g.drawLine(109, 350, 310, 350);
				g.drawLine(310, 350, 310, 149);

				//Piston
				g.setColor(new Color(100, 100, 100));
				g.drawLine(110, (int)(350 - container.getVolume() /5 - 1), 309, (int)(350 - container.getVolume() /5 - 1));
				g.fillRect(200, (int)(350 - container.getVolume() /5 - 60), 20, 60);
			}
		});
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
