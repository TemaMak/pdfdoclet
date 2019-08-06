package com.github.TemaMak.pdfdoclet;


import com.github.TemaMak.pdfdoclet.writer.ContentsWriter;
import com.github.TemaMak.pdfdoclet.writer.MethodWriter;
import com.github.TemaMak.pdfdoclet.writer.ModuleWriter;
import com.github.TemaMak.pdfdoclet.writer.PackageWriter;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.source.doctree.DocCommentTree;
import com.sun.source.util.DocTrees;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.StandardDoclet;
import org.apache.commons.lang.StringEscapeUtils;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.PackageElement;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PDFDoclet extends StandardDoclet {
    public PDFDoclet() {}

    @Override
    public boolean run(DocletEnvironment docEnv) {
        Set<? extends Element> inset = docEnv.getIncludedElements();
        DocTrees docTrees = docEnv.getDocTrees();

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("api-docs.pdf"));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        document.open();

        for(Element el: inset) {
            if (el instanceof ModuleElement) {
                ModuleElement moduleElement = (ModuleElement) el;
                try {
                    ContentsWriter.WriteContentElement(
                            document,
                            moduleElement,
                            docTrees.getDocCommentTree(moduleElement)
                    );
                } catch (DocumentException | UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }

        for(Element el: inset) {
            if (el instanceof ModuleElement) {
                ModuleElement moduleElement = (ModuleElement) el;
                document.newPage();
                ModuleWriter.writeModuleHeader(document,moduleElement,docTrees);

                for(Element packageElement: el.getEnclosedElements()){
                    if (packageElement instanceof PackageElement) {
                        PackageElement p = (PackageElement) packageElement;
                        try {
                            PackageWriter.writePackage(document,p,docTrees);
                        } catch (DocumentException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }

        document.close();
        return true;
    }

    public void printElement(DocTrees trees, Element e) {
        DocCommentTree docCommentTree = trees.getDocCommentTree(e);
        if (docCommentTree != null) {
            System.out.println("Element (" + e.getKind() + ": "
                    + e + ") has the following comments:");
            System.out.println("Entire body: " + StringEscapeUtils.unescapeJava(String.valueOf(docCommentTree.getFullBody())));
            System.out.println("Entire в ютф печатает? : " + docCommentTree.getFullBody());
            System.out.println("Block tags: " + docCommentTree.getBlockTags());
        }
    }

}
