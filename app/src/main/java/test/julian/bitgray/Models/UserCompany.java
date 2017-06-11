package test.julian.bitgray.Models;

/**
 * Created by JulianStack on 09/06/2017.
 */

public class UserCompany {
    String Name;
    String CatchPhrase;
    String Bs;

    public UserCompany(String name, String catchPhrase, String bs) {
        Name = name;
        CatchPhrase = catchPhrase;
        Bs = bs;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCatchPhrase() {
        return CatchPhrase;
    }

    public void setCatchPhrase(String catchPhrase) {
        CatchPhrase = catchPhrase;
    }

    public String getBs() {
        return Bs;
    }

    public void setBs(String bs) {
        Bs = bs;
    }
}
