import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Solver {
    private static final String PUZZLE_PATH = "src/resources/8puzzle";
    private SearchNode searchNodeCurrent;
    private boolean solvable = false;
    private List<Board> sequenceBoard = new ArrayList<>();


    public Solver(Board initialBoard) {

        // encontra a solução para o tabuleiro inicial (usando o algoritmo A*)
        Queue<SearchNode> queue = new PriorityQueue<>();

        searchNodeCurrent = new SearchNode(initialBoard, null);
        queue.add(searchNodeCurrent);

        while (!searchNodeCurrent.board.isGoal()) {
            searchNodeCurrent = queue.poll();

            for (Board board : searchNodeCurrent.board.neig()) {
                if (searchNodeCurrent.previousNode == null || !board.equals(searchNodeCurrent.previousNode.board)) {
                    queue.add(new SearchNode(board, searchNodeCurrent));
                }
            }

            if (searchNodeCurrent.board.isGoal()) {
                solvable = true;
            }
        }
    }

    public int moves() {
        // min número de movimentos para resolver o tabuleiro inicial
        return searchNodeCurrent.moves;
    }

    public Iterable<Board> solution() {
        // sequência de tabuleiros na solução mais curta
        SearchNode temp = new SearchNode(searchNodeCurrent.board, searchNodeCurrent.previousNode);

        while (temp != null) {
            sequenceBoard.add(temp.board);
            temp = temp.previousNode;
        }

        Collections.reverse(sequenceBoard);
        return sequenceBoard;
    }

    private static int[][] readTextFile() throws FileNotFoundException {
        Scanner in = new Scanner(new File(PUZZLE_PATH));
        int n = in.nextInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.nextInt();
            }
        }

        return tiles;
    }

    public static void main(String[] args) {
        try {
            int[][] tiles = readTextFile();
            Board initial = new Board(tiles);

            Solver solver = new Solver(initial);
            System.out.println("Mínimo número de movimentos = " + solver.moves());
            for (Board board : solver.solution()) {
                System.out.println(board);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}