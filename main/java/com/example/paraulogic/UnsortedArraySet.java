package com.example.paraulogic;

import java.util.Iterator;

public class UnsortedArraySet<E> {

    private E[] array;
    private int n;

    public UnsortedArraySet(int max){

        this.array = (E[]) new Object[max];
        this.n = 0;
    }

    public Iterator iterator(){
        Iterator it = new IteratorUnsortedArraySet();
        return it;
    }

    public boolean contains(E elem) {

        for (int i = 0; i < n; i++) {
            if (array[i].equals(elem)) {
                return true;
            }
        }
        return false;

    }

    public boolean add(E elem) {

        if ((n < array.length) && !(contains(elem))) {
            array[n] = elem;
            n++;
            return true;
        } else {
            return false;
        }
    }

    public boolean remove(E elem) {

        int i = 0;
        boolean trobat = false;
        while (!trobat && (i < n)) {
            trobat = elem.equals(array[i]);
            i++;
        }
        if (trobat) {
            array[i - 1] = array[n - 1];
            n--;
        }
        return trobat;

    }

    public boolean isEmpty() {
        return n == 0;
    }

    private class IteratorUnsortedArraySet implements Iterator{
        private int idxIterator;
        private IteratorUnsortedArraySet() {
            idxIterator = 0;
        }
        @Override
        public boolean hasNext() {
            return idxIterator < n;
        }
        @Override
        public Object next() {
            idxIterator++;
            return array[idxIterator-1];
        }

    }
}