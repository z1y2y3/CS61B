public class OffByOne implements CharacterComparator {
    public boolean equalChars(char x, char y) {
        int value = y - x;
        if (value == 1 || value == -1) {
            return true;
        }
        else{
            return false;
        }
    }
}
