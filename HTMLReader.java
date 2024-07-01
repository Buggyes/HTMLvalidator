import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HTMLReader {
    public String ReadHTML(String path){
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            StringBuilder fileContent = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                fileContent.append(line);
                fileContent.append(System.lineSeparator());
            }
            br.close();
            return fileContent.toString();
        } catch (IOException e) {
            return null;
        }
    }
}