package UI;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import FileProcessing.HTMLValidator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainApplication extends JFrame implements ActionListener{
    private JTextField textField;
    private JButton submit;
    private HTMLValidator validator;
    private JTextArea fileOutputText;
    public MainApplication(){
        validator = new HTMLValidator();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout(FlowLayout.CENTER));

        JPanel container = new JPanel();
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
            fileOutputText.setText("O arquivo está bem formatado.");
        }
        else{
            fileOutputText.setText("Erro ao ler o arquivo. Verifique se o caminho do diretório está correto e se o arquivo está no diretório.");
        }
    }
}