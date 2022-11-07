# GitApp

Just practice with Compose UI~
簡易的 Github User 清單

# 架構說明

1. 採用 MVVM + repository
2. 使用Koin DI
3. UI: Compose UI 建構。
4. API request 則為 OkHttp + Retrofit
5. 圖片處理使用 `Coil`, 一個專門為kotlin 處理圖片的 package, 用法跟`glide` 極為相似
6. viewModel 使用StateFlow 取代 LiveData.
