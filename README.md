# Android App Template (Jetpack Compose + MVI + Nav3)

Use this repository as a starting point for modern Android apps. It abstracts the “every app needs
this” setup (architecture, DI, networking, persistence, testing scaffolding) so you can focus on
your product features.

Any comments, feedback, or suggestions are appreciated.

## Highlights

- **Version catalog**: Organized `libs.versions.toml` with **bundles by category** in [
  `gradle/libs.versions.toml`](gradle/libs.versions.toml)
- **Single-Activity app**: [
  `app/src/main/java/com/example/app/MainActivity.kt`](app/src/main/java/com/example/app/MainActivity.kt)
- **Permissions**: Only `INTERNET` in [
  `app/src/main/AndroidManifest.xml`](app/src/main/AndroidManifest.xml)
- **Architecture (3 layers)**:
  - **Data**: DataSources + repositories (in `app/src/main/java/com/example/app/data/**`)
  - **Domain**: UseCases + models (in `app/src/main/java/com/example/app/domain/**`)
  - **UI**: Compose screens using an MVI setup (in `app/src/main/java/com/example/app/ui/**`)
- **Mappers**: DTO ↔ domain ↔ entity mapping lives in the data layer (e.g. `TaskMapper`)
- **Base `ComposeViewModel<S, E>`**: exposes a `uiState()` and handles `onEvent(...)` in [
  `app/src/main/java/com/example/app/common/ComposeViewModel.kt`](app/src/main/java/com/example/app/common/ComposeViewModel.kt)
- **Navigation**: **Compose Navigation 3** via `NavDisplay` in [
  `app/src/main/java/com/example/app/navigation/Navigation.kt`](app/src/main/java/com/example/app/navigation/Navigation.kt)
- **DI**: Hilt + modules for app wiring:
  - [
    `app/src/main/java/com/example/app/di/KtorModule.kt`](app/src/main/java/com/example/app/di/KtorModule.kt)
  - [
    `app/src/main/java/com/example/app/di/PersistenceModule.kt`](app/src/main/java/com/example/app/di/PersistenceModule.kt)
  - [
    `app/src/main/java/com/example/app/di/AppModule.kt`](app/src/main/java/com/example/app/di/AppModule.kt)
- **Database**: Room (with schema export dir configured in `app/build.gradle.kts`)
- **Key-value persistence**: DataStore Preferences
- **Token encryption**: access token stored in DataStore is **encrypted with AES/GCM** (Android
  Keystore-backed)
- **Serialization**: Kotlinx Serialization + `InstantSerializer` (for `kotlin.time.Instant`)
- **Localization**: app strings in [
  `app/src/main/res/values/strings.xml`](app/src/main/res/values/strings.xml)
- **Image loading**: Coil
- **Compose previews**: sample previews included in UI screens

## Screens included

- **Login**: [
  `app/src/main/java/com/example/app/ui/login/LoginScreen.kt`](app/src/main/java/com/example/app/ui/login/LoginScreen.kt)
- **Home**: pull-to-refresh + search in [
  `app/src/main/java/com/example/app/ui/home/HomeScreen.kt`](app/src/main/java/com/example/app/ui/home/HomeScreen.kt)
- **Settings**: logout in [
  `app/src/main/java/com/example/app/ui/settings/SettingsScreen.kt`](app/src/main/java/com/example/app/ui/settings/SettingsScreen.kt)

## Tech stack

- **UI**: Jetpack Compose + Material 3
- **Architecture**: 3-layered - Data, Domain, UI
- **Architectural pattern**: MVI
- **Navigation**: `androidx.navigation3` (Nav3)
- **DI**: Hilt (with KSP)
- **Networking**: Ktor client + ContentNegotiation + Logging + Bearer auth
- **Persistence**: Room + DataStore Preferences
- **Serialization**: Kotlinx Serialization (JSON)
- **Functional utils**: Arrow `Either`
- **Images**: Coil 3
- **Testing**:
  - Unit tests (`app/src/test`)
  - Screenshot tests (Paparazzi)
  - Automated UI tests (`app/src/androidTest`) with Compose test APIs + Hilt test runner

## Getting started

### Prerequisites

- Android Studio (recommended)
- JDK 11 (the project targets Java/Kotlin JVM 11 in Gradle)

### Run the app

- Open the project in Android Studio and run the `app` configuration, or build from the command
  line:

```bash
./gradlew :app:assembleDebug
```

## Project structure (high level)

```
app/src/main/java/com/example/app/
  data/        # DataSources, Repositories, Mappers, Persistence, Networking
  domain/      # UseCases + Models
  ui/          # Screens + ViewModels + UI state/events + UI mappers
  navigation/  # Screen definitions + Nav3 display + Simple navigator
  di/          # Hilt modules
```

## Architecture overview (MVI-style)

Each screen follows the same pattern:

- **State**: Immutable UI model (e.g. `HomeState`, `LoginState`, `SettingsState`)
- **Event**: User intents (e.g. `HomeEvent`, `LoginEvent`, `SettingsEvent`)
- **ViewModel**: Holds state and reacts to events via `onEvent(...)`

## Networking (Ktor) setup

The project provides a pre-wired `HttpClient` in [
`app/src/main/java/com/example/app/di/KtorModule.kt`](app/src/main/java/com/example/app/di/KtorModule.kt):

- **Base URL**: default request URL prefix is currently set to
  `https://api.example.com/`.\n  Update it in `KtorModule` to point to your real backend.
- **JSON**: Kotlinx Serialization via `ContentNegotiation`.
- **Logging**: Ktor Logging is installed; logs are routed through the app `Logger` which only prints
  in debug builds.
- **Auth**: Bearer auth loads access token from `SessionStorage`. For calls that should attach
  auth, mark the request as authenticated via `authenticated()` (see `KtorModule` helpers).
- **Auto logout on invalid session**: [
  `KtorLogoutPlugin`](app/src/main/java/com/example/app/data/ktor/KtorLogoutPlugin.kt) logs out and
  triggers a global invalid-session event when a protected request gets `401 Unauthorized`.

## DataSources included (template behavior)

By default, the app uses **fake** implementations for demo/testing:

- `LoginDataSource` is provided as `FakeLoginDataSource` in [
  `app/src/main/java/com/example/app/di/AppModule.kt`](app/src/main/java/com/example/app/di/AppModule.kt)
- `TaskRemoteDataSource` is provided as `FakeTaskRemoteDataSource` in the same module

When adopting this template, replace these with real implementations that call your backend using
the provided Ktor `HttpClient`.

## Persistence

- **Room**:
  - DB: [`MyAppDatabase`](app/src/main/java/com/example/app/data/database/MyAppDatabase.kt)
  - Tasks: `TaskEntity` + `TaskDao` under `app/src/main/java/com/example/app/data/database/task/`
  - Config: Room schema export directory is set in [`app/build.gradle.kts`](app/build.gradle.kts)
- **DataStore**:
  - `SessionStorage` stores the access token under `DataStoreKeys`
  - The access token is encrypted before writing (and decrypted on read)

## Security: encrypted access token

Access tokens are stored in DataStore as encrypted values:

- Encryption/decryption: [
  `Cryptography`](app/src/main/java/com/example/app/data/cryptography/Cryptography.kt) (AES/GCM)
- Storage: [`SessionStorage`](app/src/main/java/com/example/app/data/datastore/SessionStorage.kt)

## Testing

### Unit tests

Unit tests live under `app/src/test` and cover UseCases and mappers (and some repository behavior).

### Screenshot tests (Paparazzi)

This project uses Paparazzi for Compose screenshot testing. A `Makefile` is provided to simplify
recording/verifying baselines:

```bash
make record_screenshot
make verify_screenshot
```

Under the hood these run:

- `./gradlew :app:recordPaparazziDebug`
- `./gradlew :app:verifyPaparazziDebug`

Recorded baselines are committed under `app/src/test/snapshots/`.

### Automated UI tests

Instrumented UI tests live under `app/src/androidTest` and are set up with:

- **Compose UI testing**
- **Hilt testing** with a custom runner: [
  `app/src/androidTest/java/com/example/app/HiltTestRunner.kt`](app/src/androidTest/java/com/example/app/HiltTestRunner.kt)

## Release / ProGuard (R8)

- Release builds enable shrinking/obfuscation in [`app/build.gradle.kts`](app/build.gradle.kts)
- Rules are in [`app/proguard-rules.pro`](app/proguard-rules.pro)

## Configuration checklist when using this template

- **Application id/namespace**: update in [`app/build.gradle.kts`](app/build.gradle.kts)
- **App name**: update `app_name` in [
  `app/src/main/res/values/strings.xml`](app/src/main/res/values/strings.xml)
- **Backend base URL**: update in [
  `app/src/main/java/com/example/app/di/KtorModule.kt`](app/src/main/java/com/example/app/di/KtorModule.kt)
- **Replace fake DataSources**: swap out `FakeLoginDataSource` / `FakeTaskRemoteDataSource` bindings
  in [
  `app/src/main/java/com/example/app/di/AppModule.kt`](app/src/main/java/com/example/app/di/AppModule.kt)

## Feedback / contributions

Issues and PRs are welcome. If you spot something incorrect or missing in the template, please open
an issue describing:

- What you expected
- What happened
- Steps to reproduce (if applicable)