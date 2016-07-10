package com.example.yulian.weatherapp.Helper;

import com.example.yulian.weatherapp.model.Users;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Ivan on 05.07.2016.
 */
public class FirebaseHelper {
    Boolean saved = null;
    public ArrayList<Users> arrayListUser = new ArrayList<>();

    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    DatabaseReference ref = database.child("user");
//    public void save(Users users) {
////        if (users == null) {
////            saved = false;
////        } else {
////            try {
//
//
//                DatabaseReference nameRef = ref.child(users.getUserName());
//                nameRef.setValue(users);
//              //  saved = true;
////            } catch (DatabaseException e) {
////                e.printStackTrace();
////                saved = false;
////            }
////        }
//      //  return saved;
//    }

//    public void save(Users users) {
//        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
//        DatabaseReference ref = database.child("user");
//        DatabaseReference nameRef = ref.child(users.getUserName());
//        nameRef.setValue(users);
//    }

//    RETRIVE
    public ArrayList<Users> retrive() {

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return arrayListUser;
    }

    private void fetchData(DataSnapshot dataSnapshot) {
        arrayListUser.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            Users users =new Users();
            users.setLogin(ds.getValue(Users.class).getLogin());
            users.setPassword(ds.getValue(Users.class).getPassword());
            users.setUserName(ds.getValue(Users.class).getUserName());
            arrayListUser.add(users);
        }
    }
}
