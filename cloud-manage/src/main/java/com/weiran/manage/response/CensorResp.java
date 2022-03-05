package com.weiran.manage.response;

import java.util.List;


public class CensorResp {

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
         * suggestion : review
         * scenes : {"pulp":{"suggestion":"review","details":[{"suggestion":"review","label":"sexy","score":0.95752}]}}
         */

        private String suggestion;
        private ScenesBean scenes;

        public String getSuggestion() {
            return suggestion;
        }

        public void setSuggestion(String suggestion) {
            this.suggestion = suggestion;
        }

        public ScenesBean getScenes() {
            return scenes;
        }

        public void setScenes(ScenesBean scenes) {
            this.scenes = scenes;
        }

        public static class ScenesBean {
            /**
             * pulp : {"suggestion":"review","details":[{"suggestion":"review","label":"sexy","score":0.95752}]}
             */

            private PulpBean pulp;

            public PulpBean getPulp() {
                return pulp;
            }

            public void setPulp(PulpBean pulp) {
                this.pulp = pulp;
            }

            public static class PulpBean {
                /**
                 * suggestion : review
                 * details : [{"suggestion":"review","label":"sexy","score":0.95752}]
                 */

                private String suggestion;
                private List<DetailsBean> details;

                public String getSuggestion() {
                    return suggestion;
                }

                public void setSuggestion(String suggestion) {
                    this.suggestion = suggestion;
                }

                public List<DetailsBean> getDetails() {
                    return details;
                }

                public void setDetails(List<DetailsBean> details) {
                    this.details = details;
                }

                public static class DetailsBean {
                    /**
                     * suggestion : review
                     * label : sexy
                     * score : 0.95752
                     */

                    private String suggestion;
                    private String label;
                    private double score;

                    public String getSuggestion() {
                        return suggestion;
                    }

                    public void setSuggestion(String suggestion) {
                        this.suggestion = suggestion;
                    }

                    public String getLabel() {
                        return label;
                    }

                    public void setLabel(String label) {
                        this.label = label;
                    }

                    public double getScore() {
                        return score;
                    }

                    public void setScore(double score) {
                        this.score = score;
                    }
                }
            }
        }
    }
}
