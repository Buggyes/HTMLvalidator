package fileprocessing;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import mathecollections.*;

/**
 * <p>Lê e valida a formatação de um arquivo HTML.
 * Possui a maioria de seus métodos públicos para deixar a aplicação decidir como será feito o processo de leitura do arquivo.
 * @see EReadingPoint
 * @see TagOcurrence
 * @see EReadResult
 */
public class HTMLValidator {
  private ArrayList<String> fileContent;
  private ArrayList<TagOcurrence> tagOcurrences;
  private EReadingPoint readingPoint;
  private final String[] singleTons = {"meta","base","br","col","command","embed","hr","img","input","link","param","source","!DOCTYPE"};

  public HTMLValidator(){
    tagOcurrences = new ArrayList<TagOcurrence>();
    fileContent = new ArrayList<String>();
    readingPoint = EReadingPoint.TagOpenning;
  }

  /**
   * <p>Usa um <code>BufferedReader</code> para ler o conteúdo do arquivo, linha por linha, e salva essas linhas no 
   * <code>fileContent</code>.
   * @param path - caminho do arquivo HTML.
   * @return <code>true</code> se a leitura foi feita com sucesso, <code>falso</code> caso tenha ocorrido um erro.
   * @see BufferedReader
   */
  public boolean ReadHTML(String path){
    fileContent.clear();
    try (BufferedReader br = new BufferedReader(new FileReader(path))) {
        String line;
        while ((line = br.readLine()) != null) {
          fileContent.add(line);
        }
        br.close();
    } catch (IOException e) {
        return false;
    }
    return true;
  }

  /**
   * <p>Interpreta o arquivo HTML lido anteriormente com <code>ReadHTML</code>, validando se sua formatação está correta.
   * @return <code>EReadResult</code> contendo o resultado da operação.
   * @see EReadResult
   */
  public EReadResult InterpretHTML(){
    tagOcurrences.clear();
    Stack<String> openedTags = new Stack<String>();

    for (int i = 0; i < fileContent.getCount(); i++) {
      readingPoint = EReadingPoint.TagOpenning;
      String firstTagName = "";
      String secondTagName = "";
      String line = fileContent.get(i);

      for (int j = 0; j < line.length(); j++) {
        switch (readingPoint) {

          case TagOpenning:
            if (line.charAt(j) == '<') {
              readingPoint = EReadingPoint.Name;
            }
            break;

          case Name:
            if (line.charAt(j) == '>') {
              if (!isSingleton(firstTagName)) {
                readingPoint = EReadingPoint.TagOpenning;
                openedTags.push(firstTagName.toLowerCase());
              }
              addOcurrence(firstTagName.toLowerCase());
            }
            else if (line.charAt(j) == ' '){
              readingPoint = EReadingPoint.Atributes;
            }
            else if (line.charAt(j) == '/'){
              readingPoint = EReadingPoint.TagClosure;
            }
            else{
              firstTagName += line.charAt(j);
            }
            break;

          case Atributes:
            if (line.charAt(j) == '>') {
              readingPoint = EReadingPoint.TagOpenning;
              if (!firstTagName.isBlank()) {
                if (!isSingleton(firstTagName)) {
                  openedTags.push(firstTagName.toLowerCase());
                }
                addOcurrence(firstTagName.toLowerCase());
              }
            }
            break;

          case TagClosure:
            if (line.charAt(j) == '>' && !openedTags.isEmpty()) {
              if (openedTags.peek().equals(secondTagName.toLowerCase())) {
                openedTags.pop();
                addOcurrence(secondTagName.toLowerCase());
              }
              else {
                return EReadResult.PrematureClosure;
              }
            }
            if (fileContent.isEmpty()) {
              return EReadResult.ClosureAfterPreviousClosure;
            }
            else if(line.charAt(j) != '<' && line.charAt(j) != '/'){
              secondTagName += line.charAt(j);
            }
            break;
        }
      }
    }
    return EReadResult.Ok;
  }

  /**
   * <p>Adiciona a ocorrência de uma tag na lista de ocorrências, isto é, quando uma tag é encontrada e está formatada corretamente.
   * @param tagName - nome da tag encontrada.
   */
  private void addOcurrence(String tagName){
    if(tagOcurrences.isEmpty()){
      tagOcurrences.add(new TagOcurrence(tagName));
    }
    else{
      boolean found = false;
      for (int i = 0; i < tagOcurrences.getCount(); i++) {
        if(tagOcurrences.get(i).tagName.equals(tagName)){
          TagOcurrence ocurr = new TagOcurrence(tagName,tagOcurrences.get(i).ocurrences);
          ocurr.ocurrences++;
          tagOcurrences.set(i, ocurr);
          found = true;
          break;
        }
      }
      if(!found){
        tagOcurrences.add(new TagOcurrence(tagName));
      }
    }
  }

  /**
   * @return Um <code>ArrayList</code> contendo todas as tags encontradas no arquivo HTML, e quantas vezes foram encontradas
   * @see ArrayList
   * @see TagOcurrence
   */
  public ArrayList<TagOcurrence> getOcurrences(){
    return tagOcurrences;
  }

  /**
   * Valida se uma tag é singleton. Uma tag singleton é uma tag que não precisa de fechamento.
   * @param tagName - nome da tag encontrada.
   * @return <code>true</code> se a tag for singleton, <code>false</code> caso contrário.
   */
  private boolean isSingleton(String tagName){
    for (int i = 0; i < singleTons.length; i++) {
      if(singleTons[i].equals(tagName))
        return true;
    }
    return false;
  }
}
