package com.example.yulian.weatherapp;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yulian.weatherapp.model.Users;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;


public class Login extends AppCompatActivity {

    private static final String TAG = "";
    private Drawer result = null;
    private AccountHeader headerResult = null;
    private Toast toast = null;
    private String[] prof = new String[2];

    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    DatabaseReference ref = database.child("user");

    private Button btnOk, btnRegister, btnRegisterSave, bAddLocation;
    EditText etLogin, etPassword, etRegLog, etRegPass, etRegUserName;
    TextView tv;
    ArrayList<String> arrayListSt = new ArrayList();
    ArrayList<Users> arrayListUser = new ArrayList();
    ArrayList<String> arrayListLocation = new ArrayList();
    Users activUser = new Users();




    private void fetchData(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            arrayListSt.add(ds.getValue().toString());
        }
    }

//    private void fetchData(DataSnapshot dataSnapshot) {
//        for (DataSnapshot ds : dataSnapshot.getChildren()) {
//            Users users = new Users();
//            users.setLogin(ds.getValue().toString());
//
//            arrayListSt.add(ds.getValue().toString());
//        }
//    }




    private  void sortDataUser(){
        arrayListUser.clear();
        int userNumber = 0;
        while (userNumber < arrayListSt.size()) {
            Users users = new Users();
            users.setLogin(arrayListSt.get(userNumber).toString());
            users.setPassword(arrayListSt.get(userNumber+1).toString());
            users.setUserName(arrayListSt.get(userNumber+2).toString());
            arrayListUser.add(users);
            userNumber+=3;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeComponent();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogRegister();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortDataUser();
                for(int userNumber = 0; userNumber<arrayListUser.size(); userNumber++){
                    if (etLogin.getText().toString().equals(arrayListUser.get(userNumber).getLogin())&&
                            etPassword.getText().toString().equals(arrayListUser.get(userNumber).getPassword())){
                        activUser.setLogin(arrayListUser.get(userNumber).getLogin());
                        activUser.setPassword(arrayListUser.get(userNumber).getPassword());
                        activUser.setUserName(arrayListUser.get(userNumber).getUserName());
                       // activUser.setLocation();
                        tv.setText(activUser.getUserName());
                    }
                }

            }
        });
        bAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogAddLocation();
            }
        });

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setShowHideAnimationEnabled(true);
            //getSupportActionBar().setDisplayOptions(5);
            //getSupportActionBar().setLogo(R.drawable.iconweather);
            //getSupportActionBar().setDisplayUseLogoEnabled(true);
        }
        createAccountHeader();
        result = new DrawerBuilder(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .withTranslucentStatusBar(true)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withInnerShadow(true)
                .withSliderBackgroundColor(Color.WHITE)
                .withSliderBackgroundDrawable(getResources().getDrawable(R.drawable.drawerwall))
                .withActionBarDrawerToggle(true)
                .withTranslucentNavigationBar(true)
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        InputMethodManager inputMethodManager = (InputMethodManager) Login.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(Login.this.getCurrentFocus().getWindowToken(), 0);
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                    }

                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {

                    }
                })
                .addDrawerItems(initializeDrawerItems())
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch ((int) drawerItem.getIdentifier()) {
                            case 1:
                                toast = Toast.makeText(getApplicationContext(),
                                        "Home",
                                        Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                break;
                            case 2:
                                toast = Toast.makeText(getApplicationContext(),
                                        "Free play",
                                        Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                break;
                            default:
                                toast = Toast.makeText(getApplicationContext(),
                                        "WeatherApp ;)",
                                        Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                break;
                        }
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withDrawerGravity(Gravity.END)
                .build();
        result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
        result.getDrawerLayout().setScrimColor(Color.argb(255, 255, 10, 67));
        //result.getDrawerLayout().setStatusBarBackgroundColor(Color.GREEN);
    }

    private void initializeComponent() {
        btnOk = (Button) findViewById(R.id.btnOk);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        etLogin = (EditText) findViewById(R.id.etLogin);
        etPassword = (EditText) findViewById(R.id.etPassword);
        tv = (TextView) findViewById(R.id.tv);
        bAddLocation = (Button) findViewById(R.id.bAddLocation);
    }

    public void showDialogAddLocation() {
        Dialog d = new Dialog(this);
        d.setTitle("Add location");
        d.setContentView(R.layout.add_location);

        final EditText eTAddLocation = (EditText) d.findViewById(R.id.etAddLocation) ;
        Button bSaveLocation = (Button) d.findViewById(R.id.bSaveLocation);

       bSaveLocation.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String location = eTAddLocation.getText().toString();
             addLocation(location);
           }
       });

        d.show();
    }

    public void addLocation(String location){

    }

    public void showDialogRegister() {
        Dialog d = new Dialog(this);
        d.setTitle("Login User");
        d.setContentView(R.layout.register_layout);

        btnRegisterSave = (Button) d.findViewById(R.id.btnSave);
        etRegLog = (EditText) d.findViewById(R.id.etRegisterLogin);
        etRegPass = (EditText) d.findViewById(R.id.etRegisterPassword);
        etRegUserName = (EditText) d.findViewById(R.id.etRegisterUserName);

        btnRegisterSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData(etRegLog.getText().toString(), etRegPass.getText().toString(), etRegUserName.getText().toString());
            }
        });

        d.show();
    }

    private void addData(String login, String password, String userName) {
        Users user = new Users();
        user.setLogin(login);
        user.setPassword(password);
        user.setUserName(userName);

        if (login.length() > 0 && login != null && password.length() > 0 && password != null && userName.length() > 0 && userName != null) {

            DatabaseReference nameRef = ref.child(userName);
            nameRef.setValue(user);
            etRegLog.setText("");
            etRegPass.setText("");
            etRegUserName.setText("");
            Toast.makeText(Login.this, "User "+ userName +" Saved ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Login.this, "User did not Saved ", Toast.LENGTH_SHORT).show();
        }
    }

    private void addData(String login, String password, String userName, String location) {
        Users user = new Users();
        user.setLogin(login);
        user.setPassword(password);
        user.setUserName(userName);

        if (login.length() > 0 && login != null && password.length() > 0 && password != null && userName.length() > 0 && userName != null) {

            DatabaseReference nameRef = ref.child(userName);
            nameRef.setValue(user);
            DatabaseReference nameRefLoc = nameRef.child("location");

            etRegLog.setText("");
            etRegPass.setText("");
            etRegUserName.setText("");
            Toast.makeText(Login.this, "User "+ userName +" Saved ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Login.this, "User did not Saved ", Toast.LENGTH_SHORT).show();
        }
    }

    @NonNull
    private IDrawerItem[] initializeDrawerItems() {
        //if you want to update the items at a later time it is recommended to keep it in a variable
        return new IDrawerItem[]{new PrimaryDrawerItem().withIdentifier(1).withName(R.string.drawer_item_home).withIcon(FontAwesome.Icon.faw_home),
                new PrimaryDrawerItem().withIdentifier(2).withName(R.string.drawer_item_free_play).withIcon(FontAwesome.Icon.faw_gamepad).withBadge("19").withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700)),
                new PrimaryDrawerItem().withIdentifier(3).withName(R.string.drawer_item_custom).withIcon(FontAwesome.Icon.faw_eye),
                new SectionDrawerItem().withIdentifier(4).withName(R.string.drawer_item_section_header),
                new SecondaryDrawerItem().withIdentifier(5).withName(R.string.drawer_item_settings).withIcon(FontAwesome.Icon.faw_cog),
                new SecondaryDrawerItem().withIdentifier(6).withName(R.string.drawer_item_help).withIcon(FontAwesome.Icon.faw_question).withEnabled(false),
                new SecondaryDrawerItem().withIdentifier(7).withName(R.string.drawer_item_open_source).withIcon(FontAwesome.Icon.faw_github),
                new SecondaryDrawerItem().withIdentifier(8).withName(R.string.drawer_item_contact).withIcon(FontAwesome.Icon.faw_bullhorn)};
    }

    private void createAccountHeader() {
        // Create the AccountHeader
        prof[0] = "Mylian Yulian";
        prof[1] = "vasterlord619@gmail.com";
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.drawerwall)
                .withTextColor(Color.WHITE)
                .addProfiles(
                        new ProfileDrawerItem().withIdentifier(11).
                                withName(prof[0]).withEmail(prof[1]).withIcon(getResources().getDrawable(R.drawable.profile))
                )
                .withOnAccountHeaderProfileImageListener(new AccountHeader.OnAccountHeaderProfileImageListener() {
                    @Override
                    public boolean onProfileImageClick(View view, IProfile profile, boolean current) {
                        profileClick(profile);
                        return false;
                    }

                    @Override
                    public boolean onProfileImageLongClick(View view, IProfile profile, boolean current) {
                        return false;
                    }
                })
                .withOnAccountHeaderItemLongClickListener(new AccountHeader.OnAccountHeaderItemLongClickListener() {
                    @Override
                    public boolean onProfileLongClick(View view, IProfile profile, boolean current) {
                        ///// do some action after long click of some account
                        return false;
                    }
                })
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        profileClick(profile);
                        return false;
                    }
                })
                /* .withOnAccountHeaderSelectionViewClickListener(new AccountHeader.OnAccountHeaderSelectionViewClickListener() {
                    @Override
                    public boolean onClick(View view, IProfile profile) {
                        profileClick(profile);
                         return false;
                    }
                })*/
                .build();
    }

    //////////////////////////////////////////////////////////////////////////////////
    private void profileClick(IProfile profile) {
        switch ((int) profile.getIdentifier()) {
            case 11:
                toast = Toast.makeText(getApplicationContext(),
                        prof[0],
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                break;
            default:
                toast = Toast.makeText(getApplicationContext(),
                        "WeatherApp ;)",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = result.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
///////////////////////////////////////////////////////////////////////



 /*   Selecting an item

//set the selection to the item with the identifier 1
    result.setSelection(1);
//set the selection to the item with the identifier 2
    result.setSelection(item2);
//set the selection and also fire the `onItemClick`-listener
    result.setSelection(1, true);
    By default, when a drawer item is clicked, it becomes the new selected item. If this isn't the expected behavior, you can disable it for this item using withSelectable(false):

            new SecondaryDrawerItem().withName(R.string.drawer_item_dialog).withSelectable(false)
    Modify items or the drawer

//modify an item of the drawer
    item1.withName("A new name for this drawerItem").withBadge("19").withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700));
//notify the drawer about the updated element. it will take care about everything else
    result.updateItem(item1);

//to update only the name, badge, icon you can also use one of the quick methods
    result.updateName(1, "A new name");

//the result object also allows you to add new items, remove items, add footer, sticky footer, ..
    result.addItem(new DividerDrawerItem());
    result.addStickyFooterItem(new PrimaryDrawerItem().withName("StickyFooterItem"));

//remove items with an identifier
    result.removeItem(2);

//open / close the drawer
    result.openDrawer();
    result.closeDrawer();

//get the reference to the `DrawerLayout` itself
The MaterialDrawer supports fetching images from URLs and setting them for the Profile icons. As the MaterialDrawer does not contain an ImageLoading library the dev can choose his own implementation (Picasso, Glide, ...). This has to be done, before the first image should be loaded via URL. (Should be done in the Application, but any other spot before loading the first image is working too)

SAMPLE using PICASSO
SAMPLE using GLIDE
Code:

//initialize and create the image loader logic
DrawerImageLoader.init(new AbstractDrawerImageLoader() {
    @Override
    public void set(ImageView imageView, Uri uri, Drawable placeholder) {
        Picasso.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
    }

    @Override
    public void cancel(ImageView imageView) {
        Picasso.with(imageView.getContext()).cancelRequest(imageView);
    }
    @Override
    public Drawable placeholder(Context ctx) {
        return super.placeholder(ctx);
    }

    @Override
    public Drawable placeholder(Context ctx, String tag) {
        return super.placeholder(ctx, tag);
    }
});
    result.getDrawerLayout();*/

}


