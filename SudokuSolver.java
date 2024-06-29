import java.util.Scanner;

public class SudokuSolver {

    public static void main(String[] args) {
        int[][] grid = new int[9][9];
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the Sudoku puzzle to solve (0 for empty cells):");
        for (int row = 0; row < 9; row++) {
            System.out.printf("Enter values for row %d (9 numbers separated by space): ", row + 1);
            grid[row] = getInputRow(scanner);
        }

        if (solveSudoku(grid)) {
            System.out.println("Sudoku puzzle solved successfully!");
            printGrid(grid);
        } else {
            System.out.println("No solution exists for the given Sudoku puzzle.");
        }
        scanner.close();
    }

    private static int[] getInputRow(Scanner scanner) {
        while (true) {
            String[] input = scanner.nextLine().split(" ");
            if (input.length == 9) {
                int[] row = new int[9];
                boolean valid = true;
                for (int i = 0; i < 9; i++) {
                    try {
                        int value = Integer.parseInt(input[i]);
                        if (value < 0 || value > 9) {
                            valid = false;
                            break;
                        }
                        row[i] = value;
                    } catch (NumberFormatException e) {
                        valid = false;
                        break;
                    }
                }
                if (valid) {
                    return row;
                }
            }
            System.out.println("Invalid input. Please enter 9 numbers separated by space, each between 0 and 9.");
        }
    }

    private static void printGrid(int[][] grid) {
        for (int r = 0; r < 9; r++) {
            for (int d = 0; d < 9; d++) {
                System.out.print(grid[r][d] + " ");
            }
            System.out.println();
        }
    }

    private static boolean isValid(int[][] grid, int row, int col, int num) {
        for (int x = 0; x < 9; x++) {
            if (grid[row][x] == num || grid[x][col] == num) {
                return false;
            }
        }

        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[i + startRow][j + startCol] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean solveSudoku(int[][] grid) {
        int[] emptyCell = findEmptyLocation(grid);
        if (emptyCell == null) {
            return true; // Puzzle solved
        }

        int row = emptyCell[0];
        int col = emptyCell[1];

        for (int num = 1; num <= 9; num++) {
            if (isValid(grid, row, col, num)) {
                grid[row][col] = num;
                if (solveSudoku(grid)) {
                    return true;
                }
                grid[row][col] = 0; // Backtrack
            }
        }
        return false;
    }

    private static int[] findEmptyLocation(int[][] grid) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (grid[row][col] == 0) {
                    return new int[]{row, col};
                }
            }
        }
        return null;
    }
}
