package underhome.weather.com.underhome;

import android.os.Parcel;
import android.os.Parcelable;

public class Usuario implements Parcelable {
    private String nomb;

    public Usuario(String nomb)
    {
        this.nomb = nomb;
    }

    public String getNomb() { return nomb;}


    protected Usuario(Parcel in) {
        nomb = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nomb);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Usuario> CREATOR = new Parcelable.Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };
}
