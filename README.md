
# Biometric & Device Lock Authentication

If your device supports fingerprints, biometric authentication will function; otherwise, authentication will use a PIN Pattern and Password lock. 

## Follow these procedures for implementation.




### Installation / Implementation


#### Step 1 :

```java

    dependencies {

        implementation "androidx.biometric:biometric:1.1.0"

    }
    
```

#### Step 2 : 

```java

    private static final int REQUEST_CODE = 101010;
    private static final int REQUEST_CODE_PIN = 1234;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private KeyguardManager keyguardManager;


```


#### Step 3 :

```java

    BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate(BIOMETRIC_STRONG | DEVICE_CREDENTIAL)) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                //Toast.makeText(this, "App can authenticate using biometrics", Toast.LENGTH_SHORT).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(this, "No biometric features available on this device", Toast.LENGTH_SHORT).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(this, "Biometric features are currently unavailable", Toast.LENGTH_SHORT).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                final Intent enrollIntent = new Intent(Settings.ACTION_BIOMETRIC_ENROLL);
                enrollIntent.putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        BIOMETRIC_STRONG | DEVICE_CREDENTIAL);
                startActivityForResult(enrollIntent, REQUEST_CODE);
                break;
    }

```



#### Step 4 : 

```java

    executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(MainActivity.this,executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,@NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                // This code write when biometric failed then unlock phn password/lock/pattern
                if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON){
                    promptForPassword();
                }else{
                    Toast.makeText(getApplicationContext(),"Authentication error: " + errString, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                startActivity(new Intent(MainActivity.this, HomePage.class));
                Toast.makeText(getApplicationContext(),"Authentication succeeded!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed", Toast.LENGTH_SHORT).show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for my app")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Use account password")
                .build();

        biometricLoginButton.setOnClickListener(view -> {
            biometricPrompt.authenticate(promptInfo);
        });


```



#### Step 5 : [ This code should be written before the last } ]

```java

    private void promptForPassword(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (keyguardManager.isDeviceSecure()){
                Intent intent = keyguardManager.createConfirmDeviceCredentialIntent("Authentication Required","Please Enter Your PIN, Pattern or Password");
                if (intent != null){
                    //noinspection deprecation
                    startActivityForResult(intent,REQUEST_CODE_PIN);
                }else{
                    Toast.makeText(this, "No Secure Lock Screen Setup", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PIN){
            if (resultCode == RESULT_OK){
                startActivity(new Intent(MainActivity.this, HomePage.class));
                Toast.makeText(this, "Authentication Success", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Try Again", Toast.LENGTH_SHORT).show();
            }
        }
    }


```
## Documentation

[Biometric Authentication](https://developer.android.com/identity/sign-in/biometric-auth)


## Authors

- [Prothes](https://prothes-asp.github.io/prothes/)

