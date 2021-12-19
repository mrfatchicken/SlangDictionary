import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.*;

public class slangDictionary {
    private final HashMap<String, List<String>> dictionary=new HashMap<String,List<String>>();
    private final List<searchHistory> searchHistoryList =new ArrayList();
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
    public String getString(String key, List<String> value){
        String definition = "";
        for(String def: value){
            definition += def + ",";
        }
        String print = key + " : " +definition;
        System.out.println(print);
        return print;
    }
    public void writeHistory(String type, String content){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String dateInfo =  formatter.format(date);
        searchHistory history = new searchHistory(dateInfo,type, content);
        searchHistoryList.add(0, history);
        //
        
    }
    public void printAllWord(){
        System.out.println(dictionary.size());
        for (HashMap.Entry<String, List<String>> entry : dictionary.entrySet()) {
            String key = entry.getKey();
            List<String> value = entry.getValue();
            String definition = "";
            for(String def: value){
                definition += def + ",";
            }
            String print = key + " : " +definition;
            System.out.println(print);
        }
    }
    public HashMap.Entry<String, List<String>> findSlangWord(String word){
        HashMap.Entry<String, List<String>> temp = new AbstractMap.SimpleEntry<String, List<String>>(word,dictionary.get(word));
        return temp;
    }
    public HashMap<String, List<String>> findDefinition(String def){
        String[] definition = def.split(" ");
        HashMap<String, List<String>> list =new HashMap<String,List<String>>();
        for (HashMap.Entry<String, List<String>> entry : dictionary.entrySet()){
            for(String item: definition){
                for(String i: entry.getValue()) {
                    if(i.contains(item)) {
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
        HashMap<String, List<String>> listHashMap = slang.findDefinition("Evil Love One");
        for (HashMap.Entry<String, List<String>> entry : listHashMap.entrySet()){
            slang.getString(entry.getKey(), entry.getValue());
        }
    }
}
