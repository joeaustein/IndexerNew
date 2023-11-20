# IndexerNew

### Este é um trabalho desenvolvido pelos alunos: 

JOELINTON DORTE (GRR20214924); 

THALES ASSIS DE OLIVEIRA (GRR20214912); 

MATHEUS FERNANDO RIBEIRO BORGES (GRR20177842).

## Objetivo

Criar um programa capaz de indexar palavras de um ou mais documentos de texto, suprindo os requisitos descritos em: 

*  https://gitlab.com/ds143-alexkutzke/material/-/blob/main/trabalho_2022_02.md?plain=1

## Conteúdo:

Este repositório contém o executável "IndexerNew.jar" na pasta "dist". Seu código-fonte encontra-se no diretório "src/indexernew/".

## Execução:

Para realizar a execução do programa, após clonar este repositório, abra seu Terminal (Linux, de preferência) e vá até o diretório raiz do projeto (pasta "IndexerNew").

A partir daqui, podemos rodar o executável que encontra-se na sub-pasta "dist", escolhendo uma das operações programadas e
informando os argumentos necessários, conforme mostrado a seguir:

#### * "indexer --freq N ARQUIVO" :

  Ex de comando: java -jar dist/IndexerNew.jar --freq 10 /mnt/c/Users/CAMINHO-ATE-OS-ARQUIVOS/arquivo.txt

#### * "indexer --freq-word PALAVRA ARQUIVO" :

  Ex de comando: java -jar dist/IndexerNew.jar --freq-word the /mnt/c/Users/CAMINHO-ATE-OS-ARQUIVOS/arquivo.txt

#### * "indexer --search TERMO ARQUIVO [ARQUIVO ...]" :

  Ex de comando: java -jar dist/IndexerNew.jar --search "and the" /mnt/c/Users/CAMINHO-ATE-OS-ARQUIVOS/

## Observações:

O programa foi compilado na versão 19 do Java, por isso será necessário ter o Java instalado e atualizado (versão 19 ou posterior) para realizar a execução e/ou compilação do programa. Para isso, os seguintes comandos podem ser executados (podem haver mudanças de uma distro para outra, vale lembrar):  

apt update

apt install openjdk-19-jre-headless
  
apt install openjdk-19-jdk-headless

## Compilação:

Caso seja necessário compilar o programa em sua máquina, o mesmo pode ser feito através da própria IDE (caso esteja instalada) ou através do Terminal, utilizando os seguintes comandos:

#### Compilar:

javac -cp . src/indexernew/*.java -d build/classes/

#### Compactar em .jar:

jar cvf IndexerNew.jar build/classes/indexernew/*.class

#### Para eventuais problemas:

Caso haja algum problema ao tentar executar o arquivo compilado, como "no main manifest...", o seguinte link pode ajudar:

*  https://pt.linux-console.net/?p=2311

## Informações adicionais: 

O programa apresentou uma boa performance e executou até o final em todos os arquivos passados para teste (incluindo os maiores).










