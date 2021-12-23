import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class SlangUI extends JFrame {
    private JPanel content;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton definitionQuizButton;
    private JButton slangQuizButton;
    private JTextArea LuckySlang;
    private JTextArea Notification;
    private JPanel ActionPane;
    private JPanel NoticePane;
    private JPanel ListPane;
    private JButton quitButton;
    private JTextField SearchText;
    private JButton findButton;
    private JComboBox SearchOption;
    private JButton listAllSlangButton;
    private JPanel ListContent;
    private JPanel SearchBarPane;
    private JButton searchHistoryButton;
    private slangDictionary dictionary;
    private JScrollPane js = new JScrollPane();
    private JTable SlangList;
    public SlangUI(slangDictionary dictionary){
        this.dictionary = dictionary;
        this.updateScreenList(dictionary.getDictionary());
        this.setContentPane(content);
        this.setTitle("Slang dictionary");
        this.pack();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
        JFrame jf = this;
        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                if (JOptionPane.showConfirmDialog(jf,
                        "Are you sure you want to quit?", "Close Window?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    dictionary.saveHistory();
                    dictionary.saveData();
                    System.exit(0);
                }
            }
        });
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dictionary.saveHistory();
                dictionary.saveData();
                System.exit(0);
            }
        });
        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                find();
            }
        });
        listAllSlangButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateScreenList(dictionary.getDictionary());
            }
        });
    }

    private void find() {
        if(!Objects.equals(SearchText.getText(), "")){
            if(SearchOption.getSelectedIndex() ==  0){
                updateScreenList(dictionary.findSlangWord(SearchText.getText()));
            }
            else if (SearchOption.getSelectedIndex() ==  1){
                updateScreenList((this.dictionary.findDefinition(SearchText.getText())));
            }
        }
    }

    public static void main(String[] arg){
        slangDictionary slang=  new slangDictionary();
        SlangUI ui = new SlangUI(slang);
    }
    public String listToString(List<String> list){
        StringBuilder definition = new StringBuilder();
        for(String def: list){
            definition.append(def).append(";");
        }
        definition.deleteCharAt(definition.length()-1);
        return ""+definition;
    }
    public void updateScreenList(HashMap.Entry<String, List<String>> entry){
        String[][] dat = new String[1][2];
        int i =0;
        dat[0][0] = entry.getKey();
        dat[0][1] = listToString(entry.getValue());

        String[] column = { "Slang", "Definition"};

        DefaultTableModel model = new DefaultTableModel(dat, column);
        SlangList = new JTable(model);
        js.setViewportView(SlangList);
        ListContent.add(js);
    }
    public void updateScreenList(HashMap<String, List<String>> list){
        String[][] dat = new String[list.size()][2];
        int i =0;
        for (HashMap.Entry<String, List<String>> entry : list.entrySet()){
            String[] temp = new String[2];
            temp[0] = entry.getKey();
            temp[1] = listToString(entry.getValue());
            dat[i] = temp;
            i++;
        }

        String[] column = { "Slang", "Definition"};

        DefaultTableModel model = new DefaultTableModel(dat, column);
        SlangList = new JTable(model);
        js.setViewportView(SlangList);
        ListContent.add(js);
    }
}
