package FileProcessing;

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
