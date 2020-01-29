import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;

/**
 * When we add nodes to this customized grid pane, the nodes will be at the center. Also, the constructor
 * allows you to specify the size of the grids with respect to the ratio to the size of the Scene.
 *
 * @author Chunhao Zou
 * @version 1.0
 */
public class MyGridPane extends GridPane {

    /**
     * Customized Column Constraints. The constructor allows you to quickly create a useful constraint.
     */
    private static class MyColumnConstraints extends ColumnConstraints {

        /**
         * Constructor.
         *
         * @param percentageWidth Percentage Width.
         */
        private MyColumnConstraints(double percentageWidth) {
            super();
            this.setPercentWidth(percentageWidth);
        }
    }

    /**
     * Customized Row Constraints. The constructor allows you to quickly create a useful constraint.
     */
    private static class MyRowConstraints extends RowConstraints {

        /**
         * Constructor.
         *
         * @param percentageHeight Percentage Height.
         */
        private MyRowConstraints(double percentageHeight) {
            super();
            this.setPercentHeight(percentageHeight);
        }
    }

    /**
     * Constructor. It allows you to quickly construct a grid pane and specify
     * the size of the grids with respect to the ratio to the size of the Scene.
     *
     * @param rowConstraints    Row Constraints.
     * @param columnConstraints Column Constraints.
     */
    public MyGridPane(double[] rowConstraints, double[] columnConstraints) {
        super();
        for (int i = 0; i < rowConstraints.length; i++) {
            this.getRowConstraints().add(new MyRowConstraints(rowConstraints[i]));
        }
        for (int i = 0; i < columnConstraints.length; i++) {
            this.getColumnConstraints().add(new MyColumnConstraints(columnConstraints[i]));
        }
    }

    /**
     * Specify the size of the grids with respect to the ratio to the size of the Scene.
     *
     * @param rowConstraints    Row constraints.
     * @param columnConstraints Column constraints.
     */
    public void setConstraints(double[] rowConstraints, double[] columnConstraints) {
        this.getRowConstraints().setAll();
        this.getColumnConstraints().setAll();
        for (int i = 0; i < rowConstraints.length; i++) {
            this.getRowConstraints().add(new MyRowConstraints(rowConstraints[i]));
        }
        for (int i = 0; i < columnConstraints.length; i++) {
            this.getColumnConstraints().add(new MyColumnConstraints(columnConstraints[i]));
        }
    }

    /**
     * Constructor.
     */
    public MyGridPane() {
        super();
    }

    /**
     * Add a node and let it be at the center of the grid.
     *
     * @param node Node.
     * @param i    Column.
     * @param i1   Row.
     */
    @Override
    public void add(Node node, int i, int i1) {
        super.add(new StackPane(node), i, i1);
    }

    /**
     * Add a node and let it be at the center of the grid.
     *
     * @param node Node.
     * @param i    Column.
     * @param i1   Row.
     * @param i2   Column Span.
     * @param i3   Row Span.
     */
    @Override
    public void add(Node node, int i, int i1, int i2, int i3) {
        super.add(new StackPane(node), i, i1, i2, i3);
    }

    /**
     * Add multiple nodes and let them be at the center of the grids.
     *
     * @param i     Column.
     * @param nodes Nodes.
     */
    @Override
    public void addColumn(int i, Node... nodes) {
        Node[] newNodes = new Node[nodes.length];
        for (int j = 0; j < nodes.length; j++) {
            if (nodes[j] == null) {
                newNodes[j] = new StackPane();
            } else {
                newNodes[j] = new StackPane(nodes[j]);
            }
        }
        super.addColumn(i, newNodes);
    }

    /**
     * Add multiple nodes and let them be at the center of the grids.
     *
     * @param i     Row.
     * @param nodes Nodes.
     */
    @Override
    public void addRow(int i, Node... nodes) {
        Node[] newNodes = new Node[nodes.length];
        for (int j = 0; j < nodes.length; j++) {
            if (nodes[j] == null) {
                newNodes[j] = new StackPane();
            } else {
                newNodes[j] = new StackPane(nodes[j]);
            }
        }
        super.addRow(i, newNodes);
    }

    /**
     * Generate an array for the Constraint of a grid pane.
     * The array specifies how the space will be equally divided.
     *
     * @param x How many parts we will divide our grid pane into.
     * @return An array that specifies how the space will be equally divided.
     */
    public static double[] getSpan(int x) {
        double[] result = new double[x];
        for (int i = 0; i < x; i++) {
            result[i] = 100.0 / x;
            result[i] = 100.0 / x;
        }
        return result;
    }
}
