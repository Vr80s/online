import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Description：著作权代码
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 上午 10:56 2017/10/11 0011
 **/
public class Zzqhs {
    public static long j=0;
    public static void main(String[] args) {

//        String forderPath = "E:\\hainan-code\\pc\\attachment-center-server\\src\\main\\java";
        String forderPath = "E:\\hainan-code\\pc\\online-web\\src\\main\\java";
        List<String> resultList = new ArrayList<>();
        ergodic(new File(forderPath), resultList);
        for (int i = 0; i < resultList.size(); i++) {
            System.out.println(resultList.get(i));
//            if(j<45*60)
            cr(resultList.get(i),"C:\\yu.doc");
        }
        System.out.println(j);
    }

    private static List<String> ergodic(File file, List<String> resultFileName){
        File[] files = file.listFiles();
        if(files==null)return resultFileName;// 判断目录下是不是空的
        for (File f : files) {
            if(f.isDirectory()){// 判断是否文件夹
//                resultFileName.add(f.getPath());
                ergodic(f,resultFileName);// 调用自身,查找子目录
            }else
                resultFileName.add(f.getPath());
        }
        return resultFileName;
    }

    public static void cr(String readFile,String writeFile) {
        try {
            // read file content from file
            StringBuffer sb= new StringBuffer("");

            FileReader reader = new FileReader(readFile);
            BufferedReader br = new BufferedReader(reader);

            String str = null;

            while((str = br.readLine()) != null) {
                sb.append(str+"\n");
                j++;
//                System.out.println(str);
            }

            br.close();
            reader.close();

            // write string to file
//            FileWriter writer = new FileWriter(writeFile,true);
//            BufferedWriter bw = new BufferedWriter(writer);
//            bw.write(sb.toString());
//
//            bw.close();
//            writer.close();
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}
