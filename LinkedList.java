public class LinkedList<K> { //LinkedList used to keep track of items and commands
    Node<K> list;
    
    public void add(K key) {
        add(new Node<K>(key));
    }

    public void add(Node<K> node) {
        if(list == null) {
            list = node;            
        }
        else {
            Node<K> current = list;
            while(current.next != null) {
                current = current.next;
            }
            current.next = node;            
        }       
    }  
    
    public boolean contains(K key) {
        Node<K> current = list;
        while (current != null) {
           if (current.key.equals(key)) {
                return true;               
            }
            current = current.next;
         }
        return false;
    }
    
   public Node<K> delete(K key) {
        Node<K> current = list;
        Node<K> previous = null;
        while (current != null) {
            if (current.key.equals(key)) {
                if (previous == null) {
                    list = current.next;                    
                }
                else {
                    previous.next = current.next;                    
                }
                return current;                
            }
            previous = current;
            current = current.next;
        }
        return null;
    }    
   
  /* public V retrieve(K key) {
        Node<K> current = list;
        while(current != null) {
            if(current.key.equals(key)) {                
                return current.value;               
            }
            current = current.next;
        }         
        return null;       
    } */
          
   public Node<K> retrieveNode(K key) {
        Node<K> current = list;
        while(current != null) {
            if(current.key.equals(key)) {                
                return current;               
            }
            current = current.next;
        }         
        return null;
    } 
   
   public int length() {
	   Node<K> current = list;
	   int counter = 0;
	   while(current != null) {
		   counter++;
		   current = current.next;
	   }
	   return counter;
   }
          
    public String toString() {
        Node<K> current = list;
        String sb = "";
        while(current != null) {
            if (! sb.equals("")) {
                sb += ", " + current.key;
            } else {
                sb += current.key;                
            }
            current = current.next;            
        }        
        return sb;
    }   
}