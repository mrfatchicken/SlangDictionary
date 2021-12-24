import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class AddEditUI extends JFrame{
    private JPanel content;
    private JTextField WordField;
    private JTextField DefinitionField;
    private JButton submit;
    private JTextField Notification;
    private slangDictionary dictionary;
    private JFrame jf = this;
    public AddEditUI(slangDictionary dictionary,String type){
        this.dictionary = dictionary;
        if(Objects.equals(type, "add")){
            addInit();
        }
        else if(Objects.equals(type, "edit")){
            editInit();
        }
        this.setContentPane(content);
        this.pack();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }



    private void addInit() {
        submit.setText("Add");
        Notification.setText("Add slang word");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!Objects.equals(WordField.getText(), "") && !Objects.equals(DefinitionField.getText(), "")){
                    if(!dictionary.addSlang(WordField.getText(),DefinitionField.getText())){
                        if (JOptionPane.showConfirmDialog(jf,
                                "This slang is exist, do you want to overwrite it?", "Add Window?",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                            if(dictionary.editSlang(WordField.getText(),DefinitionField.getText())) Notification.setText("Edit successfully");
                        }
                    }
                    else {
                        Notification.setText("Add successfully");
                    }
                }
            }
        });
    }
    private void editInit() {
        submit.setText("Edit");
        Notification.setText("Edit slang word");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!Objects.equals(WordField.getText(), "") && !Objects.equals(DefinitionField.getText(), "")){
                    if(!dictionary.editSlang(WordField.getText(),DefinitionField.getText())){
                        if (JOptionPane.showConfirmDialog(jf,
                                "This slang is not exist, do you want to add it?", "Edit Window?",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                            if(dictionary.addSlang(WordField.getText(),DefinitionField.getText())) Notification.setText("Add successfully");
                        }
                    }
                    else {
                        Notification.setText("Edit successfully");
                    }
                }
            }
        });
    }
}
