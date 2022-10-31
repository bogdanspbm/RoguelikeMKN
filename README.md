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

#### Диаграмма концептуальных классов
![Alt text](images/roguelike_classes.png?raw=true "Title")

- Engine - класс отвечающий за хранение игровых сущностей, их генерацию и процессинг между ними. Содержит в себе экземпляр карты уровня, список предметов и список пешек.
- Map - класс отвечающий за игровую карту. Карта будет считываться из некоторого генеруемого файла сохранения. Так как карта состоит из тайлов, карта содержит в себе список всех экземпляров тайлов из которых она состоит.
- Tile - класс олицетворяющий собой единицу карты. Содержит такую информацию как ID, тип тайла (например проходимый, или непроходимый), текстура, а также координата расположения тайла.
- Map Save - класс/структура олицетворяющая собой сохранения карты. Возможно сохранение в файл на устройстве.
- Map Generator - класс отвечающий за генерацию сохранения карты. На выходе выдает экземпляр Map Save.
- Item - класс отвечающий за предмет, с которым можно взаимодействовать. Содержит в себе некоторый ID и количество.
- Controller - абстрактный класс контролера над пешкой. Нужно для того, чтобы позволять одному и тому же контроллеру, управлять различными пешками, используя одно и тоже управление. Например игрок может передвигаться на AWSD персонажем, а потом передать управление пешке "Машина", и управлять этой машиной при помощью тех же клавиш.
- AI Controller - реализация класса Controller, содержащая в себе алгоритмы поведения Behaviour, имитирующие управление пешкой компьютером.
- Player Controller - реализация класса Controller, интерпритирующая команды пользователя в команды, понятные пешке.
- Pawn - абстрактный класс пешки. Может управляться при помощи контролера. Может передвегаться по миру, а также быть уничтожена. По этой причине содержит в себе экземпляр контролера, название, здоровье, а также скорость.
- Bot - реализация пешки, которая управляется при помощи AI Controller.
- Player - реализация пешки, которая управляется при помощи игрока. Содержит в себе дополнительные компоненты, расширяющие возможности пешки, такие как инвентарь.
#### Информационная структура
![Alt text](images/ItemDB.png?raw=true "Title")

Набор всех возможных уникальных предметов доступных в игре будет храниться в файловой базе данных на диске системы и подгружаться в оперативную память при запуске игры. Основные параметры, которые необходимо хранить - ID для того, чтобы находить предмет в базе данных. Max Quantity - чтобы ораничить максимальное переносимое количество предметов. Type - чтобы определить возможность хранить предмет в специальных ячейках для экипировки. Action - класс интерфейса использования предмета.
#### Использование шаблонов
- Паттерн Стратегия. В игре присутствуют различные боты-противники. Различия составляет в их характеристиках. Каждый из них обладает различной логикой поведения. 
- Паттерн Наблюдатель. Контроллер в игре следит за состоянием системы, в качестве реакции на изменение состояние, передает сигнал в соответствующую ему пешку.

#### Интерфейсы
##### Меню
![Alt text](images/menu.png?raw=true "Title")
##### UI
![Alt text](images/ui.png?raw=true "Title")

#### Взаимодействие
Игрок, попадая на игровое поле, может передвигать своего персонажа по доступному ему миру. По всей игровой локации в рандомных местах разбросаны предметы и враги. Причем, встречая на своем пути противников-ботов, он может либо поразить их, либо попытаться убежать от них.

#### Динамика состояний

#### Алгоритмы
##### Генерация мира
![Alt text](images/perlin_noise.png?raw=true "Title")  
Для генерации мира, будет использоваться алгоритм построенный на [Perlin Noise](https://medium.com/nerd-for-tech/generating-digital-worlds-using-perlin-noise-5d11237c29e9). 

Мир генерируется из тайлов. Каждый тайл содержи различный тип, проходимый и не проходимый. Примером непроходимого тайла может быть водоем. Текстуры шума перлина, различного разрешения будут использоваться для генерации водоемов, биомов (для определения текстуры тайла), расположения объектов и так далее.
