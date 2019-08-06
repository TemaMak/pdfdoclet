package com.github.TemaMak.pdfdoclet.writer;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.sun.source.doctree.DocCommentTree;
import com.sun.source.util.DocTrees;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AnnotationWriter extends BaseWriter{

    public static void  writeMethod(Document document, Element methodElement, DocTrees docTrees){

        Paragraph methodTitle = new Paragraph(String.valueOf(methodElement.getSimpleName()), subTitleFont);



        String usage = "@" + methodElement.getSimpleName();
        Paragraph usageParagraph = new Paragraph(usage, baseFont);
        try {
            PdfPTable table = new PdfPTable(1);
            table.setSpacingBefore(15f);
            table.setSpacingAfter(4f);
            PdfPCell headerCell = new PdfPCell(methodTitle);
            PdfPCell usageCell = new PdfPCell(usageParagraph);

            Style.headerCellStyle(headerCell);
            Style.valueCellStyle(usageCell);

            table.addCell(headerCell);
            table.addCell(usageCell);

            DocCommentTree docCommentTree = docTrees.getDocCommentTree(methodElement);
            if(docCommentTree != null){
                Paragraph classDescription = new Paragraph(String.valueOf(docCommentTree.getFullBody()), baseFont);
                PdfPCell descriptionCell = new PdfPCell(classDescription);
                Style.valueCellStyle(descriptionCell);
                table.addCell(descriptionCell);
            }

            document.add(table);

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

}
