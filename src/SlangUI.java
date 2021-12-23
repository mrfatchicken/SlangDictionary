import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
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
    private JTextField Notification;
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
    private JTextField LuckySlang;
    private JButton resetSlangListButton;
    private slangDictionary dictionary;
    private JScrollPane js = new JScrollPane();
    private DefaultTableModel model;
    private JTable SlangList = new JTable();
    private boolean isSlangList = true;
    public SlangUI(slangDictionary dictionary){
        this.dictionary = dictionary;
        this.ListContent.add(js);
        this.updateScreenList(dictionary.getDictionary());
        this.setContentPane(content);
        this.Notification.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.setTitle("Slang dictionary");
        this.LuckySlang.setText("Lucky slang for today is \n"+dictionary.getString(dictionary.randomASlang()));
        this.pack();
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
        searchHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateScreenList(dictionary.getSearchHistoryList());
            }
        });
        SearchText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                find();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isSlangList && SlangList.getSelectedRow() != -1){
                    System.out.println(model.getValueAt(SlangList.getSelectedRow(), 0));
                    if (JOptionPane.showConfirmDialog(jf,
                            "Are you sure you want to delete this slang?", "Delete Slang?",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                                dictionary.removeSlang((String) model.getValueAt(SlangList.getSelectedRow(), 0));
                                Notification.setText("Delete successfully");
                    }
                }
                else if (!isSlangList){
                    Notification.setText("Cannot delete search history");
                }
                else if (SlangList.getSelectedRow() == -1){
                    Notification.setText("Please select row to delete");
                }

            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddEditUI add = new AddEditUI(dictionary,"add");
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddEditUI add = new AddEditUI(dictionary,"edit");
            }
        });
        resetSlangListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(jf,
                        "Are you sure you want to reset slang word list? All your adding or edit word will be deleted.", "Reset Window?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    dictionary.resetData();
                    updateScreenList(dictionary.getDictionary());
                    Notification.setText("Reset successfully");
                }
            }
        });
    }

    private void find() {
        if(!Objects.equals(SearchText.getText(), "")){
            if(SearchOption.getSelectedIndex() ==  0){
                try {
                    updateScreenList(dictionary.findSlangWord(SearchText.getText()));
                } catch (Exception e){
                    Notification.setText("This slang is not exist");
                }
            }
            else if (SearchOption.getSelectedIndex() ==  1){
                try {
                    updateScreenList((this.dictionary.findDefinition(SearchText.getText())));
                }
                catch (Exception e){
                    Notification.setText("This definition is not exist");
                }
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

        model = new DefaultTableModel(dat, column);
        SlangList.setModel(model);
        js.setViewportView(SlangList);
        isSlangList = true;
        Notification.setText("Load slang list successfully");
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

        model = new DefaultTableModel(dat, column);
        SlangList.setModel(model);
        js.setViewportView(SlangList);
        isSlangList = true;
        Notification.setText("Load slang list successfully");
    }
    public void updateScreenList(List<searchHistory> list){
        String[][] dat = new String[list.size()][3];
        int i =0;
        for (searchHistory item: list){
            String[] temp = new String[3];
            temp[0] = item.getDate();
            temp[1] = item.getType();
            temp[2] = item.getContent();
            dat[i] = temp;
            i++;
        }

        String[] column = { "Date time", "Type","Search content"};

        model = new DefaultTableModel(dat, column);
        SlangList.setModel(model);
        js.setViewportView(SlangList);
        isSlangList = false;
        Notification.setText("Load history successfully");
    }
}
