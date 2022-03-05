package com.weiran.manage.response;

import java.util.List;


public class DiscriminateResp {

    /**
     * code : 0
     * message : success
     * result : {"faces":[{"score":0.987,"pts":[[225,195],[351,195],[351,389],[225,389]]},{"score":0.997,"pts":[[225,195],[351,195],[351,389],[225,389]]}],"similarity":0.87,"same":true}
     */

    private int code;
    private String message;
    private ResultBean result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * faces : [{"score":0.987,"pts":[[225,195],[351,195],[351,389],[225,389]]},{"score":0.997,"pts":[[225,195],[351,195],[351,389],[225,389]]}]
         * similarity : 0.87
         * same : true
         */

        private double similarity;
        private boolean same;
        private List<FacesBean> faces;

        public double getSimilarity() {
            return similarity;
        }

        public void setSimilarity(double similarity) {
            this.similarity = similarity;
        }

        public boolean isSame() {
            return same;
        }

        public void setSame(boolean same) {
            this.same = same;
        }

        public List<FacesBean> getFaces() {
            return faces;
        }

        public void setFaces(List<FacesBean> faces) {
            this.faces = faces;
        }

        public static class FacesBean {
            /**
             * score : 0.987
             * pts : [[225,195],[351,195],[351,389],[225,389]]
             */

            private double score;
            private List<List<Integer>> pts;

            public double getScore() {
                return score;
            }

            public void setScore(double score) {
                this.score = score;
            }

            public List<List<Integer>> getPts() {
                return pts;
            }

            public void setPts(List<List<Integer>> pts) {
                this.pts = pts;
            }
        }
    }
}
