public class OffByOne implements CharacterComparator {
    public boolean equalChars(char x, char y) {
        int value = y - x;
        return value == 1 || value == -1;
    }
}
