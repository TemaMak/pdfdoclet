package com.github.TemaMak.pdfdoclet.writer;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.sun.source.util.DocTrees;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

public class PackageWriter extends BaseWriter {

    public static void  writePackage(Document document, PackageElement packageElement, DocTrees docTrees) throws DocumentException {
        Paragraph methodTitle = new Paragraph("Package: " + String.valueOf(packageElement.getQualifiedName()), pageTitleFont);
        try {
            document.add(methodTitle);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        for(Element e: packageElement.getEnclosedElements()){
            switch(e.getKind()){
                case CLASS:
                    TypeElement classElement = (TypeElement) e;
                    ClassWriter.writeClass(document,classElement,docTrees);
                    break;
                case ANNOTATION_TYPE:
                    AnnotationWriter.writeMethod(document,e,docTrees);
                    break;
            }

        }
    }


}
