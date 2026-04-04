# Outstanding

This repository contains the ongoing development of a KMP application for a geolocation social media platform. The project was originally prototyped by [@tavro](https://tavro.se) and [@parslie](https://parslie.github.io) as part of a university course and later reimagined as a hobby project. The goal of this repository is to evolve that prototype into a finalized open-source implementation.

## Software Architecture Ideas

### Technology Stack

  | Layer                | Technology             |
  |----------------------|------------------------|
  | Language             | Kotlin                 |
  | UI Framework         | Compose Multiplatform  |
  | Architecture Pattern | Component-based        |
  | Dependency Injection | Koin                   |
  | Navigation           | Decompose              |
  | Networking           | Ktor + Ktorfit + Wire  |
  | Database             | AndroidX Room          |
  | Serialization        | kotlinx.serialization  |
  | Build System         | Gradle with Kotlin DSL |

## Component Structure

Each feature should follow this pattern:

```kotlin
class FeatureComponent(
    componentContext: ComponentContext,
    private val navigator: Navigator,
    private val useCase: FeatureUseCase,
) : ComponentContext by componentContext {

    private val _state = MutableStateFlow(FeatureState())
    val state = _state.asStateFlow()

    val stack: Value<ChildStack<Config, Child>> = childStack(...)

    fun onAction(action: Action) { ... }
}

@Composable
fun FeatureScreen(component: FeatureComponent) {
    val state by component.state.collectAsStateWithLifecycle()
}
```

## Navigation System

### Config

```kotlin
@Serializable
sealed interface Config {
    @Serializable
    data class Main(val tab: Tab = Tab.Map) : Config

    @Serializable
    sealed interface Settings : Config {
        @Serializable
        data object Main : Settings
    }

    enum class Tab { Map, Feed, Create, Profile }
}
```

### Navigator

```kotlin
class Navigator {
    val stack = StackNavigation<Config>()

    fun navigateTo(config: Config) = stack.navigate { listOf(config) }
    fun popToMain(tab: Config.Tab = Config.Tab.Map) = stack.navigate { listOf(Config.Main(tab)) }
    fun pop() = stack.navigate { it.dropLast(1).ifEmpty { listOf(Config.Main()) } }
}
```

## Dependency Injection

  Modules will be organized per feature:

```kotlin
val uiModule = module {
    includes(
        mapModule,
        feedModule,
        profileModule,
        navigationModule,
    )

    factory<RootComponent> { params ->
        RootComponent(
            componentContext = params.get(),
            navigator = get(),
        )
    }

    factoryOf(::MainComponent)
    factoryOf(::MapComponent)
    factoryOf(::FeedComponent)
    // ...
}
```

## Data Layer

### Repository Pattern

```kotlin
class AccountRepository(private val dao: AccountDao) {
    suspend fun save(account: Account): Long
    suspend fun update(account: Account)
    fun getAccountFlow(): Flow<Account?>
    suspend fun clear()
}
```

### Room Database

```kotlin
@Dao
interface AccountDao {
    @Query("SELECT * FROM accounts LIMIT 1")
    fun getAccountFlow(): Flow<AccountEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(account: AccountEntity): Long
}
```

### Networking

Ktor + Ktorfit for REST APIs

```kotlin
@Ktorfit
interface ExampleApi {
    @POST("/v1/examples")
    suspend fun createExample(@Body request: ExampleRequest): ExampleResponse
}
```

---

## Project Structure

* [/composeApp](./composeApp/src) is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - [commonMain](./composeApp/src/commonMain/kotlin) is for code that's common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple's CoreCrypto for the iOS part of your Kotlin app,
    the [iosMain](./composeApp/src/iosMain/kotlin) folder would be the right place for such calls.

* [/iosApp](./iosApp/iosApp) contains iOS applications. Even if you're sharing your UI with Compose Multiplatform,
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

## Build and Run

### Android

```shell
./gradlew :composeApp:assembleDebug
```

### iOS

Open the [/iosApp](./iosApp) directory in Xcode and run it from there, or use the IDE run configuration.

### Web (Wasm — modern browsers)

```shell
./gradlew :composeApp:wasmJsBrowserDevelopmentRun
```

### Web (JS — broader browser support)

```shell
./gradlew :composeApp:jsBrowserDevelopmentRun
```

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html),
[Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform/#compose-multiplatform),
[Kotlin/Wasm](https://kotl.in/wasm/).
