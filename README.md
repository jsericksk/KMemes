<p align="center">
  <a href="REAMED-pt-BR.md">Read README in Portuguese Version</a>
</p>

## About

Simple project using Firebase Cloud Storage and Realtime Database, focusing on the new modern toolkit for creating Android declarative UIs, [Jetpack Compose](https://developer.android.com/jetpack/compose). The project also follows the [MVVM](https://developer.android.com/jetpack/guide) architecture with **ViewModel** and **LiveData**.

<p align="center">
  <img src="screenshots/demo.gif" width="320" height="580" />
</p>

## Clone

If you want to clone and test this project, you must first have a Firebase account and connect to your project. Follow these steps:
1. Connect to Firebase:
- [Login to Firebase](https://console.firebase.google.com) and then create a project with any name, following all the steps described on the site during creation. Follow the [official guide](https://firebase.google.com/docs/android/setup) for project setup or more information.
2. Configure **Storage**:
- With the Firebase project properly connected to your Android Studio project, open the **Storage** option in the Firebase console and create two folders to store your images (memes), one for popular memes and one for anime memes. This is for organizational purposes only.
- Upload the images to the corresponding folders.
- For each image you upload, you will have to copy their direct link. The link should look something like this: https://firebasestorage.googleapis.com/v0/b/YOUR-PROJECT.appspot.com/o/FOLDER%2FIMAGE-NAME.jpg?alt=media&token=TOKEN. These links will be used in JSON which will act as an API in our Realtime Database.
- Having the link of all images, go to the last step.
3. Configure the **Realtime Database**:
- You will have to have a JSON with the following structure:

```
{
  "popular": [
    {
      "imageUrl": "IMAGE-URL",
      "imageName": "Image Name"
    }
    // More popular memes...
  ],
  "anime": [
    {
      "imageUrl": "IMAGE-URL",
      "imageName": "Image Name"
    }
    // More anime memes...
  ]
}
```

- You have two options:
1. Create a JSON file with the links you copied and, after that, import the JSON into Realtime Database;
2. Manually create this entire structure in the Realtime Database dashboard, adding the **imageUrl** and **imageName** values ​​one at a time.

No matter which choice, the JSON must have the values ​​mentioned above.
If after importing the JSON into the Realtime Database panel the structure is different from your JSON file, with the *anime* array being at the beginning, instead of the *popular* array, you don't need to worry. This will not affect the search for values ​​in the application, the important thing is that the JSON has those values.  

All of this was done for study purposes only.

## Libraries used

[Coil](https://coil-kt.github.io/coil/compose/): to load images.  
[Accompanist-Pager](https://google.github.io/accompanist/pager/): for using "ViewPager" with Tabs.  
[Navigation with Compose](https://developer.android.com/jetpack/compose/navigation): to navigate between Compose screens using Compose's Navigation Component.  
[Coroutines](https://developer.android.com/kotlin/coroutines): for Firebase access in an IO thread.  

**Jetpack Compose supremacy!** 🧎
