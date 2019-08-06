package com.github.TemaMak.pdfdoclet.writer;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.sun.source.doctree.DocCommentTree;
import com.sun.source.doctree.DocTree;
import com.sun.source.util.DocTrees;

import javax.lang.model.element.ModuleElement;
import java.util.List;

public class ModuleWriter extends BaseWriter {


    public static void writeModuleHeader(Document document, ModuleElement element, DocTrees docTrees){
        Paragraph pageTitle = new Paragraph("Module: " + String.valueOf(element.getSimpleName()), pageTitleFont);
        try {
            document.add(pageTitle);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        DocCommentTree docCommentTree = docTrees.getDocCommentTree(element);
        List<? extends DocTree> tags = docCommentTree.getBlockTags();
        for(DocTree tag: tags){
            final String tagOriginal = tag.toString();
            String[] parsedTag = tagOriginal.split(" ",2);
            String tagHead = parsedTag[0];
            String tagBody = parsedTag[1];

            if(tagHead.contains("mavenArtefactID")){
                TagWriter.writeTag(document,tagHead,tagBody);
            }
        }
    }

}
