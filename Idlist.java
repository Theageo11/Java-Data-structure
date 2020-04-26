package main;
import java.util.ArrayList;

public class Idlist<T> {

    private Node<T> head;
    private Node<T> tail;

    private int size = 0;

    private ArrayList<Node<T>> indices = new ArrayList<>();

    private class Node<T>{
        private T data;
        private Node<T> next = this;
        private Node<T> prev = this;

        Node(T data){
            this.data = data;
        }

        Node(T data,Node<T> prev,Node<T> next){
            this.data = data;
            this.next = next;
            this.prev = prev;
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }


    public Node getNode(int index){
        if(index<0||index>size){
            throw  new IndexOutOfBoundsException("the index out of bounds");
        }
        if(index<size/2){
            Node node = head;
            for (int i = 0; i <=index; i++) {
                node = node.next;
            }
            return node;
        }else {
            Node node = head;
            for (int i = size - 1; i >= index; i--) {
                node = node.prev;
            }
            return node;
        }
    }

    public boolean add(int index,T data){
        addBefore(new Node(data),getNode(index));
        return true;
    }

    public boolean add(T data){
        Node<T> node = new Node<T>(data);
        this.head = node;
        if(size == 0){
            this.tail = node;
        }
        this.indices.add(node);
        size++;
        return true;
    }

    public boolean append(T data){
        Node<T> node = new Node<T>(data);
        if(size == 0){
            this.tail = node;
            this.head = node;
            size++;
            this.indices.add(node);
            return true;
        }else{
            node.next = head;
            node.prev = head.prev;
            node.prev.next = node;
            node.next.prev = node;
            indices.add(node);
            this.tail = node;
            size++;
            return true;
        }
    }

    public T get(int index){
       return this.indices.get(index).data;
    }

    public Node<T> getHead() {
        return head;
    }


    public Node<T> getTail() {
        return tail;
    }

    public int getSize() {
        return size;
    }

    public T remove(){
        this.indices.remove(head);
        Node node  = head.next;
        removeNode(head);
        this.head = node ;
        return this.head.data;
    }

    public T removeLast(){
        this.indices.remove(tail);
        Node node = tail.prev;
        removeNode(tail);
        this.tail = node;
        return this.tail.data;
    }

    public T removeAt(int index){
        if(index == 0){
            remove();
        }
        if(index == size){
            removeLast();
        }
        Node node = this.indices.get(index);
        removeNode(node);
        this.indices.remove(index);
        return this.indices.get(index).data;
    }


    public boolean remove(T data){
        Integer index = null;
        for (int i = 0; i < size; i++) {
            if(this.indices.get(i).data == data){
                index = Integer.valueOf(i);
                break;
            }
        }
        if(index!=null){
            removeAt(index);
            return true;
        }else{
            return false;
        }
    }


    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < this.indices.size(); i++) {
            System.out.println(this.indices.get(i).data+","+this.indices.get(i).next.data+","+this.indices.get(i).prev.data);
        };
        return str;
    }

    private boolean addlast(Object value){
        addBefore(new Node(value),head);
        return true;
    }


    private void addAfter(Node newNode,Node node){
        newNode.next = node.next;
        newNode.prev = node;
        newNode.prev.next = newNode;
        newNode.next.prev = newNode;
        ++size;
    }

    private void addBefore(Node newNode,Node node){
        newNode.next = node;
        newNode.prev = node.prev;
        newNode.prev.next = newNode;
        newNode.next.prev = newNode;

        size++;
    }

    private void removeNode(Node node){
        if(size==0)
            throw new IndexOutOfBoundsException("LinkedList is Empty");
        node.prev.next = node.next;
        node.next.prev = node.prev;
        node.next = null;
        node.prev = null;
        --size;
    }

}

