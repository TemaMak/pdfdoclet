package com.github.TemaMak.pdfdoclet.writer;

import com.itextpdf.text.Paragraph;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

public class ClassClassificator {

    public static ClassType classificateClass(TypeElement methodElement){

        for(TypeMirror tp: methodElement.getInterfaces()){
            if(tp.toString().contains("org.junit.rules")){
                return ClassType.JUNIT_RULE;
            }
        }

        if(methodElement.getSuperclass().toString().contains("org.junit.rules")){
            return ClassType.JUNIT_RULE;
        }

        return ClassType.SIMPLE;
    }

}
