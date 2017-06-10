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
		pressureSlider = new JSlider(1, 100000, 1);
		tempLock = new JCheckBox("Temperature Independent");
		volumeLock = new JCheckBox("Volume Independent");
		pressureLock = new JCheckBox("Pressure Independent");
		
		JFrame frame = new JFrame("PV = nRT");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.getContentPane().add(new JPanel(){
			private static final long serialVersionUID = -8266151342944832435L;
			
			//Constructor
			{
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
					}
				});
				volumeSlider.addChangeListener(new ChangeListener(){
					public void stateChanged(ChangeEvent e)
					{
						volume = volumeSlider.getValue();
						volumeLabel.setText(volume + " m^3");
					}
				});
				pressureSlider.addChangeListener(new ChangeListener(){
					public void stateChanged(ChangeEvent e)
					{
						pressure = pressureSlider.getValue();
						pressureLabel.setText(pressure+ " Pa");
					}
				});
				
				JPanel p = this;
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
				g.drawLine(109, 149, 109, 350);
				g.drawLine(109, 350, 310, 350);
				g.drawLine(310, 350, 310, 149);

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
