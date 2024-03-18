public class OffByN implements CharacterComparator {
    private static int N;

    public OffByN(int N) {
        this.N = N;
    }

    public boolean equalChars(char x, char y) {
        int value = y - x;
        if (value == this.N || value == this.N) {
            return true;
        } else {
            return false;
        }
    }
}
