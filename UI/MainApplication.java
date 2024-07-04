package UI;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import FileProcessing.EReadResult;
import FileProcessing.HTMLValidator;
import FileProcessing.TagOcurrence;
import MatheCollections.ArrayList;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainApplication extends JFrame implements ActionListener{
    private JPanel container;
    private JTextField textField;
    private JButton submit;
    private HTMLValidator validator;
    private JTextArea fileOutputText;
    private JTable ocurrenceTable;
    private JScrollPane scrollPane;

    public MainApplication(){
        validator = new HTMLValidator();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout(FlowLayout.CENTER));

        container = new JPanel();
        container.setPreferredSize(new Dimension(450, 520));
        container.setBackground(Color.lightGray);
        container.setLayout(new FlowLayout(FlowLayout.CENTER));

        JPanel fileOutput = new JPanel();
        fileOutput.setPreferredSize(new Dimension(400, 250));
        fileOutput.setBackground(Color.white);
        fileOutput.setLayout(new FlowLayout(FlowLayout.LEADING));

        JLabel label = new JLabel("Arquivo:");

        textField = new JTextField();
        textField.setPreferredSize(new Dimension(250, 20));

        submit = new JButton("Analisar");
        submit.addActionListener(this);

        fileOutputText = new JTextArea();
        fileOutputText.setEditable(false);
        fileOutputText.setLineWrap(true);
        fileOutputText.setWrapStyleWord(true);
        fileOutputText.setSize(390, HEIGHT);

        fileOutput.add(fileOutputText);
        container.add(label);
        container.add(textField);
        container.add(submit);
        container.add(fileOutput);
        this.add(container);
        this.pack();
        this.setTitle("HTML Validator");
        this.setSize(450,520);
        this.setVisible(true);
        this.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == submit) {
            verifyHTML(textField.getText());
        }
    }

    private void verifyHTML(String path){
        if (validator.ReadHTML(path)) {
            EReadResult result = validator.InterpretHTML();
            switch (result) {
                case PrematureClosure:
                    fileOutputText.setText("ERRO: Houve o termino prematuro de uma tag");
                    break;
                case ClosureAfterPreviousClosure:
                    fileOutputText.setText("ERRO: Houve o término de uma tag que não foi aberta");
                    break;
                case Ok:
                    ArrayList<TagOcurrence> tagOcurrences = validator.getOcurrences();
                    String[] columns = new String[] {"Tags","Número de ocorrências"};
                    Object[][] data = new Object[tagOcurrences.count()][2];
                    for (int i = 0; i < tagOcurrences.count(); i++) {
                        data[i][0] = tagOcurrences.get(i).tagName;
                        data[i][1] = tagOcurrences.get(i).ocurrences;
                    }
                    fileOutputText.setText("O arquivo está bem formatado.");
                    ocurrenceTable = new JTable(data, columns);
                    ocurrenceTable.setDefaultEditor(Object.class, null);
                    scrollPane = new JScrollPane(ocurrenceTable);
                    container.add(scrollPane);
                    this.add(container);
                    this.pack();
                    break;
            
                default:
                    break;
            }
        }
        else{
            fileOutputText.setText("Erro ao ler o arquivo. Verifique se o caminho do diretório está correto e se o arquivo está no diretório.");
        }
    }
}