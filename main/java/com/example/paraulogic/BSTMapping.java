package com.example.paraulogic;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.Iterator;
import java.util.Stack;

/**
 *
 * @author Diego
 */
public class BSTMapping<K extends Comparable, V> {

    private Node root;

    public BSTMapping() {
        root = null;
    }
    public Iterator IteratorBSTMapping(){
        return new IteratorBSTMapping();
    }

    public V put(K key, V value){
        V valor=get(key); //obtenir valor anterior/null
        this.root = put(key, value, root);
        return valor;
    }

    private Node put(K key, V value, Node current) {
        if (current == null) {
            return new Node(key, value, null, null);
        } else {
            if (current.key.equals(key)) {
                current.value = value;
                return current;
            }
            if (key.compareTo(current.key) < 0) {
                current.left = put(key, value, current.left);
            } else {
                current.right = put(key, value, current.right);
            }
            return current;
        }
    }

    public V get(K key) {
        return get(key, root);
    }

    private V get(K key, Node current) {
        if (current == null) {
            return null;
        } else {
            if (current.key.equals(key)) {
                return current.value;
            }
            if (key.compareTo(current.key) < 0) {
                return get(key, current.left);
            } else {
                return get(key, current.right);
            }
        }
    }

    public V remove(K key) {
        this.root = remove(key, root);
        return root.value;
    }

    private Node remove(K key, Node current) {
        if (current == null) { // Element no trobat
            return null;
        }
        if (current.key.equals(key)) { // Element trobat
            // Eliminar node
            if (current.right == null && current.left == null) {
                return null;
            } else {
                if (!(current.left == null && current.right == null)) { //1 hijo
                    if (current.left == null) {
                        return current.right;
                    } else {
                        return current.left;
                    }
                } else { //2 hijos
                    // Node mÃ©s esquerra del fill dret
                    Node plowest = current.right;
                    Node parent = current;
                    while (plowest.left != null) {
                        parent = plowest;
                        plowest = plowest.left;
                    }
                    plowest.left = current.left;
                    if (plowest != current.right) {
                        parent.left = plowest.right;
                        plowest.right = current.right;
                    }
                    return plowest;
                }
            }
        }
        if (key.compareTo(current.key) < 0) { // Subarbre esquerra
            current.left = remove(key, current.left);
        } else {// Subarbre dret
            current.right = remove(key, current.right);
        }
        return current;
    }

    public boolean isEmpty() {
        return root == null;
    }

    //Classes Privades
    private class Node {

        public Node(K key, V value, Node left, Node right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }

        private K key;
        private V value;
        private Node left, right;
    }

    protected class Pair {

        private K key;
        private V value;

        private Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

    }

    private class IteratorBSTMapping implements Iterator {

        private Stack<Node> iterator;
        // Quin és el primer node a visitar

        public IteratorBSTMapping() {
            Node p;
            iterator = new Stack();
            if (root != null) {
                p = root;
                while (p.left != null) {
                    iterator.push(p);
                    p = p.left;
                }
                iterator.push(p);
            }
        }
// Tenim més nodes per visitar?

        @Override
        public boolean hasNext() {
            return !iterator.isEmpty();
        }
// Quin és el següent node a visitar

        @Override
        public Object next() {
            Node p = iterator.pop();
            Pair pair = new Pair(p.key, p.value);
            if (p.right != null) {
                p = p.right;
                while (p.left != null) {
                    iterator.push(p);
                    p = p.left;
                }
                iterator.push(p);
            }
            return pair;
        }
    }

}
