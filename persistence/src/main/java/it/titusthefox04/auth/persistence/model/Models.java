package it.titusthefox04.auth.persistence.model;


public class Models {
    private Models() {}

    public static final int XXS_LENGTH = 16;
    public static final int XS_LENGTH = 32;
    public static final int S_LENGTH = 64;
    public static final int M_LENGTH = 128;
    public static final int L_LENGTH = 256;
    public static final int XL_LENGTH = 512;
    public static final int XXL_LENGTH = 4000;

    public static final int CODE_LENGTH = XS_LENGTH;
    public static final int NAME_LENGTH = XS_LENGTH;
    public static final int IDLIST_LENGTH = XL_LENGTH;
}
