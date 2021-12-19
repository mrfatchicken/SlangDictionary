import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class slangDictionary {
    private HashMap<String, List<String>> dictionary=new HashMap<String,List<String>>();
    private List<String> searchHistory =new ArrayList();
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
    public static void main(String[] arg){
        slangDictionary slang = new slangDictionary();
        slang.getData();
        slang.printAllWord();
    }
}
