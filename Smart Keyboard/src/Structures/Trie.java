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

    public Trie () {
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
        String s = this.getWord("", false);
        if (s != "") {
            System.out.println(s);
        }
        for (int i = 0; i < Trie.alphabet; i++) {
            if (this.children.get(i) != null){
                this.children.get(i).print();
            }
        }
    }

    public String getWord(String lastW, boolean between) {
        if (this.parent == null) {
            return lastW;
        }else if (this.isValid || between){
            return this.parent.getWord(this.val + lastW, true);
        }
        return "";
    }
}
