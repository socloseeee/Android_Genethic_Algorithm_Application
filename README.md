# PMU_Android_labs
## Колисниченко "Программирование для Android" 177-183 (13-й в списке)
## Глава 8
Предпочтения хранятся в ХМL-файлах в незашифрованном виде в локальном хранилище.
Они невидимы, поэтому для простого пользователя недоступны.
При работе с предпочтениями следует учитывать следующие моменты. Поскольку
они хранятся в незашифрованном виде, то не рекомендуется сохранять в них чувствительные
данные типа паролей или номеров банковских карт. Кроме того, они
представляют данные, ассоциированные с приложением, и через панель управления
приложениями пользователь может удалить эти данные вместе с приложением.
Существуют разные типы доступа к предпочтениям:
+ ```MODE_PRIVATE```
+ ```MODE_WORLD_READABLE```
+ ```MODE_WORLD_WRITABLE```

Для работы с разделяемыми предпочтениями в классе ```Activity``` (точнее, в его базовом
классе ```Context```) имеется метод ```getSharedPreferences()```:
```
import android.content.SharedPreferences;
// .......................... .
SharedPreferences settings = getSharedPreferences("PreferencesName",
    MODE PRIVATE) ;
```
Первый параметр метода указывает на название предпочтения. В показанном случае
это название: ```"PreferencesName"```. Если предпочтений с подобным названием
нет, то они создаются при вызове этого метода. Второй параметр указывает на
режим доступа. В нашем случае режим описан константой ```MODЕ_PRIVATE```.
Класс ```android.content.SharedPreferences``` предоставляет ряд методов для управления
предпочтениями:
