package FileProcessing;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import MatheCollections.*;

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

  public boolean ReadHTML(String path){
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

  public EReadResult InterpretHTML(){
    Stack<String> openedTags = new Stack<String>();

    for (int i = 0; i < fileContent.count(); i++) {
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
              if (!IsSingleton(firstTagName)) {
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
              if (!firstTagName.isBlank() && !IsSingleton(firstTagName)) {
                openedTags.push(firstTagName.toLowerCase());
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
              else{
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

  private void addOcurrence(String tagName){
    if(tagOcurrences.isEmpty()){
      tagOcurrences.add(new TagOcurrence(tagName));
    }
    else{
      boolean found = false;
      for (int i = 0; i < tagOcurrences.count(); i++) {
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

  public ArrayList<TagOcurrence> getOcurrences(){
    return tagOcurrences;
  }

  private boolean IsSingleton(String tagName){
    for (int i = 0; i < singleTons.length; i++) {
      if(singleTons[i].equals(tagName))
        return true;
    }
    return false;
  }
}
