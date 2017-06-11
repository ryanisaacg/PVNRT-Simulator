import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PVNRTUI
{
	public double temperature, volume, pressure;
	JLabel tempLabel, volumeLabel, pressureLabel;
	JSlider tempSlider, volumeSlider, pressureSlider;
	public JCheckBox tempLock, volumeLock, pressureLock;
	
	public static void main(String[] args)
	{
		new PVNRTUI();
	}
	
	public PVNRTUI()
	{
		temperature = 1;
		volume = 1;
		pressure = 1;
		tempLabel = new JLabel(temperature + " K");
		volumeLabel = new JLabel(volume + " m^3");
		pressureLabel = new JLabel(pressure + " Pa");
		tempSlider = new JSlider(1, 500, 1);
		volumeSlider = new JSlider(1, 1000, 1);
		pressureSlider = new JSlider(1, 1000, 1);
		tempLock = new JCheckBox("Temperature Independent");
		volumeLock = new JCheckBox("Volume Independent");
		pressureLock = new JCheckBox("Pressure Independent");
		
		JFrame frame = new JFrame("PV = nRT");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.getContentPane().add(new JPanel(){
			private static final long serialVersionUID = -8266151342944832435L;
			
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

				tempSlider.addChangeListener(new ChangeListener(){
					public void stateChanged(ChangeEvent e)
					{
						temperature = tempSlider.getValue();
						tempLabel.setText(temperature + " K");
						PVNRT.update(p);
						refresh();
					}
				});
				volumeSlider.addChangeListener(new ChangeListener(){
					public void stateChanged(ChangeEvent e)
					{
						volume = volumeSlider.getValue();
						volumeLabel.setText(volume + " m^3");
						PVNRT.update(p);
						refresh();
					}
				});
				pressureSlider.addChangeListener(new ChangeListener(){
					public void stateChanged(ChangeEvent e)
					{
						pressure = pressureSlider.getValue();
						pressureLabel.setText(pressure+ " kPa");
						PVNRT.update(p);
						refresh();
					}
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
			
			public void refresh()
			{
				tempSlider.setValue((int)temperature);
				volumeSlider.setValue((int)volume);
				pressureSlider.setValue((int)pressure);
				tempLabel.setText(temperature + " K");
				volumeLabel.setText(volume + " m^3");
				pressureLabel.setText(pressure+ " kPa");
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
				g.fillRoundRect(325, (int)(330 - temperature*75/500), 6, (int)(5+temperature*75/500), 3, 3);
				
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
				g.drawLine(80, 325, 80+(int)(22*(Math.cos(Math.toRadians(160 - pressure*140/1000)))),
						325-(int)(22*(Math.sin(Math.toRadians(160 - pressure*140/1000)))));

				//Container
				g.drawLine(109, 149, 109, 350);
				g.drawLine(109, 350, 310, 350);
				g.drawLine(310, 350, 310, 149);

				//Piston
				g.setColor(new Color(100, 100, 100));
				g.drawLine(110, (int)(350 - volume/5 - 1), 309, (int)(350 - volume/5 - 1));
				g.fillRect(200, (int)(350 - volume/5 - 60), 20, 60);
			}
		});
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
