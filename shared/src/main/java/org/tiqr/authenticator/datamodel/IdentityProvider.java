package org.tiqr.authenticator.datamodel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Simple wrapper class for services.
 */
public class IdentityProvider implements Parcelable {
    private long _id = -1;
    private String _identifier;
    private String _displayName;
    private byte[] _logoData;
    private Bitmap _logoBitmap;
    private String _authenticationURL;
    private String _infoURL;
    private String _ocraSuite = null;

    // The default version is compatible with old moby dick servers that don't specify a
    // suite. Default is to use an SHA1 hash
    public final static String DEFAULT_OCRA_SUITE = "OCRA-1:HOTP-SHA1-6:QN10";

    /**
     * Factory.
     */
    public static final Parcelable.Creator<IdentityProvider> CREATOR = new Parcelable.Creator<IdentityProvider>() {
        public IdentityProvider createFromParcel(Parcel source) {
            return new IdentityProvider(source);
        }

        public IdentityProvider[] newArray(int size) {
            return new IdentityProvider[size];
        }
    };

    /**
     * Constructor.
     */
    public IdentityProvider() {
    }

    /**
     * Constructor.
     */
    private IdentityProvider(Parcel source) {
        _id = source.readLong();
        _identifier = source.readString();
        _displayName = source.readString();
        _logoData = source.createByteArray();
        _logoBitmap = source.readParcelable(Bitmap.class.getClassLoader());
        _authenticationURL = source.readString();
        _infoURL = source.readString();
        _ocraSuite = source.readString();
    }

    /**
     * Describe.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Export to parcel.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(_id);
        dest.writeString(_identifier);
        dest.writeString(_displayName);
        dest.writeByteArray(_logoData);
        dest.writeParcelable(_logoBitmap, 0);
        dest.writeString(_authenticationURL);
        dest.writeString(_infoURL);
        dest.writeString(_ocraSuite);
    }

    /**
     * Returns the service (row) id.
     * 
     * The id is -1 for a service that hasn't bee inserted yet.
     * 
     * @return service id
     */
    public long getId() {
        return _id;
    }

    /**
     * Sets the service row id.
     * 
     * @param id row id
     */
    public void setId(long id) {
        _id = id;
    }

    /**
     * Returns the service identifier.
     * 
     * @return service identifier
     */
    public String getIdentifier() {
        return _identifier;
    }

    /**
     * Sets the service identifier.
     * 
     * @param identifier service identifier
     */
    public void setIdentifier(String identifier) {
        _identifier = identifier;
    }

    /**
     * Returns the display name.
     * 
     * @return display name
     */
    public String getDisplayName() {
        return _displayName;
    }

    /**
     * Sets the display name.
     * 
     * @param displayName display name
     */
    public void setDisplayName(String displayName) {
        _displayName = displayName;
    }

    /**
     * Returns the logo image bytes.
     * 
     * @return logo image bytes
     */
    public byte[] getLogoData() {
        return _logoData;
    }

    /**
     * Sets the logo image bytes.
     * 
     * @param logoData logo image bytes
     */
    public void setLogoData(byte[] logoData) {
        _logoData = logoData;
        _logoBitmap = null;
    }

    /**
     * Return the logo bitmap (creates one if it hasn't been created yet).
     * 
     * @param Bitmap logo bitmap
     */
    public Bitmap getLogoBitmap() {
        if (_logoBitmap != null) {
            return _logoBitmap;
        } else if (_logoData.length > 0) {
            _logoBitmap = BitmapFactory.decodeByteArray(_logoData, 0, _logoData.length);
            return _logoBitmap;
        } else {
            return null;
        }
    }

    /**
     * Returns the authentication URL.
     * 
     * @return authentication URL
     */
    public String getAuthenticationURL() {
        return _authenticationURL;
    }

    /**
     * Sets the authentication URL.
     * 
     * @param authenticationUrl authentication URL
     */
    public void setAuthenticationURL(String authenticationURL) {
        _authenticationURL = authenticationURL;
    }

    /**
     * @return the Identity provider infoURL
     */
    public String getInfoURL() {
        return _infoURL;
    }

    /**
     * @param String the info URL for the Identity Provider
     */
    public void setInfoURL(String _infoURL) {
        _infoURL = _infoURL;
    }

    /**
     * Retrieve the ocra suite for this service.
     * 
     * @return The OCRA suite
     */
    public String getOCRASuite() {
        if (_ocraSuite == null) {
            return DEFAULT_OCRA_SUITE;
        }
        return _ocraSuite;
    }

    public void setOCRASuite(String ocraSuite) {
        _ocraSuite = ocraSuite;
    }
}
