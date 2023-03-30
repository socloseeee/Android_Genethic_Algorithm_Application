# Genetic Algorithm Android app

[![](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)](
https://developer.android.com/studio/intro)
[![](https://img.shields.io/badge/Python-blue?style=for-the-badge&logo=python&logoColor=white)](
https://www.python.org/)
[![](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](
https://www.java.com)

## Table of Contents

+ [About](#about)
+ [Requirements](#installing)
+ [Application Review](#apprev)

## About <a name = "about"></a>

This project presents a mobile implementation of the calculation of the solution of a specific task of the theory of schedules by the Goldberg method with different formation of the initial generation.

Python support is provided by:

[![](https://img.shields.io/badge/CHAQUOPY-8B89CC?style=for-the-badge&logo=java&logoColor=white)](
https://chaquo.com/chaquopy/)

## Requirements <a name = "installing"></a>

1. build.gradle(project) 
    ```androiddatabinding
    buildscript {
        repositories {
            maven { url "https://chaquo.com/maven"}
        }
        dependencies {
            classpath "com.chaquo.python:gradle:14.0.2"
        }
    }
    
    plugins {
        id 'com.android.application' version '7.4.0' apply false
        id 'com.android.library' version '7.4.0' apply false
        id 'com.chaquo.python' version '14.0.2' apply false
    }
    ```
2. build.gradle(:app)
    ```androiddatabinding
    plugins {
         id 'com.android.application'
         id 'com.chaquo.python'
    }
    
    
    
    android {
        namespace 'com.example.lab1'
        ompileSdk 33

        defaultConfig {

            sourceSets {
                main {
                    python.srcDir "some/main/python"
                }
            }

            python {
                version "3.8"
                buildPython *your python.exe file* (for e.g. "/usr/bin/python3" or "C:/ProgramData/Anaconda3/python.exe")
                pip {
                    install "tqdm"
                    install "colorama"
                    install "matplotlib"
                } 
            }

            ndk {
                abiFilters "armeabi-v7a", "arm64-v8a", "x86", "x86_64"
            }

            applicationId "com.example.lab1"
            minSdk 24
            targetSdk 33
            versionCode 1
            versionName "1.0"

            testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
        }

        buildTypes {
            release {
                minifyEnabled false
                proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
            }
        }
        compileOptions {
            sourceCompatibility JavaVersion.VERSION_1_8
            targetCompatibility JavaVersion.VERSION_1_8
        }
    }
    
    dependencies {
        implementation 'com.github.MikeOrtiz:TouchImageView:1.4.1'
        implementation 'io.apisense:rhino-android:1.0'
        implementation 'androidx.appcompat:appcompat:1.6.1'
        implementation 'com.google.android.material:material:1.8.0'
        implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
        implementation 'androidx.navigation:navigation-runtime:2.5.3'
        testImplementation 'junit:junit:4.13.2'
        androidTestImplementation 'androidx.test.ext:junit:1.1.5'
        androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
        implementation 'com.google.code.gson:gson:2.8.9'
        implementation 'com.squareup.retrofit2:retrofit:2.9.0'
        implementation 'com.squareup.retrofit2:converter-gson:2.5.0'
    }
    ```
    
## Application Review <a name = "apprev"></a>

### 1. When the program starts, its start window appears, where we can set the starting variables.


<img alt=img_1.png height="640" src="https://user-images.githubusercontent.com/65871712/228956135-5c1996c3-0174-4010-8897-fc0325c57b6a.png" width="299"/>
       
    If the data entered by the user is correctly validated, the transition 
    to the next window occurs, otherwise errors by type pop up:
      
    Error #1 (going beyond acceptable values):

<img alt="img_2.png" height="640" src="https://user-images.githubusercontent.com/65871712/228956459-4cbd7b56-6460-4a12-84b4-9740b7149066.png" width="299"/>

    Error #2 (Incorrect data type):

<img alt="img_3.png" height="640" src="https://user-images.githubusercontent.com/65871712/228956701-db289115-ae99-4352-907f-6e0722e3c0e7.png" width="299"/>

### 2. Switching to the next window with correct validation of the entered data:

<img alt="img_4.png" height="640" src="https://user-images.githubusercontent.com/65871712/228956775-5584c7a4-4fd6-4c3e-94b9-cb6d0c41913c.png" width="299"/>

    When you click on the "ГОТОВЫЙ ВАРИАНТ"(“READY OPTION”) button, 
    the matrix is read from the file system generated during the old iterations 
    and goes to the next:

<img alt="img_5.png" height="640" src="https://user-images.githubusercontent.com/65871712/228956840-3926eb30-8a52-4ee1-8c9e-460d08427a10.png" width="299"/> <img alt="img_6.png" height="640" src="https://user-images.githubusercontent.com/65871712/228956889-5ccad1cd-55d7-4f8d-bc33-a27e46c04e8a.png" width="299"/>

    When you click on the "НОВАЯ МАТРИЦА"(“NEW MATRIX”) button, 
    a new matrix is generated, uploaded to a file inside the system 
    and moved to the next window:

<img alt="img_7.png" height="640" src="https://user-images.githubusercontent.com/65871712/228957474-27a08bda-10a8-4fdf-9fae-0b3e713408b8.png" width="299"/> <img alt="img_8.png" height="640" src="https://user-images.githubusercontent.com/65871712/228957513-8c4f83db-143b-4a6d-9e38-b8d71d2f45ee.png" width="299"/>

    Validation check for the field (EditText Object) repeats:

<img alt="img_9.png" height="640" src="https://user-images.githubusercontent.com/65871712/228957583-7ae512fc-cca5-4332-acd7-0d5ef47118a6.png" width="299"/> <img alt="img_10.png" height="640" src="https://user-images.githubusercontent.com/65871712/228957640-9afa4817-9d63-46fd-809d-0e9f2d28356b.png" width="299"/>

### 3. Switching to the next window with correct data validation:

<img alt="img_11.png" height="640" src="https://user-images.githubusercontent.com/65871712/228957715-c099dd34-8a33-42cf-beaf-b50547bc2be5.png" width="299"/>

    When you click on the "КОНКРЕТНЫЙ МЕТОД"(“SPECIFIC METHOD”) button without 
    selecting more than one of the methods (RadioButton objects), the following 
    error window will pop up:
    
<img alt="img_12.png" height="640" src="https://user-images.githubusercontent.com/65871712/228957767-5b3b1366-9068-4409-9c0f-1b0e722c5732.png" width="299"/> <img alt="img_13.png" height="640" src="https://user-images.githubusercontent.com/65871712/228957797-78bfa87f-88a5-4d12-bba1-f0d7cbf6c8a7.png" width="299"/>

    Transition with the Method Selection combination + Specific Method/All methods:

<img alt="img_14.png" height="640" src="https://user-images.githubusercontent.com/65871712/228957846-a95bf5ea-b2fa-45c2-af76-155b3fe24e44.png" width="299"/>

    When you click on the “NEXT” button without selecting any of the methods 
    (RadioButton objects), the following error window will pop up:

<img alt="img_15.png" height="640" src="https://user-images.githubusercontent.com/65871712/228957889-c6e35c9e-6f42-4b58-8abc-850d873c7c7f.png" width="299"/> <img alt="img_16.png" height="640" src="https://user-images.githubusercontent.com/65871712/228957948-ce29b0d8-ddae-4b59-b9c8-b621d2957784.png" width="299"/>

### 4. Switching to the next window with correct data validation:

<img alt="img_17.png" height="640" src="https://user-images.githubusercontent.com/65871712/228958001-601401bd-f597-432b-bff3-f19ea9b301b8.png" width="299"/>

    When you click on the ("СТАРТ")“START” button, the Genethic Algorythm start 
    is initialized:

<img alt="img_18.png" height="640" src="img_18.png" width="299"/> <img alt="img_19.png" height="640" src="img_19.png" width="299"/>

    When you click on the “SHOW JSON” button, a JSON file is shown that is fed 
    inside the python-script:

<img alt="img_20.png" height="640" src="https://user-images.githubusercontent.com/65871712/228958398-e39197a5-13ba-41e9-a901-7600f8027757.png" width="299"/>

### 5. Switching to the next window when clicking on the "ДАЛЕЕ"(“NEXT”) button:

<img alt="img_21.png" height="640" src="https://user-images.githubusercontent.com/65871712/228958342-a6da383c-c87e-4666-a006-68868c54f584.png" width="299"/>

    When you click on the “OK" button, the generation of histograms begins:

<img alt="img_22.png" height="640" src="https://user-images.githubusercontent.com/65871712/228958289-6efdd959-05d9-4b3f-bf2f-25deb0cd8c33.png" width="299"/>

    Clicking on the histogram takes you to the demo window:

<img alt="img_23.png" height="640" src="https://user-images.githubusercontent.com/65871712/228958253-d255e82f-091e-4ae4-9b6d-4164beddb844.png" width="299"/> <img alt="img_24.png" height="640" src="https://user-images.githubusercontent.com/65871712/228958213-b1c877b4-15cb-4515-a1c8-0991d568faa1.png" width="299"/>

---
