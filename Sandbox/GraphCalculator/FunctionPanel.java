import java.awt.Color;
import java.awt.Dimension;
import javax.swing.*;
public class FunctionPanel extends JPanel{
	
	public FunctionPanel(GraphCalculatorGUI gui){
		panel(gui);
	}
	public void panel(GraphCalculatorGUI gui){
		Dimension functionDim = new Dimension(100,gui.getDim().height);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setPreferredSize(functionDim);
		this.setBackground(Color.RED);
		addButtons(gui);
		addSliders(gui);
	}

	public void addButtons(GraphCalculatorGUI gui){
		JButton newFunc = new JButton("New");
		this.add(newFunc);
		newFunc.addActionListener(e -> {
			gui.getGraphPanel().addFunction(queryForFunc());
		});
		JButton clear = new JButton("Clear");
		this.add(clear);
		clear.addActionListener(e -> {
			gui.getGraphPanel().clearFunctions();
		});
	}

	public Function queryForFunc(){
		try{
			String func = JOptionPane.showInputDialog("Enter a Function");
			Function f = new Function(func);
			for(int x = 0; x < 10; x++){
				f.inputPositive(x);
			}
			return f;
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,"Invalid Function","Inane error",JOptionPane.ERROR_MESSAGE);			
			return queryForFunc();
		}
	}

	public void addSliders(GraphCalculatorGUI gui){
		final int MIN_TICK = 1;
		final int MAX_TICK = 10;
		JSlider graphScale = new JSlider(JSlider.VERTICAL,
                                      MIN_TICK, MAX_TICK, MIN_TICK);
		graphScale.setMajorTickSpacing(1);
		graphScale.setPaintTicks(true);
		graphScale.setPaintLabels(true);
		this.add(graphScale);
		graphScale.addChangeListener(e -> {
			JSlider source = (JSlider) e.getSource();
			int s = source.getValue();
			gui.getGraphPanel().scale(s);
		});
	}

}