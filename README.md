# react-native-exoplayer-intent-video
React native video player with exoplayer, play in new intent Android only.

[![npm version](https://img.shields.io/badge/npm-0.0.1-blue.svg)](https://www.npmjs.com/package/react-native-exoplayer-intent-video)

![Screenshot](https://drive.google.com/uc?export=view&id=0BwIOCc0bQ1AncV8tRm9ITjFMYTg)

### A. Installation the package

`npm install react-native-exoplayer-intent-video --save`

### B. Linking the library to android

Use automatically complete the installation:  

`react-native link react-native-exoplayer-intent-video`

or manually like so:

```gradle
// file: android/settings.gradle
...

include ':react-native-exoplayer-intent-video'
project(':react-native-exoplayer-intent-video').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-exoplayer-intent-video/android')
```
```gradle
// file: android/app/build.gradle
...

dependencies {
    ...
    compile project(':react-native-exoplayer-intent-video')
}
```
```java
// file: android/app/src/main/java/com/<...>/MainApplication.java
...

import com.herudi.exovideo.ExoPlayerIntentPackage; // <-- add this import

public class MainApplication extends Application implements ReactApplication {
    @Override
    protected List<ReactPackage> getPackages() {
        return Arrays.<ReactPackage>asList(
            new MainReactPackage(),
            new ExoPlayerIntentPackage() // <-- add this line
        );
    }
...
}

```
### C. Add Activity in AndroidManifest.xml
```xml
  <application
      android:name=".MainApplication"
      android:allowBackup="true"
      android:label="@string/app_name"
      android:icon="@mipmap/ic_launcher"
      android:theme="@style/AppTheme">
      
      ...
      
      <!-- Add this code-->
      <activity 
        android:name="com.herudi.exovideo.PlayerActivity" 
        android:screenOrientation="landscape"> 
      </activity>  
      
      ...
      
    </application>
```

### D. Usage Example

```javascript
/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  Button,
  View
} from 'react-native';

//add or import this
import VideoPlayer from 'react-native-exoplayer-intent-video';

export default class videoTest extends Component {
  render() {
    return (
      <View style={styles.container}>
        <Button
          onPress={()=>{
              //add this to play video
              VideoPlayer.play({
                url:'http://techslides.com/demos/sample-videos/small.mp4', //required
                title:'Sample Video Title', //optional
                subtitle:'http://yourlink.srt' //optional
              });
              
          }}
          title="Test Video"
          color="#841584"
          accessibilityLabel="Test video button"
        />
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  }
});