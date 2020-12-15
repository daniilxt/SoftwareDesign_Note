# Заметки
<br /> Группа 3530904/80103 <br /> Над проектом работали: Фирсов, Коршунов, Королев
# Проблема
<br /> Данное приложение решает проблему структурирования заметок.

# Требования
<br />1.	Разработать систему позволяющую удаленно хранить заметки, изменять, просматривать и удалять их
<br />2.	Реализовать возможность изменять статус «избранное» у заметок и сортировать их по данному статусу
<br />3.	Реализовать поиск заметок по заглавию

# Разработка архитектуры и детальное проектирование
<br />1)	Контекстная диаграмма
<br /> ![Image alt](https://github.com/daniilxt/SoftwareDesign_Note/blob/master/img/contextDiag.jpg)
<br />2)	Контейнерная диаграмма
<br /> ![Image alt](https://github.com/daniilxt/SoftwareDesign_Note/blob/master/img/containerDiag.jpg)

# Кодирование и отладка
<br />Проект представляет собой Android приложение, реализованное на языке kotlin с использованием nosql базы данных реального времени Firebase.

# Unit тестирование
<br />Было реализовано Unit тестирование авторизации и регистрации новых пользователей в облачной базе данных Firebase. Тестирование было проведено с помощью фреймворка автоматического тестирования JUnit.
<br />Данные тесты представлены в файле<a href="https://github.com/daniilxt/SoftwareDesign_Note/tree/master/app/src/androidTest/java/com/university/softwaredesign_note/FirebaseAuthUnitTest.kt">FirebaseAuthUnitTest.kt</a>
  
# Интеграционное тестирование
<br />Реализованы интеграционные тесты, которые покрывают следующие пользовательские сценарии:
<br />1)	Добавление и удаление заметки (fragment_view_model_add_Note_is_current_test)
<br />2)	Изменение статуса “в избранных” заметки (fragment_view_model_change_like_status_is_current_test)
<br />3)	Поиск заметок по заглавию (fragment_view_model_search_is_current_test)
<br />4)	Сортировка заявок по статусу “в избранных” (fragment_view_model_filter_by_like_is_current_test)
<br />Данные тесты представлены в файле<a href="https://github.com/daniilxt/SoftwareDesign_Note/tree/master/app/src/androidTest/java/com/university/softwaredesign_note/NoteViewIntegrationTest.kt">NoteViewIntegrationTest.kt</a>

# Сборка проекта
<br />Для сборки, выполнения тестов, интеграционных тестов и запуска приложения необходимо выполнить следующею команду в терминале в директории с проектом.
<br />gradlew test app:connectedAndroidTest installDebug
<br /> ![Image alt](https://github.com/daniilxt/SoftwareDesign_Note/blob/master/img/build.jpg)

# Скриншоты работы приложения
<br /> 1)	Окно авторизации и регистрации
<br /><img src="https://github.com/daniilxt/SoftwareDesign_Note/blob/master/img/auth.jpg" 
  width="480" height="960">
<br /> 2)	Главное окно, в котором отображены все существующие заявки 
<br /><img src="https://github.com/daniilxt/SoftwareDesign_Note/blob/master/img/main.jpg" 
  width="480" height="960">
<br /> 3)	Окно редактирования заметок и добавления новых
<br /><img src="https://github.com/daniilxt/SoftwareDesign_Note/blob/master/img/edit.jpg" 
  width="480" height="960">
<br /> 4)	Окно избранных заметок
<br /><img src="https://github.com/daniilxt/SoftwareDesign_Note/blob/master/img/liked.jpg" 
  width="480" height="960">
<br /> 5)	Окно поиска
<br /><img src="https://github.com/daniilxt/SoftwareDesign_Note/blob/master/img/search.jpg" 
  width="480" height="960">
