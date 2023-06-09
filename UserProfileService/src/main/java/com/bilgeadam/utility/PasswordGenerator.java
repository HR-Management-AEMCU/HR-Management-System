package com.bilgeadam.utility;

import java.util.UUID;

public class PasswordGenerator {
    public static String generatePassword(){
        String code= UUID.randomUUID().toString();
        String[] data= code.split("-");
        String newCode="";
        for (String str:data){
            newCode += str.substring(0,3);
        }
        return newCode;
    }
}
