# Inventory Manager Android

## Overview

Inventory Manager Android is a mobile application created with Kotlin for managing a small product inventory.

The app allows users to view products, increase or decrease stock quantities, identify products with low stock, and add new products through a separate screen. To use the app, the user can tap the `+` or `-` buttons to update the quantity of a product or select **Add Product** to enter a product name, category, quantity, and minimum stock level.

I created this application to continue improving my Kotlin skills while learning how to develop Android applications. My main goal was to learn how to create mobile user interfaces, manage application state, respond to user input, and work with multiple screens using Jetpack Compose.

# Development Environment

I developed the application using **Android Studio** and tested it with the Android Emulator using a virtual Pixel device.

The application is written in **Kotlin** and uses **Jetpack Compose** to build the user interface. I also used **Material 3** components such as buttons, cards, text fields, and text elements.

The project uses Kotlin features such as data classes, collections, null-safe number conversion, and the `copy()` function to update product information.

# Useful Websites

* [Android Developers](https://developer.android.com/)
* [Jetpack Compose Documentation](https://developer.android.com/develop/ui/compose)
* [Kotlin Documentation](https://kotlinlang.org/docs/home.html)

# Future Work

* Add persistent storage so inventory data remains available after the app is closed.
* Connect the application to a cloud database such as Firebase Firestore.
* Add options to edit or delete existing products.
* Add search, filtering, and sorting options for larger inventories.
