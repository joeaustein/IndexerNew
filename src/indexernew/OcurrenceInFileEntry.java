package indexernew;

import java.util.Objects;

// classe para armazenar a ocorrência de cada palavra nos arquivos:
public class OcurrenceInFileEntry {
    private String word;
    private Integer ocurrence;
    // Criadores:
    public OcurrenceInFileEntry(String word, Integer ocurrence) {
        this.word = word;
        this.ocurrence = ocurrence;
    }
    public OcurrenceInFileEntry() { 
    }
    // Métodos:
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.word);
        hash = 19 * hash + Objects.hashCode(this.ocurrence);
        return hash;
    }
    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;    
        OcurrenceInFileEntry temp = (OcurrenceInFileEntry) obj;  
        return word.equalsIgnoreCase(temp.word) && Objects.equals(word, temp.word);
    } 
    public String getWord() {
        return word;
    }
    public void setWord(String word) {
        this.word = word;
    }
    public Integer getOcurrence() {
        return ocurrence;
    }
    public void setOcurrence(Integer ocurrence) {
        this.ocurrence = ocurrence;
    }  
}
