import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class slangDictionary {
    private final HashMap<String, List<String>> dictionary=new HashMap<String,List<String>>();
    private final List<searchHistory> searchHistoryList =new ArrayList();

    public HashMap<String, List<String>> getDictionary() {
        return dictionary;
    }

    public List<searchHistory> getSearchHistoryList() {
        return searchHistoryList;
    }

    public void getData(){
        try
            {
                File f=new File("Data/slang.txt");
                FileReader fr=new FileReader(f);
                BufferedReader br=new BufferedReader(fr);
                String line;
                while((line=br.readLine())!=null)
                {
                    if (line.contains("`"))
                    {
                        String[] s=line.split("`");
                        String[] tmp=s[1].split("\\|");
                        List<String> temp= Arrays.asList(tmp);
                        dictionary.put(s[0],temp);
                    }
                }
            fr.close();
            br.close();
        }
        catch (Exception ex)
        {
            System.out.println("ERROR"+ex);
        }
    }
    public void getHistory(){
        try
        {
            File f=new File("Data/history.txt");
            FileReader fr=new FileReader(f);
            BufferedReader br=new BufferedReader(fr);
            String line;
            while((line=br.readLine())!=null)
            {
                String[] strings = line.split("\\|");
                System.out.println(strings[0] + " " + strings[1]+ " " + strings[2]);
                searchHistory temp = new searchHistory(strings[0],strings[1],strings[2]);
                searchHistoryList.add(temp);
            }
            fr.close();
            br.close();
        }
        catch (Exception ex)
        {
            System.out.println("ERROR"+ex);
        }
    }
    public String getString(String key, List<String> value){
        StringBuilder definition = new StringBuilder();
        for(String def: value){
            definition.append(def).append(",");
        }
        String print = key + " : " +definition;
        System.out.println(print);
        return print;
    }
    public void saveHistory(){
        FileWriter fileWrite;
        try{
            fileWrite = new FileWriter("Data/history.txt");
            System.out.println("Writing to file");
            for(searchHistory item: searchHistoryList){
                fileWrite.write(item.toString());
            }
            System.out.println("Writing complete.");
            fileWrite.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void writeHistory(String type, String content){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String dateInfo =  formatter.format(date);
        searchHistory history = new searchHistory(dateInfo,type, content);
        searchHistoryList.add(0, history);
        saveHistory();
    }
    public boolean addSlang(String word, String definition){
        if(!dictionary.containsKey(word)){
            String[] strings = definition.split(",");
            List<String> temp = new ArrayList<>(Arrays.asList(strings));
            dictionary.put(word,temp);
            return true;
        }
        else return false;
    }
    public boolean editSlang(String word, String definition){
        if(dictionary.containsKey(word)){
            String[] strings = definition.split(",");
            List<String> temp = new ArrayList<>(Arrays.asList(strings));
            dictionary.put(word,temp);
            return true;
        }
        return false;
    }
    public void removeSlang(String word){
        dictionary.remove(word);
    }
    public void saveData(){
        try {
            File f = new File("./data/slang.txt");
            FileWriter fw = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(fw);
            for (String word: dictionary.keySet())
            {
                fw.write(word +"`");
                List<String> temp=dictionary.get(word);
                for (int i=0;i<temp.size();i++)
                {
                    fw.write(temp.get(i));
                    if (i+1<temp.size()) fw.write("|");
                }
                fw.write("\n");
            }
            fw.close();
            bw.close();
        }
        catch (Exception ex) {
            System.out.println("Error: "+ex);
        }
    }
    public void printAllWord(){
        System.out.println(dictionary.size());
        for (HashMap.Entry<String, List<String>> entry : dictionary.entrySet()) {
            String key = entry.getKey();
            List<String> value = entry.getValue();
            StringBuilder definition = new StringBuilder();
            for(String def: value){
                definition.append(def).append(",");
            }
            String print = key + " : " +definition;
            System.out.println(print);
        }
    }

    public HashMap.Entry<String, List<String>> findSlangWord(String word){
        this.writeHistory("Find slang word",word);
        return new AbstractMap.SimpleEntry<>(word, dictionary.get(word));
    }
    public HashMap<String, List<String>> findDefinition(String def){
        this.writeHistory("Find by definition",def);
        String[] definition = def.split(" ");
        HashMap<String, List<String>> list =new HashMap<String,List<String>>();
        for (HashMap.Entry<String, List<String>> entry : dictionary.entrySet()){
            for(String item: definition){
                for(String i: entry.getValue()) {
                    if(i.toLowerCase(Locale.ROOT).contains(item.toLowerCase(Locale.ROOT))) {
                        list.put(entry.getKey(), entry.getValue());
                    }
                }
            }
        }
        return list;
    }
    public static void main(String[] arg){
        slangDictionary slang = new slangDictionary();
        slang.getData();
        slang.getHistory();
        HashMap<String, List<String>> listHashMap = slang.findDefinition("smile");
        for (HashMap.Entry<String, List<String>> entry : listHashMap.entrySet()){
            slang.getString(entry.getKey(), entry.getValue());
        }
    }
}
