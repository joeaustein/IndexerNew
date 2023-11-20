package indexernew;

public class FileTermFrequency {
    // Atributos:
    private double nrTermosEncontrados;
    private int nrPalavras;
    private double fileTF;
    private double fileTFIDF;
    private String fileName;
    private String palavraBuscada;
    
    // Construtor 01:
    public FileTermFrequency(int nrPalavras, int nrTermosEncontrados, String fileName, String palavraBuscada) {
        this.fileName = fileName;
        this.palavraBuscada = palavraBuscada;
        this.nrPalavras = nrPalavras;
        this.nrTermosEncontrados = nrTermosEncontrados;
        // Calculo Term Frequency (TF):
        this.fileTF = (double)nrTermosEncontrados / (double)nrPalavras;
    }
    // Construtor 02:
    public FileTermFrequency(){}
    
    // Gets&Sets:
    public double getNrTermosEncontrados() {
        return nrTermosEncontrados;
    }
    public void setNrTermosEncontrados(double nrTermosEncontrados) {
        this.nrTermosEncontrados = nrTermosEncontrados;
    }
    public int getNrPalavras() {
        return nrPalavras;
    }
    public void setNrPalavras(int nrPalavras) {
        this.nrPalavras = nrPalavras;
    }
    public double getFileTF() {
        return fileTF;
    }
    public void setFileTF(double fileTF) {
        this.fileTF = fileTF;
    }
    public double getFileTFIDF() {
        return fileTFIDF;
    }
    public void setFileTFIDF(double fileTFIDF) {
        this.fileTFIDF = fileTFIDF;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getPalavraBuscada() {
        return palavraBuscada;
    }
    public void setPalavraBuscada(String palavraBuscada) {
        this.palavraBuscada = palavraBuscada;
    }
    // Calcula TFIDF:
    public void calcularTFIDF(double IDF) {
        this.fileTFIDF = fileTF * IDF;
    }
}

