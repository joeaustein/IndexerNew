package indexernew;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Indexer {
    // Constantes referentes aos parâmetros de entrada:
    public static final String FREQ_OPERATION = "freq";
    public static final String FREQWORD_OPERATION = "freq-word";
    public static final String SEARCH_OPERATION = "search";
    // -------------------------------------------------------------------- //
    public static void main(String[] args) {
        // Veriica o tipo da operação passada no primeiro parâmetro de entrada - arg[0]
        try {
            if (args[0].contains(FREQ_OPERATION) && !args[0].contains(FREQWORD_OPERATION)) {
                // Opção "--freq N ARQUIVO":
                freqNFile(args[1], args[2]);
            } else if (args[0].contains(FREQWORD_OPERATION)) {
                // Opção "--freq-word PALAVRA ARQUIVO":
                freqWordNFile(args[1], args[2]);
            } else if (args[0].contains(SEARCH_OPERATION)) {
                // Opção "--search TERMO ARQUIVO [ARQUIVO ...]":
                searchTermNFile(args[1], args[2]);
            } else {
                System.out.println("Forneça um argumento válido");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Forneça todos os 3 argumentos para a execucao correta do indexador");
        } catch (RuntimeException e) {
            System.out.println("Erro ao executar operacao! Forneca os argumentos corretamente! " + e.getMessage());
        }
    }
    // -------------------------------------------------------------------- //
    private static void freqNFile(String n, String filePath) throws RuntimeException {
        System.out.println("Iniciando operação --freq N ARQUIVO");
        // Cria a hashTable:
        IndexerHashTable hashTable = new IndexerHashTable();
        try {
            // Nr de palavras a serem contadas:
            int wordsNumber = Integer.valueOf(n);
            // Arquivo:
            String content = readFile(filePath);
            // Matcher para desconsiderar pontuações e espaços em branco:
            Pattern pattern = Pattern.compile("\\b\\w+\\b");
            Matcher matcher = pattern.matcher(content);
            // Insere as palavras validas dentro da hashtable:
            while (matcher.find()) {
                String currentWord = matcher.group();
                currentWord = sanitazeString(currentWord);
                if (currentWord.length() >= 2) {
                    hashTable.put(currentWord.hashCode(), currentWord);
                }
            }
            freqNFileOperation(hashTable, wordsNumber); 
        } catch (NumberFormatException e) {
            String msg = "Forneça um número válido para a operação de busca";
            System.out.println(msg);
            throw new RuntimeException(msg);
        }
    }
    // -------------------------------------------------------------------- //
    private static void freqWordNFile(String word, String filePath) throws RuntimeException {
        System.out.println("Iniciando operação --freq-word PALAVRA ARQUIVO");
        // Cria a hashTable:
        IndexerHashTable hashTable = new IndexerHashTable();
        String sanitezedWord = sanitazeString(word);
        try {
            // Arquivo:
            String content = readFile(filePath);
            // Matcher para desconsiderar pontuações e espaços em branco:
            Pattern pattern = Pattern.compile("\\b\\w+\\b");
            Matcher matcher = pattern.matcher(content);
            // Insere as palavras validas dentro da hashtable:
            while (matcher.find()) {
                String currentWord = matcher.group();
                currentWord = sanitazeString(currentWord);
                if (currentWord.length() >= 2) {
                    hashTable.put(currentWord.hashCode(), currentWord);
                }
            }
            // Imprime contagem de ocorrencias:
            System.out.println("A palavra \"" + word + "\" aparece " + hashTable.getWordsCount(sanitezedWord.hashCode(), sanitezedWord) + " vezes dentro do arquivo!");
        } catch (Exception e) {
            String msg = "Para esta operacao, forneca o caminho para um arquivo de texto";
            System.out.println(msg);
            throw new RuntimeException(msg);
        }
    }
    // -------------------------------------------------------------------- //    
    private static void searchTermNFile(String term, String directoryPath) throws RuntimeException {
        System.out.println("Iniciando operação --search TERMO ARQUIVO");
        // Recupera uma lista de arquivos presentes no diretorio informado:
        List<File> files = getFiles(directoryPath);
        // Lista para calcular o TFIDF dos arquivos onde existe o termo:
        ArrayList<FileTermFrequency> listTF = new ArrayList<FileTermFrequency>();
        // Variavel para calculo do IDF após termos o numero de documentos:        
        double IDF = 0;
        // StringTokenizer para dividir o termo em palavras:
        StringTokenizer tokenizer = new StringTokenizer(term);
        // Cria uma lista para armazenar as palavras do termo separadamente:
        List<String> wordsList = new ArrayList<>();
        // Adicione cada palavra à lista
        while (tokenizer.hasMoreTokens()) {
            String word = sanitazeString(tokenizer.nextToken());
            wordsList.add(word);
        } 
        // Se houver arquivos no diretorio:
        if (files != null && files.size() > 0) {
            for (File file : files) {
                // Cria a hashTable:
                IndexerHashTable hashTable = new IndexerHashTable();
                // Contadores de palavras e termos encontrados:
                int wordsCount = 0;
                int termsFoundCount = 0;
                // Pega o conteudo de cada arquivo listado:
                String filePath = file.getAbsolutePath();
                String content = readFile(filePath);
                // Matcher para desconsiderar pontuações e espaços em branco:
                Pattern pattern = Pattern.compile("\\b\\w+\\b");
                Matcher matcher = pattern.matcher(content);
                // Insere as palavras validas dentro da hashtable:
                while (matcher.find()) {
                    String currentWord = matcher.group();
                    currentWord = sanitazeString(currentWord);
                    if (currentWord.length() >= 2) {
                        hashTable.put(currentWord.hashCode(), currentWord);
                        // Incrementa contador de palavras:
                        wordsCount++;   
                    }
                }
                // Vai contar na tabela hash todas as palavras do termo:
                for(String cWord : wordsList) {
                    termsFoundCount = hashTable.getWordsCount(cWord.hashCode(), cWord);
                    // Adiciona na listTF quando for encontrado ao menos um termo:
                    if (termsFoundCount > 0) {
                        // TF calculado na criação do OBJ:
                        listTF.add(new FileTermFrequency(wordsCount, termsFoundCount, file.getName(), cWord));
                    }
                }     
            }      
            // Contando tamanho real de arquivos em que o termo apareceu:
            int nrDocsComTermo = 1; // inicia com 1 mesmo 
            for(int i = 1; i <  listTF.size(); i++) {
                if(!listTF.get(i).getFileName().equals(listTF.get(i-1).getFileName())) {
                    nrDocsComTermo++; 
                }   
            }
            // Calculo IDF:
            // log[ (Número de Documentos) / (Número de documentos em que t está presente)
            IDF = Math.log(files.size()) / nrDocsComTermo;
            // Calculo do TFIDF de cada arquivo e cada palavra em listTF:
            for (FileTermFrequency fileTF : listTF) {
                fileTF.calcularTFIDF(IDF);
            }      
            // Lista para união e calculo de medias:
            ArrayList<FileTermFrequency> listTFUnion = new ArrayList<FileTermFrequency>();
            // Une todos os arquivos repetidos, calculando média do TFIDF:
            for(int i = 0; i <  listTF.size(); i++) { 
                int nrArquivosIguais = 1;
                double somaTFIDF = listTF.get(i).getFileTFIDF();
                double mediaTFIDF = 0;        
                // Ve se arquivo atual é o mesmo que os próximos para unir e realizar calculos:
                while(true) {
                    if((i +1) < listTF.size()) {
                        if(listTF.get(i).getFileName().equals(listTF.get(i+1).getFileName())) {
                            somaTFIDF += listTF.get(i+1).getFileTFIDF();
                            nrArquivosIguais ++;
                            if((i +1) < listTF.size()) {
                                i++;
                            } else {
                                break;
                            }  
                        } else {
                            break;
                        }
                    } else {
                        break;
                    }       
                }
                // Calcula Media e inclui na nova lista:
                mediaTFIDF = somaTFIDF / nrArquivosIguais;        
                FileTermFrequency arquivoERelevancia = new FileTermFrequency();
                arquivoERelevancia.setFileName(listTF.get(i).getFileName());
                arquivoERelevancia.setFileTFIDF(mediaTFIDF);
                listTFUnion.add(arquivoERelevancia);
            }     
            // Ordena a lista após a uniao (decrescente) a partir do atributo FileTFIDF:
            List<FileTermFrequency> sortedTFIDF = listTFUnion.stream()
                    .sorted(Comparator.comparingDouble(FileTermFrequency::getFileTFIDF).reversed())
                    .collect(Collectors.toList());
            // Imprime as palavras na ordem correta:
            int count = 0; 
            for (FileTermFrequency fileTF : sortedTFIDF) {
                System.out.printf(String.valueOf(count + 1) + "o arquivo mais relevante = \"" +  fileTF.getFileName() + "\", com TFIDF = %.10f\n", fileTF.getFileTFIDF());
                count++;
            } 
            System.out.println(files.size() + " arquivos presentes no diretório " + directoryPath);
        } else {
            System.out.println("Diretório vazio ou inexistente");
        }
    }
    // -------------------------------------------------------------------- //    
    //retorna o conteúdo do arquivo em formato de String. utilizando nos casos de --freq e --freq-word
    public static String readFile(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(Path.of(filePath), StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {

        }
        return content.toString();
    }
    // -------------------------------------------------------------------- // 
    // Traz todos os arquivos presentes no diretório passado por parâmetro na operação de --search
    private static List<File> getFiles(String pathName) {
        List<File> arquivos = null;
        File path = new File(pathName);
        arquivos = Arrays.asList(path.listFiles());
        return arquivos;
    }
    // -------------------------------------------------------------------- //
    // Retira todos os caracteres que não sejam letras:
    private static String sanitazeString(String s) {
        String str = new String();
        str = s.toLowerCase().replaceAll("[^a-zA-Z]", "");
        return str;
    }
    // -------------------------------------------------------------------- // 
    private static void freqNFileOperation(IndexerHashTable hashTable, int wordsNumber) {
        // Cria obj para contar ocorrencia das palavras no arquivo:
        List<OcurrenceInFileEntry> entries = new ArrayList();
        LinkedList<IndexerHashTableEntry<Integer, String>>[] tableEntries = hashTable.getTable();
        int count = 0;
        // Percorre a tabela hash contando as ocorrencias das palavras:
        for (LinkedList<IndexerHashTableEntry<Integer, String>> tableEntrie : tableEntries) {
            if (tableEntrie != null) {      
                //System.out.println("Palavra " + tableEntrie.getLast().getValue() + " count " + tableEntrie.getLast().getCount());
                OcurrenceInFileEntry fileEntry = new OcurrenceInFileEntry(tableEntrie.getLast().getValue(), tableEntrie.getLast().getCount());
                if (!entries.contains(fileEntry)) {
                    entries.add(fileEntry);                    
                }
            }
        }
        // ordena a lista (decrescente) a partir do atributo ocurrence:
        List<OcurrenceInFileEntry> sortedEntries = entries.stream()
                .sorted(Comparator.comparingInt(OcurrenceInFileEntry::getOcurrence).reversed())
                .collect(Collectors.toList());
        // Imprime as palavras na ordem correta:
        for (OcurrenceInFileEntry entry : sortedEntries) {
            if (count >= wordsNumber) {
                break;
            }
            System.out.println(String.valueOf(count + 1) + " - (" + entry.getWord() + ") = " + entry.getOcurrence() + " vezes");
            count++;
        }
    }
    // -------------------------------------------------------------------- //
}
