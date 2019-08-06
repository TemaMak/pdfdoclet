package com.github.TemaMak.pdfdoclet.writer;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.sun.source.doctree.DocCommentTree;
import org.apache.commons.lang.StringEscapeUtils;

import javax.lang.model.element.ModuleElement;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class ContentsWriter extends BaseWriter{
    public static BaseFont baseFont;
    public static Font contentHeaderFont = FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD, BaseColor.BLACK);
    public static Font descriptionHeaderFont = FontFactory.getFont(FontFactory.HELVETICA, 16, BaseColor.BLACK);

    public static void WriteContentElement(Document document, ModuleElement element, DocCommentTree docCommentTree) throws DocumentException, UnsupportedEncodingException {
        Paragraph paragraphOne = new Paragraph(String.valueOf(element.getSimpleName()), contentHeaderFont);
        document.add(paragraphOne);

        String ansiString = convertString(String.valueOf(docCommentTree.getFullBody()));
        Paragraph paragraphTwo = new Paragraph(String.valueOf(docCommentTree.getFullBody()), descriptionHeaderFont);
        document.add(paragraphTwo);
    }

}
