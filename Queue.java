public class Queue<V> { //just takes States
	 Node<V> head;

	    public void append_front(V item) {
	    // appends to front of list:
	        Node<V> node = new Node<V>(item);
	        node.next = head;
	        head = node;
	    }
	    public void append_end(V item) {
	        // appends to tail of list:
	        Node<V> current = head;
	        if (current == null) {
	            Node<V> node = new Node<V>(item);
	            head = node;
	        } else {
	            while (current.next != null) {
	            current = current.next;
	            }
	            Node<V> node = new Node<V>(item);
	            current.next = node;
	        }
	    }
	    public boolean isEmpty() {
	        return (head == null);
	    }
	    public V pop_front() {
	        Node<V> temp = head;
	        head = head.next;
	        return temp.key;
	    }
	    public V pop_end() {
	        Node<V> temp = head;
	        Node<V> prev = head;
	        while (temp.next != null) {
	            prev = temp;
	            temp = temp.next;
	        }
	        prev.next = null;
	        return temp.key;
	}
}