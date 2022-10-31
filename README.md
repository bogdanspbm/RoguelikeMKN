# RoguelikeMKN
Roguelike — это довольно популярный жанр компьютерных игр, назван в честь игры Rogue, 1980 года выхода.
## Общие сведения о системе
### Жанр, платформа, требования

Жанр игры: Roguelike  

Количество игроков: однопользовательская локальная игра  

Операционная система: Windows 10/ Ubuntu Unix  

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
- Бот - «робот», неодушевленный участник игры под управлением компьютера
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
##### Бот:
![rougelike_automat drawio (2)](https://user-images.githubusercontent.com/55112338/199109875-ec18ce6a-d745-4f0a-b0a8-b95a200dc1c7.png)
##### Предмет:
![rougelike_automat drawio (3)](https://user-images.githubusercontent.com/55112338/199109853-0ef32496-1419-486f-8fc8-94c7b28b327d.png)
#### Алгоритмы
##### Генерация мира
![Alt text](images/perlin_noise.png?raw=true "Title")  
Для генерации мира, будет использоваться алгоритм построенный на [Perlin Noise](https://medium.com/nerd-for-tech/generating-digital-worlds-using-perlin-noise-5d11237c29e9). 

Мир генерируется из тайлов. Каждый тайл содержи различный тип, проходимый и не проходимый. Примером непроходимого тайла может быть водоем. Текстуры шума перлина, различного разрешения будут использоваться для генерации водоемов, биомов (для определения текстуры тайла), расположения объектов и так далее.

## Роли и случаи использования
### Случаи использования
![Alt text](images/roles.png?raw=true "Title") 
#### Пользовательский вход в игру
Краткое описание: пользователь желает войти в игру.  
Заинтересованность людей и их требования: игрок, работоспособное приложение, исправный компьютер с девайсами.  
Предусловия: игрок желает зайти в игру, имея работоспособный компьютер.  
Постусловия: пользователь смог войти в игру.   
1) Пользователь запускает игру.
2) Приложение инициализирует запуск игры.
3) Пользователю предоставляется на дисплее главное меню игры (GUI интерфейс) со следующим функциональностью:
    - Играть
    - Глосарий
    - Настройки
    - Выход

Расширения:  
1) Игра позволяет выйти в главное меню 3. в любой момент.
2) Игра может предварительно завершится в случае обесточивания компьютера или полном разряжении батареи. 

Открытые вопросы:  
1) В случае непредвиденного завершения, после ребута, игра должна локально сохранить хоть какое-то состояние, для восстановлении статистики игрока.
#### Пользовательский выход из игры
Краткое описание: пользователь желает выйти из игры.
Заинтересованность людей и их требования: игрок, работоспособное приложение, исправный компьютер с девайсами.
Предусловия: прецедент *«Пользовательский вход в игру»*.
Постусловия: пользователь смог успешно выйти из игры. 
1) Пользователь в главном меню выбирает «Выход».
2) Система инициализирует выход из игры, сохраняет статистику, закрывает GUI.

Расширения:  
1) Игра позволяет выйти в главное меню в любой момент.
2) Игра может предварительно завершится в случае обесточивания компьютера или полном разряжении батареи. 

Открытые вопросы:  
1) В случае непредвиденного завершения, после ребута, игра должна локально сохранить хоть какое-то состояние, для восстановлении статистики игрока.
#### Пользовательская инициализация игры
Краткое описание: пользователь желает запустить игровой процесс.
Заинтересованность людей и их требования: игрок, работоспособное приложение, исправный компьютер с девайсами.
Предусловия: прецедент *«Пользовательский вход в игру»*.
Постусловия: пользователь смог запустить игровой процесс.
1) Пользователь в главном меню выбирает раздел «Играть».
2) Система заменят экран главного меню на экран игрового поля. Она на карте расставляет: противников, предметы, персонажа игрока. Более того происходит генерация ландшафта. Уведомляет с помощью сообщения игрока о том, что игра началась.


Альтернативы:
    <dd>2.1. Противники, предметы и персонаж в случайном порядке расставляются на карте.</dd>
    <dd>2.2. Количество противников и предметов создается в заданном диапазоне.</dd>

Расширения:  
1) Игра позволяет выйти в главное меню в любой момент.
2) Игра может предварительно завершится в случае обесточивания компьютера или полном разряжении батареи. 

Открытые вопросы:  
1) В случае непредвиденного завершения, после ребута, игра должна локально сохранить хоть какое-то состояние, для восстановлении статистики игрока.

#### Управление персонажем
Краткое описание: пользователь хочет перемещать своего персонажа по игровой карте.
Заинтересованность людей и их требования: игрок, работоспособное приложение, исправный компьютер с девайсами.
Предусловия: прецедент *«Пользовательская инициализация игры»*.
Постусловия: пользователь смог успешно управлять персонажем.
1) Пользователю предоставляется управление персонажем с помощью клавиатуры, а именно однократным нажатием на клавишу: «W»(вверх относительно центра дисплея), «S»(вниз), «D»(вправо), «A»(влево).
2) Игра реагирует на нажатие любой из перечисленных выше клавиш и перемещает на одно игровое поле персонажа в соответствующем направлении. 

Альтернативы:
    <ol>1.1. Пользователь нажимает и удерживает палец на клавише.
    <dd>1.2. Персонаж начинается перемещается по направлению, заданному нажатой клавишей.</dd>
		<ol>1.2.1. Пользователь отпускает клавишу.
        		<ol>1.2.1.1.  Система перестает перемещать персонажа по игровой карте.</ol>
			</ol>
        	<ol>1.2.2. Пользователь не отпускает клавишу перемещения.
           		<ol>1.2.2.1. Система перестает перемещать персонажа по игровой карте, так как предмет, ландшафтный элемент или граница карты не позволяет это сделать.</ol>
			</ol>
    2.1. Если поле, которое должен занять персонаж в результате перемещения, является недоступным для персонажа (предмет, противник, граница карты, ландшафтные предметы), то он попросту не совершает перемещение.
    </ol>
    

Расширения:  
1) Игра позволяет выйти в главное меню в любой момент.
2) Игра может предварительно завершится в случае обесточивания компьютера или полном разряжении батареи. 

Открытые вопросы:  
1) В случае непредвиденного завершения, после ребута, игра должна локально сохранить хоть какое-то состояние, для восстановлении статистики игрока.
#### Персонаж атакует противника
Краткое описание: пользователь желает атаковать своим персонажем противника.
Заинтересованность людей и их требования: игрок, работоспособное приложение, исправный компьютер с девайсами.
Предусловия: прецедент *«Управление персонажем»*.
Постусловия: пользователь смог атаковать противника с помощью своего персонажа.
1) Пользователь перемещает своего персонажа по карте с помощью управления с клавиатуры, ему на пути встречается враг.
2) Когда персонаж попадает в заданный активный радиус противника, то противник начинает двигаться навстречу пользователю.
3) Пользователь направляет персонажа на врага.
4) Враг попал в радиус атаки персонажа. 
5) С помощью ЛКМ пользователь совершает атаку по врагу.
6) Значение здоровья противника уменьшается на одну условную единицу.

Альтернативы:
    <ol>3.1. Пользователь может направить своего персонажа в противоположную сторону от  приближающегося противника.
        <ol>3.1.1. Если скорость персонажа больше скорости бота, то по итогу выхода персонажа из активного радиуса врага, он уснет.</ol>
        <ol>3.1.2. В противном случае враг атакует персонажа, в следствии чего он потеряет условную единицу здоровья.</ol>
	</ol>
        
   <ol>5.1. Персонаж пользователя не атакует бота.
	    <ol>5.1.1. Если персонаж вне радиуса атаки врага, никто не получает урона.
	    <dd>5.1.2.  Если персонаж попал в радиус атаки противника, то враг атакует его.</dd>
		<ol>5.1.2.1. Если у персонажа перед атакой более одной единицы здоровья, то после успешной атаки он теряет одну условную единицу здоровья.</ol>
		<ol>5.1.2.2. В противном случае персонаж пользователя умирает (прецедент <i>«Смерть персонажа»</i>).</ol>
		</ol>
		</ol>
		
   <ol>6.1. После совершения атаки, враг остался с нулевым значением здоровья. Бот становится неактивным и через заданное время исчезает с игрового поля.</ol>

Расширения:  
1) Игра позволяет выйти в главное меню в любой момент.
2) Игра может предварительно завершится в случае обесточивания компьютера или полном разряжении батареи. 

Открытые вопросы:  
1) В случае непредвиденного завершения, после ребута, игра должна локально сохранить хоть какое-то состояние, для восстановлении статистики игрока.
#### Смерть персонажа
Краткое описание: персонажа пользователя умирает.
Заинтересованность людей и их требования: игрок, работоспособное приложение, исправный компьютер с девайсами.
Предусловия: прецедент *«Персонаж атакует противника»*.
Постусловия: игра закончилась. 
1) Противник наносит урон персонажу .
2) Персонаж теряет последнюю условную единицу здоровья. 
3) Игра отключает возможность управления персонажа игроком. После чего персонаж пропадает с поля. Игра уведомляет пользователя о том, что персонаж умер и игра закончилась.  

Расширения:  
1) Игра позволяет выйти в главное меню в любой момент.
2) Игра может предварительно завершится в случае обесточивания компьютера или полном разряжении батареи. 

Открытые вопросы:  
1) В случае непредвиденного завершения, после ребута, игра должна локально сохранить хоть какое-то состояние, для восстановлении статистики игрока.
#### Подбор предмета
Краткое описание: пользователь желает подобрать с помощью персонажа предмет.
Заинтересованность людей и их требования: игрок, работоспособное приложение, исправный компьютер с девайсами.
Предусловия: прецедент *«Управление персонажем»*.
Постусловия: Пользователь смог подобрать предмет с помощью персонажа.
1) Пользователь перемещает вплотную к предмету персонажа.
2) Предмет начинает светится. Игра с помощью сообщения уведомляет пользователя о том, что он может его подобрать с помощью клавиши «E».
3) Пользователь нажимает клавишу «Е», после чего предмет помещается в активный инвентарь.
4) Предмет исчезает с игрового поля.

Альтернативы: 
    <ol>3.1. Если активный инвентарь заполнен полностью, то предмет попадает в рюкзак.
    	<ol>3.1.1. Пользователь может поменять предмет из инвентаря на предмет из рюкзака.</ol>
    <dd>3.2. Заполнен как активный инвентарь, так и рюкзак.<dd>
        	<ol>3.2.1. Игра уведомляет о том, что невозможно подобрать предмет из-за нехватки места в рюкзаке.
            	<ol>3.2.1.1. Пользователь заходит в раздел рюкзак,нажимая клавишу «B», выбирает мышкой (ПКМ) предмет, который хочет вынуть из рюкзака. Нажимает на клавишу «F».</ol>
            	<ol>3.2.1.2. Предмет появляется на соседнем игровом поле от персонажа, таким образом, что он не перекрывает новый предмет.</ol>
            	<ol>3.2.1.3. Пользователь нажимает клавишу «Е».</ol>
            	<ol>3.2.1.4. Желаемый предмет исчезает с игрового поля и «перемещается в рюкзак».</ol>
		</ol>
		</ol>

Расширения:  
1) Игра позволяет выйти в главное меню в любой момент.
2) Игра может предварительно завершится в случае обесточивания компьютера или полном разряжении батареи. 

Открытые вопросы:  
1) В случае непредвиденного завершения, после ребута, игра должна локально сохранить хоть какое-то состояние, для восстановлении статистики игрока.
#### Замена активного предмета на вещь из рюкзака
Краткое описание: пользователь желает изменить активный предмет персонажа на предмет из его рюкзака.
Заинтересованность людей и их требования: игрок, работоспособное приложение, исправный компьютер с девайсами.
Предусловия: прецедент *«Подбор предмета»*.
Постусловия: пользователь смог успешно заменил предмет.
1) Пользователь нажимает мышкой (ПКМ) на предмет, который хочет заменить, в активном инвентаре.
2) В активном инвентаре предмет начинает светиться, символизируя таким образом, что он выбран.
3) Затем пользователь нажимает клавишу «В».
4) На экране появляется раздел рюкзака.
5) Игрок выбирает предмет (ПКМ), на который хочет заменить выбранную ранее вещь.
6) Выбранная вещь начинает светиться в разделе рюкзака, символизируя то, что она выбрана.
7) Пользователь нажимает клавишу «E».
8) Раздел рюкзака пропадает с экрана. Желаемый предмет появляется на месте старого в поле активного инвентаря.

Альтернативы:
    <dd>7.1. Пользователь может захотеть изменить свой выбор. Тогда ему достаточно просто выбрать (ПКМ) другой предмет.</dd>
    <dd>7.2. Предмет аналогично начнет светиться.</dd>
    <dd>7.3. Пoльзователь нажимает клавишу «E».</dd>
    <dd>7.4. Раздел рюкзака пропадает с экрана. Желаемый предмет появляется на месте старого в поле активного инвентаря.</dd>

Расширения:  
1) Игра позволяет выйти в главное меню в любой момент.
2) Игра может предварительно завершится в случае обесточивания компьютера или полном разряжении батареи. 

Открытые вопросы:  
1) В случае непредвиденного завершения, после ребута, игра должна локально сохранить хоть какое-то состояние, для восстановлении статистики игрока.
#### Выпадение лута и начисления опыта
Краткое описание: после смерти противника, на его месте возникает одна условная единица здоровья.
Заинтересованность людей и их требования: игрок, работоспособное приложение, исправный компьютер с девайсами.
Предусловия: прецедент *«Персонаж атакует противника»*.
Постусловия: лут успешно выпал с противника после его кончины.
1) Пользователь нанес критический урон врагу.
2) Враг умирает, то есть исчезает с игрового поля, а на его месте с заданной вероятностью может появиться изображение сердца. 
3) Если пользователь с помощью своего персонажа подберет его, то он может поднять свой уровень здоровья на одну условную единицу. За убийство бота персонаж получает одну условную единицу опыта.

Альтернативы:
    <dd>3.1. Пусть у пользовательского персонажа максимального количество условного запаса здоровья. Пользователь решает подобрать его, нажав клавишу «E».</dd>
    <dd>3.2. Изображение сердечка пропадает с игрового поля, а значение здоровья не изменяется.</dd>

Расширения:  
1) Игра позволяет выйти в главное меню в любой момент.
2) Игра может предварительно завершится в случае обесточивания компьютера или полном разряжении батареи. 

Открытые вопросы:  
1) В случае непредвиденного завершения, после ребута, игра должна локально сохранить хоть какое-то состояние, для восстановлении статистики игрока.
