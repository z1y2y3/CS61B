import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuffmanEncoder {
    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : inputSymbols) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        return map;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java HuffmanEncoder <symbol>");
            throw new IllegalArgumentException();
        }
        String inputFileName = args[0];
        String outputFileName = args[0] + ".huf";

        // 1: Read the file as 8 bit symbols.
        char[] inputSymbols = FileUtils.readFile(inputFileName);
        // 2: Build frequency table.
        Map<Character, Integer> frequencyTable = buildFrequencyTable(inputSymbols);
        // 3: Use frequency table to construct a binary decoding trie.
        BinaryTrie binaryDecodingTrie = new BinaryTrie(frequencyTable);
        // 4: Write the binary decoding trie to the .huf file.
        ObjectWriter ow = new ObjectWriter(outputFileName);
        ow.writeObject(binaryDecodingTrie);
        // 5: (optional: write the number of symbols to the .huf file)
        // 6: Use binary trie to create lookup table for encoding.
        Map<Character, BitSequence> lookupTable = binaryDecodingTrie.buildLookupTable();
        // 7: Create a list of bitsequences.
        List<BitSequence> bitsequences = new ArrayList<>();
        /*
           8: For each 8 bit symbol:
               Lookup that symbol in the lookup table.
               Add the appropriate bit sequence to the list of bitsequences.
        * */
        for (Character inputSymbol : inputSymbols) {
            bitsequences.add(lookupTable.get(inputSymbol));
        }
        // 9: Assemble all bit sequences into one huge bit sequence.
        BitSequence hugeBitSequence = BitSequence.assemble(bitsequences);
        // 10: Write the huge bit sequence to the .huf file.
        ow.writeObject(hugeBitSequence);
    }
}