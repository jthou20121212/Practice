package com.jthou.generics;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class RegexStudy {

    public static void main(String[] args) {
        String regex = "\\\\n\\n\\\\";
        String[] subjects = {"\n", "\\n", "\\\n", "\\\\n"};
        Pattern pattern = Pattern.compile(regex);
        for (String subject : subjects) {
            Matcher matcher = pattern.matcher(subject);
            while (matcher.find()) {
                System.out.println(matcher.group());
            }
        }
    }

}
