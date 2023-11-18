package indexernew;

import java.util.Map;

// Classe que implementa a estrutura da HashTable:
public class IndexerHashTableEntry<Integer, String> implements Map.Entry<Integer, String> {
    // Definição:
    private Integer key;
    private String value;
    private int count;  
    private IndexerHashTableEntry<Integer,String> next;
    // Criador:
    public IndexerHashTableEntry(Integer key, String value) {
        this.key = key;
        this.value = value;
    }
    // Métodos:
    @Override
    public Integer getKey() {
        return this.key;
    }
    @Override
    public String getValue() {
        return this.value;
    }
    @Override
    public String setValue(String value) {
        this.value = value;
        return value;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public IndexerHashTableEntry<Integer, String> getNext() {
        return next;
    }
    public void setNext(IndexerHashTableEntry<Integer, String> next) {
        this.next = next;
    }   
}
