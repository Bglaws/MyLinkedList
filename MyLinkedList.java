import java.util.*;
import java.io.Serializable;

public class MyLinkedList<E> extends AbstractSequentialList<E> implements Deque<E>, Cloneable, Serializable {

    private static final long serialVersionUID = 1L;
    private int size;
    private DListNode<E> nil;

    // DListNode data
    private static class DListNode<E> {
        public E data;
        public DListNode<E> next;
        public DListNode<E> previous;
    }

    // DList Iterator class
    private class DListIterator implements ListIterator<E> {
        private DListNode<E> next;
        private DListNode<E> prev;
        private DListNode<E> lastElement;
        private int iterIndex;

        // Constructor 
        public DListIterator(DListNode<E> startNode, int index) {
            next = startNode;
            iterIndex = index;
        }

        @Override
        public boolean hasNext() {
            return (next != nil);
        }

        @Override
        public E next() {
            if (!hasNext()) {
                return null;
            }
            lastElement = next;
            next = next.next;
            iterIndex++;
            return (E) lastElement.data;
        }

        @Override
        public boolean hasPrevious() {
            return (prev != nil);
        }

        @Override
        public E previous() {
            if (!hasPrevious()) {
                return null;
            }
            lastElement = prev;
            prev = prev.previous;
            iterIndex--;
            return (E) lastElement.data;
        }

        @Override
        public int nextIndex() {
            return iterIndex;
        }

        @Override
        public int previousIndex() {
            return iterIndex - 1;
        }

        @Override
        public void remove() {
            if (lastElement == null) {
                throw new IllegalArgumentException("last element is null");
            }
            next = lastElement.next;
            prev = lastElement.previous;
            DList.this.remove(iterIndex);
            lastElement = null;
            iterIndex--;
        }

        @Override
        public void set(E e) {
            if (lastElement == null) {
                throw new IllegalStateException("Last element is null");
            }
            lastElement.data = e;
        }

        @Override
        public void add(E e) {
            DListNode<E> newNode = new DListNode<E>();
            newNode.data = e;
            newNode.next = next;
            newNode.previous = prev;
            next.previous = newNode;
            prev.next = newNode;
            prev = newNode;
            lastElement = null;
            size++;
            iterIndex++;
        }

    }


     // Descending DList Iterator class
     private class DescendingDListIterator implements ListIterator<E> {
        private DListNode<E> next;
        private DListNode<E> prev;
        private DListNode<E> lastElement;
        private int iterIndex;

        // Constructor
        public DescendingDListIterator(DListNode<E> startNode) {
            next = startNode;
            iterIndex = size() - 1;
        }

        @Override
        public boolean hasNext() {
            return (next != nil);
        }

        @Override
        public E next() {
            if (!hasNext()) {
                return null;
            }
            lastElement = next;
            next = next.previous;
            iterIndex--;
            return (E) lastElement.data;
        }

        @Override
        public boolean hasPrevious() {
            return (prev != nil);
        }

        @Override
        public E previous() {
            if (!hasPrevious()) {
                return null;
            }
            lastElement = prev;
            prev = prev.next;
            iterIndex++;
            return (E) lastElement.data;
        }

        @Override
        public int nextIndex() {
            return iterIndex;
        }

        @Override
        public int previousIndex() {
            return iterIndex + 1;
        }

        @Override
        public void remove() {
            if (lastElement == null) {
                throw new IllegalArgumentException("last element is null");
            }
            next = lastElement.next;
            prev = lastElement.previous;
            DList.this.remove(iterIndex);
            lastElement = null;
            iterIndex--;
        }

        @Override
        public void set(E e) {
            if (lastElement == null) {
                throw new IllegalStateException("Last element is null");
            }
            lastElement.data = e;
        }

        @Override
        public void add(E e) {
            DListNode<E> newNode = new DListNode<E>();
            newNode.data = e;
            newNode.next = next;
            newNode.previous = prev;
            next.previous = newNode;
            prev.next = newNode;
            prev = newNode;
            lastElement = null;
            size++;
            iterIndex++;
        }

    }

    // DList constructor
    public DList() {
        nil = new DListNode<E>();
        nil.previous = nil;
        nil.next = nil;
        nil.data = null;
    }

    public DList(Collection<? extends E> c) {
        this();
        addAll(c);
    }

    public E getFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Empty list");
        }
        return (E) nil.next.data;
    }

    public E getLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Empty list");
        }
        return (E) nil.previous.data;
    }

    public void addFirst(E elem) {
        DListNode<E> temp = nil.next;
        nil.next = new DListNode<E>();
        nil.next.previous = nil;
        nil.next.data = elem;
        nil.next.next = temp;
        temp.previous = nil.next;
        size++;
    }

    public void addLast(E elem) {
        DListNode<E> temp = nil.previous;
        nil.previous = new DListNode<E>();
        nil.previous.next = nil;
        nil.previous.data = elem;
        nil.previous.previous = temp;
        temp.next = nil.previous;
        size++;
    }

    public E removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Empty list");
        }
        DListNode<E> temp = nil.next;
        nil.next = temp.next;
        temp.next.previous = nil;
        size--;
        return (E) temp.data;
    }

    public E removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Empty list");
        }
        DListNode<E> temp = nil.previous;
        nil.previous = temp.previous;
        temp.previous.next = nil;
        size--;
        return (E) temp.data;
    }

    public E get(int index) {
        DListNode<E> temp;

        if (nil.next == nil) {
            throw new IndexOutOfBoundsException("aint no vitamins in that shit");
        } else {
            temp = nil.next;
        }

        while (index > 0) {
            temp = temp.next;
            if (temp == nil) {
                throw new IndexOutOfBoundsException("aint no vitamins in that shit");
            }
            index--;
        }
        return (E) temp.data;
    }

    // replaces and returns old value
    public E set(int index, E value) {
        DListNode<E> temp;
        if (nil.next == nil) {
            throw new IndexOutOfBoundsException();
        } else {
            temp = nil.next;
        }

        while (index > 0) {
            temp = temp.next;
            if (temp == nil) {
                throw new IndexOutOfBoundsException();
            }
            index--;
        }
        E o = (E) temp.data;
        temp.data = value;
        return o;
    }

    public boolean contains(Object obj) {
        DListNode<E> temp = nil.next;
        while (temp != nil) {
            if (temp.data == obj) {
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public int indexOf(Object obj) {
        int count = 0;
        DListNode<E> temp = nil.next;
        while (temp != nil) {
            if (temp.data.equals(obj)) {
                return count;
            } else {
                count++;
            }
        }
        return count = -1;
    }

    // Extra Credit

    @Override
    public boolean offerFirst(E e) {
        addFirst(e);
        return true;
    }

    @Override
    public boolean offerLast(E e) {
        addLast(e);
        return true;
    }

    @Override
    public E pollFirst() {
        return removeFirst();
    }

    @Override
    public E pollLast() {
        return removeLast();
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            return null;
        }
        return getFirst();
    }

    @Override
    public E peekFirst() {
        return peek();
    }

    @Override
    public E peekLast() {
        if (isEmpty()) {
            return null;
        }
        return getLast();
    }

    @Override
    public E remove() {
        return removeFirst();
    }

    @Override
    public boolean remove(Object o) {
        int target = indexOf(o);
        if (target >= 0) {
            remove(target);
            return true;
        }
        return false;
    }

    @Override
    public E remove(int index) {
        DListNode<E> temp = nil;
        while (index > 0) {
            temp = temp.next;
            if (temp == nil) {
                throw new IndexOutOfBoundsException();
            }
            index--;
        }

        temp.next = temp.previous;
        temp.previous = temp.next;
        size--;
        return (E) temp.data;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return remove(o);
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        DListNode<E> temp = nil.previous;
        int target = size - 1;
        while (temp != nil) {
            if (temp.data.equals(o)) {
                remove(target);
                return true;
            } else {
                target--;
                temp = temp.previous;
            }
        }
        return false;
    }

    @Override
    public boolean offer(E e) {
        addLast(e);
        return true;
    }

    @Override
    public E poll() {
        if (isEmpty()) {
            return null;
        }
        return removeFirst();
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public void push(E e) {
        addFirst(e);
    }

    @Override
    public E pop() {
        return removeFirst();
    }

    @Override
    public boolean isEmpty() {
        if (nil.next == nil) {
            return true;
        }
        return false;
    }

    @Override
    public boolean add(E e) {
        addLast(e);
        return true;
    }

    @Override
    public void add(int index, E element) {
        // needs to create new node
        // iterate to index
        DListNode<E> temp = nil;

        while (index > 0) {
            temp = temp.next;
            if (temp == nil) {
                throw new IndexOutOfBoundsException();
            }
            index--;
        }

        DListNode<E> newNode = new DListNode<E>();

        newNode.next = temp.next;
        temp.next.previous = newNode;
        newNode.previous = temp;
        temp.next = newNode;
        newNode.data = element;
        size++;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {

        c.forEach((E elem) -> {
            add(elem);
        });
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        DListNode<E> temp = nil;

        while (index - 1 > 0) {
            temp = temp.next;
            if (temp == nil) {
                throw new IndexOutOfBoundsException();
            }
            index--;
        }
        c.forEach((E elem) -> {
            add(elem);
        });
        return true;
    }

    @Override
    public void clear() {
        nil.next = nil;
        nil.previous = nil;
    }

    @Override
    public int lastIndexOf(Object o) {
        DListNode<E> temp = nil;

        for (int target = size() - 1; target > 0; target--) {
            temp = temp.previous;
            if (temp == nil) {
                return -1;
            }
            if (temp.data == o) {
                return target;
            }
        }
        return -1;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        int i = index;
        DListNode<E> temp = nil.next;
        while (i > 0) {
             temp = temp.next;
             i--;
        }
        return (ListIterator<E>) new DListIterator(temp, index);
    }

    @Override
    public ListIterator<E> descendingIterator() {
        DListNode<E> temp = nil. previous;
        if (temp == nil) {
            throw new NoSuchElementException("Empty list");
        }
        return (ListIterator<E>) new DescendingDListIterator(temp);
    }

    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size];
        DListNode<E> temp = nil.next;

        for (int i = 0; temp != nil.next; i++) {
            arr[i] = temp;
            temp = temp.next;
        }
        return arr;
    }

    @Override
    public <E> E[] toArray(E[] a) {
        
        if (a.length < size()) {
            a = (E[])java.lang.reflect.Array.newInstance
            (a.getClass().getComponentType(), size);
        }
        else if (size() < a.length) {
            a[size] = null;
        }
      
        DListNode temp = nil.next;
        for (int i = 0; temp != nil; i++) {
            
                a[i] = (E) temp.data;
                temp = temp.next;
        }
        return a;
    }
}
