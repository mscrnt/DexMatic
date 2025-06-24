# DexMatic

DexMatic is an AI‑powered digital Rolodex for Android and iOS. It lets you scan or import business cards, performs on‑device OCR, parses contact details, enriches them via services like Google Places and LinkedIn, and keeps everything synced to the cloud.

## Tech Stack
- **Kotlin Multiplatform** for shared business logic
- **Jetpack Compose** for Android UI
- **SwiftUI** for iOS UI
- **Ktor** for networking
- **GraphQL/REST** sync service

## Repository Layout
- `architecture/` – design documents and diagrams
- `shared/` – Kotlin Multiplatform core (OCR, models, networking)
- `android/` – Android app using Jetpack Compose
- `ios/` – iOS app using SwiftUI

## Roadmap
1. Scaffold shared core and platform apps
2. Integrate on-device OCR
3. Implement card parsing logic
4. Add data enrichment and background sync
5. Build out full UI flows
6. Persistence and cloud sync
7. CI/CD pipelines and tests
8. Finalize docs and release v0.1.0
