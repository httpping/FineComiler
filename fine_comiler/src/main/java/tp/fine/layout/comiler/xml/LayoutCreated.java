package tp.fine.layout.comiler.xml;
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

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.Modifier;

import tp.fine.layout.FineBindId;
import tp.fine.layout.FineBindLayout;
import tp.fine.layout.FineLayout;
import tp.fine.layout.comiler.util.FileParse;
import tp.fine.layout.comiler.util.StringUtls;

/**
 * 项目名称: z
 * 类描述：
 * 创建时间:2019/1/.22 10:17
 *
 * @author tanping
 */
public class LayoutCreated {

    //最后一次的路径
    public static String lastLayoutPath;

    private List<ViewModel> viewModels;
    String layout;
    String r;

    public LayoutCreated(String layout){
        this.layout = layout;
    }

    public LayoutCreated(String layout, String rpackage) throws Exception {
        this.layout = layout;
        this.r = rpackage;
        parse();
    }


    public void parse() throws Exception {
        List<File> files = null;
        //快速查找，提高编译速度
        if(lastLayoutPath!=null){
            files = FileParse.searchFiles(new File(lastLayoutPath),layout+".xml");
        }

        //继续查找
        if (StringUtls.isEmpty(files)) {
            File file = new File("fine");
            if (!file.exists()) {
                file.mkdirs();
            }
            String path = file.getAbsolutePath();
            path = path.substring(0, path.length() - "fine".length() - 1);
            files = FileParse.searchFiles(new File(path), layout + ".xml");
            if (StringUtls.isEmpty(files)) {
                throw new IllegalArgumentException("no find layout:" + layout);
            }
        }
        lastLayoutPath = files.get(0).getParentFile().getAbsolutePath();
        viewModels = ViewXmlParse.parseXml(files.get(0).getAbsolutePath());
    }


    public  void createdField(TypeSpec.Builder typeSpec, FineLayout fineLayout){
        if (viewModels == null){
            return;
        }

        for (int i = 0; i < viewModels.size(); i++) {
            ViewModel viewModel = viewModels.get(i);

            String oldName =  viewModel.getId().replace("@id/","").replace("@+id/", "");
            String name = StringUtls.parseFristLowParamName(oldName);
            String typeName = viewModel.getTypeName();

            if (isIgnoreId(oldName,fineLayout)){
                continue;
            }
            if (isIgnoreView(typeName,fineLayout)){
                continue;
            }

            FieldSpec.Builder fieldBuilder = FieldSpec.builder(getViewClassName(typeName), name, Modifier.PUBLIC);
            if (fineLayout.showBind()) {
                AnnotationSpec.Builder annB = AnnotationSpec.builder(FineBindId.class);
                annB.addMember("id", "R.id." + oldName);
                fieldBuilder.addAnnotation(annB.build());
            }
            typeSpec.addField(fieldBuilder.build());
//            System.out.println("private " + viewModel.getTypeName() +" " + name +" ;");

        }
    }

    public  void createdMethod(TypeSpec.Builder typeSpec, String rpackage, FineLayout fineLayout){
        if (viewModels == null) {
            return;
        }
        MethodSpec.Builder layoutMenthod = MethodSpec.methodBuilder("bind")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(getViewClass(), "rootView", Modifier.FINAL);

        for (int i = 0; i < viewModels.size(); i++) {
            ViewModel viewModel = viewModels.get(i);

            String oldName =  viewModel.getId().replace("@+id/", "");
            String name = StringUtls.parseFristLowParamName(oldName);
            String typeName = viewModel.getTypeName();

            if (isIgnoreId(oldName,fineLayout)){
                continue;
            }
            if (isIgnoreView(typeName,fineLayout)){
                continue;
            }

            ClassName r ;
            if (StringUtls.isEmpty(this.r)){
                r = ClassName.get(rpackage,"R");
            }else {
                r = ClassName.get(this.r,"R");
            }

            layoutMenthod.addStatement("this."+name +" = rootView.findViewById($T.id." +oldName+")",r);
        }

        typeSpec.addMethod(layoutMenthod.build());
    }

    public void createdMethodInit(TypeSpec.Builder typeSpec,String packageName,String beanName){
        ClassName className = ClassName.get(packageName, beanName);

        MethodSpec.Builder initMenthod = MethodSpec.methodBuilder("init")
                .addModifiers(Modifier.PUBLIC,Modifier.STATIC)
                .returns(className)
                .addParameter(getViewClass(), "rootView", Modifier.FINAL);
        initMenthod.addStatement( "$T bean = new $T()",className ,className);
        initMenthod.addStatement("bean.bind(rootView)");
        initMenthod.addStatement("return bean");

        typeSpec.addMethod(initMenthod.build());

    }


    public ClassName getViewClassName(String typeName){
        if (typeName.indexOf(".")>0){
            int len = typeName.length();
            int pos = typeName.lastIndexOf(".");
            String pn = typeName.substring(0,pos);
            String name = typeName.substring(pos+1,len);
            return ClassName.get(pn,name);
        }

        if (typeName.equalsIgnoreCase("View")){
            return getViewClass();
        }


        return ClassName.get("android.widget", typeName);
    }

    public ClassName getViewClass(){
        return ClassName.get("android.view","View");
    }


    /**
     * 忽略的View
     */
    static Set ignore ;
    static{
        ignore = new HashSet();
        ignore.add("include");
        ignore.add("ViewStub");
        ignore.add("merge");

    }


    /**
     * 忽略View
     * @param view
     * @param fineLayout
     * @return
     */
    public boolean isIgnoreView(String view,FineLayout fineLayout){
        boolean result = ignore.contains(view);
        if (result == false) {
            String[] views = fineLayout.ignoreView();
            if (views!=null){
                for (String string:views){
                    //忽略
                    if (view.equalsIgnoreCase(string)){
                        return true;
                    }
                }
            }
        }
        return result;
    }

    /**
     * 忽略ID
     * @param id
     * @param fineLayout
     * @return
     */
    public boolean isIgnoreId(String id,FineLayout fineLayout){
        boolean result =false;
        String[] ids = fineLayout.ignoreId();
        if (ids!=null){
            for (String string:ids){
                //忽略
                if (id.equalsIgnoreCase(string)){
                    return true;
                }
            }
        }
        return result;
    }

}
