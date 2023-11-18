package indexernew;

import java.util.LinkedList;

public class IndexerHashTable<Integer, String> {
    // Constantes referentes à criação e redimensionamento da tabela hash:
    private static final int INITIAL_CAPACITY = 5;
    private static final double LOAD_FACTOR = 0.75;
    // Variavel para tamanho da tabela:
    public static int TABLE_SIZE;
    // Lista encadeada da Tabela hash:
    private LinkedList<IndexerHashTableEntry<Integer, String>>[] table;
    // -------------------------------------------------------------------- //
    // Criador:
    public IndexerHashTable() {
        TABLE_SIZE = 0;
        table = new LinkedList[INITIAL_CAPACITY];
        for (int i = 0; i < INITIAL_CAPACITY; i++) {
            table[i] = new LinkedList<>();
        }
    }
    // -------------------------------------------------------------------- //
    // Inserir:
    public void put(int key, String value) {
        if (TABLE_SIZE + 1 > LOAD_FACTOR * table.length) {
            // Redimensionar a tabela se o fator de carga for atingido.
            resize();
        }
        int count = 0;
        // Calcula Index:
        int index = getIndex(key, table.length);
        // Carrega o slot com base no index calculado:
        LinkedList<IndexerHashTableEntry<Integer, String>> slot = table[index];
        // Se slot for null, cria e atribui uma lista encadeada a ele:
        if (slot == null) {
            slot = new LinkedList<>();
            table[index] = slot;  
        }
        // Adiciona o elemento à lista deste slot:
        slot.add(new IndexerHashTableEntry(key, value));
        // Realiza a contagem aqui:
        count = slot.size();
        slot.getLast().setCount(count);
        // System.out.println("palavra: " + value + " - count atual: " + count); 
        // Atualiza tabela e incrementa o tamanho: 
        table[index] = slot;
        TABLE_SIZE++;
    }
    // -------------------------------------------------------------------- //
    // Recupera a palavra na chave passada:
    public String get(int key) {
        // Calcula Index:
        int index = getIndex(key, table.length);
        // Carrega o slot com base no index calculado:
        LinkedList<IndexerHashTableEntry<Integer, String>> slot = table[index];
        // Percorre lista do slot, retornando o valor quando a chave corresponder:
        for (IndexerHashTableEntry<Integer, String> entry : slot) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }
        return null;
    }
    // -------------------------------------------------------------------- //
    // Conta e retorna numero de ocorrencias de uma palavra:
    public int getWordsCount(int key, String word) {
        int count = 0;
        // Calcula Index:
        int index = getIndex(key, table.length);
        // Carrega o slot com base no index calculado:
        LinkedList<IndexerHashTableEntry<Integer, String>> slot = table[index];
        // Percorre lista do slot, incrementando count a cada palavra correspondente:
        for (IndexerHashTableEntry<Integer, String> entry : slot) {
            if (entry.getValue() != null && entry.getValue().equals(word)) {
                count++;
            }
        }
        return count;
    }
    // -------------------------------------------------------------------- //
    // Calcula Index:
    private int getIndex(int key, int tableSize) {
        return (Math.abs(key)) % tableSize;
    }
    // -------------------------------------------------------------------- //
    // Valida se é número primo:
    private static boolean isPrimo(int number) {
        if (number <= 1) {
            return false;
        }
        if (number <= 3) {
            return true;
        }
        for (int i = 2; i * i <= number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
    // -------------------------------------------------------------------- //
    // Redimensiona a tabela hash:
    private void resize() {
        int newSize = table.length * 2;
        // garante que o tamanho da tabela seja um número primo pois facilita na alocação dos valores.
        while (!isPrimo(newSize)) {
            newSize++;
        }
        // Cria a nova tabela redimensionada:
        LinkedList<IndexerHashTableEntry<Integer, String>>[] newTable = new LinkedList[newSize];
        for (LinkedList<IndexerHashTableEntry<Integer, String>> bucket : table) {
            if (bucket != null) {
                // Recalcula index e aloca na nova tabela e nova posição:
                for (IndexerHashTableEntry<Integer, String> entry : bucket) {
                    int newIndex = getIndex((int) entry.getKey(), newSize);
                    if (newTable[newIndex] == null) {
                        newTable[newIndex] = new LinkedList<>();
                    }
                    newTable[newIndex].add(entry);
                }
            }
        }
        // Atualiza a tabela hash para o novo modelo:
        table = newTable;
    }
    // -------------------------------------------------------------------- //
    // Recupera a tabela hash:
    public LinkedList<IndexerHashTableEntry<Integer, String>>[] getTable() {
        return table;
    }
    // -------------------------------------------------------------------- // 
}
