public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        LinkedListDeque<Character> deque = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            char x = word.charAt(i);
            deque.addLast(x);
        }
        return deque;
    }

    public boolean isPalindrome(String word) {
        if (word == null || word.equals(" ") || word.length() == 1) {
            return true;
        }
        Deque<Character> deque = wordToDeque(word);
        for (int i = 0; i < word.length() / 2; i++) {
            char front = deque.removeFirst();
            char back = deque.removeLast();
            if (front != back) {
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word == null || word.equals(" ") || word.length() == 1) {
            return true;
        }
        Deque<Character> deque = wordToDeque(word);
        for (int i = 0; i < word.length() / 2; i++) {
            char front = deque.removeFirst();
            char back = deque.removeLast();
            if (!cc.equalChars(front,back)) {
                return false;
            }
        }
        return true;
    }
}
