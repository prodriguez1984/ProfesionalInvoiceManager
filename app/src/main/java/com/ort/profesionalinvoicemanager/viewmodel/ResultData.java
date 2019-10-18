
package com.ort.profesionalinvoicemanager.viewmodel;

import java.util.ArrayList;

public class ResultData {
    private String messagge;
    private boolean error;
    private ArrayList<String> errorList;

    public ResultData(){
        errorList = new ArrayList<>();
    }


    public ResultData(String messagge, boolean error){
        this();
        this.messagge = messagge;
        this.error = error;
    }

    public String getMessagge() {
        return messagge;
    }

    public void setMessagge(String messagge) {
        this.messagge = messagge;
    }

    public void setError(boolean error){
       this.error = error;

    }

    public boolean isError() {
        return error;
    }
}

