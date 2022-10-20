package underhome.weather.com.underhome;
import android.os.Parcel;
import android.os.Parcelable;

public class Identificador implements Parcelable {
    private String identificador;

    public Identificador(String identificador)
    {
        this.identificador = identificador;
    }

    public String getIdentificador() { return identificador;}


    protected Identificador(Parcel in) {
        identificador = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(identificador);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Identificador> CREATOR = new Parcelable.Creator<Identificador>() {
        @Override
        public Identificador createFromParcel(Parcel in) {
            return new Identificador(in);
        }

        @Override
        public Identificador[] newArray(int size) {
            return new Identificador[size];
        }
    };
}
