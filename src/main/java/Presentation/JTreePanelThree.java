package Presentation;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import java.awt.*;

public class JTreePanelThree {
	private JPanel jpanel = new JPanel();
    private JTree treeOne;


    DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
    private DefaultTreeModel treeModel = new DefaultTreeModel(root);

    public JTreePanelThree() {
        super();


        jpanel.setLayout(new FlowLayout());

        DefaultMutableTreeNode sub1 = new DefaultMutableTreeNode("Thick Crust");
        DefaultMutableTreeNode sub2 = new DefaultMutableTreeNode("Thin Crust");
        DefaultMutableTreeNode sub3 =  new DefaultMutableTreeNode("Medium Crust");
         
        sub1.add( new DefaultMutableTreeNode("OkLOL" ));
        sub1.add( new DefaultMutableTreeNode("Nu s ok scuby" ));
        sub1.add( new DefaultMutableTreeNode("plm") );
 
        sub2.add( new DefaultMutableTreeNode("urat") );
        sub2.add( new DefaultMutableTreeNode("simt ca plesnesc") );
         
        root.add(sub1);
        root.add(sub2);
        root.add(sub3);

        treeModel.setRoot(root);

        treeOne = new JTree(treeModel);
        treeOne.setRootVisible(false);
        treeOne.setShowsRootHandles(true);
        jpanel.add(treeOne);

    }

    public void removeNode(){
    }

    public void removeAllNodes(){
        root = new DefaultMutableTreeNode();
        treeModel.reload();
    }

    public void addNode(String nodeName){
        treeModel.insertNodeInto(new DefaultMutableTreeNode(nodeName), root, root.getChildCount());
        treeModel.reload();
    }

    public JPanel getJpanel() {
        return jpanel;
    }

}
