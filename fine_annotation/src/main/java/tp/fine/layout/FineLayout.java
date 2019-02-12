package tp.fine.layout;
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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 项目名称: z
 * 类描述： 生成代码块,编译结束后丢弃
 * 创建时间:2019/1/17 17:25
 *
 * @author tanping
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface FineLayout {

    /**
     * layout
     * @return
     */
    String[] layout() default "";


    /**
     * R   R.id
     * @return
     */
    String rpackage() default "";


    /**
     * 忽略的View Id
     * @return
     */
    String[] ignoreId() default "";


    /**
     * 根据自定义文件名生成， name 和 layout的长度 要相同，否则报错
     * @return
     */
    String[] className() default "";


    /**
     * 忽略View
     * @return
     */
    String[] ignoreView() default "";


    /**
     * 是否显示 bind 资源
     * @return
     */
    boolean showBind() default  true;

}
