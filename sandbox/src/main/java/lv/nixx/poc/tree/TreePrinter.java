package lv.nixx.poc.tree;

public class TreePrinter {

    private StringBuilder sb = new StringBuilder();

    public void traverseInOrder(BinaryTree binaryTree) {
        Node root = binaryTree.getRoot();

        sb.append("Root:" + root.value + "\n");
        traverseInOrder(root);

        System.out.println(sb.toString());
    }

    private void traverseInOrder(Node node) {
        if (node != null) {
            traverseInOrder(node.left);
            if (node.value != null) {
                sb.append(node + "\n");
            }
            traverseInOrder(node.right);
        }
    }

}
