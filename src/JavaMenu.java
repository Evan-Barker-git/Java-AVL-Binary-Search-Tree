import java.util.Scanner;

public class JavaMenu {

    private Scanner scanner;
    private BinarySearchTree tree;

    public JavaMenu() {
        this.scanner = new Scanner(System.in);
        this.tree = new BinarySearchTree();
    }

    public void run() {
        int choice = 0;

        // Loop until user requests an exit (Choice 7)
        while (choice != 7) {
            // Call displayMenu method and display options
            displayMenu();

            // Prompt for and validate input
            System.out.println("Please select a choice: ");
            choice = getValidInteger(scanner);
            processChoice(choice); // Call processChoice method to start processing data.
        }

        System.out.println("Choice 7 submitted. Exiting menu...");
    }

    private static void displayMenu() {
        System.out.println("/ ---------------- Menu ---------------- /");
        System.out.println("/ 1. Create a binary search tree         /");
        System.out.println("/ 2. Add a node                          /");
        System.out.println("/ 3. Delete a node                       /");
        System.out.println("/ 4. Print nodes by InOrder              /");
        System.out.println("/ 5. Print nodes by PreOrder             /");
        System.out.println("/ 6. Print nodes by PostOrder            /");
        System.out.println("/ 7. Exit                                /");
        System.out.println("/ -------------------------------------- /");
    }

    private static int getValidInteger(Scanner scanner) {
        int choice;

        // Keep looping until valid input is received
        while (true) {
            String input = scanner.nextLine().trim();

            // Validate input is an int
            try {
                choice = Integer.parseInt(input);
                return choice; // Valid int, return value.
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer.");
            }
        }
    }

    private void processChoice(int choiceID) {
        switch(choiceID) {
            case 1:
                System.out.println("Option 1...");
                System.out.println("Creating new tree!");
                int[] defaultTree = {1, 2, 3, 4, 5, 6, 7};
                for (int i : defaultTree){
                    tree.addNode(i);
                }
                break;

            case 2:
                System.out.println("Option 2...");
                System.out.println("Adding a node!");
                System.out.println("Please enter the integer value you would like to add");
                int addKey = getValidInteger(scanner);
                tree.addNode(addKey);
                System.out.println("Added " + addKey + " to the tree.");
                break;

            case 3:
                System.out.println("Option 3...");
                System.out.println("Deleting a node!");
                System.out.println("Please enter the integer value you would like to delete");
                int deleteKey = getValidInteger(scanner);
                tree.deleteNode(deleteKey);
                System.out.println("Deleted "+ deleteKey + " from the tree.");
                break;

            case 4:
                System.out.println("Option 4...");
                System.out.println("Viewing the tree InOrder!");
                tree.viewInOrder();
                System.out.println("\n That's all folks!");
                break;

            case 5:
                System.out.println("Option 5...");
                System.out.println("Viewing the tree PreOrder!");
                tree.viewPreOrder();
                System.out.println("\n That's all folks!");
                break;

            case 6:
                System.out.println("Option 6...");
                System.out.println("Viewing the tree PostOrder!");
                tree.viewPostOrder();
                System.out.println("\n That's all folks!");
                break;

        }
    }
}
