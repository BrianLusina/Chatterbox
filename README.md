ChatterBox App

Android app that has the same functionality as the web [app variant ](https://github.com/BrianLusina/Chatterbox)

<iframe src="https://appetize.io/embed/3aqmq2pq9bzn60q7u83x4evqzm?device=nexus5&scale=100&autoplay=false&orientation=portrait&deviceColor=black" width="400px" height="795px" frameborder="0" scrolling="no"></iframe>

## Project Dependencies

This project uses the following libraries:

+ __Circle Image View__
    This gives the user a Circular ImageView of their profile photo. An example image:
    [!circle_image](https://camo.githubusercontent.com/e17a2a83e3e205a822d27172cb3736d4f441344d/68747470733a2f2f7261772e6769746875622e636f6d2f68646f64656e686f662f436972636c65496d616765566965772f6d61737465722f73637265656e73686f742e706e67)

+ ____
   
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


```

### Github Sign In



