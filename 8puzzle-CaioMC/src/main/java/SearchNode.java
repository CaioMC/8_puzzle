public class SearchNode implements Comparable<SearchNode> {

    Board board;
    int hamming;
    int moves = 0;
    SearchNode previousNode;
    int priority;

    public SearchNode(Board board, SearchNode previousNode) {
        if (board == null) {
            throw new IllegalArgumentException();
        }

        this.board = board;
        this.hamming = board.hamming();

        if (previousNode != null) {
            this.moves = previousNode.moves + 1;
            this.previousNode = previousNode;
        }

        this.priority = this.moves + this.hamming;
        board.setManhattan(this.priority);
    }

    @Override
    public int compareTo(SearchNode previous) {
        if (this.priority < previous.priority) {
            return -1;
        }

        if (this.priority > previous.priority) {
            return 1;
        }

        return 0;
    }

}
