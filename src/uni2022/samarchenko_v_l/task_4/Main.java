package uni2022.samarchenko_v_l.task_4;

import uni2022.samarchenko_v_l.task_4.treemap.ITreeRBMap;
import uni2022.samarchenko_v_l.task_4.treemap.TreeRBMap;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    // http://webcode.me
    //

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        ITreeRBMap<String, String> map = new TreeRBMap<>();

        System.out.println("Enter url: ");
        while (scanner.hasNextLine()) {

            String url = scanner.nextLine();

            if (map.containsKey(url)) {
                System.out.println("contain");
                System.out.println(map.get(url));
            } else {
                System.out.println("    New url");
                String html = ReadWebPage.getHTML(url);
                map.put(url, html);
                System.out.println(html);
            }
        }

    }
}


//        while (!scanner.nextLine().equals("*")) {
//            System.out.println("Enter url: ");
//            String url = scanner.nextLine();
//
//            if (map.containsKey(url)) {
//                System.out.println(map.get(url));
//            } else {
//                System.out.println("    New url");
//                String html = ReadWebPage.getHTML(url);
//                map.put(url, html);
//                System.out.println(html);
//            }
//        }




//    String url = "http://webcode.me";
//        System.out.println(map.containsKey(url));
//        String html = ReadWebPage.getHTML(url);
//        map.put(url, html);
//        System.out.println(map.containsKey(url));