package com.github.TemaMak.pdfdoclet.writer;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;

public class TagWriter extends BaseWriter {

    public static void writeTag(Document document, String tagHead, String tagBody){
        switch(tagHead){
            case "@mavenArtefactID":
                writeMavenDependcy(document,tagBody);
                break;
        }
    }

    public static void writeMavenDependcy(Document document,String tagBody){
        Paragraph usageTitle = new Paragraph("Maven usage:", subTitleFont);
        String mavenUsageBody = "<dependency>\n";
            mavenUsageBody = mavenUsageBody + "    <groupId>...</groupId>\n";
            mavenUsageBody = mavenUsageBody + "    <artifactId>" + tagBody + "</artifactId>\n";
            mavenUsageBody = mavenUsageBody + " </dependency>";

        Paragraph usageBody = new Paragraph(mavenUsageBody, italicFont);
        try {
            document.add(usageTitle);
            document.add(usageBody);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

}
