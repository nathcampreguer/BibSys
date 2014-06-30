BibSys
======

Trabalho de conclusão da disciplina Sistemas Orientados a Objetos
do Bacharelado em Ciências da Computação - UNESP - Campus Rio Claro.

O sistema aceita apenas arquivos .bib e possui as seguintes funcionalidades:
 - Gerar árvore de referência pela Bibkey
 - Gerar Bibkey nos seguintes formatos
     – autor:ano (para um autor)
     – autor1.autor2:ano (para dois autores)
     – autor.et.al:ano (para três ou mais)
 - Comparar dois aqrquivos .bib

Observações:
 - Ao gerar a bibkey no formato sugerido na descrição do projeto,
   a bibkey pode perder seu caráter único caso mais de um item
   tenha os mesmos autores e ano de publicação. Nestes caso, o
   sistema mantém apenas o primeiro item que contém a chave repetida. 
   Todos os outros itens são removidos;
 - Na função para gerar/formatar a bibkey, o arquivo original é 
   sobrescrito com o novo conteúdo gerado;
 - Na comparação, é feita uma interseção dos arquivos;
 - Para gerar a árvore de referência na JTree é preciso que o
   documento não contenha caracteres ilegais. Para suprir esta
   limitação, também é gerada uma árvore em formato de texto;
 - Para evitar problemas com o Jar da biblioteca utilizada,
   optei por inserir o seu código fonte em um pacote do projeto.

Bibliotecas utilizadas:
 Jbibtex : https://github.com/jbibtex/jbibtex
