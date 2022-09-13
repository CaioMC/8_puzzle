import java.util.ArrayList;
import java.util.List;

public class Board {
    private final char[] board;
    private int size;
    private int indexRow;
    private int indexColumn;
    private List<Board> neighbour = new ArrayList<>();
    public Board(int[][] tiles){
        // constrói um tabuleiro a partir de um array de quadros N-por-N
        // (onde tiles[i][j] = quadro na linha i, coluna j)

        size = tiles[0].length;
        board = new char[size * size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[size * i + j] = (char) tiles[i][j];
                if (board[size * i + j] == 0) {
                    indexRow = i;
                    indexColumn = j;
                }
            }
        }
    }

    public boolean bound(int i, int j) {
        // retorna o quadro na linha i, coluna j (ou 0 se vazio)
        return i < size && i >= 0 && j < size && j >= 0;
    }

    public int size() {
        // o tamanho N do tabuleiro
        return size;
    }

    public int hamming() {
        // número de quadros fora do lugar

        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                int value = board[size * i + j];
                int correctPositionValue = size * i + j + 1;

                if (value != 0) {
                    if (value != correctPositionValue) {
                        count++;
                    }
                }

            }
        }

        return count;
    }

    public int manhattan() {
        // soma das distâncias de Manhattan entre os quadros e o objetivo final
        return 0;
    }

    public boolean isGoal() {
        // este tabuleiro é o tabuleiro objetivo?
        return hamming() == 0;
    }

    public boolean isSolvable() {
        // este tabuleiro é resolvível?
        return false;
    }

    public boolean equals(Object y) {
        // este tabuleiro é igual ao tabuleiro y?

        if (y == this) {
            return true;
        }

        if (y == null) {
            return false;
        }

        Board previous = (Board) y;

        if (previous.size == this.size) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    int value = size * i + j;
                    if (this.board[value] != previous.board[value]) {
                        return false;
                    }
                }
            }

            return true;
        }

        return false;
    }

    private Board trade(int indexRow, int indexColumn, int row, int column) {
        int[][] tempboard = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tempboard[i][j] = board[size * i + j];
            }
        }

        int temp = tempboard[indexRow][indexColumn];
        tempboard[indexRow][indexColumn] = tempboard[row][column];
        tempboard[row][column] = temp;
        Board result = new Board(tempboard);

        return result;
    }

    public Iterable<Board> neighbors() {
        // todos os tabuleiros vizinhos

        if (neighbour.isEmpty()) {
            if (bound(indexRow + 1, indexColumn)) { // DOWN
                neighbour.add(trade(indexRow, indexColumn, indexRow + 1, indexColumn));
            }

            if (bound(indexRow, indexColumn + 1)) { // RIGTH
                neighbour.add(trade(indexRow, indexColumn + 1, indexRow, indexColumn));
            }

            if (bound(indexRow, indexColumn - 1)) { // LEFT
                neighbour.add(trade(indexRow, indexColumn - 1, indexRow, indexColumn));
            }

            if (bound(indexRow - 1, indexColumn)) { // UP
                neighbour.add(trade(indexRow - 1, indexColumn, indexRow, indexColumn));
            }

            return neighbour;
        }

        return null;
    }

    public String toString() {
        // representação em string deste tabuleiro (no formato especificado abaixo)
        String string = size + "\n";

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                string += ((int)board[size * i + j]) + " ";
            }

            string += "\n";
        }

        return string;
    }
}