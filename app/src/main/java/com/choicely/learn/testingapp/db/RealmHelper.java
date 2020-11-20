package com.choicely.learn.testingapp.db;

import com.choicely.learn.testingapp.app.TestingApplication;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmHelper {
    private static final String TAG = "RealmHelper";

    private static final String REALM_NAME = "NotepadApplication.realm";
    private static final int REALM_VERSION = RealmHistory.VERSION_1;

    private Realm realm;

    private static class RealmHistory {
        static final int VERSION_1 = 1;
    }

    private static RealmHelper instance;

    private RealmHelper(){

    }

    public static RealmHelper getInstance() {
        if (instance == null) {
            throw new IllegalStateException(TAG + " is not initialized!");
        }
        return instance;
    }

    public static void init(TestingApplication testingApplication) {
        if(instance != null){
            throw new IllegalStateException(TAG + " is already initialized!");
        }

        instance = new RealmHelper();

        Realm.init(testingApplication);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(REALM_NAME)
                .schemaVersion(REALM_VERSION)
                .allowWritesOnUiThread(true)
                .deleteRealmIfMigrationNeeded()
                .build();

        instance.realm = Realm.getInstance(config);
    }

    public Realm getRealm(){
        return realm;
    }
}
