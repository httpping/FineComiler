package tp.fine.layout.comiler;


import tp.fine.layout.FineBindId;
import tp.fine.layout.FineBindLayout;
import tp.fine.layout.FineLayout;
import tp.fine.layout.comiler.util.ParseR;
import tp.fine.layout.comiler.xml.LayoutCreated;
import tp.fine.layout.comiler.util.StringUtls;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;


/**
 *
 */
public class AnnotatedClass {
    //class
    public TypeElement mTypeElement;
    public Elements mElements;
    //日志打印
    public Messager mMessager;

    public String defaultPacakge;
    List<String> layouts;
    Filer mFiler;
    public AnnotatedClass(TypeElement typeElement, Elements elements, Messager messager, String deafultPackage, List<String> layouts, Filer mFiler) {
        mTypeElement = typeElement;
        mElements = elements;
        this.mMessager = messager;
        this.defaultPacakge = deafultPackage;
        this.layouts = layouts;
        this.mFiler = mFiler;
    }



    public void generateActivityFile() throws Exception {
        FineLayout fineLayout = mTypeElement.getAnnotation(FineLayout.class);


        synchronized (layouts) {

            String[] layoutsStr = fineLayout.layout();
            int index  = -1 ;



            for (String layout : layoutsStr){
                index ++;

                String packgeName = mElements.getPackageOf(mTypeElement).getQualifiedName().toString();
                String beanName = "FineLayout" + "$" + StringUtls.parseFristUpdateParamName(layout);

                //自定义名称
                if (fineLayout.className().length>index){
                    if (StringUtls.isNotEmpty(fineLayout.className()[index])) {
                        beanName = fineLayout.className()[index];
                    }
                }

                if (layouts.contains(beanName)) {
                    continue;
                }
                layouts.add(beanName);

                //generaClass
                TypeSpec.Builder injectClassBuild = TypeSpec.classBuilder(beanName)
                        .addModifiers(Modifier.PUBLIC);
                //add field
                LayoutCreated layoutCreated = new LayoutCreated(layout, fineLayout.rpackage());
                layoutCreated.createdField(injectClassBuild,fineLayout);

                //add method
                layoutCreated.createdMethod(injectClassBuild, defaultPacakge,fineLayout);

                layoutCreated.createdMethodInit(injectClassBuild,packgeName,beanName);


                if (fineLayout.showBind()) {
                    AnnotationSpec.Builder annB = AnnotationSpec.builder(FineBindLayout.class);
                    annB.addMember("layout", "R.layout." + layout);
                    injectClassBuild.addAnnotation(annB.build());
                }
                //生成代码
                TypeSpec injectClass = injectClassBuild.build();


                JavaFile.builder(packgeName, injectClass).addFileComment("created by fine layout auto ：$S ; " ,packgeName+"."+mTypeElement.getSimpleName().toString())
                        .addFileComment(" layout : R.layout.$S ;",layout).build().writeTo(mFiler);
            }

        }


    }


}
