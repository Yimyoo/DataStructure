public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        // Deque<Character> palind = new Deque<Character>(); // Deque is an interface, it cannot be instantiated.
        Deque<Character> palind = new LinkedListDeque<Character>();
        String words = word; // Copy the word to words
        int i = 0;
        while (i < words.length()) { // It is not right to compare i index of words to 0 character.
            palind.addLast(words.charAt(i));
            i++;
        }
        return palind;
    }

    public boolean isPalindrome(String word) {
        return  substringIsPalindrome(word, 0, word.length() - 1);
    }

    public boolean substringIsPalindrome(String s, int start, int end) {
        if (start >= end)   return true;
        if (s.charAt(start) == s.charAt(end))   return substringIsPalindrome(s, start + 1, end -1);
        else    return false;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        int start = 0;
        int end = word.length() - 1;
        while (end > start) {
            if (!cc.equalChars(word.charAt(start++), word.charAt(end--)))   return false;
        }
        return  true;
    }
}
