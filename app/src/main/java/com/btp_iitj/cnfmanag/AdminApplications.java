package com.btp_iitj.cnfmanag;

import android.widget.Button;

public class AdminApplications {
    String userId;
    public AdminApplications(){

    }
   public AdminApplications(String userId){
        this.userId=userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


}
