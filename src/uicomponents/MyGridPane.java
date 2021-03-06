package uicomponents;

import javafx.geometry.HPos;
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
        if (rowConstraints != null) {
            for (int i = 0; i < rowConstraints.length; i++) {
                this.getRowConstraints().add(new MyRowConstraints(rowConstraints[i], vPos));
            }
        }
        if (columnConstraints != null) {
            for (int i = 0; i < columnConstraints.length; i++) {
                this.getColumnConstraints().add(
                        new MyColumnConstraints(columnConstraints[i], hPos));
            }
        }
    }

    public MyGridPane(double[] rowConstraints, double[] columnConstraints) {
        this(rowConstraints, columnConstraints, HPos.LEFT, VPos.TOP);
    }

    /**
     * Constructor.
     */
    public MyGridPane() {
        this(new double[]{100}, new double[]{100}, HPos.LEFT, VPos.TOP);
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
     * Add multiple nodes and let them be at the center of the grids.
     *
     * @param i     Column.
     * @param nodes Nodes.
     */
    @Override
    public void addColumn(int i, Node... nodes) {
        addColumn(50, i, nodes);
    }

    public void addColumn(int minHeight, int i, Node... nodes) {
        Node[] newNodes = new Node[nodes.length];
        for (int j = 0; j < nodes.length; j++) {
            if (nodes[j] == null) {
                newNodes[j] = new Pane();
            } else {
                newNodes[j] =  nodes[j];
            }
        }
        super.addColumn(i, newNodes);
        // If there are no rowConstraints specified for the columns we add,
        // we automatically add constraints.
        int endRow = GridPane.getRowIndex(newNodes[newNodes.length - 1]);
        for (int j = getRowConstraints().size(); j <= endRow; j++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(minHeight);
            rowConstraints.setMaxHeight(9999);
            rowConstraints.setValignment(VPos.TOP);
            this.getRowConstraints().add(rowConstraints);
        }
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

    public void addRowConstraints(double[] rowConstraints, VPos vPos) {
        for (double rowConstraint : rowConstraints) {
            this.getRowConstraints().add(new MyRowConstraints(rowConstraint, vPos));
        }
    }

    public void addColumnConstraints(double[] columnConstraints, HPos hPos) {
        for (double columnConstraint: columnConstraints) {
            this.getColumnConstraints().add(new MyColumnConstraints(columnConstraint, hPos));
        }
    }

    public void cleanAllConstraints() {
        this.getRowConstraints().setAll();
        this.getColumnConstraints().setAll();
    }

    public void setRowConstraints(double[] rowConstraints, VPos vPos) {
        this.getRowConstraints().clear();
        this.addRowConstraints(rowConstraints, vPos);
    }
    public void setColumnConstraints(double[] columnConstraints, HPos hPos) {
        this.getColumnConstraints().clear();
        this.addColumnConstraints(columnConstraints, hPos);
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
            this(percentageWidth, HPos.LEFT);
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
            this(percentageHeight, VPos.TOP);
        }

        private MyRowConstraints(double percentageHeight, VPos vPos) {
            super();
            this.setPercentHeight(percentageHeight);
            this.setValignment(vPos);
        }
    }

}
