import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class HTMLValidator {
  private ArrayList<String> fileContent;
  private EReadingPoint readingPoint;
  private final String[] singleTons = {"meta","base","br","col","command","embed","hr","img","input","link","param","source","!DOCTYPE"};
  public HTMLValidator(){
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

  private boolean IsSingleton(String tagName){
    for (int i = 0; i < singleTons.length; i++) {
      if(singleTons[i].equals(tagName))
        return true;
    }
    return false;
  }

  public EReadResult InterpretHTML(){
    Stack<String> openedTags = new Stack<String>();
    for (String line : fileContent) {
      readingPoint = EReadingPoint.TagOpenning;
      String firstTagName = "";
      String secondTagName = "";
      for (int i = 0; i < line.length(); i++) {
        switch (readingPoint) {
          case TagOpenning:
            if (line.charAt(i) == '<') {
              readingPoint = EReadingPoint.Name;
            }
            break;
          case Name:
            if (line.charAt(i) == '>' && !IsSingleton(firstTagName)) {
              readingPoint = EReadingPoint.TagOpenning;
              openedTags.push(firstTagName);
            }
            else if (line.charAt(i) == ' '){
              readingPoint = EReadingPoint.Atributes;
            }
            else if (line.charAt(i) == '/'){
              readingPoint = EReadingPoint.TagClosure;
            }
            else{
              firstTagName += line.charAt(i);
            }
            break;
          case Atributes:
            if (line.charAt(i) == '>') {
              readingPoint = EReadingPoint.TagOpenning;
              if (!firstTagName.isBlank() && !IsSingleton(firstTagName)) {
                openedTags.push(firstTagName);
              }
            }
            break;
          case TagClosure:
            if (line.charAt(i) == '>' && !openedTags.isEmpty()) {
              if (openedTags.peek().equals(secondTagName)) {
                openedTags.pop();
              }
              else{
                return EReadResult.PrematureClosure;
              }
            }
            if (fileContent.isEmpty()) {
              return EReadResult.ClosureAfterPreviousClosure;
            }
            else if(line.charAt(i) != '<' && line.charAt(i) != '/'){
              secondTagName += line.charAt(i);
            }
            break;
          default:
            break;
        }
      }
    }
    return EReadResult.Ok;
  }
}
