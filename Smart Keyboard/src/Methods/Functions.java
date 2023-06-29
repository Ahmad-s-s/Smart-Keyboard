package Methods;

import Structures.Trie;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Functions {
    public static void addToTrie(Trie r, String word) {
        for (int i = 0; i < word.length(); i++) {
            int index = (int) word.charAt(i) - 'a';
            if (r.children.get(index) == null) {
                r.children.set(index, new Trie());
            }
            r.tChild += 1;
            r.children.get(word.charAt(i) - 'a').val = word.charAt(i);
            r.children.get(word.charAt(i) - 'a').parent = r;
            r = r.children.get(word.charAt(i) - 'a');
        }
        r.isValid = true;
    }

    public static Trie read() {
        ArrayList<String> words = new ArrayList<>();
        Trie root = new Trie();

        try {
            File allWord = new File("D:\\Uni\\semester 4\\Data Structure\\Project\\DS_project\\src\\words.txt");
            Scanner fScanner = new Scanner(allWord);
            while (fScanner.hasNext()) {
                words.add(fScanner.next().toLowerCase().replace("-", ""));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found !");
            return null;
        }
        if (!words.isEmpty()) {
            for (String string :
                    words) {
                addToTrie(root, string);
            }
        } else {
            System.out.println("empty list");
        }
        return root;
    }

    public static boolean spellCheck(Trie mainTrie, String textFiled) {
        Trie root = mainTrie;

        for (int level = 0; level < textFiled.length(); level++) {

            int index = textFiled.charAt(level) - 'a';

            if (root.children.get(index) == null){
                return false;
            }

            root = root.children.get(index);
        }

        return root.isValid;
    }
}
