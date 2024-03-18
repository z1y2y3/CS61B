import org.junit.Test;

import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        assertTrue(palindrome.isPalindrome(" "));
        assertTrue(palindrome.isPalindrome("a"));
        assertFalse(palindrome.isPalindrome("cat"));
        assertFalse(palindrome.isPalindrome("black"));
        assertFalse(palindrome.isPalindrome("aaaaab"));
        assertTrue(palindrome.isPalindrome("racecar"));
        assertTrue(palindrome.isPalindrome("noon"));
        assertTrue(palindrome.isPalindrome("ABBA"));
        assertTrue(palindrome.isPalindrome("osABBAso"));
    }


    @Test
    public void testTIsPalindromeOffByOne() {
        CharacterComparator cc = new OffByOne();
        assertFalse(palindrome.isPalindrome("aba", cc));
        assertFalse(palindrome.isPalindrome("black", cc));

        assertTrue(palindrome.isPalindrome(" ", cc));
        assertTrue(palindrome.isPalindrome("a", cc));
        assertTrue(palindrome.isPalindrome("%&", cc));
        assertTrue(palindrome.isPalindrome("flake", cc));
        assertTrue(palindrome.isPalindrome("aflakeb", cc));
        assertTrue(palindrome.isPalindrome("raflakebq", cc));
    }

}
