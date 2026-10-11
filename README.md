# Overview

Inventory Manager Android is a mobile inventory management application built with Kotlin and Jetpack Compose. The application is connected to Google Cloud Firestore so inventory information is stored in the cloud instead of only in the application's local memory.

The application allows users to view products, add new products, increase or decrease stock quantities, identify products with low stock, and delete products. When a product is created or changed in the Android application, the corresponding information is also created or updated in Firestore. The application listens for database changes and updates the inventory displayed on the screen.

To use the application, the user can select **Add Product** and enter a product name, category, quantity, and minimum stock level. The `+` and `-` buttons modify the current quantity, and the **Delete** button removes the product from the inventory and from the cloud database.

I created this software to continue developing my Android and Kotlin skills while learning how mobile applications communicate with cloud databases. My main goal was to understand how to create, retrieve, update, and delete cloud data and keep a Jetpack Compose user interface synchronized with remotely stored information.

[Software Demo Video](PASTE_YOUR_YOUTUBE_LINK_HERE)

# Cloud Database

The application uses **Google Cloud Firestore**, a NoSQL cloud database provided by Firebase. Firestore stores the inventory independently from the Android application, which allows product information to remain in the database even when the development environment or application is closed.

The database contains a collection named `products`. Each product is stored as a separate Firestore document with an automatically generated document ID.

The structure is similar to:

products
- documentId
    - name: "Tea"
    - category: "Food"
    - quantity: 8
    - minimumStock: 5

The application performs the following database operations:

- **Create:** Add a new product to the `products` collection.
- **Read:** Retrieve products from Firestore and display them in the inventory.
- **Update:** Change the quantity of a product using the `+` and `-` buttons.
- **Delete:** Remove a product and its document from Firestore.

# Development Environment

I developed the application using **Android Studio** and tested it using the Android Emulator with a virtual Pixel device.

The application is written in **Kotlin** and uses **Jetpack Compose** and **Material 3** for the user interface. I used the **Firebase Firestore Android SDK** to connect the Android application to Cloud Firestore.

The project also uses Kotlin data classes, collections, null-safe number conversion, state management, and Firestore snapshot listeners. A `FirestoreRepository` class separates the database operations from the user interface code and handles retrieving, adding, updating, and deleting products.

# Useful Websites

- [Firebase Documentation](https://firebase.google.com/docs)
- [Cloud Firestore Documentation](https://firebase.google.com/docs/firestore)
- [Android Developers - Jetpack Compose](https://developer.android.com/develop/ui/compose)
- [Kotlin Documentation](https://kotlinlang.org/docs/home.html)

# Future Work

- Replace the development Firestore security rules with more restrictive production rules.
- Add user authentication so inventory data can be associated with specific users.
- Add the ability to edit product names, categories, and minimum stock levels.
- Add search, filtering, and sorting for larger inventories.
- Improve loading, error, and confirmation messages for cloud database operations.