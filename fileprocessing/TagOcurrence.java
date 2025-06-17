package fileprocessing;

/**
 * Classe responsável em armazenar quantas vezes uma tag foi declarada em um arquivo HTML.
 */
public class TagOcurrence {
    public String tagName;
    public int ocurrences;

    public TagOcurrence(String tagName){
        this.tagName = tagName;
        ocurrences = 1;
    }
    public TagOcurrence(String tagName, int ocurrences){
        this.tagName = tagName;
        this.ocurrences = ocurrences;
    }
}
