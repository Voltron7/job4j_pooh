package ru.job4j.pooh;

public class Req {
    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    public static Req of(String content) {
        String[] line = content.split("\\n");
        String[] requestArray = line[0].split(" ");
        String httpRequestType = requestArray[0];
        String[] modeAndSourceAndParam = requestArray[1].split("/");
        String poohMode = modeAndSourceAndParam[1];
        String sourceName = modeAndSourceAndParam[2];
        String param;
        if (modeAndSourceAndParam.length == 4) {
            param = modeAndSourceAndParam[3];
        } else {
            param = line[line.length - 1].trim();
        }
        return new Req(httpRequestType, poohMode, sourceName, param);
    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }
}
