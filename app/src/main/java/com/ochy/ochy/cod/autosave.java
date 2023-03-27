package com.ochy.ochy.cod;

public class autosave {
    public static String someProperty;
    public static String getSomeProperty() {
        return someProperty;
    }
    public static void setSomeProperty(String newProperty){
        someProperty = newProperty;
    }

    public static String dbSetUserTableName;
    public static String getDbSetUserTableName() {
        return dbSetUserTableName;
    }
    public static void setDbSetUserTableName(String newName){someProperty = newName;}

    public static String url;
    public static String getPathOfPhoto() {
        return url;
    }
    public static void setPathOfPhoto(String path){url = path;}

    public static String name;
    public static String getName() {
        return name;
    }
    public static void setName(String s){name = s;}

    public static String urls;
}
