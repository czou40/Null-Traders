import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.*;

/**
 * When we add nodes to this customized grid pane,
 * the nodes will be at the center. Also, the constructor
 * allows you to specify the size of the grids with respect to the ratio to the size of the Scene.
 *
 * @author Chunhao Zou
 * @version 1.0
 */
public class MyGridPane extends GridPane {
    /**
     * Constructor. It allows you to quickly construct a grid pane and specify
     * the size of the grids with respect to the ratio to the size of the Scene.
     *
     * @param rowConstraints    Row Constraints.
     * @param columnConstraints Column Constraints.
     * @param hPos horizontal position
     * @param vPos vertical position
     */
    public MyGridPane(double[] rowConstraints, double[] columnConstraints, HPos hPos, VPos vPos) {
        super();
        for (int i = 0; i < rowConstraints.length; i++) {
            this.getRowConstraints().add(new MyRowConstraints(rowConstraints[i], vPos));
        }
        for (int i = 0; i < columnConstraints.length; i++) {
            this.getColumnConstraints().add(new MyColumnConstraints(columnConstraints[i], hPos));
        }
    }

    public MyGridPane(double[] rowConstraints, double[] columnConstraints) {
        this(rowConstraints, columnConstraints, HPos.LEFT, VPos.CENTER);
    }


    /**
     * Constructor.
     */
    public MyGridPane() {
        this(new double[]{100}, new double[]{100}, HPos.LEFT, VPos.CENTER);
    }

    /**
     * Constructor.
     * @param hPos horizontal position
     * @param vPos vertical position
     */
    public MyGridPane(HPos hPos, VPos vPos) {
        this(new double[]{100}, new double[]{100}, hPos, vPos);
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
        super.add(node, i, i1);
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
        super.add(node, i, i1, i2, i3);
    }

    /**
     * Add multiple nodes and let them be at the center of the grids.
     *
     * @param i     Column.
     * @param nodes Nodes.
     */
    @Override
    public void addColumn(int i, Node... nodes) {
        addColumn(Pos.TOP_LEFT, i, nodes);
    }

    public void addColumn(Pos pos, int i, Node... nodes) {
        Node[] newNodes = new Node[nodes.length];
        for (int j = 0; j < nodes.length; j++) {
            if (nodes[j] == null) {
                newNodes[j] = new Pane();
            } else {
                newNodes[j] =  nodes[j];
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
        addRow(Pos.TOP_LEFT, i, nodes);
    }

    public void addRow(Pos pos, int i, Node... nodes) {
        Node[] newNodes = new Node[nodes.length];
        for (int j = 0; j < nodes.length; j++) {
            if (nodes[j] == null) {
                newNodes[j] = new Pane();
            } else {
                newNodes[j] = nodes[j];
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

    public void setConstraint(int i, int i1, double percentageHeight,
                              double percentageWidth, HPos hPos, VPos vPos) {
        this.getRowConstraints().set(i, new MyRowConstraints(percentageHeight, vPos));
        this.getColumnConstraints().set(i1, new MyColumnConstraints(percentageWidth, hPos));

    }

    /**
     * Customized Column Constraints.
     * The constructor allows you to quickly create a useful constraint.
     */
    private static class MyColumnConstraints extends ColumnConstraints {

        /**
         * Constructor.
         *
         * @param percentageWidth Percentage Width.
         */
        private MyColumnConstraints(double percentageWidth) {
            this(percentageWidth, HPos.CENTER);
        }

        private MyColumnConstraints(double percentageWidth, HPos hPos) {
            super();
            this.setPercentWidth(percentageWidth);
            this.setHalignment(hPos);
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
            this(percentageHeight, VPos.CENTER);
        }

        private MyRowConstraints(double percentageHeight, VPos vPos) {
            super();
            this.setPercentHeight(percentageHeight);
            this.setValignment(vPos);
        }
    }

}
