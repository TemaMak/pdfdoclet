package com.github.TemaMak.pdfdoclet.writer;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import org.apache.commons.lang.StringEscapeUtils;

import java.io.UnsupportedEncodingException;

public class BaseWriter {
    public static Font baseFont = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
    public static Font pageTitleFont = FontFactory.getFont(FontFactory.HELVETICA, 24, Font.BOLD, BaseColor.BLACK);
    public static Font subTitleFont = FontFactory.getFont(FontFactory.HELVETICA, 18, BaseColor.BLACK);


    public static String convertString(String originalString){
        final String unescape = StringEscapeUtils.unescapeJava(originalString);
        try {
            String ansiString = new String(unescape.getBytes("UTF-16"), "windows-1252");
            System.out.println("1252:" + ansiString);
            return  ansiString;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return  unescape;
    }

}
