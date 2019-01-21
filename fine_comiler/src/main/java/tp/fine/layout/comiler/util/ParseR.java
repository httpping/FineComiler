package tp.fine.layout.comiler.util;
/*

                   _ooOoo_
                  o8888888o
                  88" . "88
                  (| -_- |)
                  O\  =  /O
               ____/`---'\____
             .'  \\|     |//  `.
            /  \\|||  :  |||//  \
           /  _||||| -:- |||||-  \
           |   | \\\  -  /// |   |
           | \_|  ''\---/''  |   |
           \  .-\__  `-`  ___/-. /
         ___`. .'  /--.--\  `. . __
      ."" '<  `.___\_<|>_/___.'  >'"".
     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
     \  \ `-.   \_ __\ /__ _/   .-` /  /
======`-.____`-.___\_____/___.-`____.-'======
                   `=---='
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
         佛祖保佑       永无BUG

*/

import java.lang.reflect.Field;

/**
 * 项目名称: z
 * 类描述：
 * 创建时间:2019/1/21 9:25
 *
 * @author tanping
 */
public class ParseR {


    public static String parseLayout(String rpackage ,int value) throws ClassNotFoundException, IllegalAccessException {

        Class cls = Class.forName(rpackage+".R.layout");

        Field[] fileds = cls.getFields();
        for (Field field :fileds){
            if (field.getInt(cls) == value){
                return field.getName();
            }
            System.out.println( field.getName() + " " +field.getInt(cls));
        }

        return  null;
    }


    public static String parseId(String rpackage ,int value) throws ClassNotFoundException, IllegalAccessException {

        Class cls = Class.forName(rpackage+".R.id");

        Field[] fileds = cls.getFields();
        for (Field field :fileds){
            if (field.getInt(cls) == value){
                return field.getName();
            }
            System.out.println( field.getName() + " " +field.getInt(cls));
        }

        return  null;
    }
}
