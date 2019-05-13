package com.zyb.traffic;

public class LogPreparser {
    private String ServTime;
    private String serverIp;
    private String method;
    private String uriStem;
    private String queryString;
    private int serverPort;
    private String clientIp;
    private String userAgent;
    private int profileId;
    private String command;
    private int year;
    private int month;
    private int day;

    public String getServTime() {
        return ServTime;
    }

    public void setServTime(String servTime) {
        ServTime = servTime;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUriStem() {
        return uriStem;
    }

    public void setUriStem(String uriStem) {
        this.uriStem = uriStem;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    @Override
    public String toString() {
        return "LogPreparser{" +
                "ServTime='" + ServTime + '\'' +
                ", serverIp='" + serverIp + '\'' +
                ", method='" + method + '\'' +
                ", uriStem='" + uriStem + '\'' +
                ", queryString='" + queryString + '\'' +
                ", serverPort=" + serverPort +
                ", clientIp='" + clientIp + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", profileId=" + profileId +
                ", command='" + command + '\'' +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                '}';
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    /*
        解析方法
     */
    public static LogPreparser parse(String line){
        if(line.startsWith("#")){
            return null;//排除注释行
        }

        LogPreparser logPreparser=new LogPreparser();
        String[] fields=line.split(" ");

        logPreparser.setServTime(fields[0]+" "+fields[1]);
        logPreparser.setServerIp(fields[2]);
        logPreparser.setMethod(fields[3]);
        logPreparser.setUriStem(fields[4]);
        logPreparser.setQueryString(fields[5]);
        logPreparser.setServerPort(Integer.parseInt(fields[6]));
        String profileId=fields[7];
        logPreparser.setProfileId(Integer.valueOf(profileId.replace("-","0")));
        logPreparser.setClientIp(fields[8]);
        logPreparser.setUserAgent(fields[9].replace("+"," "));
        logPreparser.setCommand(fields[5].split("&")[1].split("=")[1]);
        logPreparser.setYear(Integer.parseInt(fields[0].substring(0,4)));
        logPreparser.setMonth(Integer.parseInt(fields[0].substring(5,7)));
        logPreparser.setDay(Integer.parseInt(fields[0].substring(8,10)));


        return logPreparser;
    }

    public static void main(String[] args) {
        String test_str="2018-06-15 13:41:39 10.200.200.98 GET /gs.gif gsver=3.8.0.9&gscmd=pv&gssrvid=GWD-000702&gsuid=28872593x9769t21&gssid=t291319151wwp6d11&pvid=291355119hvjhz11&gsltime=1529164311206&gstmzone=8&rd=i3u8k&gstl=Under%20Armour%7C%E5%AE%89%E5%BE%B7%E7%8E%9B%E4%B8%AD%E5%9B%BD%E5%AE%98%E7%BD%91%20-%20UA%E8%BF%90%E5%8A%A8%E5%93%81%E7%89%8C%E4%B8%93%E5%8D%96%EF%BC%8C%E7%BE%8E%E5%9B%BD%E9%AB%98%E7%AB%AF%E8%BF%90%E5%8A%A8%E7%A7%91%E6%8A%80%E5%93%81%E7%89%8C&gscp=2%3A%3Acookie%2520not%2520exist.%7C%7C3%3A%3Acookie%2520not%2520exist.%7C%7C4%3A%3Acookie%2520not%2520exist.%7C%7C5%3A%3Acookie%2520not%2520exist.%7C%7C6%3A%3Acookie%2520not%2520exist.&gsce=1&gsclr=24&gsje=0&gsst=0&gswh=759&gsph=5461&gspw=1519&gssce=1&gsscr=1536*864&dedupid=29135511vx5ccp11&gsurl=https%3A%2F%2Fwww.underarmour.cn%2F&gsorurl=https%3A%2F%2Fwww.underarmour.cn 80 - 58.210.35.226 Mozilla/5.0+(Windows+NT+10.0;+Win64;+x64)+AppleWebKit/537.36+(KHTML,+like+Gecko)+Chrome/67.0.3396.87+Safari/537.36\n";
        LogPreparser prePased=LogPreparser.parse(test_str);
        System.out.println(prePased.toString());
    }

}


