Original App Design Project
===

# SEEFOOD

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
The app is developed based on the need of visualization and images for menus of different kinds of restaurants. Users are able to scan the menu and click on the food titles of their choice to preview its image to be shown on the screen, which makes it easier for them to order.

### App Evaluation
- **Category:** Food service/Restaurant
- **Mobile:** This app is primarily intended to be in a mobile application since it will utilize the feature of a phone's camera and it will be a daily convenient for people since they carry their phone most of the time. The app can also be viable on a dedicated app in the computer or a web app by browsing previously saved menus done through the phone app.
- **Story:** User can snap a picture of a menu taken into their phone's camera, and can visually see the titles of the food as well as its corresponding images. 
- **Market:** The target audience of this app is the general public wherein anyone can use this user-friendly app and take a snap of the menu. The app also targets the food services businesses as they can also use it effectively to promote their food business.
- **Habit:** This app can be used most often when users go to restaurants whose menus lack visual representations of their food titles. This app can also be viewed anytime if the user saved the informaton in the app to view previous snap.
- **Scope:** The project's goal is to effectively take a picture of the menu and recognize the texts in the image as well as the translate the texts into images that can be viewed by users of the app. We can then move on to have an effectively machine learning capability where it can detect different languages. Additionally, user can store their previous snap and use that as a reference. Eventually, if we have enough time, users will have the capability to map out their saved menus to places they are geographically located at so that other users can view them.

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* Scan a menu using the camera feature of the phone
* See a virtual version of the menu wherein the texts in the images are present in this virtual menu
* Click on food titles to view its corresponding image.
* Rate the accuracy of the images
* View the translated title on their own language

**Optional Nice-to-have Stories**
* Add items on cart
* View cart
* Delete cart
* Click on Order button and put the items on a different order tab. For each item, there will be shown the price and calories.
* Leave reviews after finishing their meal
* See ingredients along with dish image itself
* Save the menu in their account
* Post the menu on other restaurants where they dined-in.

### 2. Screen Archetypes

* Menu
   * User can scan the menu page using the camera feature
   * User can retrieve a virtual page of menu
   *  User can click on food titles to view the images
* Order list
   * User can click on Order button and put the items on a different order tab

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* User Information
* Menu Image
* Order List

**Flow Navigation** (Screen to Screen)

* User
   * User information
   * User profile
* Menu
   * Menu image
* Order list
   * Order List

## Wireframes

## Schema 
### Models

#### User

   | Property      | Type     | Description                            |
   | ------------- | -------- | -------------------------------------- |
   | objectId      | String   | unique id for the user (default field) |
   | email         | String   | email address of the user (primary key)|
   | password      | String   | password of the user (hidden)          |
   | profilePic    | File     | profile picture of the user to be shown|
   | createdAt     | DateTime | date when the user was created         |
   | previousRes   | Array    | list of previous restaurants visited   |
   
   
#### Menu

   | Property      | Type     | Description                              |
   | ------------- | -------- | ---------------------------------------- |
   | objectId      | String   | unique id for the menu (default field)   |
   | menuName      | String   | name of the menu generated               |
   | restoName     | String   | name of the restaurant associated with it|
   | foodTitles    | Array    | array of Food that the menu have         |
   | createdAt     | DateTime | date when the menu was created           |
   | itemPrice     | Float    | price of the item                        |
   
#### Food

   | Property         | Type     | Description                              |
   | -------------    | -------- | ---------------------------------------- |
   | objectId         | String   | unique id for the food (default field)   |
   | name             | String   | name of the food generated in menu       |
   | calories         | String   | amount of calories that the food has     |
   | restaurant       | String   | name of the restaurant associated with it|
   | foodPic          | DateTime | dpicture of the food                     |
   | createdAt        | DateTime | date when the food was created           |
   | itemPrice        | Float    | price of the item                        |
   | foodPicMetaData  | String   | food Pic data information                |
   | accuratedRate    | int      | Accuracy of the foodPic                  |
   
#### Cart

   | Property      | Type           | Description                              |
   | ------------- | -------------- | ---------------------------------------- |
   | objectId      | String         | unique id for the cart (default field)   |
   | user          | User Pointer   | points to the cart owner                 |
   | items         | Array          | array of Food that was requested         |
   | restaurant    | String         | name of the restaurant associated with it|
   | foodPic       | DateTime       | dpicture of the food                     |
   | createdAt     | DateTime       | date when the cart was created           |
   | itemPrice     | Float          | price of the item                        |
   | totalPrice    | Float          | total price of the cart                  |
   
   
### Networking
   - Main Activity Screen
      - (Read/GET) Query all food included in the menu
         ```java
         db.collection("food")
        .whereEqualTo("restaurant", "Pho Cali")
        .get()
        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
         ```
      - (Create/POST) Create a new entry of food on a post
          ```java
          Map<String, Object> food = new HashMap<>();
          food.put("name", "Pho");
          food.put("restaurant", "Pho Cali");

          db.collection("Food").document("Pho")
                  .set(food)
                  .addOnSuccessListener(new OnSuccessListener<Void>() {
                      @Override
                      public void onSuccess(Void aVoid) {
                          Log.d(TAG, "DocumentSnapshot successfully written!");
                      }
                  })
                  .addOnFailureListener(new OnFailureListener() {
                      @Override
                      public void onFailure(@NonNull Exception e) {
                          Log.w(TAG, "Error writing document", e);
                      }
                  });
         ```
      - (Create/POST) Create a new entry of menu on a post
      - (Delete) Delete a specific menu item
        ```java
        db.collection("cities").document("Pho")
        .delete()
        .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "DocumentSnapshot successfully deleted!");
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error deleting document", e);
            }
        });
        ```
      - (Delete) Delete a specific food item
   - Order Cart Screen
      - (Create/POST) Create a cart for the user
      - (Read/GET) Query the cart of the user
      - (submit/POST) Post the cart to the owner of the restaurant
   - Profile Screen
      - (Read/GET) Query logged in user object
      - (Read/GET) Query logged in user saved menus
      - (Update/PUT) Update user profile image
