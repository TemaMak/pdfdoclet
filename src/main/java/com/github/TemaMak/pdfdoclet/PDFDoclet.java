package com.github.TemaMak.pdfdoclet;


import com.sun.source.doctree.DocCommentTree;
import com.sun.source.util.DocTrees;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.StandardDoclet;

import javax.lang.model.element.Element;
import javax.lang.model.element.ModuleElement;
import java.util.List;
import java.util.Set;

public class PDFDoclet extends StandardDoclet {
    public PDFDoclet() {}

    @Override
    public boolean run(DocletEnvironment docEnv) {
        Set<? extends Element> inset = docEnv.getIncludedElements();
        DocTrees docTrees = docEnv.getDocTrees();

        for(Element el: inset){
            if (el instanceof ModuleElement) {
                System.out.println("=== MY PDFDOCLET MODULE: " + el.getClass().getSimpleName() +"|" + el.getKind());
                printElement(docTrees,el);
                List<? extends Element> packages = el.getEnclosedElements();
                for(Element packageElement: packages){
                    System.out.println("====== MY PDFDOCLET PACKAGE: " + packageElement.getSimpleName() + ":" + packageElement.getClass().getSimpleName() +"|" + packageElement.getKind());
                    List<? extends Element> classes = packageElement.getEnclosedElements();
                    for(Element classElement: classes){
                        System.out.println("========= MY PDFDOCLET CLASS: " + classElement.getSimpleName() + ":" + classElement.getClass().getSimpleName() +"|" + classElement.getKind());
                        List<? extends Element> classEnclosedElements= classElement.getEnclosedElements();
                        for(Element element : classEnclosedElements){
                            printElement(docTrees,element);
                        }
                    }
                }
            }


        }
        return true;
    }


    public void printElement(DocTrees trees, Element e) {
        DocCommentTree docCommentTree = trees.getDocCommentTree(e);
        if (docCommentTree != null) {
            System.out.println("Element (" + e.getKind() + ": "
                    + e + ") has the following comments:");
            System.out.println("Entire body: " + docCommentTree.getFullBody());
            System.out.println("Block tags: " + docCommentTree.getBlockTags());
        }
    }

}
