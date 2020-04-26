import java.util.Random;
import java.util.Stack;

public class Treap<E extends Comparable<E>> {
	public class Node<F>{
		public F data;
		public int priority;
		public Node<F> left;
		public Node<F> right;
		
		/**
		 * 
		 * @param data Data of node
		 * @param priority Priority of node
		 */
		public Node(F data, int priority) {
			if (data == null) {
				throw new IllegalArgumentException();
			} else {
				this.data = data;
				this.priority = priority;
				this.left = null;
				this.right = null;
			}
		}
		
		/**
		 * performs a right tree rotation on a node
		 * @return reference to new root
		 */
		public Node<F> rotateRight() {
            Node<F> L = this.left;
            Node<F> R = L.right;
            L.right = this;
            this.left = R;
            return L;
        }

        /**
         * performs a left tree rotation on a node
         * @return reference to new root
         */
        public Node<F> rotateLeft() {
            Node<F> L = this.right;
            Node<F> R = L.left;
            L.left = this;
            this.right = R;
            return L;
        }
 
	}

	private Random priorityGenerator;
	private Node<E> root;

	/**
	 * Constructor for Treap
	 */
	public Treap() {
		priorityGenerator = new Random();
		root = null;
	}

	/**
	 * Constructor for Treap with a set seed for the random number generator
	 */
	public Treap(long seed) {
		priorityGenerator = new Random(seed);
		root = null;
	}
	
	
	/**
	 * Private method called by delete and add functions
	 * @param curr Reference to current node
	 * @param path Reference to path of nodes in a stack
	 */
	private void reheap(Node<E> curr, Stack<Node<E>> path)  {
        while (!path.isEmpty()) {
            Node<E> parent = path.pop();
            if (parent.priority < curr.priority){
                if (parent.data.compareTo(curr.data) > 0) {
                    curr = parent.rotateRight();
                } else {
                    curr = parent.rotateLeft();
                }
                if (!path.isEmpty()) {
                    if (path.peek().left == parent) {
                        path.peek().left = curr;
                    } else {
                        path.peek().right = curr;
                    }
                } else { 
                    this.root = curr;
                }
            } else {
                break;
            }
        }
    }

	/**
	 * helper function for the public add function
	 * @param key Argument for the key of the node that will be added 
	 * @param priority Randomly generated heap priority of new node being added
	 * @return boolean value for successful addition
	 */
	public boolean add(E key, int priority) {
		if (root == null) {
			root = new Node<E>(key, priority);
			return true;
		} else {
			Stack<Node<E>> stackTemp = new Stack<Node<E>>();
			Node<E> current = root;
			while (current != null) {
				int comparison = key.compareTo(current.data);
				if (comparison == 0) {
					return false;
				} else {
					if (comparison > 0) {
						stackTemp.push(current);
						if (current.right == null) {
							current.right = new Node<E> (key, priority);
							reheap(current.right, stackTemp);
							return true;
						} else {
							current = current.right;
						}
					} else {
						stackTemp.push(current);
						if (current.left == null) {
							current.left = new Node<E> (key,priority);
							reheap(current.left, stackTemp);
							return true;
						} else {
							current = current.left;
						}
					}
				}
			}
			return false;
		}
	}

	/**
	 * Public add function that calls private helper function
	 * @param key Key of the node being added
	 * @return boolean for successful addition
	 */
	public boolean add(E key) {
		return add(key, priorityGenerator.nextInt());
	}
	
	/**
	 * Private helper function that is called by public delete function
	 * @param current Argument that represents the current node being checked
	 * @param key  Value you are searching for to delete
	 * @return reference to new value of root
	 */
	private Node<E> delete(Node<E> current, E key){
		if (current == null) {
			return current;
		} else {
			int comp = key.compareTo(current.data);
			if (comp < 0) {
				current.left = delete(current.left, key);
			} else if (comp > 0) {
				current.right = delete(current.right, key);
			} else {
				if (current.left == null) {
					Node<E> temp = current.right;
					current = temp;
				} else if (current.right == null) {
					Node<E> temp = current.left;
					current = temp;
				} else if (current.left.priority < current.right.priority) {
					current = current.rotateLeft();
					current.left = delete(current.left, key);
				} else {
					current = current.rotateRight();
					current.right = delete(current.right, key);
				}
			}
		}
		return current;
	}
		
    /**
     * calls the private helper function, and returns true if delete is completed, false if not possible
     * @param key key to delete from Treap
     * @return true if successful
     */
    public boolean delete(E key){
        if(!find(key)){
            return false;
        }
        this.root = delete(this.root, key);
        return true;
    }

    /**
     * Private helper function to the public find function
     * @param n Reference to the root node of the Treap
     * @param key Reference to the key being searched for inside the Treap
     * @return boolean value for found or not
     */
	private boolean find(Node<E> n, E key){
		if (n == null) {
			return false;
		} else if (key.compareTo(n.data) == 0){
			return true;
		} else {
			return find(n.left, key) || find(n.right, key);
		}
	}

	/**
	 * Public find function that calls private helper
	 * @param key Reference to the key being searched for inside the Treap
	 * @return boolean value for found or not
	 */
	public boolean find(E key) {
		return find(root, key);
	}

	/**
	 * private helper for toString, creates string representation of treap
	 * @param n reference to Node
	 * @param depth reference to depth of the level we are current only
	 * @return String representation of Treap
	 */
	private String toString(Node<E> n, int depth) {
		StringBuilder r = new StringBuilder();
		for (int i=0;i<depth;i++) {
			r.append("--");
		}
		if (n==null) {
			r.append("null");
		} else {
			r.append("(key = " + n.data +", priority = " + n.priority + ")" );
			r.append("\n");
			r.append(toString(n.left, depth+1));
			r.append("\n");
			r.append(toString(n.right, depth+1));
		}
		return r.toString();

	}
	
	/**
	 * Public toString that calls the private toString to build the Treap, passing arguments for root and depth = 0
	 */
	public String toString() {
		return toString(root,0);
	}



	public static void main(String[] args) {
		Treap<Integer> testTree = new Treap<Integer>();
		testTree.add(4,19); 
		testTree.add(2,31);
		testTree.add(6,70); 
		testTree.add(1,84);
		testTree.add(3,12); 
		testTree.add(5,83);
		testTree.add(7,26);
		System.out.println(testTree);
		testTree.delete(2);
		System.out.println(testTree);
		testTree.add(2,31);
		testTree.add(9, 97);
		System.out.println(testTree);
		// TODO Auto-generated method stub

	}

}
