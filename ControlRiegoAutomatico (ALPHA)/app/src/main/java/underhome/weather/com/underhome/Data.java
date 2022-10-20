package underhome.weather.com.underhome;

/**
 * Created by nicolas on 18-03-18.
 */

public class Data {
    private String a,b,c,d;

    public Data(){

    }

    public Data(String a, String b, String c, String d){
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;

    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

}