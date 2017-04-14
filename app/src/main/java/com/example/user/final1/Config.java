package com.example.user.final1;

/**
 * Created by User on 06-Jan-17.
 */

public class Config {


    //Address of our scripts of the CRUD

    public static final String URL_ADD="http://192.168.56.1/AndroidDB1/addDrug.php";
    public static final String URL_GET_ALL = "http://192.168.56.1/AndroidDB1/getAllDrug.php";
    public static final String URL_BACK_CHECK = "http://192.168.56.1/AndroidDB1/backCheck.php";
    //public static final String URL_GET_EMP = "http://192.168.56.1/AndroidDB1/getEmp.php";
    //public static final String URL_UPDATE_EMP = "http://192.168.56.1/AndroidDB1/updateEmp.php";
    //public static final String URL_DELETE_EMP = "http://192.168.56.1/AndroidDB1/deleteEmp.php";


    //Keys that will be used to send the request to php scripts
    public static final String KEY_ENTRY = "ENTRY";
    public static final String KEY_DRUG_NAME = "DRUG_NAME";
    public static final String KEY_EXP = "EXP";
    public static final String KEY_REM = "Rem_Days";

    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_ENTRY = "ENTRY";
    public static final String TAG_DRUG_NAME = "DRUG_NAME";
    public static final String TAG_EXP = "EXP";
    public static final String TAG_REM = "Rem_Days";

    //employee id to pass with intent
    public static final String ENTRY = "ENTRY";
}
