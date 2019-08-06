package com.github.TemaMak.pdfdoclet.writer;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.sun.source.doctree.DocCommentTree;
import com.sun.source.util.DocTrees;

import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.List;

public class ClassWriter extends BaseWriter {

    public static void writeClass(Document document, TypeElement methodElement, DocTrees docTrees) throws DocumentException {
        Paragraph classTitle = new Paragraph("Class: " + String.valueOf(methodElement.getSimpleName()), subTitleFont);
        document.add(classTitle);

        DocCommentTree docCommentTree = docTrees.getDocCommentTree(methodElement);
        if(docCommentTree != null){
            Paragraph classDescription = new Paragraph(String.valueOf(docCommentTree.getFullBody()), baseFont);
            document.add(classDescription);
        }

        switch (ClassClassificator.classificateClass(methodElement)) {
            case JUNIT_RULE:
                documentateJUNITRule(document, methodElement);
                break;
            case SIMPLE:
            default:
                documentateSimpleClass(document,methodElement,docTrees);
                break;
        }
        document.add( Chunk.NEWLINE );
        document.add( Chunk.NEWLINE );

    }

    public static void documentateJUNITRule(Document document, TypeElement typeElement) throws DocumentException {
        String usegeJUNITRuleText = "It is JUNIT4 Rule. You don't work with this class directly\n"
                + "Just register rule with @Rule annotation:"
                ;

        List<String> usageConstructor = new ArrayList<>();

        for(Element e: typeElement.getEnclosedElements()){
            if(e.getKind() == ElementKind.CONSTRUCTOR){
                String usageJUNITRuleTextExample = "@Rule\n"
                        + typeElement.getSimpleName() + " " + Character.toLowerCase(typeElement.getSimpleName().charAt(0)) + typeElement.getSimpleName().toString().substring(1)
                        + " = new " +  typeElement.getSimpleName() + "("
                        ;

                ExecutableElement executableElement = (ExecutableElement) e;
                List<String> params = new ArrayList<>();
                for(VariableElement el: executableElement.getParameters()){
                    params.add(el.asType().toString() + " " + el.getSimpleName().toString());
                }

                usageJUNITRuleTextExample = usageJUNITRuleTextExample + String.join(",",params) + ")";
                usageConstructor.add(usageJUNITRuleTextExample);
            }
        }
        String usageConstructorFull = String.join("\n\n OR \n\n",usageConstructor);


        Paragraph usage = new Paragraph(usegeJUNITRuleText, baseFont);
        Paragraph usageExample = new Paragraph(usageConstructorFull, italicFont);

        document.add(usage);
        document.add(usageExample);
    }

    public static void documentateSimpleClass(Document document, TypeElement typeElement, DocTrees docTrees) throws DocumentException {
        for(Element e: typeElement.getEnclosedElements()){
            if (e.getKind() == ElementKind.METHOD) {
                MethodWriter.writeMethod(document,e,docTrees);

            }
        }
    }
}
