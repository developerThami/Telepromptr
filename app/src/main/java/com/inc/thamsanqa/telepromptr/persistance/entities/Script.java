package com.inc.thamsanqa.telepromptr.persistance.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


@Entity(tableName = "script")
public class Script implements Parcelable {

    @NonNull
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    int id;

    @Nullable
    @ColumnInfo(name = "title")
    String title;

    @Nullable
    @ColumnInfo(name = "uri")
    String uri;

    @Nullable
    @ColumnInfo(name = "body")
    String body;

    @NonNull
    @ColumnInfo(name = "date_created_milliseconds")
    long dateInMilli;

    public Script() {
    }

    Script(Parcel in) {
        id = in.readInt();
        title = in.readString();
        uri = in.readString();
        body = in.readString();
        dateInMilli = in.readLong();
    }

    public static final Creator<Script> CREATOR = new Creator<Script>() {
        @Override
        public Script createFromParcel(Parcel in) {
            return new Script(in);
        }

        @Override
        public Script[] newArray(int size) {
            return new Script[size];
        }
    };

    @NonNull
    public int getId() {
        return id;
    }

    @Nullable
    public String getUri() {
        return uri;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    @Nullable
    public String getBody() {
        return body;
    }

    @NonNull
    public long getDateInMilli() {
        return dateInMilli;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public void setTitle(@Nullable String title) {
        this.title = title;
    }

    public void setBody(@Nullable String body) {
        this.body = body;
    }

    public void setDateInMilli(@NonNull long dateInMilli) {
        this.dateInMilli = dateInMilli;
    }

    public void setUri(@Nullable String uri) {
        this.uri = uri;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(uri);
        dest.writeString(body);
        dest.writeLong(dateInMilli);
    }
}
