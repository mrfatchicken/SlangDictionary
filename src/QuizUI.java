import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class QuizUI extends JFrame {
    private JTextField Question;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JRadioButton radioButton3;
    private JRadioButton radioButton4;
    private JButton submitButton;
    private JButton nextButton;
    private JTextPane Notification;
    private JPanel content;
    private slangDictionary dictionary;
    private HashMap.Entry<String, List<String>> result;
    private boolean radioChoose = false;
    private String chooseAnswer;
    ButtonGroup bg;
    public QuizUI(slangDictionary dictionary, String type){
        this.dictionary = dictionary;
        bg = new ButtonGroup();
        bg.add(radioButton1);
        bg.add(radioButton2);
        bg.add(radioButton3);
        bg.add(radioButton4);
        if (Objects.equals(type, "slang")){
            SlangQuizInit();
        }
        else if (Objects.equals(type, "definition")){
            DefQuizInit();
        }
        this.setContentPane(content);
        this.pack();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setVisible(true);

        radioButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseAnswer = radioButton1.getText();
                radioChoose = true;
            }
        });
        radioButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseAnswer = radioButton2.getText();
                radioChoose = true;
            }
        });
        radioButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseAnswer = radioButton3.getText();
                radioChoose = true;
            }
        });
        radioButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseAnswer = radioButton4.getText();
                radioChoose = true;
            }
        });
    }

    private void DefQuizInit() {
        getDefQuiz();
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!radioChoose){
                    Notification.setText("Please choose answer");
                }
                else if(Objects.equals(chooseAnswer, result.getKey())){
                    Notification.setText("Correct. The slang has definition '"+ result.getValue().get(0)+ "' is "+ chooseAnswer);
                }
                else {
                    Notification.setText("Incorrect. The slang has definition '"+ result.getValue().get(0)+ "' is "+ result.getKey());
                }
            }
        });
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Notification.setText("");
                getDefQuiz();
            }
        });
    }

    private void getDefQuiz() {
        result = dictionary.randomASlang();
        Question.setText("What slang for this definition '"+result.getValue().get(0)+"' ?");
        List<String> answerSet = dictionary.randomKey(result.getKey());
        Random r = new Random();
        int randomNum = r.nextInt(4);
        switch (randomNum){
            case 0 -> {
                radioButton1.setText(result.getKey());
                radioButton2.setText(answerSet.get(0));
                radioButton3.setText(answerSet.get(1));
                radioButton4.setText(answerSet.get(2));
            }
            case 1 -> {
                radioButton2.setText(result.getKey());
                radioButton1.setText(answerSet.get(0));
                radioButton3.setText(answerSet.get(1));
                radioButton4.setText(answerSet.get(2));
            }
            case 2 -> {
                radioButton3.setText(result.getKey());
                radioButton1.setText(answerSet.get(0));
                radioButton2.setText(answerSet.get(1));
                radioButton4.setText(answerSet.get(2));
            }
            case 3 -> {
                radioButton4.setText(result.getKey());
                radioButton1.setText(answerSet.get(0));
                radioButton2.setText(answerSet.get(1));
                radioButton3.setText(answerSet.get(2));
            }
        }
    }

    private void SlangQuizInit() {
        getSlangQuiz();
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!radioChoose){
                    Notification.setText("Please choose answer");
                }
                else if(Objects.equals(chooseAnswer, result.getValue().get(0))){
                    Notification.setText("Correct. The definition of "+result.getKey()+ " is "+ chooseAnswer);
                }
                else {
                    Notification.setText("Incorrect. The definition of "+result.getKey()+ " is "+ result.getValue().get(0));
                }
            }
        });
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Notification.setText("");
                getSlangQuiz();
            }
        });
    }

    private void getSlangQuiz() {
        result = dictionary.randomASlang();
        Question.setText("What meaning for the slang '"+result.getKey()+"' ?");
        List<String> answerSet = dictionary.randomDefinition(result.getKey());
        Random r = new Random();
        int randomNum = r.nextInt(4);
        switch (randomNum){
            case 0 -> {
                radioButton1.setText(result.getValue().get(0));
                radioButton2.setText(answerSet.get(0));
                radioButton3.setText(answerSet.get(1));
                radioButton4.setText(answerSet.get(2));
            }
            case 1 -> {
                radioButton2.setText(result.getValue().get(0));
                radioButton1.setText(answerSet.get(0));
                radioButton3.setText(answerSet.get(1));
                radioButton4.setText(answerSet.get(2));
            }
            case 2 -> {
                radioButton3.setText(result.getValue().get(0));
                radioButton1.setText(answerSet.get(0));
                radioButton2.setText(answerSet.get(1));
                radioButton4.setText(answerSet.get(2));
            }
            case 3 -> {
                radioButton4.setText(result.getValue().get(0));
                radioButton1.setText(answerSet.get(0));
                radioButton2.setText(answerSet.get(1));
                radioButton3.setText(answerSet.get(2));
            }
        }
    }
}
