package com.project.upload.bean;

import java.util.List;

/**
 * @author ydy
 */

public class UploadResponse {

    /**
     * error_no : 0
     * results : [{"upload_id":"f43438c3788647d4b11843cbc1c4bb48","file_path":"/20180605/201806051528183146257.jpg","high_file_addr":"","low_file_addr":"","file_name":"20741-102.jpg","is_safe":"0","middle_file_addr":""}]
     * dsName : ["results"]
     * error_info : 上传文件成功
     */

    private String error_no;
    private String error_info;
    private List<ResultsBean> results;
    private List<String> dsName;

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

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public List<String> getDsName() {
        return dsName;
    }

    public void setDsName(List<String> dsName) {
        this.dsName = dsName;
    }

    public static class ResultsBean {
        /**
         * upload_id : f43438c3788647d4b11843cbc1c4bb48
         * file_path : /20180605/201806051528183146257.jpg
         * high_file_addr :
         * low_file_addr :
         * file_name : 20741-102.jpg
         * is_safe : 0
         * middle_file_addr :
         */

        private String upload_id;
        private String file_path;
        private String high_file_addr;
        private String low_file_addr;
        private String file_name;
        private String is_safe;
        private String middle_file_addr;

        public String getUpload_id() {
            return upload_id;
        }

        public void setUpload_id(String upload_id) {
            this.upload_id = upload_id;
        }

        public String getFile_path() {
            return file_path;
        }

        public void setFile_path(String file_path) {
            this.file_path = file_path;
        }

        public String getHigh_file_addr() {
            return high_file_addr;
        }

        public void setHigh_file_addr(String high_file_addr) {
            this.high_file_addr = high_file_addr;
        }

        public String getLow_file_addr() {
            return low_file_addr;
        }

        public void setLow_file_addr(String low_file_addr) {
            this.low_file_addr = low_file_addr;
        }

        public String getFile_name() {
            return file_name;
        }

        public void setFile_name(String file_name) {
            this.file_name = file_name;
        }

        public String getIs_safe() {
            return is_safe;
        }

        public void setIs_safe(String is_safe) {
            this.is_safe = is_safe;
        }

        public String getMiddle_file_addr() {
            return middle_file_addr;
        }

        public void setMiddle_file_addr(String middle_file_addr) {
            this.middle_file_addr = middle_file_addr;
        }

        @Override
        public String toString() {
            return "ResultsBean{" +
                    "upload_id='" + upload_id + '\'' +
                    ", file_path='" + file_path + '\'' +
                    ", high_file_addr='" + high_file_addr + '\'' +
                    ", low_file_addr='" + low_file_addr + '\'' +
                    ", file_name='" + file_name + '\'' +
                    ", is_safe='" + is_safe + '\'' +
                    ", middle_file_addr='" + middle_file_addr + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "UploadResponse{" +
                "error_no='" + error_no + '\'' +
                ", error_info='" + error_info + '\'' +
                ", results=" + results +
                ", dsName=" + dsName +
                '}';
    }
}
