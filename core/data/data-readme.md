Data Module Readme

This document explains the architecture and components of the data module, which is responsible for all network communication and data storage for the application. It uses Retrofit for API calls and Hilt for dependency injection.
📂 Project Structure

This module is composed of several key components that work together to handle data.
1. Retrofit Interfaces

   AuthService.kt & Profile.kt

        What they do: These are Retrofit interfaces that define the API endpoints for authentication (/login, /register) and fetching a user's profile (/api/profile/profile). They serve as the "contracts" for your network communication.

        How they work: Hilt, configured by the NetworkModule, provides a concrete implementation of these interfaces that Retrofit generates at runtime. The ViewModel then calls the methods on this generated implementation to make network requests.

2. Token Management

   TokenRepository.kt & SharedPreferencesTokenRepository.kt

        What they do: TokenRepository is an interface that abstracts the process of saving and retrieving an authentication token. SharedPreferencesTokenRepository is the concrete class that implements this behavior using Android's SharedPreferences.

        How they work: The RepositoryModule, with the @Binds annotation, tells Hilt: "Whenever a TokenRepository interface is requested, provide a SharedPreferencesTokenRepository instance." This allows the consuming class (e.g., the ViewModel) to use the repository without knowing the specifics of how the token is stored.

3. Dependency Injection (Hilt Modules)

These modules are the "recipes" that tell Hilt how to construct all the dependencies within this module.

    NetworkModule.kt (object)

        What it does: Uses the @Provides annotation to create complex instances like AuthService, Profile, and OkHttpClient, which cannot be constructor-injected.

    RepositoryModule.kt (abstract class)

        What it does: Uses the more efficient @Binds annotation to map the TokenRepository interface to its SharedPreferencesTokenRepository implementation.

    StorageModule.kt (object)

        What it does: Uses the @Provides annotation to create a SharedPreferences instance, a context-dependent class that cannot be constructor-injected and is required by the SharedPreferencesTokenRepository.