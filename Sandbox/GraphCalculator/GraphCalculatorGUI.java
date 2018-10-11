import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
public class GraphCalculatorGUI extends JPanel{

	private Dimension windowDim = new Dimension(1000,800);
	private FunctionPanel functionPanel;
	private GraphPanel graphPanel;

	public GraphCalculatorGUI(){
		super(new FlowLayout(FlowLayout.LEFT, 0, 0));
		new Calculator();
		panel();
	}

	public Dimension getDim(){ return windowDim; }
	public FunctionPanel getFunctionPanel(){ return functionPanel; }
	public GraphPanel getGraphPanel(){ return graphPanel; }




	public void panel(){
		JFrame frame = new JFrame("Graphing Calculator");
		frame.add(this);
		frame.setPreferredSize(windowDim);
		functionPanel = new FunctionPanel(this);
		graphPanel = new GraphPanel(this);
		this.add(functionPanel);
		this.add(graphPanel);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}