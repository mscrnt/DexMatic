# DexMatic Architecture

DexMatic uses Kotlin Multiplatform to share core logic across Android and iOS. The shared `core` module provides OCR interfaces, data models, and networking logic. Each platform implements its own UI using the shared core.

## Module Diagram

```mermaid
graph TD
    subgraph Shared
        Core["KMM Core: OCR, Models, Networking"]
    end
    subgraph Android
        AUI["Jetpack Compose App"]
    end
    subgraph iOS
        IUI["SwiftUI App"]
    end
    subgraph Services
        Sync["REST/GraphQL Sync Service"]
    end
    Core --> AUI
    Core --> IUI
    Core --> Sync
```

This architecture maximizes shared business logic while allowing each platform to leverage native UI frameworks and capabilities.
