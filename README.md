# Aspen - Travel & Vacation Booking App (Kotlin / Jetpack Compose)

Современное мобильное и кроссплатформенное приложение на **Kotlin** в **Android Studio**, созданное по мотивам макета Figma: [Travel Vacation Booking Android App](https://www.figma.com/design/siieyH9bEDpzsA2abXQ4aJ/Travel-Vacation-Booking-Android-App--Community-?node-id=11-1273).

---

## 📱 Реализованные экраны и возможности

1. **Экран приветствия (Onboarding Screen)**:
   - Полноэкранный атмосферный фон с градиентным затемнением.
   - Заголовок *"Plan your Luxurious Vacation"* и описание.
   - Интерактивная кнопка **«Explore»** для перехода в главное меню.

2. **Главный экран (Explore / Home Screen)**:
   - Верхняя панель: выбор текущей локации (*Aspen, USA*) и колокольчик уведомлений.
   - Строка поиска с фильтром (*"Find things to do"*).
   - Горизонтальные чипы категорий (*Location, Hotels, Food, Adventure, Villas*).
   - Секция **«Popular»**: горизонтальная карусель карточек с фотографией, бейджем рейтинга (⭐ 4.8), кнопкой добавления в избранное (сердечко) и ценой.
   - Секция **«Recommended»**: карточки с длительностью тура (*4N/5D*).
   - Нижняя навигационная панель (*Home, Saved, Wishlist, Profile*).

3. **Экран деталей локации (Destination Details Screen)**:
   - Крупная галерея/обложка с плавающими кнопками «Назад» и «В избранное».
   - Название, рейтинг с количеством отзывов, геометка и кнопка *"Show map"*.
   - Раскрывающееся описание (*"Read more / Show less"*).
   - Сетка удобств (**Facilities**): обогреватель, ужин, джакузи/ванна, бассейн, скоростной Wi-Fi.
   - Фиксированная нижняя панель с ценой за ночь и кнопкой **«Book Now»**.

4. **Модальное окно бронирования (Booking Modal Sheet)**:
   - Выбор дат заезда и выезда.
   - Счетчик количества гостей (+ / -).
   - Автоматический расчет стоимости (проживание + сервисный сбор).
   - Подтверждение бронирования с экраном успешного оформления.

5. **Экран сохраненного (Favorites Screen)**:
   - Список всех добавленных в избранное мест с возможностью быстрого перехода или удаления.

---

## 🛠 Стек технологий

- **Язык**: Kotlin 2.0+
- **UI Toolkit**: Jetpack Compose (Material 3)
- **Архитектура**: MVVM (Model-View-ViewModel) + Single Source of Truth
- **Реактивность**: Kotlin Coroutines, StateFlow, Flow combine
- **Навигация**: Jetpack Navigation Compose
- **Загрузка изображений**: Coil Compose
- **Сборщик**: Gradle 8.7+ (Kotlin DSL `.gradle.kts`, Version Catalog `libs.versions.toml`)

---

## 🚀 Как открыть и запустить проект в Android Studio

1. Откройте **Android Studio** (Hedgehog, Iguana, Koala, Ladybug или новее).
2. Выберите **File -> Open...** (или на стартовом экране *Open*).
3. Перейдите по пути:
   ```
   C:\Users\caran\.gemini\antigravity\scratch\TravelBookingApp
   ```
4. Дождитесь автоматической синхронизации Gradle (**Sync Project with Gradle Files**).
5. Выберите эмулятор (например, *Pixel 8*) или подключите реальное Android-устройство.
6. Нажмите зеленую кнопку **Run 'app'** (Shift + F10).

---

## 🌐 Кроссплатформенность и Web (Compose Multiplatform / Wasm)

Код интерфейса написан на чистом декларативном **Compose**. При необходимости сборки под Web:
- Данную кодовую базу можно преобразовать в **Compose Multiplatform (CMP)** с таргетами `android` и `wasmJs` / `js`.
- Вся верстка экранов (`OnboardingScreen`, `HomeScreen`, `DetailScreen`) остаётся 100% общей в модуле `composeApp/commonMain` и рендерится как в Android, так и в браузере в WebAssembly (Wasm).
