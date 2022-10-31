# RoguelikeMKN
Roguelike — это довольно популярный жанр компьютерных игр, назван в честь игры Rogue, 1980 года выхода.
## Общие сведения о системе
### Жанр, платформа, требования

Жанр игры: Roguelike  

Количество игроков: однопользовательская локальная игра  

Операционная система: Windows 10/ Mac OS Monterey 12.6  

Графика: 2D 

ОЗУ: 1GB  

Свободное место на диске: 512MB   

Управление: мышь, клавиатура  

Общие требования:  
- Наличие меню;
- Предоставление собственноручной статистики игроку;
- Необходимость сохранения прогресса игрока;
- Загрузка и генерация уровня;
- Слабосвязанная архитектура компонент.

Игра не использует торговые марки или другую собственность, подлежащую лицензированию.

### Ключевые особенности игры
-  Игра расчитана на реиграбельность

## Architectural Drivers
### Словарь
- Бот - "робот", неодушевленный участник игры под управлением компьютера
- Лут - предметы, которые можно подбирать в инвентарь
- ПКМ - правая кнопка мыши
- ЛКМ - лева кнопка мыши

### Ключевые требования
#### Технические ограничения: 
Программный язык разработки - Java  
Платформа разработки - Intellij Idea  
#### Качественные характеристики системы
Ограничие частоты кадров - 30 FPS
#### Функциональные требования
- Возможность загрузить уровень из файла
- Возможность процедурно сгенерировать уровень
- Игрок может перемещаться по карте
- Персонаж игрока обладает характеристиками: здоровье, опыт, броня, атака. В процессе игры данные характеристики могут изменяться.
- Песронаж игрока обладает инвентарем. Инвентарь делится на две части. Обычный инвентарь и инвентарь надетых вещей.
- Предметы находящиеся в инвентаре надетых вещей влияют на характеристики персонажа
- Рюкзак обладает лимитом находящихся в нем вещей
- Предметы находятся на карте и их можно поднимать
- В мире находятся интерактивные объекты с которыми можно взаимодействовать

### Архитектурные виды
#### Контекст
Система осуществляет взаимодействие с игроком через графический интерфейс. Пользователь ожидает от системы интуитивную понятность в игре, а также геймплей соответсвующий жанру Roguelike.
#### Диаграмма компонентов 
![Alt text](images/roguelike_component.png?raw=true "Title")

- UI - компонент который отвечает за ввод пользователем в систему, а также отображает вывод системы.
- Engine - компонент отвечающий за обработку игровых событий, а также преобразованием их в системный вывод. Компонент Engine состоит из двух дочерних компонент.
- - Render - компонента отвечающая за преобразование игрового состояния в системный выход. Также отвечает за оптимизацию вывода.
- - Game Processer - компонента которая создает и манипулирует игровыми объектами.
- World - компонента мира. Отвечает за карту и другие состояния уровня.
- Map Generator - компонента отвечающая за генерацию уровней.
- Pawn - компонента (объект) персонажа, которая может управляться как пользователем, так и ботом. В зависимоти от ситуации может содержать в себя различные вспомогательные компоненты, расширяющие возможности пешки. Основные компоненты:
- - Inventory Component - добавляет пешке инвентарь. Отвечает за возможность хранить и пользоваться подбираемыми объектами.
- - Param Component - добавляет пешке изменяемые характеристики. Отвечает за возможность повышать уровень и улучшать характеристики.
- - Combat Component - добавляет пешке возможность атакавать.
- Item - компонента (объект) отвечающая за предметы с которыми можно взаимодействовать. Содержит компоненту:
- - Interact Component - предоставляющая интерфейс для взаимодействия.
