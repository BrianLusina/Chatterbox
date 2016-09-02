ChatterBox App

Android app that has the same functionality as the web [app variant ](https://github.com/BrianLusina/Chatterbox)

<iframe src="https://appetize.io/embed/3aqmq2pq9bzn60q7u83x4evqzm?device=nexus5&scale=100&autoplay=false&orientation=portrait&deviceColor=black" width="400px" height="795px" frameborder="0" scrolling="no"></iframe>

## Project Dependencies

This project uses the following libraries:

+ __Circle Image View__
    This gives the user a Circular ImageView of their profile photo. An example image:
    [!circle_image](https://camo.githubusercontent.com/e17a2a83e3e205a822d27172cb3736d4f441344d/68747470733a2f2f7261772e6769746875622e636f6d2f68646f64656e686f662f436972636c65496d616765566965772f6d61737465722f73637265656e73686f742e706e67)

+ __Material Drawer Library__


---

## Sign in Options

The application allows uses the following sign in options:

### Email And Password

Users can sign in with their email and password. These credentials will be verified by Firebase database to check whether given credentials match the Database records. If not, they will have to sign up or sign in with either Google, Twitter, Github or Facebook.

### Google Sign In

### Twitter Sign In


### Facebook Sign In
    
+ ___Prerequisites___
    1. Have a Facebook app for Android
    2. Requires a Facebook developer's account, this will allow you to create Facebook apps.
    3. Download the Android SDK
    4. Enable single Sign On for your application.
        This is done in the Facebook developer's console.
    5. Include the Activity in the manifest of your application, if you are using an Activity
    
+ __Adding the facebook login button__
    Add the LoginButton from the Facebook SDK. This is a custom view implementation of a `Button`. In addition to using the `LoginButton` you will also use the following classes:
     1. **LoginManager**  Initiates the login process with the requested read or publish permissions.
     2. **CallbackManager** Used to route calls back to the Facebook SDK and your registered callbacks. You should call it from the initiating activity or fragments onActivityResult call.
     3. **AccessToken**: Use this class Graph API requests. It shows the user id, and the accepted and denied permissions.
     4. **Profile** - This class has basic information about person logged in.

The LoginButton is a UI element that wraps functionality available in the LoginManager. So when someone clicks on the button, the login is initiated with the permissions set in the LoginManager. The button follows the login state, and displays the correct text based on someone's authentication state.

```xml
<com.facebook.login.widget.LoginButton
    android:id="@+id/facebook_login_button"
    android:layout_width="220dp"
    android:layout_height="wrap_content"
    android:layout_gravity="center_horizontal"
    android:layout_margin="10dp" />
```
> Add this to your XML layout

Then set up the button in your UI by adding it to a fragment and update your activity to use your fragment.
You can customize the properties of Login button and register a callback in your onCreateView() method.
Properties you can customize includes `LoginBehavior`, `DefaultAudience`, `ToolTipPopup.Style` and `permissions` on the LoginButton.
An example:

```java
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.splash, container, false);
    
    // your other awesome code...
    facebookLoginBtn = (LoginButton)rootView.findViewById(R.id.facebook_login_button);
    initializeFacebookLogin();
    //...
    });    
    
    /**Initializes the Facebook login*/
    private void initializeFacebookLogin() {
        facebookLoginBtn.setReadPermissions("email");
        // If using in a fragment
        facebookloginBtn.setFragment(this);
        // Callback registration
        facebookloginBtn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        }
}
```

You then need to call `FacebookSdk.sdkInitialize` to initialize the SDK, and then call `CallbackManager.Factory.create` to create a callback manager to handle login responses. Here's an example of adding the callback in a **fragment**

```java
    private CallbackManager callbackManager;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //...other code
        configureFacebookSignIn();
    }
        
    private void configureFacebookSignIn() {
        FacebookSdk.sdkInitialize(getContext());
        callbackManager = CallbackManager.Factory.create();
    }    
```

### Register a callback

``` java
@Override
public void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
 
    configureFacbookSignIn();
    FacebookSdk.sdkInitialize(getContext());
    callbackManager = CallbackManager.Factory.create();
    initializeFacebookLogin();
    LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {

        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {

        }
    });
}
```
> If login succeeds, the LoginResult parameter has the new AccessToken, and the most recently granted or declined permissions.You don't need a registerCallback for login to succeed, you can choose to follow current access token changes with the AccessTokenTracker class.

``` java
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
```
> Every activity and fragment that you integrate with the FacebookSDK Login or Share should forward onActivityResult to the callbackManager

### Github Sign In


## Home Activity

This is the '*landing*' page of the application. It has a drawer that will enable user to switch between various panes in the application. The application will use fragments except for Settings, Open Source, Manage Account, Help and About drawer items. The drawer is created courtesy of [Mike Penz Material Drawer Library](https://github.com/mikepenz/MaterialDrawer). No XML layout needed for the drawer. The drawer is created purely in `JAVA` :coffee:.

The layout used here is:
``` xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.design.widget.CoordinatorLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:fitsSystemWindows="true"
    tools:context="com.chatterbox.chatterbox.mainpack.HomeActivity">

    <!--Toolbar-->
    <include
        layout="@layout/toolbar_layout"/>

    <FrameLayout
        android:id="@+id/container_body"
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        android:layout_weight="1" />

    <android.support.design.widget.FloatingActionButton
        android:id="@+id/home_floating_action_btn"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom|end"
        android:layout_margin="@dimen/fab_margin"
        android:src="@android:drawable/ic_input_add" />

</android.support.design.widget.CoordinatorLayout>
```
> The takeaway from this is the `FrameLayout` element which will contain each fragment

Then Create each individual fragment layout as needed.

Read more on Material Drawer Library here -> [Click me](https://github.com/mikepenz/MaterialDrawer).

  
## Chats Fragment

