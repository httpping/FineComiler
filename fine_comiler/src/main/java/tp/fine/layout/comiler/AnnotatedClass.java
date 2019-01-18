package tp.fine.layout.comiler;


import tp.fine.layout.FineLayout;
import tp.fine.layout.comiler.xml.LayoutCreated;
import tp.fine.layout.comiler.util.StringUtls;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.util.List;

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
            for (String layout : layoutsStr){

                if (layouts.contains(layout)) {
                    continue;
                }
                layouts.add(layout);

                //generaClass
                TypeSpec.Builder injectClassBuild = TypeSpec.classBuilder("FineLayout" + "$" + StringUtls.parseFristUpdateParamName(layout))
                        .addModifiers(Modifier.PUBLIC);
                //add field
                LayoutCreated layoutCreated = new LayoutCreated(layout, fineLayout.rpackage());
                layoutCreated.createdField(injectClassBuild);

                //add method
                layoutCreated.createdMethod(injectClassBuild, defaultPacakge);

                //生成代码
                TypeSpec injectClass = injectClassBuild.build();

                String packgeName = mElements.getPackageOf(mTypeElement).getQualifiedName().toString();
                JavaFile.builder(packgeName, injectClass).addFileComment("created by fine layout auto").build().writeTo(mFiler);
            }

        }


    }



/*
    public JavaFile generateActivityFile() {
        // build inject method
        MethodSpec.Builder injectMethod = MethodSpec.methodBuilder(TypeUtil.METHOD_NAME)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(TypeName.get(mTypeElement.asType()), "activity", Modifier.FINAL);
        injectMethod.addStatement("Toast.makeText" +
                "(activity, $S,android.widget.Toast.LENGTH_SHORT).show();", "from build");

        ClassName toast = ClassName.get("android.widget","Toast");
        ClassName r = ClassName.get("com.example.spc.myapplication","R");

        TestAnno a = mTypeElement.getAnnotation(TestAnno.class);
        String layout =  a.layout();
        MethodSpec.Builder layoutMenthod = MethodSpec.methodBuilder(layout)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(TypeName.get(mTypeElement.asType()), "activity", Modifier.FINAL);
        layoutMenthod.addStatement("$T.makeText" +
                "(activity, $S,$T.LENGTH_SHORT).show();",toast, "from build"+layout,toast);
        layoutMenthod.addStatement("int id =$T.id.abc ;",r);

        if (true) {
//            throw new IllegalArgumentException("layout not fund ");
            mMessager.printMessage(Diagnostic.Kind.NOTE,"layout not found ",mTypeElement);
        }
        FieldSpec.Builder fieldBuilder = FieldSpec.builder(TypeName.INT, a.layout(), Modifier.PUBLIC);
        //generaClass
        TypeSpec injectClass = TypeSpec.classBuilder(mTypeElement.getSimpleName() + "$$InjectActivity")
                .addModifiers(Modifier.PUBLIC)
                .addMethod(injectMethod.build())
                .addMethod(layoutMenthod.build())
                .addField(fieldBuilder.build())
                .build();
        String packgeName = mElements.getPackageOf(mTypeElement).getQualifiedName().toString();
        return JavaFile.builder(packgeName, injectClass).skipJavaLangImports(true)
                .build();
    }*/



}