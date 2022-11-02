# GitApp

合翔-Android 工程師筆試
請用以下 API 獲取 list-users 資料 https://docs.github.com/en/rest/users/users#list-users

> 需求
1. Network 請求 @GET https://api.github.com/users 2. RecyclerView 清單顯示
   - 使用者名稱 login
   - 大頭照 avatar_url
3. 點項目開啟 Browser html_url

# 架構說明

1. 採用 MVVM + repository
2. 使用Koin DI
3. UI 的部分，整體來說是使用 Compose UI 建構。但為符合考題, 清單的部分預設採用 `RecyclerView` 實作。如果清單也要換成 Compose UI, 可以將 `MainActivity.ky:43`裡的 `useRecyclerView` 改成false
```Kotlin
// MainActivity.ky:43
UserListScreen(userList = userList, useRecyclerView = true) // useRecyclerView = false, 即直接使用LazyColumn
```
4. API request 則為 OkHttp + Retrofit
5. 圖片處理使用 `Coil`, 一個專門為kotlin 處理圖片的 package, 用法跟`glide` 極為相似
6. viewModel 使用StateFlow 取代 viewModel.

# 其他
若期望為 傳統完整的XML 方式構築畫面，我也有預備一份，需要可以說。

架構為 MVVM + DataBinding + LiveData + Repository
圖片處理使用`Glide`

