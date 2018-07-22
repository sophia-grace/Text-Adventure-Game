public class HashTable<K,V> {
	public int keys = 0;
	LinkedListHT<K,V>[] table; //array of LinkedLists
	    
	public HashTable() { //constructor
	    this.table = new LinkedListHT[10];
	    for(int i = 0; i < table.length; i++) {
	        table[i] = new LinkedListHT<K,V>();            
	   }
	}
	    
	public void store(K key, V value) {
	     int index = Math.abs((key.hashCode())%(table.length));
	     if(!this.contains(key)) { //if the key with that value is not already in the table
	         table[index].add(key, value);
	          keys++;           
	        } else {
	            NodeHT<K,V> node = table[index].retrieveNode(key); //can still add repeat key, just update value
	            node.value = value;
	        }
	        //check the length of the array
	        if(keys >= (table.length * 4)) {
	            rehash();
	        } 
	    }

	    public void rehash() {
	        LinkedListHT<K,V>[] old = table;
	        table = new LinkedListHT[table.length * 2 + 1];            
	        for(int i = 0; i < table.length; i++) { //set linkedList to each index of hashtable
	            table[i] = new LinkedListHT<K,V>();                
	        }
	        keys = 0;
	        for(int j = 0; j < old.length; j++) {
	            NodeHT<K,V> current = old[j].list;
	            while (current != null) {                
	                store(current.key, current.value); //REHASH, store old in new table
	                current = current.next;               
	            }
	        }
	    }
	    
	    public boolean contains(K key) {
	        int index = Math.abs((key.hashCode())%(table.length));
	        return table[index].contains(key);
	    }
	        
	    public boolean delete(K key) {
	        int index = Math.abs((key.hashCode())%table.length);
	        boolean deleted = table[index].delete(key);
	        if (deleted) 
	            keys--;
	        return deleted;
	    }
	    
	    public V retrieve(K key) {
	        int index = Math.abs((key.hashCode()) % table.length);
	        Object obj = table[index].retrieve(key);
	        if (obj == null) {
	            return null;
	        }
	        return (V)obj;
	    } 
	    
	    public String toString() {
	        String sb = "";
	        for(int i = 0; i < table.length; i++) {
	            if (table[i].list != null) {
	                if (! sb.equals("")) {
	                    sb += ""+ table[i].toString();
	                } else {
	                    sb += "" + table[i].toString();
	                }
	            }
	        }        
	        return sb;
	    } 
}