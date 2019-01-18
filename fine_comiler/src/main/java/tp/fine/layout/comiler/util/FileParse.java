package tp.fine.layout.comiler.util;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileParse {
    public static List<File> searchFiles(File folder, final String keyword) {
        List<File> result = new ArrayList<File>();
        if (folder.isFile()) {
            result.add(folder);
        }

        File[] subFolders = folder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.isDirectory()) {
                    return true;
                }
                if (file.getName().toLowerCase().equalsIgnoreCase(keyword)) {
                    return true;
                }
                return false;
            }
        });

        if (subFolders != null) {
            for (File file : subFolders) {
                if (file.isFile()) {
                    // 如果是文件则将文件添加到结果列表中
                    result.add(file);
                    return  result;
                } else {
                    if (isIngone(file)){
                        continue;
                    }
                    List<File> abc = searchFiles(file, keyword);
                    //已经找到
                    if (abc!=null &&abc.size()!=0){
                        return abc;
                    }
                }
            }
        }

        return result;
    }


    static Set ingone ;
    static{
        ingone = new HashSet();
        ingone.add(".gradle");
        ingone.add(".git");
        ingone.add(".idea");
        ingone.add("gradle");
        ingone.add("build");
        ingone.add("java");
        ingone.add("androidTest");
        ingone.add("mipmap-xxhdpi");
        ingone.add("mipmap-xxxhdpi");
        ingone.add("values");
        ingone.add("assets");
        ingone.add("jniLibs");
    }

    /**
     * 是否忽视
     * @param file
     * @return
     */
    public static boolean isIngone(File file){
        return ingone.contains(file.getName());
    }


   /* public static void main(String[] args) {
        List<File> files = searchFiles(new File("C:\\work\\apt\\aptdemo\\app"), "activity_main.xml");
//        System.out.println("共找到:" + files.size() + "个文件");
        for (File file : files) {
            System.out.println(file.getAbsolutePath());
        }
    }*/
}
