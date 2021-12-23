import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class slangDictionary {
    private final HashMap<String, List<String>> dictionary=new HashMap<String,List<String>>();
    private final List<searchHistory> searchHistoryList =new ArrayList();
    public slangDictionary(){
        this.getData();
        this.getHistory();
    }
    public HashMap<String, List<String>> getDictionary() {
        return dictionary;
    }

    public List<searchHistory> getSearchHistoryList() {
        return searchHistoryList;
    }

    public void getData(){
        try
            {
                File file = new File("Data/slang.txt");
                FileReader fr=new FileReader(file);
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
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void resetData(){
        try
        {
            dictionary.clear();
            File file =new File("Data/root.txt");
            FileReader fr=new FileReader(file);
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
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void getHistory(){
        try
        {
            File file = new File("Data/history.txt");
            FileReader fr=new FileReader(file);
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
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public String getString(String key, List<String> value){
        StringBuilder definition = new StringBuilder();
        for(String def: value){
            definition.append(def).append(";");
        }
        String print = key + " : " +definition;
        System.out.println(print);
        return print;
    }
    public String getString(HashMap.Entry<String, List<String>> entry){
        String key = entry.getKey();
        StringBuilder definition = new StringBuilder();
        for(String def: entry.getValue()){
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
            ex.printStackTrace();
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
    public HashMap.Entry<String, List<String>> randomASlang(){
        List<String> keysAsArray = new ArrayList<String>(dictionary.keySet());
        Random r = new Random();
        int randomKey = r.nextInt(keysAsArray.size());
        return new AbstractMap.SimpleEntry<>(keysAsArray.get(randomKey), dictionary.get(keysAsArray.get(randomKey)));
    }
    public List<String> randomDefinition(String key){
        List<String> keysAsArray = new ArrayList<String>(dictionary.keySet());
        List<String> randomDef = new ArrayList<>();
        Random r = new Random();
        int count = 0;
        while (count < 3) {
            int randomKey = r.nextInt(keysAsArray.size());
            if (!Objects.equals(keysAsArray.get(randomKey), key)){
                int randomValue = r.nextInt(dictionary.get(keysAsArray.get(randomKey)).size());
                randomDef.add(dictionary.get(keysAsArray.get(randomKey)).get(randomValue));
                count++;
            }
        }
        return randomDef;
    }
    public List<String> randomKey(String key){
        List<String> keysAsArray = new ArrayList<String>(dictionary.keySet());
        List<String> randomKey = new ArrayList<>();
        Random r = new Random();
        int count = 0;
        while (count < 3) {
            int randomK = r.nextInt(keysAsArray.size());
            if (!Objects.equals(keysAsArray.get(randomK), key)){
                randomKey.add(keysAsArray.get(randomK));
                count++;
            }
        }
        return randomKey;
    }
    public static void main(String[] arg){
        slangDictionary slang = new slangDictionary();
        slang.getData();
        slang.getHistory();
        List<String> n = slang.randomDefinition(slang.randomASlang().getKey());
        for(String item: n){
            System.out.println(item);
        }
        slang.resetData();
        List<String> m = slang.randomKey(slang.randomASlang().getKey());
        for(String item: m){
            System.out.println(item);
        }
    }
}
