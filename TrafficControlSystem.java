import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TrafficControlSystem {

    public static boolean isSafeTraffic(int col, int row, Set<Integer> cols, Set<Integer> diag1, Set<Integer> diag2) {
        // Check if placing a signal at (row, col) is safe
        return !cols.contains(col) && !diag1.contains(row - col) && !diag2.contains(row + col);
    }

    public static void setTrafficSignals(int row, int n, Set<Integer> cols, Set<Integer> diag1, Set<Integer> diag2,
                                         int[] grid, List<int[]> solutions) {
        // Recursively try placing traffic signals row by row
        if (row == n) {
            solutions.add(grid.clone()); // Add a valid solution
            return;
        }

        for (int col = 0; col < n; col++) {
            if (isSafeTraffic(col, row, cols, diag1, diag2)) {
                grid[row] = col; // Place the signal at (row, col)
                cols.add(col);
                diag1.add(row - col);
                diag2.add(row + col);

                // Recurse to the next row
                setTrafficSignals(row + 1, n, cols, diag1, diag2, grid, solutions);

                // Backtrack: Remove the signal and update the sets
                grid[row] = -1;
                cols.remove(col);
                diag1.remove(row - col);
                diag2.remove(row + col);
            }
        }
    }

    public static List<int[]> trafficControlSystem(int n) {
        // Solve the traffic signal placement problem using backtracking
        int[] grid = new int[n]; // Initialize the grid with -1
        for (int i = 0; i < n; i++) {
            grid[i] = -1;
        }

        List<int[]> solutions = new ArrayList<>();
        Set<Integer> cols = new HashSet<>();
        Set<Integer> diag1 = new HashSet<>();
        Set<Integer> diag2 = new HashSet<>();

        setTrafficSignals(0, n, cols, diag1, diag2, grid, solutions);
        return solutions;
    }

    public static void printSolutions(List<int[]> solutions, int n, String visualizationType) {
        // Prints each solution in a clear and formatted way
        System.out.println("\nTotal setups found: " + solutions.size() + "\n");

        for (int idx = 0; idx < solutions.size(); idx++) {
            System.out.println("=".repeat(2 * n + 3));
            System.out.printf(" Setup %d ", idx + 1);
            System.out.println("=".repeat(2 * n + 3));

            int[] setup = solutions.get(idx);

            if (visualizationType.equals("text")) {
                // Column headers
                System.out.print("   ");
                for (int col = 0; col < n; col++) {
                    System.out.printf("%2d ", col);
                }
                System.out.println();

                System.out.print("  ");
                for (int col = 0; col < n; col++) {
                    System.out.print("--");
                }
                System.out.println("-");

                // Display grid with 'S' for signals and '.' for empty spaces
                for (int row = 0; row < n; row++) {
                    System.out.printf("%2d | ", row);
                    for (int col = 0; col < n; col++) {
                        System.out.print((setup[row] == col ? "S" : ".") + " ");
                    }
                    System.out.println();
                }

                System.out.print("  ");
                for (int col = 0; col < n; col++) {
                    System.out.print("--");
                }
                System.out.println("-");

            } else if (visualizationType.equals("summary")) {
                System.out.print("Row positions of signals: ");
                for (int position : setup) {
                    System.out.print(position + " ");
                }
                System.out.println();
            } else {
                System.out.println("Invalid visualization type. Please choose 'text' or 'summary'.");
            }

            System.out.println(); // Blank line after each setup
        }
    }

    public static void main(String[] args) {
        try {
            java.util.Scanner scanner = new java.util.Scanner(System.in);
            System.out.print("Enter the grid size for traffic control system (n x n): ");
            int n = scanner.nextInt();

            if (n <= 0) {
                System.out.println("The grid size must be a positive integer.");
            } else {
                List<int[]> setups = trafficControlSystem(n);
                printSolutions(setups, n, "text");
            }

        } catch (java.util.InputMismatchException e) {
            System.out.println("Please enter a valid integer for the grid size.");
        }
    }
}
