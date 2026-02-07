# Outstanding

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

```
  class FeatureComponent(
      componentContext: ComponentContext,
      private val navigator: Navigator,
      private val useCase: FeatureUseCase,
  ) : ComponentContext by componentContext, MiniPayKoinComponent {

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

```
  @Serializable
  sealed class Config(override val debugName: String) : BaseConfig {
      @Serializable
      data class Main(val tab: Tab = Tab.Home) : Config("Main")

      object Settings {
          @Serializable
          data object Main : Config("Settings")
          // ...
      }
  }
```

### Navigator

```
  class Navigator : StackNavigation<Config> {
      val dialog: SlotNavigation<SlotConfig>

      fun navigateToUrl(url: String, ...)
      fun popToMain(tab: Config.Main.Tab)
      // ...
  }
```

## Dependency Injection

  Modules will be organized per feature:

```
  val uiModule = module {
      includes(
          onboardingModule,
          settingsModule,
          navigationModule,
          // ...
      )

      factory<RootComponent> { params ->
          RootComponent(
              componentContext = params.get(),
              accountProvider = get(),
              navigator = get(),
              // ...
          )
      }

      factoryOf(::MainComponent)
      factoryOf(::HomeComponent)
      // ...
  }
```

## Data Layer

### Repository Pattern

```
  class AccountRepository(private val dao: AccountDao) {
      suspend fun save(account: Account): Long
      suspend fun update(account: Account)
      fun getAccountFlow(): Flow<Account?>
      suspend fun clear()
  }
```

### Room Database

```
  @Dao
  interface AccountDao {
      @Query("SELECT * FROM accounts LIMIT 1")
      fun getAccountFlow(): Flow<AccountEntity?>

      @Insert(onConflict = OnConflictStrategy.REPLACE)
      suspend fun save(account: AccountEntity): Long
  }
```

### Networking

```
  Ktor + Ktorfit for REST APIs:

  @Ktorfit
  interface ExampleApi {
      @POST("/v1/examples")
      suspend fun createExample(@Body request: ExampleRequest): ExampleResponse
  }
```
