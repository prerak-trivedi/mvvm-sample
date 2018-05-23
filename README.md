# MVVM Sample Project

Just a sample project to demonstrate a simple use case for using MVVM architecture along with the [Arch Extensions library](https://developer.android.com/arch)

Currently, only `SplashActivity` observes the streams on `SplashViewModel` and accordingly updates the UI.

A handler is used to simulate network calls and notify the view. Obviously, in reality it should be replaced with `DataManagers` which make the actual network calls to get the `DataModels` from the server.
