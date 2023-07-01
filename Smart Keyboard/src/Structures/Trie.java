package Structures;

import java.util.ArrayList;
import java.util.Objects;


public class Trie {

    final static public int alphabet = 26;

    public ArrayList<Trie> children;
    public Trie parent;
    public boolean isValid;
    public int frequency;
    public int tChild;

    public char val;

    public Trie() {
        this.children = new ArrayList<>();
        for (int i = 0; i < Trie.alphabet; i++) {
            this.children.add(null);
        }
        this.isValid = false;
        this.frequency = 0;
        this.tChild = 0;
        this.parent = null;
    }

    public void print() {
        StringFreq nl = new StringFreq();
        nl.word = "";
        nl.frequency = -1;
        StringFreq s = this.getWord(nl, false);
        if (s != null) {
            System.out.println(s.word + "  " + s.frequency);
        }
        for (int i = 0; i < Trie.alphabet; i++) {
            if (this.children.get(i) != null) {
                this.children.get(i).print();
            }
        }
    }

    public StringFreq getWord(StringFreq lastW, boolean between) {
        if (this.parent == null) {
            StringFreq res = new StringFreq();
            res.word = lastW.word;
            res.frequency = lastW.frequency;
            return lastW;
        } else if (between) {
            StringFreq res = new StringFreq();
            res.word = this.val + lastW.word;
            res.frequency = lastW.frequency;
            return this.parent.getWord(res, true);
        } else if (this.isValid) {
            StringFreq res = new StringFreq();
            res.word = this.val + lastW.word;
            res.frequency = this.frequency;
            return this.parent.getWord(res, true);
        }
        return null;
    }

    public ArrayList<StringFreq> getAllChildren() {
        ArrayList<StringFreq> allWords = new ArrayList<>();
        StringFreq nl = new StringFreq();
        nl.word = "";
        nl.frequency = -1;
        StringFreq wordUpToHere = this.getWord(nl, false);
        if (wordUpToHere != null)
            allWords.add(wordUpToHere);

        ArrayList<StringFreq> allChildren = new ArrayList<>();
        for (int i = 0; i < Trie.alphabet; i++) {
            if (this.children.get(i) != null) {
                allChildren = this.children.get(i).getAllChildren();
                for (StringFreq strfr : allChildren) {
                    allWords.add(strfr);
                }
            }
        }
        return allWords;
    }

    ;
}











