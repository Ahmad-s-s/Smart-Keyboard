package Methods;

import Structures.StringFreq;
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
            File allWord = new File("D:\\semester 4\\DS\\FinalProject\\smart-keyboard\\Smart Keyboard\\src\\words.txt");
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

    public static Trie readRev() {
        ArrayList<String> words = new ArrayList<>();
        Trie root = new Trie();

        try {
            File allWord = new File("D:\\semester 4\\DS\\FinalProject\\smart-keyboard\\Smart Keyboard\\src\\words.txt");
            Scanner fScanner = new Scanner(allWord);
            while (fScanner.hasNext()) {
                words.add(fScanner.next().toLowerCase().replace("-", ""));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found !");
            return null;
        }
        if (!words.isEmpty()) {
            for (String string : words) {
                StringBuilder strReverse = new StringBuilder(string);
                strReverse.reverse();
                String reversed = strReverse.toString();
                addToTrie(root, reversed);
            }
        } else {
            System.out.println("empty list");
        }
        return root;
    }

    public static Trie spellCheck(Trie mainTrie, String textFiled) {
        // returns the word if it exists or mainRoot otherwise.
        Trie root = mainTrie;

        for (int level = 0; level < textFiled.length(); level++) {

            int index = textFiled.charAt(level) - 'a';

            if (root.children.get(index) == null){
                return mainTrie;
            }

            root = root.children.get(index);
        }

        return root;
    }

    public static Trie findRoot(Trie mainTrie, String textFiled) {
        // returns the last possible node of trie
        Trie root = mainTrie;

        for (int level = 0; level < textFiled.length(); level++) {

            int index = textFiled.charAt(level) - 'a';

            if (root.children.get(index) == null) {
                return root;
            }

            root = root.children.get(index);
        }

        return root;
    }

    public static Trie findRootNull(Trie mainTrie, String textFiled) {
        // returns the last root if it exists, null otherwise.
        Trie root = mainTrie;

        for (int level = 0; level < textFiled.length(); level++) {

            int index = textFiled.charAt(level) - 'a';

            if (root.children.get(index) == null) {
                return null;
            }

            root = root.children.get(index);
        }

        return root;
    }

    public static ArrayList<String> missSpell(Trie mainTrie, Trie reverseTrie, String textField) {
        Trie root = mainTrie;
        Trie reverseRoot = reverseTrie;
        String toCorrect = textField;
        StringBuilder reverser = new StringBuilder(toCorrect);
        reverser.reverse();
        String toCorrectReverse = reverser.toString();


        Trie toCorrectRoot = Functions.findRoot(root, toCorrect);
        Trie toCorrectReverseRoot = Functions.findRoot(reverseRoot, toCorrectReverse);

        StringFreq nl = new StringFreq();
        nl.word = "";
        nl.frequency = -1;

        StringFreq toCorrectText = toCorrectRoot.getWord(nl, true);
        StringFreq toCorrectTextReverse = toCorrectReverseRoot.getWord(nl, true);

        ArrayList<String> toCorrectResult = AutoComplete(toCorrectRoot, toCorrectText.word);
        ArrayList<String> toCorrectReverseResult = AutoComplete(toCorrectReverseRoot, toCorrectTextReverse.word);

        for (int i = 0; i < toCorrectReverseResult.size(); i++) {
            reverser = new StringBuilder(toCorrectReverseResult.get(i));
            reverser.reverse();
            toCorrectReverseResult.set(i, reverser.toString());
        }

        ArrayList<String> finalResult = new ArrayList<>();
        if (toCorrectText.word.length() > toCorrectTextReverse.word.length()) {

            // sort on prefix suggestions
            int lenPrefix = toCorrectText.word.length();
            for (int i = 0; i < toCorrectResult.size()-1; i++) {
                int index = i;
                int key = toCorrectResult.get(i).length() - lenPrefix;
                for (int j = i+1; j < toCorrectResult.size(); j++) {
                    if (toCorrectResult.get(j).length() - lenPrefix > key){
                        index = j;
                    }
                }
                String temp = toCorrectResult.get(index);
                toCorrectResult.set(index, toCorrectResult.get(i));
                toCorrectResult.set(i, temp);
            }

            //sort on prefix suggestions
            int lenSuffix = toCorrectText.word.length();
            for (int i = 0; i < toCorrectReverseResult.size()-1; i++) {
                int index = i;
                int key = toCorrectReverseResult.get(i).length() - lenSuffix;
                for (int j = i+1; j < toCorrectReverseResult.size(); j++) {
                    if (toCorrectReverseResult.get(j).length() - lenSuffix > key){
                        index = j;
                    }
                }
                String temp = toCorrectReverseResult.get(index);
                toCorrectReverseResult.set(index, toCorrectReverseResult.get(i));
                toCorrectReverseResult.set(i, temp);
            }
            finalResult.addAll(toCorrectResult);
            finalResult.addAll(toCorrectReverseResult);
        } else{
            // sort on suffix suggestions
            int lenSuffix = toCorrectText.word.length();
            for (int i = 0; i < toCorrectReverseResult.size()-1; i++) {
                int index = i;
                int key = toCorrectReverseResult.get(i).length() - lenSuffix;
                for (int j = i+1; j < toCorrectReverseResult.size(); j++) {
                    if (toCorrectReverseResult.get(j).length() - lenSuffix > key){
                        index = j;
                    }
                }
                String temp = toCorrectReverseResult.get(index);
                toCorrectReverseResult.set(index, toCorrectReverseResult.get(i));
                toCorrectReverseResult.set(i, temp);
            }

            // sort on prefix suggestions
            int lenPrefix = toCorrectText.word.length();
            for (int i = 0; i < toCorrectResult.size()-1; i++) {
                int index = i;
                int key = toCorrectResult.get(i).length() - lenPrefix;
                for (int j = i+1; j < toCorrectResult.size(); j++) {
                    if (toCorrectResult.get(j).length() - lenPrefix > key){
                        index = j;
                    }
                }
                String temp = toCorrectResult.get(index);
                toCorrectResult.set(index, toCorrectResult.get(i));
                toCorrectResult.set(i, temp);
            }
            finalResult.addAll(toCorrectReverseResult);
            finalResult.addAll(toCorrectResult);
        }


        if (finalResult.size() <= 5) {
            return finalResult;
        }else{
            ArrayList<String> f = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                f.add(finalResult.get(i));
            }
            return f;
        }
    }

    public static ArrayList<String> AutoComplete(Trie endNode,String textField){
        ArrayList<StringFreq> stringFreqs = endNode.getAllChildren();

        ArrayList<String> result = new ArrayList<>();
        int maxAdd = 5;

        if (endNode.isValid){
            StringFreq nl= new StringFreq();
            nl.word = "";
            nl.frequency = -1;
            StringFreq selfWord = endNode.getWord(nl,false);
            result.add(selfWord.word);
            maxAdd = maxAdd - 1;
        }
        if (stringFreqs.size() < maxAdd)
            maxAdd = stringFreqs.size();

        for (int i = 0; i < maxAdd ; i++){
            int minFreq = 0;
            StringFreq max = new StringFreq();
            max.frequency = -1;
            for (int j = stringFreqs.size()-1; j > -1  ; j--) {
                if (stringFreqs.get(j).frequency >= minFreq && !result.contains(stringFreqs.get(j).word)){
                    max = stringFreqs.get(j);
                    minFreq = stringFreqs.get(j).frequency;
                }
            }
            if (max.frequency != -1)
                result.add(max.word);
        }
        return result;
    }
}