# Quiz App — Android Developer Technical Test

Native Android Quiz App built using **Kotlin + Jetpack Compose** for the Android Developer Technical Test.

## Features

- Home → Select Difficulty → Quiz → Result
- Easy, Medium, and Hard difficulty
- 10 dynamically generated questions per quiz
- 4 unique answer options with exactly 1 correct answer
- Current question progress and live score
- Replay quiz
- Best score persisted locally for each difficulty
- Unit tests for important question-generation rules

## Tech Stack

- Kotlin
- Native Android
- Jetpack Compose + Material 3
- MVVM-style state management with `ViewModel` + `StateFlow`
- Navigation Compose
- DataStore Preferences
- JUnit 4

## How to Run

1. Clone this repository.
2. Open it in Android Studio.
3. Use **JDK 17**.
4. Allow Gradle Sync to complete.
5. Run the `app` configuration on an emulator or physical Android device.

Project configuration:
- Minimum SDK: 24
- Target SDK: 35
- Compile SDK: 35

> If the repository does not contain Gradle Wrapper binaries, Android Studio can sync using its configured Gradle installation or you can generate the wrapper locally with `gradle wrapper`.

## Project Structure

```text
app/src/main/java/com/arwin/quizapp/
├── data/
│   └── BestScoreRepository.kt
├── domain/
│   ├── Models.kt
│   └── QuestionGenerator.kt
├── ui/
│   ├── screens/
│   │   ├── HomeScreen.kt
│   │   ├── DifficultyScreen.kt
│   │   ├── QuizScreen.kt
│   │   └── ResultScreen.kt
│   └── theme/
│       └── Theme.kt
├── MainActivity.kt
└── QuizViewModel.kt
```

The project intentionally uses a lightweight MVVM-style structure. A larger clean-architecture/module split was avoided because this is a small technical test and would add unnecessary complexity.

## Quiz Flow

```text
Home
  ↓
Select Difficulty
  ↓
Quiz (10 questions)
  ↓
Result
  ├── Play Again
  └── Back to Home
```

## Question Generator

Questions are generated at runtime rather than loaded from a static question bank.

Each `Question` contains:
- question text
- 4 unique integer options
- exactly 1 correct answer

Wrong answers are generated around the correct result, deduplicated, and shuffled with the correct answer.

### Difficulty

**Easy**
- Addition and subtraction
- Numbers mainly between 1 and 20

Example:
```text
12 + 7 = ?
```

**Medium**
- Multiplication
- Exact division
- Operands mainly between 2 and 12

Example:
```text
8 × 7 = ?
```

**Hard**
- Mixed operators
- Requires simple operator precedence

Example:
```text
16 + 4 × 6 = ?
```

The difference between difficulties is therefore implemented directly in the generation algorithm rather than only through labels.

## State Management

`QuizViewModel` owns the current `QuizState`, including:
- selected difficulty
- generated questions
- current question index
- score
- best score
- completion status

The UI observes a `StateFlow<QuizState>` and renders itself from that state.

## Local Persistence

`BestScoreRepository` uses **AndroidX DataStore Preferences**.

A separate best score is stored for:
- Easy
- Medium
- Hard

DataStore was chosen instead of Room because only a few scalar preference values need to be persisted.

## Testing

`QuestionGeneratorTest` verifies business rules that are important for the quiz:

- generated quiz contains exactly 10 questions
- every question has exactly 4 unique choices
- the correct answer is present exactly once
- Hard questions contain mixed operations

The goal is not maximum coverage, but testing the logic where bugs would affect core quiz behavior.

Run unit tests with Android Studio or:

```bash
./gradlew test
```

when Gradle Wrapper is available.

## Technical Decisions

### Jetpack Compose
Compose fits the application's state-driven and relatively small UI and avoids unnecessary XML boilerplate.

### No dependency-injection framework
For a project of this size, Hilt/Koin would add setup and abstraction with little practical benefit. Dependencies are passed through a small `ViewModelProvider.Factory`.

### Runtime question generation
This makes the app fully offline and demonstrates business logic without requiring an API or database.

### DataStore instead of Room
Only the best-score values need persistence, so a relational database would be unnecessary.

## Trade-offs / Possible Improvements

With more time I would consider:
- visual correct/wrong feedback before advancing
- timer-based quiz mode
- question categories
- richer score history
- UI/instrumentation tests
- localization
- accessibility review
- saved-state restoration for an interrupted quiz

## Author

**Muhammad Arwin Renardi Widyoko**
