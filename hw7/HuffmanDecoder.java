import java.util.ArrayList;
import java.util.List;

public class HuffmanDecoder {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java HuffmanDecoder <input file> <output file>");
            throw new IllegalArgumentException();
        }
        String inputFileName = args[0];
        String outputFileName = args[1];
        // 1: Read the Huffman coding trie.
        ObjectReader or = new ObjectReader(inputFileName);
        BinaryTrie binaryDecodingTrie = (BinaryTrie) or.readObject();
        // 2: If applicable, read the number of symbols.
        Integer numsOfSymbols = (Integer) or.readObject();
        // 3: Read the massive bit sequence corresponding to the original txt.
        BitSequence hugeBitSequence = (BitSequence) or.readObject();
        /*
           4: Repeat until there are no more symbols:
               4a: Perform a longest prefix match on the massive sequence.
               4b: Record the symbol in some data structure.
               4c: Create a new bit sequence containing the remaining unmatched bits.
        * */
        List<Character> symbols = new ArrayList<Character>();
        while (hugeBitSequence.length() != 0) {
            Match match = binaryDecodingTrie.longestPrefixMatch(hugeBitSequence);
            BitSequence sequence = match.getSequence();
            int sequenceLength = sequence.length();
            Character symbol = match.getSymbol();

            symbols.add(symbol);
            hugeBitSequence = hugeBitSequence.allButFirstNBits(sequenceLength);
        }
        // 5: Write the symbols in some data structure to the specified file.
        char[] outputSymbols = new char[symbols.size()];
        int index = 0;
        for (Character symbol : symbols) {
            outputSymbols[index] = symbol;
            index++;
        }
        FileUtils.writeCharArray(outputFileName, outputSymbols);
    }
}
