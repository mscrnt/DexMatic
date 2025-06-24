# DexMatic

DexMatic is an AI-powered digital Rolodex for Android and iOS. It lets you scan or import business cards, performs on-device OCR, parses contact details, enriches them via services like Google Places and LinkedIn, and keeps everything synced to the cloud.

---

## Tech Stack
- **Kotlin Multiplatform** for shared business logic  
- **Jetpack Compose** for Android UI  
- **SwiftUI** for iOS UI  
- **Ktor** for networking  
- **GraphQL/REST** sync service  

---

## Repository Layout

```

architecture/        — design docs & diagrams
shared/              — Kotlin Multiplatform core (OCR, models, networking)
androidApp/          — Android app (Jetpack Compose)
iosApp/              — iOS app (SwiftUI)

```

---

## What’s Done

### 🏗️ Project Setup
- **Kotlin Multiplatform** scaffold in `shared/`
- Gradle (8.3.0) + Kotlin plugin (1.9.21) configured
- AndroidX & Compose BOM enabled, `android.useAndroidX=true`

### 📱 Android App (androidApp/)
- **Camera → OCR flow**  
  • `ScanCardScreen` captures camera preview, stubs OCR via `FakeOcrService`  
  • `CameraViewModel` drives UI states (Idle, Loading, Success, Error)  
- **Navigation** via Compose Navigation (`NavGraph.kt`)  
  • `scan` → `review` → `list` destinations wired up  
- **Review & List screens** stubbed (`ReviewCardScreen`, `ContactListScreen`)  
- **UI Theme**  
  • `Color.kt`, `Typography.kt`, `Shape.kt`, `Theme.kt`  
  • MaterialComponents DayNight base theme  
- **Build & run** on emulator / device (Gradle tasks: `:androidApp:installDebug`)

---

## What’s Left

1. **Review UI & Edit**  
   - Flesh out `ReviewCardScreen` for manual corrections, confirm/save button  
2. **Contact List**  
   - Load & display saved contacts in `ContactListScreen`  
   - Swipe-to-delete, detail view  
3. **Persistence Layer**  
   - Room (Android) / Core Data (iOS) integration  
4. **Real OCR**  
   - Hook up ML Kit (Android) & Vision/Core ML (iOS) instead of stub  
5. **Data Enrichment**  
   - Google Places, LinkedIn lookups  
6. **Networking & Sync**  
   - Ktor client + GraphQL/REST sync service, conflict resolution  
7. **iOS App**  
   - SwiftUI equivalents of all screens & flow under `iosApp/`  
8. **CI/CD & Tests**  
   - Unit & UI tests for shared logic and platform apps  
   - GitHub Actions / Fastlane pipelines  
9. **v0.1.0 Release**  
   - Final polish, docs, store listings  