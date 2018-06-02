package com.project.upload.bean;

/**
 * Author: ydy
 * Created: 2017/4/7 10:50
 * Description:
 */

public class FileUploadResponse {

    private String error_no;
    private String error_info;

    public String getError_no() {
        return error_no;
    }

    public void setError_no(String error_no) {
        this.error_no = error_no;
    }

    public String getError_info() {
        return error_info;
    }

    public void setError_info(String error_info) {
        this.error_info = error_info;
    }
}
