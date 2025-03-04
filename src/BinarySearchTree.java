// Define the BinarySearchTree class
public class BinarySearchTree {
    // Create the class (blueprint/template) for a BinarySearchTree object.
    // Each instance (object) will have an instance variable (field) "root" of the Node class.
    // The "root" acts as the central or highest "parent" node.
    Node root;

    // Define the DEFAULT constructor method for the BinarySearchTreeClass
    public BinarySearchTree() {
        // Initialize root to null
        root = null;
    }

    // Public setter method to add a Node
    public void addNode(int key) {
        this.root = insert(this.root, key);
    }

    // Public setter method to remove a Node
    public void deleteNode(int key) {
        this.root = delete(this.root, key);
    }

    // Public inOrder view getter method
    public void viewInOrder(){
        inorder(this.root);
    }

    // Public PreOrder view getter method
    public void viewPreOrder(){
        preorder(this.root);
    }

    // Public PostOrder view getter method
    public void viewPostOrder() {
        postorder(this.root);
    }

    // Private methods to get balance information from the AVL Tree
    private int height(Node currentNode) {
        // If Node does not exist height is 0
        if (currentNode == null) {
            return 0;
        }
        // If node exists it should have a height associated with it.
        return currentNode.height;
    }

    // Private method to get teh balance factor of a Node
    private int getBalance(Node currentNode) {
        // If Node does not exist balance is 0
        if (currentNode == null) {
            return 0;
        }
        // If node exists, compute balance based off its children.
        return height(currentNode.left) - height(currentNode.right);
    }

    // Right Rotate subtree
    private Node rightRotate(Node y) {
        // Right Rotate subtree rooted at "y"
        // This rotation is used when the left subtree is too tall (LL imbalance).
        // The left child "x" of node "y" moves up to become the new root.
        // The previous root "y" moves down to become the right child of "x".
        // The subtree "z" (right child of "x") is reassigned to "y" to maintain structure.
        //
        //      y      -->          x
        //     / \                 / \
        //    x   ?    -->        ?   y
        //   / \                     / \
        //  ?   z      -->          z   ?
        Node x = y.left; // Define y's left child as our x value.
        Node z = x.right; // Define x's right child as our z value.

        // Perform Rotation
        x.right = y; // Move y to become right child of x
        y.left = z; // Move z to become left child of y

        // Update height
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        // Return new root of the subtree.
        return x;
    }

    // Left rotate subtree
    private Node leftRotate(Node x) {
        // Left Rotate subtree rooted at "x"
        // This rotation is used when the right subtree is too tall (RR imbalance).
        // The right child "y" of node "x" moves up to become the new root.
        // The previous root "x" moves down to become the left child of "y".
        // The subtree "z" (left child of "y") is reassigned to "x" to maintain structure.
        //
        //      x        -->         y
        //     / \                  / \
        //    ?   y      -->       x   ?
        //       / \              / \
        //      z   ?    -->     ?  z
        Node y = x.right;  // Store right child of x
        Node z = y.left;  // Store left child of y temporarily

        // Perform rotation
        y.left = x;  // Move x to become left child of y
        x.right = z; // Reassign z as the right child of x

        // Update heights
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        // Return the new root of the subtree
        return y;
    }

    // Private method to delete a node that matches the given key
    private Node delete(Node currentNode, int key) {
        // If node is null we either did not find key or are checking for presence
        // of child relationship. A null return stops the recursion from continuing.
        if (currentNode == null) {
            return null;
        }

        // Compare values to find key to delete
        if (key < currentNode.key) {
            currentNode.left = delete(currentNode.left, key);
        } else if (key > currentNode.key) {
            currentNode.right = delete(currentNode.right, key);
        } else { // key == currentNode.key
            // Found key, determine what state
            // Case 1: Node has no children (leaf node)
            // Case 2: Node has one child
            // Case 3 Node has two children
            if (currentNode.left == null) {
                return currentNode.right; // Only right child, return value to parent and overwrite relationship.
            } else if (currentNode.right == null) {
                return currentNode.left; // Only left child, return value to parent and overwrite relationship.
            }

            // Node with two children: Get the inorder successor (smallest in right subtree)
            Node temp = getMinValue(currentNode.right);
            // Replace current node's key with the successor's key
            currentNode.key = temp.key;
            // Delete the successor node from the right subtree
            currentNode.right = delete(currentNode.right, temp.key);
        }

        // Balance the tree
        return balanceTree(currentNode);
    }

    // Method to insert a new node with the given key
    private Node insert(Node currentNode, int key) {
        // If tree is empty, return new node.
        if (currentNode == null) {
            return new Node(key);
        }

        // If key is present in tree, just return the node itself
        if (currentNode.key == key) {
            return currentNode;
        }

        // Otherwise traverse tree
        if (key < currentNode.key) {
            currentNode.left = insert(currentNode.left, key); // Traverse left if value less than currentNode
        }
        else {
            currentNode.right = insert(currentNode.right, key); // Traverse right if value greater than currentNode
        }
        // The above logic recursively inserts the new node at the correct position.
        // Once the node is inserted, the parent node’s references to its left or right
        // child are updated accordingly.

        // Delegate balancing logic to separate method
        return balanceTree(currentNode);
    }

    // Private method to handle balancing of tree.
    private Node balanceTree(Node currentNode) {
        // After an edit to the tree, the height of the current node is updated based on its
        // child nodes. This ensures the AVL tree remains height-balanced.
        currentNode.height = 1 + Math.max(height(currentNode.left), height(currentNode.right));

        // The balance factor of the current node is checked to determine
        // if re-balancing is needed. If an imbalance is detected, appropriate
        // rotations (single or double) are performed to restore balance.
        int balance = getBalance(currentNode);

        // Compare balance factor with desired state.

        // Left + Left (LL) Case - Single Right Rotation
        // Imbalance is from a node inserted into the LEFT subtree of the LEFT child
        if (balance > 1) {
            if (getBalance(currentNode.left) >= 0) {// Left + Left (LL) Case - Single Right Rotation
                // Imbalance is from a node inserted into the LEFT subtree of the LEFT child
                return rightRotate(currentNode);
            } else { // Left + Right (LR) Case - Double Rotation (Left-Right)
                // Imbalance is from a node inserted into the RIGHT subtree of the LEFT child
                currentNode.left = leftRotate(currentNode.left);
                return rightRotate(currentNode);
            }
        }

        // Right Heavy (Left Rotation Needed)
        if (balance < -1) {
            if (getBalance(currentNode.right) <= 0) {// Right + Right (RR) Case - Single Left Rotation
                return leftRotate(currentNode);
            } else { // Right + Left (RL) Case – Double Rotation (Right-Left)
                currentNode.right = rightRotate(currentNode.right);
                return leftRotate(currentNode);
            }
        }

        // If balanced, return unchanged
        return currentNode;
    }

    // Private method to get the smallest node in subtree
    private Node getMinValue(Node currentNode) {
        // Set temp variable for processing
        Node node = currentNode;
        while (node.left != null) { // Traverse left until we reach the leftmost node
            node = node.left;
        }
        return node; // Return the farthest left node
    }

    // Private InOrder view
    private void inorder(Node currentNode) {
        if (currentNode != null) {
            inorder(currentNode.left);
            System.out.print(currentNode.key + " ");
            inorder(currentNode.right);
        }
    }

    // Private PreOrder view
    private void preorder(Node currentNode) {
        if (currentNode != null) {
            System.out.print(currentNode.key + " ");
            preorder(currentNode.left);
            preorder(currentNode.right);
        }
    }

    // Private PostOrder view
    private void postorder(Node currentNode) {
        if (currentNode != null) {
            postorder(currentNode.left);
            postorder(currentNode.right);
            System.out.print(currentNode.key + " ");
        }
    }
}
// Define the Node class
class Node {
    // Create the class (blueprint/template) for a Node object.
    // Each Node instance (object) will have 2 instance variable "key" and "height" of type int.
    // Each Node instance (object) will have 2 instance variables "left" and "right"
    // "left" and "right" hold references to the instance's children Nodes.
    int key, height;
    Node left, right;

    // Define the constructor method for the Node class.
    public Node(int item) {
        // Initializes a new Node Object.
        // The "key" instance variable is set to the provided "item" int value.
        // The "height" instance variable is set to default height of 1.
        // The "left" and "right" instance variables are initialized to null.
        key = item;
        height = 1;
        left = right = null;
    }
}
